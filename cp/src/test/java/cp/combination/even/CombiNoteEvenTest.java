package cp.combination.even;

import cp.DefaultConfig;
import cp.combination.RhythmCombinations;
import cp.model.note.Note;
import cp.model.rhythm.DurationConstants;
import org.junit.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = DefaultConfig.class)
@ExtendWith(SpringExtension.class)
public class CombiNoteEvenTest {

    @Autowired
    private CombiNoteEven combiNoteEven;
    @Autowired
    private RhythmCombinations rhythmCombinations;

    @Test
    public void combiHalf() {
        List<Note> combi = combiNoteEven.combiHalf(rhythmCombinations.twoNoteEven::pos12, rhythmCombinations.threeNoteEven::pos234, DurationConstants.WHOLE);
//        List<Note> combi = combiNoteUneven.apply(f, g, DurationConstants.QUARTER);
        combi.forEach(note -> System.out.println(note.getPosition() + ", " + note.isRest()));
    }
}