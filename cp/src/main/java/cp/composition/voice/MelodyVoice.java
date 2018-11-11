package cp.composition.voice;

import cp.combination.RhythmCombination;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.*;

/**
 * Created by prombouts on 23/11/2016.
 */
@Component
public class MelodyVoice extends Voice {

    @PostConstruct
    public void init(){
        beatGroupThree.setRhythmCombinationMap(getRhythmCombinations());
        allBeatgroups = Arrays.asList(
//                beatgroups.beatGroupMotiveTwo,
//                beatgroups.beatGroupMotiveThree
//                beatgroups.beatGroupMotiveFour
//                beatgroups.beatGroupOne,
//                beatgroups.beatGroupTwo);
                beatGroupThree
//                beatgroups.beatGroupFour

        );
//        allBeatgroups = Arrays.asList(beatgroups.beatGroupMotiveOne, beatgroups.beatGroupMotiveTwo);
        setTimeconfig();
    }


    private Map<Integer, List<RhythmCombination>> getRhythmCombinations(){
        Map<Integer, List<RhythmCombination>> map = new HashMap<>();

        List<RhythmCombination> beatGroups4 = new ArrayList<>();
        beatGroups4.add(rhythmCombinations.twoNoteUneven::pos12);
        map.put(2, beatGroups4);
        return map;
    }

}
