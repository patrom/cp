package cp.nsga.operator.mutation.rhythm;

import java.util.HashMap;

import jmetal.core.Solution;
import jmetal.util.JMException;
import jmetal.util.PseudoRandom;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import cp.generator.MusicProperties;
import cp.model.Motive;
import cp.model.melody.CpMelody;
import cp.nsga.MusicVariable;
import cp.nsga.operator.mutation.AbstractMutation;

@Component(value="removeRhythm")
public class RemoveRhythm extends AbstractMutation {
	
	private static Logger LOGGER = LoggerFactory.getLogger(RemoveRhythm.class.getName());
	
	@Autowired
	private MusicProperties musicProperties;

	@Autowired
	public RemoveRhythm(HashMap<String, Object> parameters) {
		super(parameters);
	}

	@Override
	public Object execute(Object object) throws JMException {
		Solution solution = (Solution) object;
		if (PseudoRandom.randDouble() < (double)getParameter("probabilityRemoveRhythm")) {
			Motive motive = ((MusicVariable)solution.getDecisionVariables()[0]).getMotive();
			CpMelody mutableMelody = motive.getRandomRhythmMutableMelody();
			mutableMelody.removeNote();
			LOGGER.info("rhythm note removed");
		} 
		return solution;
	}


}

