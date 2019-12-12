package cp.combination.balance;

import cp.DefaultConfig;
import cp.model.note.Note;
import cp.model.rhythm.DurationConstants;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;

@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = DefaultConfig.class)
@TestPropertySource(properties = "composition.voices=4")
public class BalancedPatternTest {

    @Autowired
    private BalancedPattern balancedPattern;

    @Test
    public void pos5() {
        List<Note> notes = balancedPattern.pos5N30(DurationConstants.SIXTEENTH * 30, DurationConstants.SIXTEENTH);
        notes.forEach(note -> System.out.println(note.getPosition() + ", " + note.getLength()));
    }

    @Test
    public void pos3() {
        List<Note> notes = balancedPattern.pos3N30(DurationConstants.EIGHT * 30, DurationConstants.EIGHT);
        notes.forEach(note -> System.out.println(note.getPosition() + ", " + note.getLength()));
    }

    @Test
    public void pos6in30() {
        List<Note> notes = balancedPattern.pos6N30(DurationConstants.EIGHT * 30, DurationConstants.EIGHT);
        notes.forEach(note -> System.out.println(note.getPosition() + ", " + note.getLength()));
    }

}