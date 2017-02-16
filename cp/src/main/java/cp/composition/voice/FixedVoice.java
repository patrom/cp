package cp.composition.voice;

import cp.composition.beat.BeatGroup;
import cp.composition.beat.BeatGroupThree;
import cp.composition.beat.BeatGroupTwo;
import cp.model.note.Dynamic;
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
        volume = Dynamic.MP.getLevel();
        pitchClassGenerators.add(repeatingPitchClasses::updatePitchClasses);
        pitchClassGenerators.add(randomPitchClasses::randomPitchClasses);
        pitchClassGenerators.add(passingPitchClasses::updatePitchClasses);
//        pitchClassGenerators.add(restPitchClasses::updatePitchClasses);
        beatGroupStrategy = this::getWaltzBeatGroups;
        randomBeats = false;
        randomRhytmCombinations = false;
    }

    private List<BeatGroup> getBeatGroups(){
        List<BeatGroup> beatGroups = new ArrayList<>();
        beatGroups.add(new BeatGroupTwo(DurationConstants.HALF, Collections.singletonList(twoNoteEven::pos13)));
        beatGroups.add(new BeatGroupTwo(DurationConstants.HALF, Collections.singletonList(threeNoteUneven::pos123)));
        return beatGroups;
    }

    private List<BeatGroup> getUnevenBeatGroups(){
        List<BeatGroup> beatGroups = new ArrayList<>();
        beatGroups.add(new BeatGroupThree(DurationConstants.QUARTER, Collections.singletonList(twoNoteUneven::pos13)));
        beatGroups.add(new BeatGroupThree(DurationConstants.QUARTER, Collections.singletonList(threeNoteUneven::pos123)));
        return beatGroups;
    }

    private List<BeatGroup> getWaltzBeatGroups(){
        List<BeatGroup> beatGroups = new ArrayList<>();
        beatGroups.add(new BeatGroupThree(DurationConstants.QUARTER, Collections.singletonList(twoNoteUneven::pos23)));
        return beatGroups;
    }
}
