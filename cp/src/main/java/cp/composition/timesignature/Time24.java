package cp.composition.timesignature;

import cp.combination.RhythmCombination;
import cp.composition.beat.BeatGroup;
import cp.model.rhythm.DurationConstants;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * Created by prombouts on 14/02/2017.
 */
@Component(value = "time24")
public class Time24 extends TimeConfig {

    @Override
    public List<RhythmCombination> getAllBeats() {
        return defaultEvenCombinations;
    }

    @Override
    public boolean randomBeatGroup() {
        return true;
    }

    @Override
    public void init() {
        super.init();
        BeatGroup defaultGroup6 = beatGroupFactory.getBeatGroupEven(DurationConstants.EIGHT);
        beatGroups.add(defaultGroup6);
//        beatsAll.add(defaultGroup6);
        minimumLength = DurationConstants.QUARTER;
        distance = new int[]{2,4,8,10,12,14,16,18,20,21,22,24,26,27,28,30,32};//minimumRhythmicValue = 12 - 4/4
        offset = DurationConstants.HALF;
    }

    @Override
    public boolean randomCombination() {
        return true;
    }

    @Override
    public List<RhythmCombination> getFixedBeatGroup() {
        return fixedEven;
    }

    @Override
    public List<RhythmCombination> getHomophonicBeatGroup() {
        return homophonicEven;
    }

}
