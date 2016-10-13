package cp.objective.meter;

import cp.composition.Composition;
import cp.model.Motive;
import cp.objective.Objective;
import org.apache.commons.lang.ArrayUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

import static java.util.stream.Collectors.toList;
@Component
public class MeterObjective extends Objective{
	
	private static Logger LOGGER = LoggerFactory.getLogger(MeterObjective.class);

	private Composition composition;
	@Autowired
	private InnerMetricWeightFunctions innerMetricWeightFunctions;

	@Override
	public double evaluate(Motive motive) {
		Map<Integer, Double> profile = motive.extractRhythmProfile();
		double globalFilterLevel = composition.getTimeConfig().getMinimumRhythmFilterLevel() * motive.getMelodyBlocks().size();
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
				,musicProperties.getMinimumLength(), composition.getTimeConfig().getDistance());
		LOGGER.debug(innerMetricWeightMerged.getInnerMetricWeightMap().toString());
		return innerMetricWeightMerged.getInnerMetricWeightAverage();
	}
	
	public void setComposition(Composition composition) {
		this.composition = composition;
	}
	
}
