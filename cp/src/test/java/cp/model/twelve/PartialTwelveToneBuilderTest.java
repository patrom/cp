package cp.model.twelve;

import cp.DefaultConfig;
import cp.combination.RhythmCombinations;
import cp.model.note.Note;
import cp.model.note.Scale;
import cp.model.rhythm.DurationConstants;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;
import java.util.stream.Stream;

import static java.util.stream.Collectors.toList;

@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = DefaultConfig.class)
@TestPropertySource(properties = "composition.voices=4")
public class PartialTwelveToneBuilderTest {

    @Autowired
    protected RhythmCombinations rhythmCombinations;

    private List<Integer> durations;

    @BeforeEach
    public void setUp(){
        durations = Stream.of(DurationConstants.QUARTER, DurationConstants.QUARTER, DurationConstants.QUARTER).collect(toList());
    }

    @Test
    public void testNotesLargerThanScale(){
        AggregateBuilder aggregateBuilder = new PartialTwelveToneBuilder(0, durations, 0,
                Scale.ALL_INTERVAL_TRETRACHORD1.getPitchClasses(),
                rhythmCombinations.twoNoteEven::pos12,
                rhythmCombinations.twoNoteEven::pos13,
                rhythmCombinations.oneNoteEven::pos3);
        aggregateBuilder.createGridrepeat();
        List<Note> notes = aggregateBuilder.getGridNotes();
        aggregateBuilder.notesLargerOrEqualThanScale(Scale.ALL_INTERVAL_TRETRACHORD1.getPitchClasses());
        System.out.println(notes.stream().filter(note -> !note.isRest()).count());
        notes.forEach(note -> System.out.println(note.getPosition() + ", " + note.getPitchClass() + ", " + note.isRest()));
    }

    @Test
    public void testAddNoteDependenciesAndPitchClasses(){
        durations = Stream.of(DurationConstants.QUARTER, DurationConstants.QUARTER).collect(toList());
        AggregateBuilder aggregateBuilder = new PartialTwelveToneBuilder(0, durations, 0,
                Scale.ALL_INTERVAL_TRETRACHORD1.getPitchClasses(),
                rhythmCombinations.oneNoteEven::pos1,
                rhythmCombinations.oneNoteEven::pos1);
        aggregateBuilder.createGridrepeat();
        List<Note> allNotes = aggregateBuilder.addNoteDependenciesAndPitchClasses(Scale.ALL_INTERVAL_TRETRACHORD1.getPitchClasses());
        System.out.println(allNotes.stream().filter(note -> !note.isRest()).count());
        for (Note note : allNotes) {
            System.out.println(note.getPosition() + ", " + note.getPitchClass()
                    + ", " + note.isRest());
        }
    }

}