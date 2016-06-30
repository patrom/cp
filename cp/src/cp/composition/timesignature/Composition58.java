package cp.composition.timesignature;

import java.util.ArrayList;
import java.util.List;

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;

import cp.composition.beat.BeatGroup;

@Component
@ConditionalOnProperty(name = "composition.timesignature", havingValue = "5/8")
public class Composition58 extends CompositionConfig{

	@Override
	public boolean randomBeatGroup() {
		return false;
	}
	
	@Override
	public void init() {
		super.init();
		BeatGroup defaultGroupUneven = beatGroupFactory.getBeatGroupUneven(6, "homophonic");
		BeatGroup defaultGroupEven = beatGroupFactory.getBeatGroupEven(6, "homophonic");
		beats.add(defaultGroupEven); // 2 + 3
		beats.add(defaultGroupUneven);
		beatsDoubleLength.add(defaultGroupEven);
		beatsDoubleLength.add(defaultGroupUneven);
		beatsAll.add(defaultGroupEven);
		beatsAll.add(defaultGroupUneven);
		musicProperties.setMinimumRhythmFilterLevel(6);
		int[] distance = new int[]{2,5,7,10,12,15,17,20};
		musicProperties.setDistance(distance);
		offset = 30;
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


