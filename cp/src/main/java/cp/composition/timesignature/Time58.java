package cp.composition.timesignature;

import cp.model.rhythm.DurationConstants;
import org.springframework.stereotype.Component;

@Component(value="time58")
//@ConditionalOnProperty(name = "composition.timesignature", havingValue = "5/8")
public class Time58 extends TimeConfig{
	
	@Override
	public void init() {
		super.init();
		minimumLength = DurationConstants.EIGHT;
		distance = new int[]{2,5,7,10,12,15,17,20};
		offset = 5 * DurationConstants.EIGHT;

		measureDuration =  5 * DurationConstants.EIGHT;
	}
	
}


