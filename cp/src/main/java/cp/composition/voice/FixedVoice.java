package cp.composition.voice;

import cp.combination.RhythmCombination;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by prombouts on 25/11/2016.
 */
@Component
public class FixedVoice extends Voice {

    @PostConstruct
    public void init(){
        setTimeconfig();

        evenRhythmCombinationsPerNoteSize = fixedEven;
        unevenRhythmCombinationsPerNoteSize = getWaltzBeatGroups();

        pitchClassGenerators.add(repeatingPitchClasses::updatePitchClasses);
        pitchClassGenerators.add(randomPitchClasses::randomPitchClasses);
        pitchClassGenerators.add(passingPitchClasses::updatePitchClasses);

    }

    private Map<Integer, List<RhythmCombination>> getWaltzBeatGroups(){
        Map<Integer, List<RhythmCombination>> map = new HashMap<>();
        List<RhythmCombination> beatGroups = new ArrayList<>();
        beatGroups.add(twoNoteUneven::pos23);
        map.put(2, beatGroups);
        return map;
    }

}
