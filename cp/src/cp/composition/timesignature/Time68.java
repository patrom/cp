package cp.composition.timesignature;

import java.util.Collections;
import java.util.List;

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;

import cp.composition.beat.BeatGroup;

@Component(value="time68")
//@ConditionalOnProperty(name = "composition.timesignature", havingValue = "6/8")
public class Time68 extends TimeConfig{

	@Override
	public boolean randomBeatGroup() {
		return true;
	}
	
	@Override
	public void init() {
		super.init();
		BeatGroup defaultGroup6 = beatGroupFactory.getBeatGroupUneven(6, "");
		BeatGroup group12 = beatGroupFactory.getBeatGroupUneven(12, "homophonic");
		beats.add(defaultGroup6);
		beatsDoubleLength.add(defaultGroup6);
		beatsDoubleLength.add(group12);
		beatsAll.add(defaultGroup6);
		beatsAll.add(group12);
		minimumRhythmFilterLevel = 6;
		distance = new int[]{3,6,9,12,15,18,20,21,22,24,26,27,28,30,32};
		offset = 36;
	}

	@Override
	public boolean randomCombination() {
		return true;
	}

	@Override
	public List<BeatGroup> getFixedBeatGroup() {
		return Collections.singletonList(beatGroupFactory.getBeatGroupUneven(6, "fixed"));
	}

	@Override
	public List<BeatGroup> getHomophonicBeatGroup() {
		return Collections.singletonList(beatGroupFactory.getBeatGroupUneven(6, "homophonic"));
	}
	
}


