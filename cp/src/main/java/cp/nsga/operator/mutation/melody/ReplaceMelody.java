package cp.nsga.operator.mutation.melody;

import cp.composition.Composition;
import cp.composition.voice.VoiceConfig;
import cp.generator.pitchclass.PitchClassGenerator;
import cp.model.Motive;
import cp.model.TimeLine;
import cp.model.melody.CpMelody;
import cp.model.melody.MelodyBlock;
import cp.model.note.Note;
import cp.nsga.MusicVariable;
import cp.nsga.operator.mutation.AbstractMutation;
import cp.out.instrument.Articulation;
import cp.out.play.InstrumentConfig;
import jmetal.core.Solution;
import jmetal.util.Configuration;
import jmetal.util.JMException;
import jmetal.util.PseudoRandom;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.Optional;

@Component(value="replaceMelody")
public class ReplaceMelody extends AbstractMutation{

	private static Logger LOGGER = LoggerFactory.getLogger(ReplaceMelody.class);

	@Autowired
	private ReplaceRhythmDependantMelody replaceRhythmDependantMelody;
	@Autowired
	private Composition composition;
	@Autowired
	private InstrumentConfig instrumentConfig;
	@Autowired
	private TimeLine timeLine;

	@Autowired
	public ReplaceMelody(HashMap<String, Object> parameters) {
		super(parameters);
	}

	public void doMutation(double probability, Solution solution) throws JMException {
		if (PseudoRandom.randDouble() < probability) {
			Motive motive = ((MusicVariable)solution.getDecisionVariables()[0]).getMotive();
			MelodyBlock melodyBlock = motive.getRandomMutableMelody();
			Optional<CpMelody> optionalMelody = melodyBlock.getRandomMelody(m -> m.isReplaceable());
			if (optionalMelody.isPresent()) {
				CpMelody melody = optionalMelody.get();

//				final List<Note> notesToRemove = melody.getNotes();
				VoiceConfig voiceConfig = composition.getVoiceConfiguration(melodyBlock.getVoice());

				List<Note> melodyNotes = voiceConfig.getNotes(melody.getBeatGroup());

//				if (timeLine.hasDependantHarmonies(melody.getVoice())) {
//					for (Note note : notesToRemove) {
//                        timeLine.removeDependantHarmonyAtPosition(note.getPosition(), melody.getVoice());
//                    }
//					for (Note note : melodyNotes) {
//                        timeLine.insertDependantHarmonyAtPosition(note.getPosition(), melody.getVoice());
//                    }
//				}

				Articulation articulation = instrumentConfig.getArticuationForVoice(melodyBlock.getVoice());
				melodyNotes.forEach(n -> {
					n.setVoice(melody.getVoice());
					n.setDynamicLevel(voiceConfig.getVolume());
					n.setArticulation(articulation);
					n.setPosition(n.getPosition() + melody.getStart());
				});
				PitchClassGenerator pitchClassGenerator = composition.getRandomPitchClassGenerator(melody.getVoice());
				melodyNotes = pitchClassGenerator.updatePitchClasses(melodyNotes);
				melody.updateNotes(melodyNotes);
//				LOGGER.info("Melody replaced: " + melody.getVoice());

				//Rhythm dependant melodies
//				this.replaceRhythmDependantMelody.setPitchClassGenerator(pitchClassGenerator);
//				List<MelodyBlock> rhythmDependantMelodies =  motive.getMelodyBlocks().stream()
//						.filter(m -> m.isRhythmDependant() && m.getDependingVoice() == melody.getVoice())
//						.collect(toList());
//				replaceRhythmDependantMelody.updateDependantMelodyBlockWithMelody(melody, rhythmDependantMelodies);
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
		doMutation(probability, solution);
		return solution;
	}

}
