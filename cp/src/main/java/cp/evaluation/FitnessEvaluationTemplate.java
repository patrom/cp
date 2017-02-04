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
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
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
	private RhythmWeight rhythmWeight;
	@Autowired
	private Objective meterObjective;
	
	@Autowired
	private HarmonyExtractor harmonyExtractor;
	@Autowired
	private Composition composition;
	@Autowired
	private TimeLine timeLine;
	@Autowired
	@Qualifier(value = "dependantGenerator")
	private DependantHarmonyGenerator dependantGenerator;

	public FitnessObjectiveValues evaluate(Motive motive) {
		List<MelodyBlock> melodies = motive.getMelodyBlocks();
		List<MelodyBlock> melodiesToCalculate = melodies.stream().filter(m -> m.isCalculable() && !m.getMelodyBlockNotes().isEmpty()).collect(toList());
		updatePitchesFromContour(melodies);
		updateRhythmWeight(melodiesToCalculate);

		dependantGenerator.generateDependantHarmonies(melodies);

		List<Note> allNotes = melodies.stream().flatMap(m -> m.getMelodyBlockNotes().stream()).collect(toList());
		List<CpHarmony> harmonies = harmonyExtractor.extractHarmony(allNotes, motive.getMelodyBlocks().size());
		motive.setHarmonies(harmonies);
//		melodies.forEach(h ->  LOGGER.debug(h.getMelodyBlockNotes() + ", "));
		return evaluateObjectives(motive);
	}

	private void updatePitchesFromContour(List<MelodyBlock> melodies) {
		List<MelodyBlock> updatebleMelodies = melodies.stream().filter(m -> m.isCalculable() && !m.isRhythmDependant() && !m.getMelodyBlockNotes().isEmpty()).collect(toList());
		updatebleMelodies.forEach(m -> m.updatePitchesFromContour());
		updatebleMelodies.forEach(m -> m.updateMelodyBetween());
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
		
		FitnessObjectiveValues fitnessObjectives = new FitnessObjectiveValues();
		fitnessObjectives.setHarmony(harmony);
		fitnessObjectives.setMelody(melodic);
		fitnessObjectives.setVoiceleading(voiceLeading);
//		fitnessObjectives.setTonality(tonality);
		fitnessObjectives.setRhythm(rhythm);
		fitnessObjectives.setMeter(meter);
		fitnessObjectives.setResolution(harmonyResolution);

		//constraints
//		objectives[5] = lowestIntervalRegisterValue;
//		objectives[6] = repetitionsrhythmsMean;	//only for small motives (5 - 10 notes)
//		objectives[7] = repetitionsPitchesMean;	//only for small motives (5 - 10 notes)
		return fitnessObjectives;	
	}
	
}

