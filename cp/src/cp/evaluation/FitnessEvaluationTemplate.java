package cp.evaluation;

import static java.util.stream.Collectors.toList;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import cp.generator.MusicProperties;
import cp.model.Motive;
import cp.model.harmony.CpHarmony;
import cp.model.harmony.HarmonyExtractor;
import cp.model.melody.CpMelody;
import cp.model.melody.Transposition;
import cp.model.note.Note;
import cp.model.rhythm.RhythmWeight;
import cp.objective.Objective;
import cp.objective.meter.MeterObjective;
import cp.objective.rhythm.RhythmObjective;

@Component
public class FitnessEvaluationTemplate {

	private static Logger LOGGER = LoggerFactory.getLogger(FitnessEvaluationTemplate.class.getName());

	@Autowired
	private Objective harmonicObjective;
	@Autowired
	private Objective melodicObjective;
	@Autowired
	private Objective voiceLeadingObjective;
	@Autowired
	private Objective tonalityObjective;
	@Autowired
	private RhythmObjective rhythmObjective;
	@Autowired
	private RhythmWeight rhythmWeight;
	@Autowired
	private MeterObjective meterObjective;
	
	@Autowired
	private HarmonyExtractor harmonyExtractor;
	@Autowired
	private MusicProperties musicProperties;

	public FitnessObjectiveValues evaluate(Motive motive) {
		List<CpMelody> melodies = motive.getMelodies();
		
		for (CpMelody melody : melodies) {
			melody.updateMelodyBetween();
			List<Note> notes = melody.getNotes();
			rhythmWeight.setNotes(notes);
			rhythmWeight.updateRhythmWeight();
		}
		
		fugue(melodies);
		
		List<Note> allNotes = melodies.stream().flatMap(m -> m.getNotes().stream()).collect(toList());
		List<CpHarmony> harmonies = harmonyExtractor.extractHarmony(allNotes, motive.getMelodies().size());
		motive.setHarmonies(harmonies);
		melodies.forEach(h ->  LOGGER.debug(h.getNotes() + ", "));
		return evaluateObjectives(motive);
	}

	private void fugue(List<CpMelody> melodies) {
		CpMelody dux = findMelodyForVoice(melodies, 0);
		CpMelody comes = findMelodyForVoice(melodies, 1);
		comes.copyMelody(dux, 4, Transposition.RELATIVE);
	}

	protected CpMelody findMelodyForVoice(List<CpMelody> melodies, int voice) {
		return melodies.stream().filter(m -> m.getVoice() == voice).findFirst().get();
	}

	private FitnessObjectiveValues evaluateObjectives(Motive motive) {
		double harmony = harmonicObjective.evaluate(motive);
		LOGGER.debug("harmonic: " + harmony);

//		double voiceLeading = voiceLeadingObjective.evaluate(motive);
//		LOGGER.fine("voiceLeadingSize: " + voiceLeading);
		
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
//		fitnessObjectives.setVoiceleading(voiceLeading);
//		fitnessObjectives.setTonality(tonality);
		fitnessObjectives.setRhythm(rhythm);
		fitnessObjectives.setMeter(meter);

		//constraints
//		objectives[5] = lowestIntervalRegisterValue;
//		objectives[6] = repetitionsrhythmsMean;	//only for small motives (5 - 10 notes)
//		objectives[7] = repetitionsPitchesMean;	//only for small motives (5 - 10 notes)
		return fitnessObjectives;	
	}
	
}

