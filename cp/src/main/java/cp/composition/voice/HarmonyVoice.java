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
 * Created by prombouts on 7/01/2017.
 */
@Component
public class HarmonyVoice extends VoiceConfig {

    @PostConstruct
    public void init(){
        setTimeconfig();
        volume = Dynamic.MF.getLevel();
        pitchClassGenerators.add(repeatingPitchClasses::updatePitchClasses);
        pitchClassGenerators.add(randomPitchClasses::randomPitchClasses);
        pitchClassGenerators.add(passingPitchClasses::updatePitchClasses);
//        pitchClassGenerators.add(restPitchClasses::updatePitchClasses);
        beatGroupStrategy = this::getBeatGroups;
        randomBeats = false;
        randomRhytmCombinations = false;
    }

    private List<BeatGroup> getBeatGroups(){
        List<BeatGroup> beatGroups = new ArrayList<>();
        beatGroups.add(new BeatGroupTwo(DurationConstants.QUARTER, Collections.singletonList(twoNoteEven::pos13)));
//        beatGroups.add(new BeatGroupTwo(DurationConstants.QUARTER, Collections.singletonList(fourNoteEven::pos1234)));
//        beatGroups.add(new BeatGroupTwo(DurationConstants.EIGHT, Collections.singletonList(twoNoteEven::pos12)));
        return beatGroups;
    }
}
