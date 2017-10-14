package cp.composition.timesignature;

import cp.composition.beat.BeatGroup;
import cp.model.rhythm.DurationConstants;
import org.springframework.stereotype.Component;

@Component(value = "timeRandom")
public class TimeRandom extends TimeConfig {

    @Override
    public void init() {
        super.init();
        BeatGroup defaultGroup6 = beatGroupFactory.getBeatGroupEven(DurationConstants.EIGHT);
        allBeatgroups.add(defaultGroup6);
        BeatGroup defaultGroup12 = beatGroupFactory.getBeatGroupEven(DurationConstants.QUARTER);
        allBeatgroups.add(defaultGroup12);
        BeatGroup defaultGroupUneven = beatGroupFactory.getBeatGroupUneven(DurationConstants.EIGHT);
        allBeatgroups.add(defaultGroupUneven);
        minimumLength = DurationConstants.QUARTER;
        distance = new int[]{2,4,8,10,12,14,16,18,20,21,22,24,26,27,28,30,32};//minimumRhythmicValue = 12 - 4/4
        offset = DurationConstants.HALF;
        measureDuration = DurationConstants.HALF;
    }

}
