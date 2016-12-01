package cp.composition.timesignature;

import cp.composition.beat.BeatGroup;
import cp.model.rhythm.DurationConstants;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component(value="time44")
//@ConditionalOnProperty(name = "composition.timesignature", havingValue = "4/4")
public class Time44 extends TimeConfig{
	
	@Override
	public boolean randomBeatGroup() {
		return true;
	}
	
	@Override
	public void init() {
		super.init();
		BeatGroup defaultGroup6 = beatGroupFactory.getBeatGroupEven(DurationConstants.EIGHT, "");
		BeatGroup defaultGroup12 = beatGroupFactory.getBeatGroupEven(DurationConstants.QUARTER, "");
		beats.add(defaultGroup6);
		beatsDoubleLength.add(defaultGroup12);
		beatsAll.add(defaultGroup6);
		beatsAll.add(defaultGroup12);
		minimumLength = DurationConstants.QUARTER;
		distance = new int[]{2,4,8,10,12,14,16,18,20,21,22,24,26,27,28,30,32};//minimumRhythmicValue = 12 - 4/4
		offset = DurationConstants.WHOLE;
	}

	@Override
	public boolean randomCombination() {
		return true;
	}

	@Override
	public List<BeatGroup> getFixedBeatGroup() {
		List<BeatGroup> group = new ArrayList<>();
//		group.add(beatGroupFactory.getBeatGroupEven(DurationConstants.EIGHT, "fixed"));
		group.add(beatGroupFactory.getBeatGroupEven(DurationConstants.QUARTER, "fixed"));
		return group;
	}

	@Override
	public List<BeatGroup> getHomophonicBeatGroup() {
		List<BeatGroup> group = new ArrayList<>();
//		group.add(beatGroupFactory.getBeatGroupEven(DurationConstants.EIGHT, "homophonic"));
		group.add(beatGroupFactory.getBeatGroupEven(DurationConstants.HALF, "homophonic"));
		return group;
	}

}
