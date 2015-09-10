package cp.nsga.operator.mutation.rhythm;

import java.util.HashMap;
import java.util.logging.Logger;

import jmetal.core.Solution;
import jmetal.util.Configuration;
import jmetal.util.JMException;
import jmetal.util.PseudoRandom;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import cp.generator.MusicProperties;
import cp.model.Motive;
import cp.model.melody.CpMelody;
import cp.nsga.MusicVariable;
import cp.nsga.operator.mutation.AbstractMutation;

@Component(value="addRhythm")
public class AddRhythm extends AbstractMutation {
	
	private static Logger LOGGER = Logger.getLogger(AddRhythm.class.getName());
	
	@Autowired
	private MusicProperties musicProperties;

	@Autowired
	public AddRhythm(HashMap<String, Object> parameters) {
		super(parameters);
	}

	@Override
	public Object execute(Object object) throws JMException {
		Solution solution = (Solution) object;
		Double probability = (Double) getParameter("probabilityAddRhythm");
		if (probability == null) {
			Configuration.logger_.severe("probabilityAddRhythm: probability not " +
			"specified");
			Class cls = java.lang.String.class;
			String name = cls.getName();
			throw new JMException("Exception in " + name + ".execute()");
		}
		doMutation(probability.doubleValue(), solution);
		return solution;
	}

	private void doMutation(double probability, Solution solution) {
		if (PseudoRandom.randDouble() < probability) {
			Motive motive = ((MusicVariable)solution.getDecisionVariables()[0]).getMotive();
			CpMelody mutableMelody = motive.getRandomMutableMelody();
			mutableMelody.addRandomRhythmNote(musicProperties.getMinimumLength());
			LOGGER.info("rhythm note added");
		} 
	}

}
