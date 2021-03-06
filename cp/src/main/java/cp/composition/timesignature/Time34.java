package cp.composition.timesignature;

import cp.model.rhythm.DurationConstants;
import org.springframework.stereotype.Component;

@Component(value="time34")
//@ConditionalOnProperty(name = "composition.timesignature", havingValue = "3/4")
public class Time34 extends TimeConfig{
	
	@Override
	public void init() {
		super.init();
		minimumLength = DurationConstants.QUARTER;
		distance = new int[]{3,6,9,12,15,18,20,21,22,24,26,27,28,30,32};//minimumRhythmicValue = 12 -  3/4
		offset = DurationConstants.THREE_QUARTERS;

		measureDuration = DurationConstants.THREE_QUARTERS;
	}

}

