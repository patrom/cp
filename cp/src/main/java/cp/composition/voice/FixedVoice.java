package cp.composition.voice;

import cp.combination.RhythmCombination;
import cp.model.note.Dynamic;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by prombouts on 25/11/2016.
 */
@Component
public class FixedVoice extends Voice {

    @PostConstruct
    public void init(){
        setTimeconfig();
        dynamic = Dynamic.MF;
        pitchClassGenerators.add(repeatingPitchClasses::updatePitchClasses);
        pitchClassGenerators.add(randomPitchClasses::randomPitchClasses);
        pitchClassGenerators.add(passingPitchClasses::updatePitchClasses);
//        pitchClassGenerators.add(restPitchClasses::updatePitchClasses);
//        rhythmCombinations = timeConfig.getFixedBeatGroup();
        randomBeats = false;
        randomRhythmCombinations = false;
    }

    private List<RhythmCombination> getBeatGroups(){
        List<RhythmCombination> beatGroups = new ArrayList<>();
        beatGroups.add(twoNoteEven::pos13);
        beatGroups.add(twoNoteEven::pos14);
        return beatGroups;
    }

    private List<RhythmCombination> getUnevenBeatGroups(){
        List<RhythmCombination> beatGroups = new ArrayList<>();
//        beatGroups.add(new BeatGroupThree(DurationConstants.QUARTER, Collections.singletonList(fourNoteSexTuplet::pos1456)));
//        beatGroups.add(new BeatGroupThree(DurationConstants.QUARTER, Collections.singletonList(threeNoteUneven::pos123)));
        beatGroups.add(twoNoteUneven::pos13);
        return beatGroups;
    }

    private List<RhythmCombination> getWaltzBeatGroups(){
        List<RhythmCombination> beatGroups = new ArrayList<>();
        beatGroups.add(twoNoteUneven::pos23);
        return beatGroups;
    }
}
