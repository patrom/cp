package cp.composition.timesignature;

import cp.composition.beat.BeatGroup;
import cp.model.rhythm.DurationConstants;
import org.springframework.stereotype.Component;

@Component(value="time98")
public class Time98 extends TimeConfig{

    @Override
    public void init() {
        super.init();
        BeatGroup defaultGroup = beatGroupFactory.getBeatGroupUneven(DurationConstants.EIGHT);
        allBeatgroups.add(defaultGroup);
        minimumLength = DurationConstants.EIGHT;
        distance = new int[]{3,6,9,12,15,18,20,21,22,24,26,27,28,30,32};
        offset = DurationConstants.NINE_EIGHTS;

        measureDuration = DurationConstants.NINE_EIGHTS;
    }

}
