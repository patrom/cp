package cp.composition.timesignature;

import java.util.Collections;
import java.util.List;

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;

import cp.composition.beat.BeatGroup;

@Component(value="time34")
//@ConditionalOnProperty(name = "composition.timesignature", havingValue = "3/4")
public class Time34 extends TimeConfig{
	
	@Override
	public boolean randomBeatGroup() {
		return true;
	}
	
	@Override
	public void init() {
		super.init();
		BeatGroup defaultGroup = beatGroupFactory.getBeatGroupUneven(12, "");
		beats.add(defaultGroup);
		beatsDoubleLength.add(defaultGroup);
		beatsAll.add(defaultGroup);
		minimumRhythmFilterLevel = 12;
		distance = new int[]{3,6,9,12,15,18,20,21,22,24,26,27,28,30,32};//minimumRhythmicValue = 12 -  3/4
		offset = 36;
	}

	@Override
	public boolean randomCombination() {
		return true;
	}

	@Override
	public List<BeatGroup> getFixedBeatGroup() {
		return Collections.singletonList(beatGroupFactory.getBeatGroupUneven(12, "fixed"));
	}

}

