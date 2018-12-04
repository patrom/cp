package cp.evaluation;

import cp.composition.Composition;
import cp.composition.voice.Voice;
import cp.config.InstrumentConfig;
import cp.config.VoiceConfig;
import cp.model.Motive;
import cp.model.TimeLine;
import cp.model.harmony.CpHarmony;
import cp.model.harmony.HarmonyExtractor;
import cp.model.melody.MelodyBlock;
import cp.model.note.Note;
import cp.model.rhythm.RhythmWeight;
import cp.model.texture.Texture;
import cp.objective.Objective;
import cp.out.instrument.Instrument;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

import static java.util.stream.Collectors.toList;

@Component
public class FitnessEvaluationTemplate {

	private static final Logger LOGGER = LoggerFactory.getLogger(FitnessEvaluationTemplate.class.getName());

	@Autowired
	private Objective harmonicObjective;
	@Autowired
	private Objective harmonicResolutionObjective;
	@Autowired
	private Objective melodicObjective;
	@Autowired
	private Objective voiceLeadingObjective;
	@Autowired
	private Objective tonalityObjective;
	@Autowired
	private Objective rhythmObjective;
	@Autowired
	private Objective registerObjective;
	@Autowired
	private Objective meterObjective;
	@Autowired
	private Objective melodicHarmonicObjective;
    @Autowired
	private Objective transformationObjective;


	@Autowired
	private RhythmWeight rhythmWeight;
	@Autowired
	private InstrumentConfig instrumentConfig;
	@Autowired
	private VoiceConfig voiceConfig;
	
	@Autowired
	private HarmonyExtractor harmonyExtractor;
	@Autowired
	private Composition composition;
	@Autowired
	private TimeLine timeLine;
    @Autowired
    private Texture texture;

	public FitnessObjectiveValues evaluate(Motive motive) {
		List<MelodyBlock> melodies = motive.getMelodyBlocks();
//        melodies.stream().flatMap(melodyBlock -> melodyBlock.getMelodyBlocks().stream()).forEach(cpMelody -> cpMelody.updateContourAscending());

		List<MelodyBlock> melodiesToCalculate = melodies.stream().filter(m -> m.isCalculable() && !m.getMelodyBlockNotes().isEmpty()).collect(toList());
		updatePitchesFromContour(melodies);
		updateMelodyInRange(melodies);
		updateRhythmWeight(melodiesToCalculate);

		List<Note> allNotes = melodies.stream().flatMap(m -> m.getMelodyBlockNotes().stream()).collect(toList());
		List<CpHarmony> harmonies = harmonyExtractor.extractHarmony(allNotes);
        motive.setHarmonies(harmonies);
//		melodies.forEach(h ->  LOGGER.debug(h.getMelodyBlockNotes() + ", "));
		return evaluateObjectives(motive);
	}

	private void updatePitchesFromContour(List<MelodyBlock> melodies) {
		List<MelodyBlock> updatebleMelodies = melodies.stream().filter(m -> !m.getMelodyBlockNotes().isEmpty()).collect(toList());
		for (MelodyBlock updatebleMelody : updatebleMelodies) {
//			Instrument instrument = instrumentConfig.getInstrumentForVoice(updatebleMelody.getVoice());
//			updatebleMelody.updatePitchesFromInstrument(instrument);
			updatebleMelody.updatePitchesFromContour();

        }
	}

	private void updateMelodyInRange(List<MelodyBlock> melodies) {
//		List<MelodyBlock> updatebleMelodies = melodies.stream().filter(m -> m.isCalculable() && !m.getMelodyBlockNotes().isEmpty()).collect(toList());
		for (MelodyBlock updatebleMelody : melodies) {
			Instrument instrument = instrumentConfig.getInstrumentForVoice(updatebleMelody.getVoice());
			instrument.updateMelodyInRange(updatebleMelody.getMelodyBlockNotes());
//			removeTextureNotesOutOfRange(updatebleMelody, instrument);
		}
	}

	private void removeTextureNotesOutOfRange(MelodyBlock updatebleMelody, Instrument instrument) {
		List<Note> melodyBlockNotes = updatebleMelody.getMelodyBlockNotes();
		for (Note note : melodyBlockNotes) {
            List<Note> textureNotes = texture.getTextureForNote(note);
            if (!textureNotes.isEmpty() && instrument.hasNotesOutOfRange(textureNotes)) {
                note.setDependantHarmony(null);
            }
        }
	}

	protected void updateRhythmWeight(List<MelodyBlock> melodies) {
		for (MelodyBlock melody : melodies) {
			Voice voice = voiceConfig.getVoiceConfiguration(melody.getVoice());
			List<Note> notes = melody.getMelodyBlockNotes();
			rhythmWeight.setNotes(notes);
			rhythmWeight.updateRhythmWeightMinimum(voice.getTimeConfig().getMinimumLength());

		}
	}

	private FitnessObjectiveValues evaluateObjectives(Motive motive) {
		double harmony = harmonicObjective.evaluate(motive);
		LOGGER.debug("harmonic: " + harmony);

		double harmonyResolution = harmonicResolutionObjective.evaluate(motive);
		LOGGER.debug("harmonyResolution: " + harmonyResolution);
		
//		double voiceLeading = voiceLeadingObjective.evaluate(motive);
//		LOGGER.debug("voiceLeadingSize: " + voiceLeading);
		
		double melodic = melodicObjective.evaluate(motive);
		LOGGER.debug("melodic = " + melodic);

		double melodicHarmonic = melodicHarmonicObjective.evaluate(motive);
		LOGGER.debug("melodicHarmonic = " + melodicHarmonic);
		
//		double tonality = tonalityObjective.evaluate(motive);
//		LOGGER.debug("tonality = " + tonality);
		
		double rhythm = rhythmObjective.evaluate(motive);
		LOGGER.debug("rhythm = " + rhythm);
		
		double meter = meterObjective.evaluate(motive);
		LOGGER.debug("meter = " + meter);

		double register = registerObjective.evaluate(motive);
		LOGGER.debug("register = " + register);

        double transformation = transformationObjective.evaluate(motive);
        LOGGER.debug("transformation = " + transformation);

        FitnessObjectiveValues fitnessObjectives = new FitnessObjectiveValues();
		fitnessObjectives.setHarmony(harmony);
		fitnessObjectives.setMelody(melodic);
		fitnessObjectives.setMelodicHarmonic(melodicHarmonic);
//		fitnessObjectives.setVoiceleading(voiceLeading);
//		fitnessObjectives.setTonality(tonality);
		fitnessObjectives.setRhythm(rhythm);
		fitnessObjectives.setMeter(meter);
		fitnessObjectives.setRegister(register);
		fitnessObjectives.setResolution(harmonyResolution);
		fitnessObjectives.setTransformation(transformation);

		//constraints
//		objectives[5] = lowestIntervalRegisterValue;
//		objectives[6] = repetitionsrhythmsMean;	//only for small motives (5 - 10 notes)
//		objectives[7] = repetitionsPitchesMean;	//only for small motives (5 - 10 notes)
		return fitnessObjectives;	
	}

}

