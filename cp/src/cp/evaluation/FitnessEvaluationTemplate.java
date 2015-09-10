package cp.evaluation;

import java.util.List;
import java.util.logging.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import cp.model.Motive;
import cp.model.melody.CpMelody;
import cp.model.melody.Melody;
import cp.model.note.Note;
import cp.model.rhythm.RhythmWeight;
import cp.nsga.operator.decorator.Decorator;
import cp.nsga.operator.decorator.MelodyDecorator;
import cp.objective.Objective;
import cp.objective.rhythm.RhythmObjective;
import cp.variation.Embellisher;

@Component
public class FitnessEvaluationTemplate {

	private static Logger LOGGER = Logger.getLogger(FitnessEvaluationTemplate.class.getName());

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

	public FitnessObjectiveValues evaluate(Motive motive) {
//		motive.extractMelodies();
//		motive.updateInnerMetricWeightMelodies();
//		motive.updateInnerMetricWeightHarmonies();
//		motive.extractChords();
		CpMelody melody = motive.getMelodies().get(0);
//		melody.updatePitches(5);
//		melody.updateMelodyBetween(50, 70);
		rhythmWeight.setNotes(melody.getNotes());
		rhythmWeight.updateRhythmWeight();
		return evaluateObjectives(motive);
	}

	private FitnessObjectiveValues evaluateObjectives(Motive motive) {
//		double harmony = harmonicObjective.evaluate(motive);
//		LOGGER.fine("harmonic: " + harmony);
//
//		double voiceLeading = voiceLeadingObjective.evaluate(motive);
//		LOGGER.fine("voiceLeadingSize: " + voiceLeading);
		
		double melodic = melodicObjective.evaluate(motive);
		LOGGER.fine("melodic = " + melodic);
		
//		double tonality = tonalityObjective.evaluate(motive);
//		LOGGER.fine("tonality = " + tonality);
		
		double rhythm = rhythmObjective.evaluate(motive);
		LOGGER.fine("rhythm = " + rhythm);
		
		FitnessObjectiveValues fitnessObjectives = new FitnessObjectiveValues();
//		fitnessObjectives.setHarmony(harmony);
		fitnessObjectives.setMelody(melodic);
//		fitnessObjectives.setVoiceleading(voiceLeading);
//		fitnessObjectives.setTonality(tonality);
		fitnessObjectives.setRhythm(rhythm);

		//constraints
//		objectives[5] = lowestIntervalRegisterValue;
//		objectives[6] = repetitionsrhythmsMean;	//only for small motives (5 - 10 notes)
//		objectives[7] = repetitionsPitchesMean;	//only for small motives (5 - 10 notes)
		return fitnessObjectives;	
	}

}

