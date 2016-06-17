package cp.composition.timesignature;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;

import cp.combination.Combination;

@Component
@ConditionalOnProperty(name = "composition.timesignature", havingValue = "4/4")
public class Composition44 extends CompositionConfig{
	
	@Autowired
	@Qualifier(value="FixedEven")
	private Combination fixedEven;
	

	@Override
	public boolean randomCombinations() {
		return false;
	}
	
	@Override
	public void init() {
		super.init();
		beats.add(12);
		beatsDoubleLength.add(24);
		beatsAll.add(12);
		beatsAll.add(24);
		musicProperties.setMinimumRhythmFilterLevel(12);
		int[] distance = {2,4,8,10,12,14,16,18,20,21,22,24,26,27,28,30,32};//minimumRhythmicValue = 12 - 4/4
		musicProperties.setDistance(distance);
	}

	@Override
	public boolean randomBeats() {
		return false;
	}

	@Override
	public Combination getFixed() {
		return fixedEven;
	}

}
