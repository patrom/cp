package cp.evaluation;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import cp.generator.MusicProperties;
import cp.model.Motive;
import cp.model.harmony.CpHarmony;
import cp.model.harmony.HarmonyExtractor;
import cp.model.melody.CpMelody;
import cp.model.note.Note;
import cp.model.rhythm.RhythmWeight;
import cp.objective.Objective;
import cp.objective.rhythm.RhythmObjective;
import cp.out.instrument.Instrument;

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
	private HarmonyExtractor harmonyExtractor;
	@Autowired
	private MusicProperties musicProperties;

	public FitnessObjectiveValues evaluate(Motive motive) {
		List<CpMelody> melodies = motive.getMelodies();
//		motive.extractMelodies();
//		motive.updateInnerMetricWeightMelodies();
//		motive.updateInnerMetricWeightHarmonies();
//		motive.extractChords();
//		melody.updatePitches(5);
//		melody.updateMelodyBetween(50, 70);
		List<Note> allNotes = new ArrayList<>();
		int voice = 0;
		for (CpMelody melody : melodies) {
			Instrument instrument = findInstrument(voice);
			melody.updateMelodyBetween(instrument.getLowest(), instrument.getHighest());
			List<Note> notes = melody.getNotes();
			rhythmWeight.setNotes(notes);
			rhythmWeight.updateRhythmWeight();
			allNotes.addAll(notes);
			voice++;
		}
		List<CpHarmony> harmonies = harmonyExtractor.extractHarmony(allNotes, motive.getMelodies().size());
		motive.setHarmonies(harmonies);
		melodies.forEach(h ->  LOGGER.debug(h.getNotes() + ", "));
		return evaluateObjectives(motive);
	}

	private FitnessObjectiveValues evaluateObjectives(Motive motive) {
		double harmony = harmonicObjective.evaluate(motive);
		LOGGER.debug("harmonic: " + harmony);

//		double voiceLeading = voiceLeadingObjective.evaluate(motive);
//		LOGGER.fine("voiceLeadingSize: " + voiceLeading);
		
		double melodic = melodicObjective.evaluate(motive);
		LOGGER.debug("melodic = " + melodic);
		
		double tonality = tonalityObjective.evaluate(motive);
		LOGGER.debug("tonality = " + tonality);
		
		double rhythm = rhythmObjective.evaluate(motive);
		LOGGER.debug("rhythm = " + rhythm);
		
		FitnessObjectiveValues fitnessObjectives = new FitnessObjectiveValues();
		fitnessObjectives.setHarmony(harmony);
		fitnessObjectives.setMelody(melodic);
//		fitnessObjectives.setVoiceleading(voiceLeading);
		fitnessObjectives.setTonality(tonality);
		fitnessObjectives.setRhythm(rhythm);

		//constraints
//		objectives[5] = lowestIntervalRegisterValue;
//		objectives[6] = repetitionsrhythmsMean;	//only for small motives (5 - 10 notes)
//		objectives[7] = repetitionsPitchesMean;	//only for small motives (5 - 10 notes)
		return fitnessObjectives;	
	}
	
	private Instrument findInstrument(int voice){
		List<Instrument> instruments = musicProperties.getInstruments();
		Optional<Instrument> instrument = instruments.stream().filter(instr -> (instr.getVoice()) == voice).findFirst();
		if (instrument.isPresent()) {
			return instrument.get();
		}else{
			throw new IllegalArgumentException("Instrument for voice " + voice + " is missing!");
		}
	}
	
}

