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
public class CombiNoteEvenTest {

    @Autowired
    private CombiNoteEven combiNoteEven;
    @Autowired
    private RhythmCombinations rhythmCombinations;

    @Test
    public void combiHalf() {
        List<Note> combi = combiNoteEven.combiHalf(rhythmCombinations.twoNoteEven::pos12, rhythmCombinations.threeNoteEven::pos234, DurationConstants.WHOLE, DurationConstants.EIGHT);
//        List<Note> combi = combiNoteUneven.apply(f, g, DurationConstants.QUARTER);
        combi.forEach(note -> System.out.println(note.getPosition() + ", " + note.isRest()));
    }


    @Test
    public void custom() {
        List<Note > notes = combiNoteEven.custom(DurationConstants.HALF, DurationConstants.EIGHT);
        notes.forEach(note -> System.out.println(note.getPosition() + ", " + note.getLength()));
    }

    @Test
    public void customRandom() {
        List<Note > notes = combiNoteEven.customRandom(DurationConstants.HALF, DurationConstants.EIGHT);
        notes.forEach(note -> System.out.println(note.getPosition() + ", " + note.getLength()));
    }

    @Test
    public void quintupletpos12345pos1() {
        List<Note > notes = combiNoteEven.quintupletpos12345pos1(DurationConstants.WHOLE, DurationConstants.EIGHT);
        notes.forEach(note -> System.out.println(note.getPosition() + ", " + note.getLength()));
    }
}