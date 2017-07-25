package cp.nsga;


import cp.evaluation.FitnessEvaluationTemplate;
import cp.evaluation.FitnessObjectiveValues;
import cp.generator.MusicProperties;
import jmetal.core.Problem;
import jmetal.core.Solution;
import jmetal.core.Variable;
import jmetal.util.JMException;
import net.sourceforge.jFuzzyLogic.membership.MembershipFunction;
import net.sourceforge.jFuzzyLogic.membership.MembershipFunctionTriangular;
import net.sourceforge.jFuzzyLogic.membership.Value;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class MusicProblem extends Problem {

	private static Logger LOGGER = LoggerFactory.getLogger(MusicProblem.class.getName());
	
	@Autowired
	private FitnessEvaluationTemplate fitnessEvaluationTemplate;

	private final MembershipFunction melodyMembershipFunction;
	private final MembershipFunction harmonyMembershipFunction;
	
	@Autowired
	public MusicProblem(MusicProperties properties) throws ClassNotFoundException {
		numberOfVariables_ = 1;
		numberOfObjectives_ = 7;
		numberOfConstraints_ = 0;
		problemName_ = "MusicProblem";

		upperLimit_ = new double[numberOfVariables_];
		lowerLimit_ = new double[numberOfVariables_];
		
		this.melodyMembershipFunction = new MembershipFunctionTriangular(
				new Value(0.0), new Value(properties.getMelodyConsDissValue()),
				new Value(1.0));
		this.harmonyMembershipFunction = new MembershipFunctionTriangular(
				new Value(0.0),
				new Value(properties.getHarmonyConsDissValue()), new Value(1.0));
	}

	@Override
	public void evaluate(Solution solution) throws JMException {
		Variable[] variables = solution.getDecisionVariables();
		FitnessObjectiveValues objectives = fitnessEvaluationTemplate.evaluate(((MusicVariable) variables[0]).getMotive());

		double harmonyObjective = 1 - harmonyMembershipFunction.membership(1 - objectives.getHarmony());
//		double harmonyObjective = 1 - objectives.getHarmony();
		solution.setObjective(0, harmonyObjective);// harmony

		double melodyObjective = 1 - melodyMembershipFunction.membership(1 - objectives.getMelody());
//		double melodyObjective = 1 - objectives.getMelody();
		solution.setObjective(1, melodyObjective);// melody

		double rhythmObjective = 1 - objectives.getRhythm();
		solution.setObjective(2, rhythmObjective);

		double meterObjective = 1 - objectives.getMeter();
		solution.setObjective(3, meterObjective);

//		double tonality = 1 - objectives.getTonality();
//		solution.setObjective(6, tonality);

		solution.setObjective(4, objectives.getResolution());

		double register = objectives.getRegister();

		solution.setObjective(5, register);
		if (objectives.getVoiceleading() > 1 && objectives.getVoiceleading() < 4) {
			solution.setObjective(1, 0);
		} else {
			solution.setObjective(6, objectives.getVoiceleading());
		}
		
		// //constraints
		// objectives[5] = lowestIntervalRegisterValue;
		// objectives[6] = repetitionsPitchesMean; //only for small motives (5 -
		// 10 notes)
		// objectives[7] = repetitionsrhythmsMean; //only for small motives (5 -
		// 10 notes)

		MusicSolution musicSolution = (MusicSolution) solution;
		musicSolution.setHarmony(harmonyObjective);
		musicSolution.setMelody(melodyObjective);
//		musicSolution.setTonality(tonality);
		musicSolution.setRhythm(rhythmObjective);
		musicSolution.setMeter(meterObjective);
		musicSolution.setResolution(objectives.getResolution());
		musicSolution.setRegister(register);
		musicSolution.setVoiceLeading(objectives.getVoiceleading());

		// musicSolution.setConstraintLowestInterval(objectives[5]);
		// musicSolution.setConstraintRhythm(objectives[6]);
		// musicSolution.setConstraintRepetition(objectives[7]);

	}

	@Override
	public void evaluateConstraints(Solution solution) throws JMException {
		solution.setNumberOfViolatedConstraint(0);
		solution.setOverallConstraintViolation(0);
		// MusicSolution musicSolution = (MusicSolution) solution;
		// int violation = 0 ;
		// double total = 0.0;
		// harmony
		// if (musicSolution.getObjective(0) > 0.2) {
		// total += solution.getObjective(0);
		// violation++ ;
		// }
		// //lowest interval
		// if (musicSolution.getConstraintLowestInterval() > 0.0) {
		// total += musicSolution.getConstraintLowestInterval();
		// violation++ ;
		// }
		// //rhythm
		// if (musicSolution.getConstraintRhythm() > 0.7) {
		// total += musicSolution.getConstraintRhythm();
		// violation++ ;
		// }
		// //repetition
		// if (musicSolution.getConstraintRepetition() > 0.7) {
		// total += musicSolution.getConstraintRepetition();
		// violation++ ;
		// }
		//
		// solution.setOverallConstraintViolation(total);
		// solution.setNumberOfViolatedConstraint(violation) ;
	}

}
