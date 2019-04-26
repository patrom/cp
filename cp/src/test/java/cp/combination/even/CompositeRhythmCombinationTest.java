package cp.combination.even;

import cp.DefaultConfig;
import cp.combination.RhythmCombinations;
import cp.model.note.Note;
import cp.model.rhythm.DurationConstants;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;

@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = DefaultConfig.class)
@ExtendWith(SpringExtension.class)
public class CompositeRhythmCombinationTest {

    private CompositeRhythmCombination compositeRhythmCombination = new CompositeRhythmCombination();

    @Autowired
    private RhythmCombinations rhythmCombinations;

    @Test
    public void getNotes() {
        compositeRhythmCombination.addRhythmCombinations(rhythmCombinations.threeNoteUneven::pos123, rhythmCombinations.twoNoteUneven::pos13);
        List<Note> notes = compositeRhythmCombination.posEven(DurationConstants.HALF, 0);
        notes.forEach(note -> System.out.println(note.getPosition()));
    }

    @Test
    public void getNotes2() {
        compositeRhythmCombination.addRhythmCombinations(
                rhythmCombinations.threeNoteEven::pos123,
                rhythmCombinations.fourNoteEven::pos1234,
                rhythmCombinations.oneNoteEven::pos2,
                rhythmCombinations.threeNoteUneven::pos123);
        List<Note> notes = compositeRhythmCombination.posEven(DurationConstants.WHOLE, 0);
        notes.forEach(note -> System.out.println(note.getPosition() + ", rest:" + note.isRest()));
    }

    @Test
    public void getNotes3() {
        compositeRhythmCombination.addRhythmCombinations(rhythmCombinations.twoNoteUneven::pos13, rhythmCombinations.threeNoteUneven::pos123);
        List<Note> notes = compositeRhythmCombination.posEven(DurationConstants.HALF, 0);
        notes.forEach(note -> System.out.println(note.getPosition()));
    }

    @Test
    public void posDoubleFirst() {
        compositeRhythmCombination.addRhythmCombinations(rhythmCombinations.threeNoteUneven::pos123,
                rhythmCombinations.twoNoteEven::pos12,
                rhythmCombinations.twoNoteEven::pos13);
        List<Note> notes = compositeRhythmCombination.posDoubleFirst(DurationConstants.WHOLE, 0);
        notes.forEach(note -> System.out.println(note.getPosition()));
    }

    @Test
    public void posFixed() {
        compositeRhythmCombination.addRhythmCombinations(rhythmCombinations.twoNoteEven::pos13, rhythmCombinations.randomCombination::random2in3);
        List<Note> notes = compositeRhythmCombination.posFixed(0, 0);
        notes.forEach(note -> System.out.println(note.getPosition()));
    }

}