package cp.composition.beat.composite;

import cp.DefaultConfig;
import cp.combination.RhythmCombination;
import cp.combination.RhythmCombinations;
import cp.composition.beat.BeatGroups;
import cp.composition.beat.melody.BeatGroupMelody;
import cp.composition.voice.NoteSizeValueObject;
import cp.generator.pitchclass.RandomPitchClasses;
import cp.model.note.Note;
import cp.model.rhythm.DurationConstants;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.*;

@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = DefaultConfig.class)
@ExtendWith(SpringExtension.class)
public class CompositeBeatgroupTest {

    private CompositeBeatgroup compositeBeatgroup;
    @Autowired
    private BeatGroups beatGroups;
    @Autowired
    private RhythmCombinations rhythmCombinations;
    @Autowired
    private RandomPitchClasses randomPitchClasses;

    @Test
    public void getRhythmNotesForBeatgroupType() {
        Map<Integer, List<RhythmCombination>> map = new HashMap<>();
        List<RhythmCombination> beatGroups3 = new ArrayList<>();
        beatGroups3.add(rhythmCombinations.twoNoteEven::pos13);
        map.put(2, beatGroups3);
        BeatGroupMelody beatGroupMelody = new BeatGroupMelody(DurationConstants.HALF, DurationConstants.EIGHT, map, Collections.singletonList(randomPitchClasses::randomPitchClasses));

        map = new HashMap<>();
        List<RhythmCombination> beatGroups = new ArrayList<>();
        beatGroups.add(rhythmCombinations.twoNoteEven::pos14);
        map.put(2, beatGroups);
        BeatGroupMelody beatGroupMelody2 = new BeatGroupMelody(DurationConstants.QUARTER, DurationConstants.EIGHT, map, Collections.singletonList(randomPitchClasses::randomPitchClasses));

        compositeBeatgroup = new CompositeBeatgroup(DurationConstants.HALF);
        compositeBeatgroup.addBeatGroups(beatGroupMelody, beatGroupMelody2);
        NoteSizeValueObject noteSizeValueObject = compositeBeatgroup.getRandomRhythmNotesForBeatgroupType();
        noteSizeValueObject.getNotes().forEach(note -> System.out.println(note.getPosition() + ", " + note.getLength()));
        List<Note> notes = compositeBeatgroup.getRhythmNotesForBeatgroupType(0);
        notes.forEach(note -> System.out.println(note.getPosition() + ", " + note.getLength()));
    }

    @Test
    public void getRandomRhythmNotesForBeatgroupType() {
    }
}