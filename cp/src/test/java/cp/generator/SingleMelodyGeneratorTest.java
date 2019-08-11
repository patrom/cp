package cp.generator;

import cp.DefaultConfig;
import cp.VariationConfig;
import cp.combination.RhythmCombination;
import cp.combination.RhythmCombinations;
import cp.config.BeatGroupConfig;
import cp.model.melody.CpMelody;
import cp.model.note.Note;
import cp.model.note.Scale;
import cp.model.rhythm.DurationConstants;
import cp.out.print.Keys;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = {DefaultConfig.class, VariationConfig.class, BeatGroupConfig.class})
@ExtendWith(SpringExtension.class)
class SingleMelodyGeneratorTest {

    @Autowired
    private SingleMelodyGenerator singleMelodyGenerator;
    @Autowired
    private Keys keys;
    @Autowired
    private RhythmCombinations allRhythmCombinations;

    @Test
    void generateSingleNoteScale() {
    }

    @Test
    void testGenerateSingleNoteScale() {
    }

    @Test
    void generateSingleNote() {
        CpMelody cpMelody = singleMelodyGenerator.generateSingleNote(4, DurationConstants.SIXTEENTH);
        Note note = cpMelody.getNotes().get(0);
        assertEquals(4, note.getPitchClass());
        assertEquals(DurationConstants.SIXTEENTH, note.getLength());
    }

    @Test
    void generateNotesScale() {
        List<RhythmCombination> rhythmCombinations = new ArrayList<>();
        rhythmCombinations.add(allRhythmCombinations.threeNoteEven::pos134);
        rhythmCombinations.add(allRhythmCombinations.threeNoteEven::pos234);
//        rhythmCombinations.add(allRhythmCombinations.twoNoteEven::pos13);
        List<CpMelody> melodies = singleMelodyGenerator.generatePermutations(Scale.MAJOR_SCALE, keys.A, 3, rhythmCombinations, DurationConstants.QUARTER);
        for (CpMelody melody : melodies) {
            melody.getNotes().forEach(note -> System.out.print(note.getPitchClass()));
            System.out.println();
//            melody.getNotes().forEach(note -> System.out.print(note.getPosition()));
//            System.out.println();
        }
//        assertEquals(42, melodies.size());
    }

    @Test
    void getPitchClassForKey() {
        int pitchClassForKey = singleMelodyGenerator.getPitchClassForKey(0, keys.A);
        assertEquals(9, pitchClassForKey);
    }
}