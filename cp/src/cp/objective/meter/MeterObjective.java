package cp.objective.meter;

import static java.util.stream.Collectors.groupingBy;
import static java.util.stream.Collectors.summingDouble;
import static java.util.stream.Collectors.toList;

import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.stream.Collectors;

import org.apache.commons.lang.ArrayUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import cp.generator.MusicProperties;
import cp.model.Motive;
import cp.model.melody.CpMelody;
import cp.model.note.Note;
import cp.objective.Objective;
@Component
public class MeterObjective extends Objective{
	
	private static Logger LOGGER = LoggerFactory.getLogger(MeterObjective.class);

	@Autowired
	private MusicProperties musicProperties;
	@Autowired
	private InnerMetricWeightFunctions innerMetricWeightFunctions;

	@Override
	public double evaluate(Motive motive) {
		List<CpMelody> melodies = motive.getMelodies();
		List<Note> mergedMelodyNotes = melodies.stream().flatMap(m -> m.getNotes().stream()).collect(Collectors.toList());
		double globalFilterLevel = musicProperties.getMinimumRhythmFilterLevel() * melodies.size();
		return getProfileMergedMelodiesAverage(mergedMelodyNotes, globalFilterLevel);
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
		InnerMetricWeight innerMetricWeightMerged = innerMetricWeightFunctions.getInnerMetricWeight(ArrayUtils.toPrimitive(positionsFiltered.toArray(filteredPos)) 
				,musicProperties.getMinimumLength(), musicProperties.getDistance());
		LOGGER.debug(innerMetricWeightMerged.getInnerMetricWeightMap().toString());
		return innerMetricWeightMerged.getInnerMetricWeightAverage();
	}
	
	protected  Map<Integer, Double> extractRhythmProfile(List<Note> notes){
		return  notes.stream().collect(groupingBy(Note::getPosition, TreeMap::new, summingDouble(Note::getPositionWeight)));
	}
	
}
