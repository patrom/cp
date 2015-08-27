package cp.nsga.operator.crossover;

import java.util.HashMap;

import jmetal.core.Solution;
import jmetal.operators.crossover.Crossover;
import jmetal.util.Configuration;
import jmetal.util.JMException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import cp.nsga.MusicSolution;

@Component(value="crossover")
public class OnePointCrossover extends Crossover {

	@Autowired
	public OnePointCrossover(HashMap<String, Object> parameters) {
		super(parameters);
	}

	/**
	 * Executes the operation
	 * 
	 * @param object
	 *            An object containing an array of two solutions
	 * @return An object containing an array with the offSprings
	 * @throws JMException
	 */
	public Object execute(Object object) throws JMException {
		Solution[] parents = (Solution[]) object;

		Double probability = (Double) getParameter("probabilityCrossover");
		if (parents.length < 2) {
			Configuration.logger_.severe("OnePointCrossover: operator "
					+ "needs two parents");
			Class cls = java.lang.String.class;
			String name = cls.getName();
			throw new JMException("Exception in " + name + ".execute()");
		} else if (probability == null) {
			Configuration.logger_.severe("OnePointCrossover: probability "
					+ "not specified");
			Class cls = java.lang.String.class;
			String name = cls.getName();
			throw new JMException("Exception in " + name + ".execute()");
		}
		Solution[] offSpring;
		offSpring = doCrossover(probability.doubleValue(), parents[0],
				parents[1]);

		// -> Update the offSpring solutions
		for (int i = 0; i < offSpring.length; i++) {
			offSpring[i].setCrowdingDistance(0.0);
			offSpring[i].setRank(0);
		}
		return offSpring;// */
	} // execute

	/**
	 * Perform the crossover operation.
	 * 
	 * @param probability
	 *            Crossover probability
	 * @param parent1
	 *            The first parent
	 * @param parent2
	 *            The second parent
	 * @return An array containing the two offsprings
	 * @throws JMException
	 */
	public Solution[] doCrossover(double probability, Solution parent1,
			Solution parent2) throws JMException {
		Solution[] offSpring = new Solution[2];
		offSpring[0] = new MusicSolution(parent1);
		offSpring[1] = new MusicSolution(parent2);
		return offSpring;
	}

}
