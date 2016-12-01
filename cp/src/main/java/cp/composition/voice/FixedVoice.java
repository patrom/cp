package cp.composition.voice;

import cp.composition.beat.BeatGroup;
import cp.composition.beat.BeatGroupTwo;
import cp.model.rhythm.DurationConstants;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created by prombouts on 25/11/2016.
 */
@Component
public class FixedVoice extends VoiceConfig {

    @PostConstruct
    public void init(){
        setTimeconfig();
        pitchClassGenerators.add(repeatingPitchClasses::updatePitchClasses);
//        pitchClassGenerators.add(randomPitchClasses::randomPitchClasses);
        pitchClassGenerators.add(passingPitchClasses::updatePitchClasses);
//        pitchClassGenerators.add(restPitchClasses::updatePitchClasses);
        beatGroupStrategy = this::getBeatGroups;
        randomBeats = false;
        randomRhytmCombinations = false;
    }

    private List<BeatGroup> getBeatGroups(){
        List<BeatGroup> beatGroups = new ArrayList<>();
        beatGroups.add(new BeatGroupTwo(DurationConstants.QUARTER, Collections.singletonList(twoNoteEven::pos14)));
        beatGroups.add(new BeatGroupTwo(DurationConstants.QUARTER, Collections.singletonList(twoNoteEven::pos12)));
        return beatGroups;
    }
}
