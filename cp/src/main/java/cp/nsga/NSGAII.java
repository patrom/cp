package cp.nsga;

import cp.model.Motive;
import cp.model.melody.CpMelody;
import cp.nsga.operator.mutation.MutationOperator;
import cp.nsga.operator.mutation.MutationType;
import cp.nsga.operator.mutation.melody.Mutators;
import cp.nsga.operator.relation.Relation;
import cp.nsga.operator.relation.RelationConfig;
import cp.util.RandomUtil;
import jmetal.core.Algorithm;
import jmetal.core.Problem;
import jmetal.core.Solution;
import jmetal.core.SolutionSet;
import jmetal.qualityIndicator.QualityIndicator;
import jmetal.util.Distance;
import jmetal.util.JMException;
import jmetal.util.Ranking;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * This class implements the NSGA-II algorithm.
 */
@Component
public class NSGAII extends Algorithm {

	private static final Logger LOGGER = LoggerFactory.getLogger(NSGAII.class.getName());

	@Autowired
	private RelationConfig operatorConfig;
	@Autowired
	private Mutators mutators;
	@Value("${composition.voices:12}")
	protected int check12Tone;

	/**
	 * Constructor
	 * 
	 * @param problem
	 *            Problem to solve
	 */
	@Autowired
	public NSGAII(Problem problem) {
		super(problem);
	}

	/**
	 * Runs the NSGA-II algorithm.
	 * 
	 * @return a <code>SolutionSet</code> that is a set of non dominated
	 *         solutions as a result of the algorithm execution
	 */
	public SolutionSet execute() throws JMException, ClassNotFoundException {
		int populationSize;
		int maxEvaluations;
		int evaluations;

		QualityIndicator indicators; // QualityIndicator object
		int requiredEvaluations; // Use in the example of use of the
		// indicators object (see below)

		SolutionSet population;
		SolutionSet offspringPopulation;
		SolutionSet union;

		Distance distance = new Distance();

		// Read the parameters
		populationSize = (Integer) getInputParameter("populationSize");
		maxEvaluations = (Integer) getInputParameter("maxEvaluations");
		indicators = (QualityIndicator) getInputParameter("indicators");

		// Initialize the variables
		population = new SolutionSet(populationSize);
		evaluations = 0;
		requiredEvaluations = 0;

		// Read the operators
		jmetal.core.Operator crossoverOperator = operators_.get("crossover");
		jmetal.core.Operator selectionOperator = operators_.get("selection");

		// Create the initial solutionSet
		Solution newSolution;
		for (int i = 0; i < populationSize; i++) {
			newSolution = new MusicSolution(problem_);
			for (Relation relation : operatorConfig.getRelations()) {
				relation.execute(newSolution);

			}
			problem_.evaluate(newSolution);
			problem_.evaluateConstraints(newSolution);
			evaluations++;
			population.add(newSolution);
		}

		int changeCount = 0;

		// Generations ...
		// while ((evaluations < maxEvaluations) && changeCount < 10 *
		// populationSize) {
		while ((evaluations < maxEvaluations)) {
			List<Solution> copySolutions = copyList(population);
			// Create the offSpring solutionSet
			offspringPopulation = new SolutionSet(populationSize);
			Solution[] parents = new Solution[2];
			for (int i = 0; i < (populationSize / 2); i++) {
				if (evaluations < maxEvaluations) {
					// obtain parents
					parents[0] = (Solution) selectionOperator.execute(population);
					parents[1] = (Solution) selectionOperator.execute(population);
					Solution[] offSpring = (Solution[]) crossoverOperator.execute(parents);
					mutateOffspring(offSpring[0]);
					mutateOffspring(offSpring[1]);
//					for (Relation relation : operatorConfig.getRelations()) {
//						relation.execute(offSpring[0]);
//						relation.execute(offSpring[1]);
//					}
					problem_.evaluate(offSpring[0]);
					problem_.evaluateConstraints(offSpring[0]);
					problem_.evaluate(offSpring[1]);
					problem_.evaluateConstraints(offSpring[1]);
					offspringPopulation.add(offSpring[0]);
					offspringPopulation.add(offSpring[1]);
					evaluations += 2;
				}
			}

			// Create the solutionSet union of solutionSet and offSpring
			union = population.union(offspringPopulation);

			// Ranking the union
			Ranking ranking = new Ranking(union);

			int remain = populationSize;
			int index = 0;
			SolutionSet front;
			population.clear();

			// Obtain the next front
			front = ranking.getSubfront(index);

			while ((remain > 0) && (remain >= front.size())) {
				// Assign crowding distance to individuals
				distance.crowdingDistanceAssignment(front,
						problem_.getNumberOfObjectives());
				// Add the individuals of this front
				for (int k = 0; k < front.size(); k++) {
					population.add(front.get(k));
				}

				// Decrement remain
				remain = remain - front.size();

				// Obtain the next front
				index++;
				if (remain > 0) {
					front = ranking.getSubfront(index);
				}
			}

			// Remain is less than front(index).size, insert only the best one
			if (remain > 0) { // front contains individuals to insert
				distance.crowdingDistanceAssignment(front,
						problem_.getNumberOfObjectives());
				front.sort(new jmetal.util.comparators.CrowdingComparator());
				for (int k = 0; k < remain; k++) {
					population.add(front.get(k));
				}

			}

//			//log solution set
//			Iterator<Solution> solutionIterator = population.iterator();
//			int i = 1;
//			while (solutionIterator.hasNext()) {
//				Solution solution = solutionIterator.next();
//				Motive solutionMotive = ((MusicVariable) solution.getDecisionVariables()[0]).getMotive();
//				for (MelodyBlock melodyBlock : solutionMotive.getMelodyBlocks()) {
//					LOGGER.info("Melody sol: " + melodyBlock.getMelodyBlockNotesWithRests());
//				}
//			}



			// This piece of code shows how to use the indicator object into the
			// code
			// of NSGA-II. In particular, it finds the number of evaluations
			// required
			// by the algorithm to obtain a Pareto front with a hypervolume
			// higher
			// than the hypervolume of the true Pareto front.
			if ((indicators != null) && (requiredEvaluations == 0)) {
				double HV = indicators.getHypervolume(population);
				double p = indicators.getTrueParetoFrontHypervolume();
				if (HV >= (0.98 * indicators.getTrueParetoFrontHypervolume())) {
					requiredEvaluations = evaluations;
				}
			}
			// check changes pareto front
			// SolutionSet paretoFront = ranking.getSubfront(0);
			List<Solution> frontSolutions = copyList(population);
			boolean changed = hasPopulationChanged(copySolutions,
					frontSolutions);
			LOGGER.debug("Population changed: " + changed + ", evaluations: "
					+ evaluations + ", change count:" + changeCount);
			if (changed) {
				changeCount = 0;
			} else {
				changeCount++;
			}
		}

		// Return as output parameter the required evaluations
		setOutputParameter("evaluations", requiredEvaluations);

		// Return the first non-dominated front
		Ranking ranking = new Ranking(population);
		return ranking.getSubfront(0);
	}

