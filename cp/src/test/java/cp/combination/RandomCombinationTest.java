package cp.combination;

import cp.DefaultConfig;
import cp.model.note.Note;
import cp.model.rhythm.DurationConstants;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = DefaultConfig.class)
@ExtendWith(SpringExtension.class)
public class RandomCombinationTest {

    @Autowired
    private RandomCombination randomCombination;

    @Test
    public void random1in2() {
        List<Note> notes = randomCombination.random1in2(0, DurationConstants.EIGHT);
        notes.forEach(note -> System.out.println(note.getPosition() + ", " + note.getLength()));
        assertEquals(1, notes.size());
    }

    @Test
    public void random2in3() {
        List<Note> notes = randomCombination.random2in3(0, DurationConstants.EIGHT);
        notes.forEach(note -> System.out.println(note.getPosition() + ", " + note.getLength()));
        assertEquals(2, notes.size());
    }

    @Test
    public void random2in4() {
        List<Note> notes = randomCombination.random2in4(0, DurationConstants.EIGHT);
        notes.forEach(note -> System.out.println(note.getPosition() + ", " + note.getLength()));
        assertEquals(2, notes.size());
    }

    @Test
    public void random4in5() {
        List<Note> notes = randomCombination.random4in5(0, DurationConstants.SIXTEENTH_QUINTUPLET);
        notes.forEach(note -> System.out.println(note.getPosition() + ", " + note.getLength()));
        assertEquals(4, notes.size());
    }

    @Test
    public void random3in6() {
        List<Note> notes = randomCombination.random3in6(0, DurationConstants.SIXTEENTH_TRIPLET);
        notes.forEach(note -> System.out.println(note.getPosition() + ", " + note.getLength()));
        assertEquals(3, notes.size());
    }

    @Test
    public void posRandom() {
        List<Note> notes = randomCombination.posRandom(2, DurationConstants.QUARTER, 1);
        notes.forEach(note -> System.out.println(note.getPosition() + ", " + note.getLength()));
        assertEquals(1, notes.size());
    }
}