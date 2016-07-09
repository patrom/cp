package cp.composition.timesignature;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;

import cp.composition.beat.BeatGroup;

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
		BeatGroup defaultGroup6 = beatGroupFactory.getBeatGroupEven(6, "");
		BeatGroup defaultGroup12 = beatGroupFactory.getBeatGroupEven(12, "");
		beats.add(defaultGroup6);
		beatsDoubleLength.add(defaultGroup12);
		beatsAll.add(defaultGroup6);
		beatsAll.add(defaultGroup12);
		minimumRhythmFilterLevel = 12;
		distance = new int[]{2,4,8,10,12,14,16,18,20,21,22,24,26,27,28,30,32};//minimumRhythmicValue = 12 - 4/4
		offset = 48;
	}

	@Override
	public boolean randomCombination() {
		return true;
	}

	@Override
	public List<BeatGroup> getFixedBeatGroup() {
		List<BeatGroup> group = new ArrayList<BeatGroup>();
		group.add(beatGroupFactory.getBeatGroupEven(6, "fixed"));
		group.add(beatGroupFactory.getBeatGroupUneven(6, "fixed"));
		return group;
	}

}
