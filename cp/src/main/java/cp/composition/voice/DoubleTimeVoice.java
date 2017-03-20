package cp.composition.voice;

import cp.composition.beat.BeatGroup;
import cp.composition.beat.BeatGroupThree;
import cp.composition.beat.BeatGroupTwo;
import cp.model.rhythm.DurationConstants;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created by prombouts on 5/12/2016.
 */
@Component
public class DoubleTimeVoice extends Voice {

    @PostConstruct
    public void init() {
        setTimeconfig();
        pitchClassGenerators.add(repeatingPitchClasses::updatePitchClasses);
        pitchClassGenerators.add(randomPitchClasses::randomPitchClasses);
        pitchClassGenerators.add(passingPitchClasses::updatePitchClasses);
//        pitchClassGenerators.add(restPitchClasses::updatePitchClasses);
//        beatGroupStrategy = timeConfig::getBeatsDoubleLength;
        beatGroupStrategy = this::getUnevenBeatGroups;
    }

    private List<BeatGroup> getBeatGroups(){
        List<BeatGroup> beatGroups = new ArrayList<>();
        beatGroups.add(new BeatGroupTwo(DurationConstants.HALF, Collections.singletonList(twoNoteEven::pos13)));
        beatGroups.add(new BeatGroupTwo(DurationConstants.HALF, Collections.singletonList(twoNoteEven::pos12)));
        return beatGroups;
    }

    private List<BeatGroup> getUnevenBeatGroups(){
        List<BeatGroup> beatGroups = new ArrayList<>();
        beatGroups.add(new BeatGroupThree(DurationConstants.QUARTER, Collections.singletonList(twoNoteUneven::pos13)));
        beatGroups.add(new BeatGroupThree(DurationConstants.QUARTER, Collections.singletonList(threeNoteUneven::pos123)));
        return beatGroups;
    }
}
