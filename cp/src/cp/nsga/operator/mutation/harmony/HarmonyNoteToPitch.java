package cp.nsga.operator.mutation.harmony;

import java.util.HashMap;
import java.util.List;
import java.util.Optional;

import jmetal.core.Solution;
import jmetal.util.Configuration;
import jmetal.util.JMException;
import jmetal.util.PseudoRandom;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import cp.generator.MusicProperties;
import cp.model.Motive;
import cp.model.harmony.Harmony;
import cp.model.melody.HarmonicMelody;
import cp.nsga.MusicVariable;
import cp.nsga.operator.mutation.AbstractMutation;

@Component(value="harmonyNoteToPitch")
public class HarmonyNoteToPitch extends AbstractMutation{

	@Autowired
	public HarmonyNoteToPitch(HashMap<String, Object> parameters) {
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
			mutateHarmonyNoteToRandomPitch(motive.getHarmonies());
			mutateHarmonyNoteToPreviousPitch(motive.getHarmonies());
			mutateHarmonyNoteToNextPitch(motive.getHarmonies());
		} 
	}

	private void mutateHarmonyNoteToRandomPitch(List<Harmony> harmonies) {
		Harmony harmony = harmonyMutation.randomHarmony(harmonies);
		HarmonicMelody harmonicMelody = harmonicMelodyMutation.randomHarmonicMelody(harmony);
		harmonicMelody.mutateHarmonyNoteToRandomPitch(musicProperties.getScale());
	}
	
	private void mutateHarmonyNoteToPreviousPitch(List<Harmony> harmonies) {
		Harmony harmony = harmonyMutation.randomHarmony(harmonies);
		HarmonicMelody harmonicMelody = harmonicMelodyMutation.randomHarmonicMelody(harmony);
		harmonicMelody.mutateHarmonyPreviousNoteToPitch(musicProperties.getScale());
	}
	
	private void mutateHarmonyNoteToNextPitch(List<Harmony> harmonies) {
		Harmony harmony = harmonyMutation.randomHarmony(harmonies);
		HarmonicMelody harmonicMelody = harmonicMelodyMutation.randomHarmonicMelody(harmony);
		harmonicMelody.mutateHarmonyPreviousNoteToPitch(musicProperties.getScale());
	}

	/**
	 * Executes the operation
	 * @param object An object containing a solution to mutate
	 * @return An object containing the mutated solution
	 * @throws JMException 
	 */
	public Object execute(Object object) throws JMException {
		Solution solution = (Solution) object;
		Double probability = (Double) getParameter("probabilityHarmonyNoteToPitch");
		if (probability == null) {
			Configuration.logger_.severe("probabilityHarmonyNoteToPitch: probability not " +
			"specified");
			Class cls = java.lang.String.class;
			String name = cls.getName();
			throw new JMException("Exception in " + name + ".execute()");
		}
		doMutation(probability.doubleValue(), solution);
		return solution;
	} 
}


