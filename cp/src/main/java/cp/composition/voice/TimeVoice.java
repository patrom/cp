package cp.composition.voice;

import cp.composition.beat.BeatGroup;
import cp.composition.beat.BeatGroupTwo;
import cp.model.note.Dynamic;
import cp.model.rhythm.DurationConstants;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created by prombouts on 10/12/2016.
 */
@Component
public class TimeVoice extends VoiceConfig {

    @PostConstruct
    public void init(){
//        setTimeconfig();
        volume = Dynamic.MF.getLevel();
        pitchClassGenerators.add(repeatingPitchClasses::updatePitchClasses);
        pitchClassGenerators.add(randomPitchClasses::randomPitchClasses);
        pitchClassGenerators.add(passingPitchClasses::updatePitchClasses);
//		pitchClassGenerators.add(restPitchClasses::updatePitchClasses);
        timeConfig = time24;
        beatGroupStrategy = this::getBeatGroups;
        randomBeats = false;
        randomRhytmCombinations = false;
    }

    private List<BeatGroup> getBeatGroups(){
        List<BeatGroup> beatGroups = new ArrayList<>();
//        beatGroups.add(new BeatGroupTwo(DurationConstants.HALF, Collections.singletonList(twoNoteEven::pos13)));
        beatGroups.add(new BeatGroupTwo(DurationConstants.QUARTER, Collections.singletonList(twoNoteEven::pos12)));
        beatGroups.add(new BeatGroupTwo(DurationConstants.QUARTER, Collections.singletonList(twoNoteEven::pos23)));
        beatGroups.add(new BeatGroupTwo(DurationConstants.QUARTER, Collections.singletonList(twoNoteEven::pos34)));
//        beatGroups.add(new BeatGroupTwo(DurationConstants.QUARTER, Collections.singletonList(threeNoteEven::pos124)));
        return beatGroups;
    }
}
