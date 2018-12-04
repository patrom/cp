package cp.composition.voice;

import cp.combination.RhythmCombination;
import cp.generator.pitchclass.RandomPitchClasses;
import cp.model.note.NoteUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.*;

/**
 * Created by prombouts on 23/11/2016.
 */
@Component
public class MelodyVoice extends Voice {

    @Autowired
    private RandomPitchClasses randomPitchClasses;
    @Autowired
    private NoteUtil noteUtil;

    private List<Integer> positions;

    @PostConstruct
    public void init(){
//        BeatGroupMelody beatGroupMelody = new BeatGroupMelody(0, DurationConstants.SIXTEENTH_TRIPLET, getRhythmCombinations(), Collections.singletonList(randomPitchClasses::randomPitchClasses));
//
//        CompositeBeatgroup compositeBeatgroup = new CompositeBeatgroup(DurationConstants.HALF);
//        compositeBeatgroup.addBeatGroups(beatgroups.beatGroupTwo);
//
//        positions = new Random().ints(0, 12)
//                .boxed()
//                .distinct()
//                .limit(5).sorted().collect(Collectors.toList());


//        beatGroupThree.setRhythmCombinationMap(getRhythmCombinations());
        allBeatgroups = Arrays.asList(
//                beatgroups.beatGroupMotiveTwo,
//                beatgroups.beatGroupMotiveThree
//                beatgroups.beatGroupMotiveFour
                beatgroups.beatGroupThree
//                compositeBeatgroup
//                beatgroups.beatGroupTwo);
//                beatGroupMelody
//                beatGroupThree
//                beatgroups.beatGroupFour

        );
//        allBeatgroups = Arrays.asList(beatgroups.beatGroupMotiveOne, beatgroups.beatGroupMotiveTwo);
        setTimeconfig();
    }


    private Map<Integer, List<RhythmCombination>> getRhythmCombinations(){
        Map<Integer, List<RhythmCombination>> map = new HashMap<>();
        List<RhythmCombination> beatGroups4 = new ArrayList<>();
        beatGroups4.add(rhythmCombinations.randomCombination::random2in3);
        map.put(2, beatGroups4);
        return map;
    }

}
