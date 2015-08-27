package cp.nsga.operator.mutation.harmony;

import java.util.HashMap;
import java.util.List;

import jmetal.core.Solution;
import jmetal.util.Configuration;
import jmetal.util.JMException;
import jmetal.util.PseudoRandom;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import cp.model.Motive;
import cp.model.harmony.Harmony;
import cp.model.melody.HarmonicMelody;
import cp.model.note.Note;
import cp.nsga.MusicVariable;
import cp.nsga.operator.mutation.AbstractMutation;

@Component(value="swapHarmonyNotes")
public class SwapHarmonyNotes extends AbstractMutation{
	
	@Autowired
	public SwapHarmonyNotes(HashMap<String, Object> parameters) {
		super(parameters);
	}
	
	/**
	 * Perform the mutation operation
	 * @param probability Mutation probability
	 * @param solution The solution to mutate
	 * @throws JMException
	 */
	public void doMutation(double probability, Solution solution) throws JMException {
		if (PseudoRandom.randDouble() < probability) {
			Motive motive = ((MusicVariable)solution.getDecisionVariables()[0]).getMotive();
			swapHarmonyNotes(motive.getHarmonies());
		} 
	}

	private void swapHarmonyNotes(List<Harmony> harmonies) {
		Harmony harmony = harmonyMutation.randomHarmony(harmonies);
		HarmonicMelody harmonicMelody = harmonicMelodyMutation.randomHarmonicMelody(harmony);
		HarmonicMelody switchHarmonicMelody = harmonicMelodyMutation.randomHarmonicMelody(harmony);
		if (harmonicMelody.getVoice() != switchHarmonicMelody.getVoice()) {
			Note harmonyNote = harmonicMelody.getHarmonyNote();
			Note switchHarmonyNote = switchHarmonicMelody.getHarmonyNote();
			if (!harmonyNote.equals(switchHarmonyNote)) {
				int harmonyPitchClass = harmonyNote.getPitchClass();
				int switchHarmonyPitchClass = switchHarmonyNote.getPitchClass();
				harmonicMelody.updateMelodyNotes(harmonyPitchClass, switchHarmonyPitchClass);
				harmonyNote.setPitchClass(switchHarmonyPitchClass);
				switchHarmonicMelody.updateMelodyNotes(switchHarmonyPitchClass, harmonyPitchClass);
				switchHarmonyNote.setPitchClass(harmonyPitchClass);
				LOGGER.fine("swap");
			}
		}
	}

	/**
	 * Executes the operation
	 * @param object An object containing a solution to mutate
	 * @return An object containing the mutated solution
	 * @throws JMException 
	 */
	public Object execute(Object object) throws JMException {
		Solution solution = (Solution) object;
		Double probability = (Double) getParameter("probabilitySwap");
		if (probability == null) {
			Configuration.logger_.severe("probabilitySwap: probability not " +
			"specified");
			Class cls = java.lang.String.class;
			String name = cls.getName();
			throw new JMException("Exception in " + name + ".execute()");
		}
		doMutation(probability.doubleValue(), solution);
		return solution;
	} 
}
