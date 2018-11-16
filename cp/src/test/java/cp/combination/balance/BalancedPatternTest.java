package cp.combination.balance;

import cp.DefaultConfig;
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

import static org.junit.jupiter.api.Assertions.assertEquals;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = DefaultConfig.class)
@ExtendWith(SpringExtension.class)
public class BalancedPatternTest {

    @Autowired
    private BalancedPattern balancedPattern;

    @Test
    public void pos5() {
        List<Note> notes = balancedPattern.pos5_X0000(DurationConstants.EIGHT * 30);
        notes.forEach(note -> System.out.println(note.getPosition() + ", " + note.getLength()));
    }

    @Test
    public void pos3() {
        List<Note> notes = balancedPattern.pos3(DurationConstants.EIGHT * 30);
        notes.forEach(note -> System.out.println(note.getPosition() + ", " + note.getLength()));
    }

    @Test
    public void pos6in30() {
        List<Note> notes = balancedPattern.pos6in30(DurationConstants.EIGHT * 30);
        notes.forEach(note -> System.out.println(note.getPosition() + ", " + note.getLength()));
    }

    @Test
    public void posRandom() {
        balancedPattern.setSize(12);
        balancedPattern.setLimitPositions(6);
        List<Note> notes = balancedPattern.posRandom(DurationConstants.EIGHT * 12);
        assertEquals(6 , notes.size());
        notes.forEach(note -> System.out.println(note.getPosition() + ", " + note.getLength()));
    }
}