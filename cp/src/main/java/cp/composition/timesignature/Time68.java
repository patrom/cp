package cp.composition.timesignature;

import cp.combination.RhythmCombination;
import cp.composition.beat.BeatGroup;
import cp.model.rhythm.DurationConstants;
import org.springframework.stereotype.Component;

import java.util.List;

@Component(value="time68")
//@ConditionalOnProperty(name = "composition.timesignature", havingValue = "6/8")
public class Time68 extends TimeConfig{

	@Override
	public boolean randomBeatGroup() {
		return true;
	}

	@Override
	public List<RhythmCombination> getAllBeats() {
		return defaultUnEvenCombinations;
	}
	
	@Override
	public void init() {
		super.init();
		BeatGroup defaultGroup6 = beatGroupFactory.getBeatGroupUneven(DurationConstants.EIGHT);
//		BeatGroup group12 = beatGroupFactory.getBeatGroupUneven(DurationConstants.QUARTER);
		beatGroups.add(defaultGroup6);
//		beatsDoubleLength.add(defaultGroup6);
////		beatsDoubleLength.add(group12);
//		beatsAll.add(defaultGroup6);
//		beatsAll.add(group12);
		minimumLength = DurationConstants.EIGHT;
		distance = new int[]{3,6,9,12,15,18,20,21,22,24,26,27,28,30,32};
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
	
}


