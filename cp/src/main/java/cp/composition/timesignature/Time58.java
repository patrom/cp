package cp.composition.timesignature;

import cp.composition.beat.BeatGroup;
import cp.model.rhythm.DurationConstants;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component(value="time58")
//@ConditionalOnProperty(name = "composition.timesignature", havingValue = "5/8")
public class Time58 extends TimeConfig{

	@Override
	public boolean randomBeatGroup() {
		return false;
	}
	
	@Override
	public void init() {
		super.init();
		BeatGroup defaultGroupUneven = beatGroupFactory.getBeatGroupUneven(6, "fixed");
		BeatGroup defaultGroupEven = beatGroupFactory.getBeatGroupEven(6, "");
		beats.add(defaultGroupEven); // 2 + 3
		beats.add(defaultGroupUneven);
		beatsDoubleLength.add(defaultGroupEven);
		beatsDoubleLength.add(defaultGroupUneven);
		beatsAll.add(defaultGroupEven);
		beatsAll.add(defaultGroupUneven);
		minimumRhythmFilterLevel = DurationConstants.EIGHT;
		distance = new int[]{2,5,7,10,12,15,17,20};
		offset = 5 * DurationConstants.EIGHT;
	}

	@Override
	public boolean randomCombination() {
		return true;
	}

	@Override
	public List<BeatGroup> getFixedBeatGroup() {
		List<BeatGroup> group = new ArrayList<>();
		group.add(beatGroupFactory.getBeatGroupEven(DurationConstants.EIGHT, "fixed"));
		group.add(beatGroupFactory.getBeatGroupUneven(DurationConstants.EIGHT, "fixed"));
		return group;
	}

	@Override
	public List<BeatGroup> getHomophonicBeatGroup() {
		List<BeatGroup> group = new ArrayList<>();
		group.add(beatGroupFactory.getBeatGroupEven(DurationConstants.EIGHT, "homophonic"));
		group.add(beatGroupFactory.getBeatGroupUneven(DurationConstants.EIGHT, "homophonic"));
		return group;
	}
	
}


