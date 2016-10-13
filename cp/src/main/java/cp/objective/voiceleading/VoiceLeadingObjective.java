package cp.objective.voiceleading;

import cp.model.Motive;
import cp.model.harmony.CpHarmony;
import cp.objective.Objective;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.DoubleSummaryStatistics;
import java.util.List;
import java.util.TreeMap;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.groupingBy;
import static java.util.stream.Collectors.toList;

@Component
public class VoiceLeadingObjective extends Objective {

	private static final Logger LOGGER = LoggerFactory.getLogger(VoiceLeadingObjective.class.getName());

	@Override
	public double evaluate(Motive motive) {
		List<CpHarmony> filteredHarmonies = filterHarmonies(motive.getHarmonies());
		double totalSize = 0;
		int harmoniesSize = filteredHarmonies.size() - 1;
		for(int i = 0; i < harmoniesSize; i++){
			VoiceLeadingSize minimalVoiceLeadingSize = VoiceLeading.caculateSize(((CpHarmony)filteredHarmonies.get(i)).getChord().getPitchClassMultiSet(), filteredHarmonies.get(i + 1).getChord().getPitchClassMultiSet());
			totalSize = totalSize + minimalVoiceLeadingSize.getSize();
		}
		return totalSize/harmoniesSize;
	}
	
	public List<CpHarmony> filterHarmonies(List<CpHarmony> harmonies){
		DoubleSummaryStatistics stats = harmonies.stream().collect(Collectors.summarizingDouble(CpHarmony::getHarmonyWeight));
		LOGGER.debug("filtered at: " + stats.getAverage());
		return harmonies.stream().filter(h -> h.getHarmonyWeight() > stats.getAverage()).collect(toList());
	}
	
	private TreeMap<Integer, List<CpHarmony>> harmoniesForBeat(List<CpHarmony> harmonies, int beat){
		return  harmonies.stream().collect(groupingBy(h -> h.beat(beat), TreeMap::new, Collectors.toList()));
	}

}