	private void mutateOffspring(Solution solution) {
        CpMelody melody = getMelodyBlock(solution);
        if (melody != null) {
            List<MutationOperator> mutationOperators = null;
            if (check12Tone == 12) {
            Motive motive = ((MusicVariable) solution.getDecisionVariables()[0]).getMotive();
            List<MutationOperator> twelveToneMutationOperators = mutators.getMutationOperators(MutationType.TWELVE_TONE);
            MutationOperator operator = RandomUtil.getRandomFromList(twelveToneMutationOperators);
            operator.execute(motive);
            mutationOperators = mutators.timbreMutationOperators();
            } else {
                mutationOperators = mutators.getMutationOperators(melody.getMutationType());
            }
            MutationOperator operator = RandomUtil.getRandomFromList(mutationOperators);
            operator.execute(melody);
        }
    }

	private CpMelody getMelodyBlock(Solution solution) {
		Motive motive = ((MusicVariable) solution.getDecisionVariables()[0]).getMotive();
		return motive.getRandomMutableMelody();
	}

	private boolean hasPopulationChanged(List<Solution> oldSolutions,
			List<Solution> frontSolutions) {
		for (Solution solution : oldSolutions) {
			if (!frontSolutions.contains(solution)) {
				return true;
			}
		}
		return false;
	}

	private List<Solution> copyList(SolutionSet population) {
		List<Solution> copySolutions = new ArrayList<>();
		Iterator<Solution> iterator = population.iterator();
		while (iterator.hasNext()) {
			Solution solution = iterator.next();
			copySolutions.add(solution);
		}
		return copySolutions;
	}
}
