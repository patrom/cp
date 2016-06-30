package cp.nsga.operator.mutation.melody;

import java.util.HashMap;
import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import cp.composition.beat.BeatGroup;
import cp.composition.timesignature.CompositionConfig;
import cp.generator.pitchclass.PitchClassGenerator;
import cp.model.Motive;
import cp.model.melody.CpMelody;
import cp.model.melody.MelodyBlock;
import cp.model.note.Note;
import cp.nsga.MusicVariable;
import cp.nsga.operator.mutation.AbstractMutation;
import jmetal.core.Solution;
import jmetal.util.Configuration;
import jmetal.util.JMException;
import jmetal.util.PseudoRandom;

@Component(value="replaceMelody")
public class ReplaceMelody extends AbstractMutation{

	private static Logger LOGGER = LoggerFactory.getLogger(ReplaceMelody.class);

	private PitchClassGenerator pitchClassGenerator;
	
	public void setPitchClassGenerator(PitchClassGenerator pitchClassGenerator) {
		this.pitchClassGenerator = pitchClassGenerator;
	}
	
	@Autowired
	private CompositionConfig compositionConfig;
	
	@Autowired
	public ReplaceMelody(HashMap<String, Object> parameters) {
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
			MelodyBlock melodyBlock = motive.getRandomMutableMelody();
			Optional<CpMelody> optionalMelody = melodyBlock.getRandomMelody(m -> m.isReplaceable());
			if (optionalMelody.isPresent()) {
				CpMelody melody = optionalMelody.get();
				BeatGroup beatGroup = melody.getBeatGroup();
				List<Note> melodyNotes;
				if (compositionConfig.randomCombination()) {
					melodyNotes = beatGroup.getNotesRandom();
				} else {
					melodyNotes = beatGroup.getNotes();
				}
				melodyNotes.forEach(n -> {
					n.setVoice(melody.getVoice());
					n.setPosition(n.getPosition() + melody.getStart());
				});
				melodyNotes = pitchClassGenerator.updatePitchClasses(melodyNotes);
				melody.updateNotes(melodyNotes);
//				LOGGER.info("Melody replaced");
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
		Double probability = (Double) getParameter("probabilityReplaceMelody");
		if (probability == null) {
			Configuration.logger_.severe("probabilityReplaceMelody: probability not " +
			"specified");
			Class cls = java.lang.String.class;
			String name = cls.getName();
			throw new JMException("Exception in " + name + ".execute()");
		}
		doMutation(probability.doubleValue(), solution);
		return solution;
	} 

}
