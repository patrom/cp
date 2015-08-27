package cp.nsga.operator.mutation.melody;

import java.util.HashMap;
import java.util.List;
import java.util.Optional;
import java.util.logging.Logger;

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
import cp.util.RandomUtil;

@Component(value="melodyNoteToHarmonyNote")
public class MelodyNoteToHarmonyNote extends AbstractMutation{
	
	@Autowired
	public MelodyNoteToHarmonyNote(HashMap<String, Object> parameters) {
		super(parameters);
	}

	private static Logger LOGGER = Logger.getLogger(OneNoteMutation.class.getName());

	/**
	 * Perform the mutation operation
	 * @param probability Mutation probability
	 * @param solution The solution to mutate
	 * @throws JMException
	 */
	public void doMutation(double probability, Solution solution) throws JMException {
		if (PseudoRandom.randDouble() < probability) {
			Motive motive = ((MusicVariable)solution.getDecisionVariables()[0]).getMotive();
			mutateMelodyNoteToHarmonyNote(motive.getHarmonies());
			LOGGER.fine("one note mutated");
		} 
	}

	protected void mutateMelodyNoteToHarmonyNote(List<Harmony> harmonies) {
		Harmony harmony = harmonies.get(RandomUtil.random(harmonies.size()));
		Optional<HarmonicMelody> harmonicMelody = harmonicMelodyMutation.randomHarmonicMelodyWithMultipleNotes(harmony);
		if (harmonicMelody.isPresent()) {
			Note harmonyNote = RandomUtil.getRandomFromList(harmony.getNotes());
			harmonicMelody.get().mutateMelodyNoteToHarmonyNote(harmonyNote.getPitchClass());
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
		Double probability = (Double) getParameter("probabilityMelodyNoteToHarmonyNote");
		if (probability == null) {
			Configuration.logger_.severe("probabilityMelodyNoteToHarmonyNote: probability not " +
			"specified");
			Class cls = java.lang.String.class;
			String name = cls.getName();
			throw new JMException("Exception in " + name + ".execute()");
		}
		doMutation(probability.doubleValue(), solution);
		return solution;
	} 

}

