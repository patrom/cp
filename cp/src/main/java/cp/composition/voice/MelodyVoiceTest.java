package cp.composition.voice;

import cp.combination.RhythmCombination;
import cp.composition.beat.BeatGroup;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.*;

@Component
public class MelodyVoiceTest extends Voice {
//
//    @Autowired
//    private BeatGroup beatGroupThree;

    @PostConstruct
    public void init(){
        BeatGroup beatGroupThree = beatgroups.beatGroupThree;
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
//        List<RhythmCombination> beatGroups2 = new ArrayList<>();
//        beatGroups2.add(rhythmCombinations.threeNoteUneven::pos123);
//        map.put(3, beatGroups2);

        List<RhythmCombination> beatGroups4 = new ArrayList<>();
        beatGroups4.add(rhythmCombinations.twoNoteUneven::pos13);
        map.put(2, beatGroups4);
        return map;
    }

}
