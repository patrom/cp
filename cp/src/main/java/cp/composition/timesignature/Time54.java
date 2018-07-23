package cp.composition.timesignature;

import cp.model.rhythm.DurationConstants;
import org.springframework.stereotype.Component;

@Component(value="time54")
public class Time54  extends TimeConfig{

    @Override
    public void init() {
        super.init();
        minimumLength = DurationConstants.QUARTER;
        distance = new int[]{2,5,7,10,12,15,17,20};
        offset = 5 * DurationConstants.QUARTER;

        measureDuration =  5 * DurationConstants.QUARTER;
    }

}

