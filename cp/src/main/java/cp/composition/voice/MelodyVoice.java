package cp.composition.voice;

import cp.combination.RhythmCombination;
import cp.composition.beat.melody.BeatGroupMelody;
import cp.generator.pitchclass.RandomPitchClasses;
import cp.model.note.Note;
import cp.model.note.NoteUtil;
import cp.model.rhythm.DurationConstants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.*;
import java.util.stream.Collectors;

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

        positions = new Random().ints(0, 12)
                .boxed()
                .distinct()
                .limit(5).sorted().collect(Collectors.toList());

        BeatGroupMelody beatGroupMelody = new BeatGroupMelody(12, DurationConstants.SIXTEENTH, getRhythmCombinations(), Collections.singletonList(randomPitchClasses::randomPitchClasses));

//        beatGroupThree.setRhythmCombinationMap(getRhythmCombinations());
        allBeatgroups = Arrays.asList(
//                beatgroups.beatGroupMotiveTwo,
//                beatgroups.beatGroupMotiveThree
//                beatgroups.beatGroupMotiveFour
//                beatgroups.beatGroupOne,
//                beatgroups.beatGroupTwo);
                beatGroupMelody
//                beatGroupThree
//                beatgroups.beatGroupFour

        );
//        allBeatgroups = Arrays.asList(beatgroups.beatGroupMotiveOne, beatgroups.beatGroupMotiveTwo);
        setTimeconfig();
    }


    private Map<Integer, List<RhythmCombination>> getRhythmCombinations(){
        Map<Integer, List<RhythmCombination>> map = new HashMap<>();
        List<RhythmCombination> beatGroups4 = new ArrayList<>();
        beatGroups4.add(this::posRandom);
        map.put(6, beatGroups4);
        return map;
    }

    private List<Note> posNotes(int beat) {
        List<Integer> positions = Arrays.asList(0,3,4,8,10,11);
        return noteUtil.getNotesForPositions(DurationConstants.SIXTEENTH, positions, 12);
    }

    public List<Note> posRandom(int beat) {
//        int limitPositions = RandomUtil.getRandomNumberInRange(1, 10);
        positions = new Random().ints(0, 12)
                .boxed()
                .distinct()
                .limit(6).sorted().collect(Collectors.toList());
//        positions = positions.stream().map(integer -> integer = ((integer + randomNumber) % size)).sorted().collect(Collectors.toList());
        return noteUtil.getNotesForPositions(DurationConstants.SIXTEENTH, positions, 12);
    }

}
