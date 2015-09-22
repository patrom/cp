package cp.objective.rhythm;

import static java.util.stream.Collectors.groupingBy;
import static java.util.stream.Collectors.summingDouble;
import static java.util.stream.Collectors.toList;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.logging.Logger;
import java.util.stream.Collectors;

import org.apache.commons.lang.ArrayUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import cp.generator.MusicProperties;
import cp.model.Motive;
import cp.model.melody.CpMelody;
import cp.model.note.Note;
import cp.model.rhythm.RhythmWeight;
import cp.objective.Objective;
import cp.objective.meter.InnerMetricWeight;
import cp.objective.meter.InnerMetricWeightFunctions;

@Component
public class RhythmObjective extends Objective{
	
	private static Logger LOGGER = Logger.getLogger(RhythmObjective.class.getName());
	
	@Autowired
	private MusicProperties musicProperties;
	@Autowired
	private InnerMetricWeightFunctions innerMetricWeightFunctions;

	@Override
	public double evaluate(Motive motive) {
		List<CpMelody> melodies = motive.getMelodies();
		double totalProfile = 0;
		List<Note> mergedMelodyNotes = new ArrayList<>();
		for(CpMelody melody: melodies){
			List<Note> notes =  melody.getNotes();
			double profileAverage = getProfileAverage(notes, musicProperties.getMinimumRhythmFilterLevel(), musicProperties.getMinimumLength());
			totalProfile = totalProfile + profileAverage;
			mergedMelodyNotes.addAll(notes);
		}
		double globalFilterLevel = musicProperties.getMinimumRhythmFilterLevel() * melodies.size();
		double globalProfileAverage = getProfileMergedMelodiesAverage(mergedMelodyNotes, globalFilterLevel);
		totalProfile = totalProfile + globalProfileAverage;
		return totalProfile/(melodies.size() + 1);
	}
	
	public double getProfileAverage(List<Note> notes, double minimumFilterLevel, int minimumRhythmicValue){
		List<Note> filteredNotes = filterRhythmWeigths(notes, minimumFilterLevel);
		LOGGER.fine(filteredNotes.toString());
		if (filteredNotes.isEmpty()) {
			return 0;
		}
		InnerMetricWeight innerMetricWeight = innerMetricWeightFunctions.getInnerMetricWeight(filteredNotes , minimumRhythmicValue);
		LOGGER.fine("InnerMetricMap: " + innerMetricWeight.getInnerMetricWeightMap().toString());
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
		InnerMetricWeight innerMetricWeightMerged = innerMetricWeightFunctions.getInnerMetricWeight(ArrayUtils.toPrimitive(positionsFiltered.toArray(filteredPos)) , musicProperties.getMinimumLength());
		LOGGER.fine(innerMetricWeightMerged.getInnerMetricWeightMap().toString());
		return innerMetricWeightMerged.getInnerMetricWeightAverage();
	}
	
	protected  Map<Integer, Double> extractRhythmProfile(List<Note> notes){
		return  notes.stream().collect(groupingBy(Note::getPosition, TreeMap::new, summingDouble(Note::getPositionWeight)));
	}
	
	public List<Note> filterRhythmWeigths(List<Note> notes, double minimumWeight){
		return notes.stream().filter(note -> note.getPositionWeight() >= minimumWeight).collect(Collectors.toList());
	}

}
