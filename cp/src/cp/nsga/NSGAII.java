/**
 * NSGAII.java
 * @author Juan J. Durillo
 * @version 1.0  
 */
package cp.nsga;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import jmetal.core.Algorithm;
import jmetal.core.Operator;
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
import org.springframework.stereotype.Component;

/**
 * This class implements the NSGA-II algorithm.
 */
@Component
public class NSGAII extends Algorithm {

	private static Logger LOGGER = LoggerFactory.getLogger(NSGAII.class.getName());

//	@Autowired
//	@Qualifier(value="TriadDecorator")
//	private Decorator decorator;
	private boolean decorated = false;

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
	 * @throws JMException
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
		populationSize = ((Integer) getInputParameter("populationSize"))
				.intValue();
		maxEvaluations = ((Integer) getInputParameter("maxEvaluations"))
				.intValue();
		indicators = (QualityIndicator) getInputParameter("indicators");

		// Initialize the variables
		population = new SolutionSet(populationSize);
		evaluations = 0;
		requiredEvaluations = 0;

		// Read the operators
		List<Operator> mutationOperators = new ArrayList<Operator>();
		mutationOperators.add(operators_.get("replaceMelody"));
		mutationOperators.add(operators_.get("addRhythm"));
		mutationOperators.add(operators_.get("removeRhythm"));
		mutationOperators.add(operators_.get("oneNoteMutation"));
		mutationOperators.add(operators_.get("articulationMutation"));
		Operator crossoverOperator = operators_.get("crossover");
		Operator selectionOperator = operators_.get("selection");

		// Create the initial solutionSet
		Solution newSolution;
		for (int i = 0; i < populationSize; i++) {
			newSolution = new MusicSolution(problem_);
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
					parents[0] = (Solution) selectionOperator
							.execute(population);
					parents[1] = (Solution) selectionOperator
							.execute(population);
					Solution[] offSpring = (Solution[]) crossoverOperator
							.execute(parents);
					for (Operator operator : mutationOperators) {
						operator.execute(offSpring[0]);
						operator.execute(offSpring[1]);
					}
//					if (decorated) {
//						decorator.decorate(offSpring[0]);
//						decorator.decorate(offSpring[1]);
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
			union = ((SolutionSet) population).union(offspringPopulation);

			// Ranking the union
			Ranking ranking = new Ranking(union);

			int remain = populationSize;
			int index = 0;
			SolutionSet front = null;
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

				remain = 0;
			}

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
		List<Solution> copySolutions = new ArrayList<Solution>();
		Iterator<Solution> iterator = population.iterator();
		while (iterator.hasNext()) {
			Solution solution = (Solution) iterator.next();
			copySolutions.add(solution);
		}
		return copySolutions;
	}
}
