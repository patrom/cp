package cp.objective.rhythm;

import static java.util.stream.Collectors.groupingBy;
import static java.util.stream.Collectors.summingDouble;
import static java.util.stream.Collectors.toList;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.logging.Logger;

import org.apache.commons.lang.ArrayUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import cp.model.Motive;
import cp.model.melody.Melody;
import cp.model.note.Note;
import cp.model.rhythm.RhythmWeight;
import cp.objective.Objective;
import cp.objective.meter.InnerMetricWeight;
import cp.objective.meter.InnerMetricWeightFunctions;

@Component
public class RhythmObjective extends Objective{
	
	private static Logger LOGGER = Logger.getLogger(RhythmObjective.class.getName());
	
	@Autowired
	private RhythmWeight rhythmWeight;
	private int minimumRhythmicValue = 12;
	private double minimumFilterLevel;

	@Override
	public double evaluate(Motive motive) {
		List<Melody> melodies = motive.getMelodies();
		double totalProfile = 0;
		List<Note> mergedMelodyNotes = new ArrayList<>();
		for(Melody melody: melodies){
			List<Note> notes =  melody.getMelodieNotes();
			double profileAverage = getProfileAverage(notes, minimumFilterLevel);
			totalProfile = totalProfile + profileAverage;
			mergedMelodyNotes.addAll(notes);
		}
		double globalFilterLevel = minimumFilterLevel * melodies.size();
		double globalProfileAverage = getProfileMergedMelodiesAverage(mergedMelodyNotes, globalFilterLevel);
		totalProfile = totalProfile + globalProfileAverage;
		return totalProfile/(melodies.size() + 1);
	}
	
	public double getProfileAverage(List<Note> notes, double minimumFilterLevel){
		rhythmWeight.setNotes(notes);
		rhythmWeight.updateRhythmWeight();
		List<Note> filteredNotes = rhythmWeight.filterRhythmWeigths(minimumFilterLevel);
		LOGGER.info(filteredNotes.toString());
		if (filteredNotes.isEmpty()) {
			return 0;
		}
		InnerMetricWeight innerMetricWeight = InnerMetricWeightFunctions.getInnerMetricWeight(filteredNotes , minimumRhythmicValue);
		LOGGER.info(innerMetricWeight.getInnerMetricWeightMap().toString());
		return innerMetricWeight.getInnerMetricWeightAverage();
	}
	
	public double getProfileMergedMelodiesAverage(List<Note> notes, double minimumFilterLevel){
		Map<Integer, Double> profile = extractRhythmProfile(notes);
		List<Integer> positionsFiltered = profile.entrySet().stream()
												.filter(p -> p.getValue().doubleValue() >= minimumFilterLevel)
												.map(p -> p.getKey()).sorted()
												.collect(toList());
		if (positionsFiltered.isEmpty()) {
			return 0;
		}
		Integer[] filteredPos = new Integer[positionsFiltered.size()];
		InnerMetricWeight innerMetricWeightMerged = InnerMetricWeightFunctions.getInnerMetricWeight(ArrayUtils.toPrimitive(positionsFiltered.toArray(filteredPos)) , minimumRhythmicValue);
		LOGGER.info(innerMetricWeightMerged.getInnerMetricWeightMap().toString());
		return innerMetricWeightMerged.getInnerMetricWeightAverage();
	}
	
	protected  Map<Integer, Double> extractRhythmProfile(List<Note> notes){
		return  notes.stream().collect(groupingBy(Note::getPosition, TreeMap::new, summingDouble(Note::getPositionWeight)));
	}

}
