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
import cp.model.melody.MelodyBlock;
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
		Map<Integer, Double> profile = motive.extractRhythmProfile();
		double globalFilterLevel = musicProperties.getMinimumRhythmFilterLevel() * motive.getMelodyBlocks().size();
		List<Integer> positionsFiltered = profile.entrySet().stream()
				.filter(p -> p.getValue().doubleValue() >= globalFilterLevel)
				.map(p -> p.getKey())
				.sorted()
				.collect(toList());
		LOGGER.debug("Filtered positions: " + positionsFiltered);
		if (positionsFiltered.isEmpty()) {
			return 0;
		}
		return getProfileMergedMelodiesAverage(positionsFiltered);
	}
	
	
	public double getProfileMergedMelodiesAverage(List<Integer> positionsFiltered){
		Integer[] filteredPos = new Integer[positionsFiltered.size()];
		InnerMetricWeight innerMetricWeightMerged = innerMetricWeightFunctions.getInnerMetricWeight(ArrayUtils.toPrimitive(positionsFiltered.toArray(filteredPos)) 
				,musicProperties.getMinimumLength(), musicProperties.getDistance());
		LOGGER.debug(innerMetricWeightMerged.getInnerMetricWeightMap().toString());
		return innerMetricWeightMerged.getInnerMetricWeightAverage();
	}
	
}
