package cp.combination.uneven;

import cp.DefaultConfig;
import cp.combination.RhythmCombinations;
import cp.model.note.Note;
import cp.model.rhythm.DurationConstants;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;
import java.util.function.Function;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = DefaultConfig.class)
@ExtendWith(SpringExtension.class)
public class CombiNoteUnevenTest {

    @Autowired
    private CombiNoteUneven combiNoteUneven;

    @Autowired
    private RhythmCombinations rhythmCombinations;

    @Test
    public void combi() {
        Function<Integer, List<Note>> f = rhythmCombinations.threeNoteEven::pos123;
        Function<Integer, List<Note>> g = rhythmCombinations.twoNoteEven::pos12;
//        Function<Integer, List<Note>> integerVFunction = f.andThen(g);


        List<Note> notes = f.apply(DurationConstants.QUARTER);
        notes.forEach(note -> System.out.println(note.getPosition()));
        List<Note> combi = combiNoteUneven.combiHalfQuarter(rhythmCombinations.twoNoteEven::pos13, rhythmCombinations.threeNoteEven::pos123, DurationConstants.THREE_QUARTERS);
//        List<Note> combi = combiNoteUneven.apply(f, g, DurationConstants.QUARTER);
        combi.forEach(note -> System.out.println(note.getPosition()));
        Assertions.assertThat(combi.size()).isEqualTo(5);

    }
}