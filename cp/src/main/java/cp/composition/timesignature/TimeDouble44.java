package cp.composition.timesignature;

import cp.model.rhythm.DurationConstants;
import org.springframework.stereotype.Component;

/**
 * Created by prombouts on 14/07/2017.
 */
@Component(value="timeDouble44")
public class TimeDouble44 extends TimeConfig{

    @Override
    public void init() {
        super.init();
        minimumLength = DurationConstants.QUARTER;
        distance = new int[]{2,4,8,10,12,14,16,18,20,21,22,24,26,27,28,30,32};//minimumRhythmicValue = 12 - 4/4
        offset = DurationConstants.WHOLE;
    }

}
