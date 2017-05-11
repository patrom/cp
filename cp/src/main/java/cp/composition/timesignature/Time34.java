package cp.composition.timesignature;

import cp.combination.RhythmCombination;
import cp.composition.beat.BeatGroup;
import cp.model.rhythm.DurationConstants;
import cp.util.RandomUtil;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

@Component(value="time34")
//@ConditionalOnProperty(name = "composition.timesignature", havingValue = "3/4")
public class Time34 extends TimeConfig{
	
	@Override
	public boolean randomBeatGroup() {
		return true;
	}

	@Override
	public Map<Integer, List<RhythmCombination>> getAllRhythmCombinations() {
		return null;
	}
	
	@Override
	public void init() {
		super.init();
		for (Integer noteSize : defaultUnEvenCombinations.keySet()) {
			BeatGroup defaultGroup6 = beatGroupFactory.getBeatGroupUneven(DurationConstants.EIGHT, noteSize);
			beatGroups3.add(defaultGroup6);
		}

		minimumLength = DurationConstants.QUARTER;
		distance = new int[]{3,6,9,12,15,18,20,21,22,24,26,27,28,30,32};//minimumRhythmicValue = 12 -  3/4
		offset = DurationConstants.SIX_EIGHTS;
	}

	@Override
	public boolean randomCombination() {
		return true;
	}

	@Override
	public List<RhythmCombination> getFixedBeatGroup() {
		return fixedUneven;
	}

	@Override
	public List<RhythmCombination> getHomophonicBeatGroup() {
		return homophonicUneven;
	}

	@Override
	public BeatGroup getBeatGroup(int index) {
		return RandomUtil.getRandomFromList(beatGroups3);
	}

}

