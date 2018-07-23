package cp.composition.timesignature;

import cp.model.rhythm.DurationConstants;
import org.springframework.stereotype.Component;

@Component(value="time68")
//@ConditionalOnProperty(name = "composition.timesignature", havingValue = "6/8")
public class Time68 extends TimeConfig{

	@Override
	public void init() {
		super.init();
		minimumLength = DurationConstants.EIGHT;
		distance = new int[]{3,6,9,12,15,18,20,21,22,24,26,27,28,30,32};
		offset = DurationConstants.SIX_EIGHTS;

		measureDuration = DurationConstants.SIX_EIGHTS;
	}

}


