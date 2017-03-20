package cp.evaluation;

import cp.composition.Composition;
import cp.composition.voice.VoiceConfig;
import cp.generator.dependant.DependantHarmonyGenerator;
import cp.model.Motive;
import cp.model.TimeLine;
import cp.model.harmony.CpHarmony;
import cp.model.harmony.HarmonyExtractor;
import cp.model.melody.MelodyBlock;
import cp.model.note.Note;
import cp.model.rhythm.RhythmWeight;
import cp.objective.Objective;
import cp.out.instrument.Instrument;
import cp.out.play.InstrumentConfig;
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
	private RhythmWeight rhythmWeight;
	@Autowired
	private Objective meterObjective;
	@Autowired
	private InstrumentConfig instrumentConfig;
	
	@Autowired
	private HarmonyExtractor harmonyExtractor;
	@Autowired
	private Composition composition;
	@Autowired
	private TimeLine timeLine;

	public FitnessObjectiveValues evaluate(Motive motive) {
		List<MelodyBlock> melodies = motive.getMelodyBlocks();


		List<MelodyBlock> melodiesToCalculate = melodies.stream().filter(m -> m.isCalculable() && !m.getMelodyBlockNotes().isEmpty()).collect(toList());
		updatePitchesFromContour(melodies);
		//after update pitches for dependant melodies! (don't have contour)
		for (DependantHarmonyGenerator dependantGenerator : composition.getDependantHarmonyGenerators()) {
			dependantGenerator.generateDependantHarmonies(melodies);
		}
		updateRhythmWeight(melodiesToCalculate);

		List<Note> allNotes = melodies.stream().flatMap(m -> m.getMelodyBlockNotes().stream()).collect(toList());
		List<CpHarmony> harmonies = harmonyExtractor.extractHarmony(allNotes, motive.getMelodyBlocks().size());
		motive.setHarmonies(harmonies);
//		melodies.forEach(h ->  LOGGER.debug(h.getMelodyBlockNotes() + ", "));
		return evaluateObjectives(motive);
	}

	private void updatePitchesFromContour(List<MelodyBlock> melodies) {
		List<MelodyBlock> updatebleMelodies = melodies.stream().filter(m -> m.isCalculable() && !m.isRhythmDependant() && !m.getMelodyBlockNotes().isEmpty()).collect(toList());
		for (MelodyBlock updatebleMelody : updatebleMelodies) {
			Instrument instrument = instrumentConfig.getInstrumentForVoice(updatebleMelody.getVoice());
//			updatebleMelody.updatePitchesFromInstrument(instrument);
			updatebleMelody.updatePitchesFromContour();
			instrument.updateMelodyInRange(updatebleMelody.getMelodyBlockNotes());
		}
	}

	protected void updateRhythmWeight(List<MelodyBlock> melodies) {
		for (MelodyBlock melody : melodies) {
			VoiceConfig voiceConfig = composition.getVoiceConfiguration(melody.getVoice());
			List<Note> notes = melody.getMelodyBlockNotes();
			rhythmWeight.setNotes(notes);
			rhythmWeight.updateRhythmWeightMinimum(voiceConfig.getTimeConfig().getMinimumLength());

		}
	}

	protected MelodyBlock findMelodyForVoice(List<MelodyBlock> melodies, int voice) {
		return melodies.stream().filter(m -> m.getVoice() == voice).findFirst().get();
	}

	private FitnessObjectiveValues evaluateObjectives(Motive motive) {
		double harmony = harmonicObjective.evaluate(motive);
		LOGGER.debug("harmonic: " + harmony);

		double harmonyResolution = harmonicResolutionObjective.evaluate(motive);
		LOGGER.debug("harmonyResolution: " + harmonyResolution);
		
		double voiceLeading = voiceLeadingObjective.evaluate(motive);
		LOGGER.debug("voiceLeadingSize: " + voiceLeading);
		
		double melodic = melodicObjective.evaluate(motive);
		LOGGER.debug("melodic = " + melodic);
		
//		double tonality = tonalityObjective.evaluate(motive);
//		LOGGER.debug("tonality = " + tonality);
		
		double rhythm = rhythmObjective.evaluate(motive);
		LOGGER.debug("rhythm = " + rhythm);
		
		double meter = meterObjective.evaluate(motive);
		LOGGER.debug("meter = " + meter);

		double register = registerObjective.evaluate(motive);
		LOGGER.debug("register = " + register);
		
		FitnessObjectiveValues fitnessObjectives = new FitnessObjectiveValues();
		fitnessObjectives.setHarmony(harmony);
		fitnessObjectives.setMelody(melodic);
		fitnessObjectives.setVoiceleading(voiceLeading);
//		fitnessObjectives.setTonality(tonality);
		fitnessObjectives.setRhythm(rhythm);
		fitnessObjectives.setMeter(meter);
		fitnessObjectives.setRegister(register);
		fitnessObjectives.setResolution(harmonyResolution);

		//constraints
//		objectives[5] = lowestIntervalRegisterValue;
//		objectives[6] = repetitionsrhythmsMean;	//only for small motives (5 - 10 notes)
//		objectives[7] = repetitionsPitchesMean;	//only for small motives (5 - 10 notes)
		return fitnessObjectives;	
	}

}

