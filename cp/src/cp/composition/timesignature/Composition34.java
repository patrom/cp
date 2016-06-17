package cp.composition.timesignature;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;

import cp.combination.Combination;

@Component
@ConditionalOnProperty(name = "composition.timesignature", havingValue = "3/4")
public class Composition34 extends CompositionConfig{
	
	@Autowired
	@Qualifier(value="FixedUnEven")
	private Combination fixedUnEven;

	@Override
	public boolean randomCombinations() {
		return false;
	}
	
	@Override
	public void init() {
		super.init();
		beats.add(36);
		beatsDoubleLength.add(36);
		beatsAll.add(36);
		musicProperties.setMinimumRhythmFilterLevel(12);
		int[] distance = {3,6,9,12,15,18,20,21,22,24,26,27,28,30,32};//minimumRhythmicValue = 12 -  3/4
		musicProperties.setDistance(distance);
	}

	@Override
	public boolean randomBeats() {
		return false;
	}
	
	@Override
	public Combination getFixed() {
		return fixedUnEven;
	}

}

