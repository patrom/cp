package cp.model.twelve;

import cp.DefaultConfig;
import cp.combination.RhythmCombinations;
import cp.model.note.Note;
import cp.model.note.Scale;
import cp.model.rhythm.DurationConstants;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.List;
import java.util.stream.Stream;

import static java.util.stream.Collectors.toList;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = DefaultConfig.class)
public class RandomSegmentBuilderTest {

    @Autowired
    protected RhythmCombinations rhythmCombinations;

    private List<Integer> durations;

    @Before
    public void setUp(){
        durations = Stream.of(DurationConstants.QUARTER, DurationConstants.QUARTER, DurationConstants.QUARTER).collect(toList());
    }

    @Test
    public void testNotesLargerThanScale(){
        AggregateBuilder aggregateBuilder = new RandomSegmentBuilder(0, durations, 0,
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
        AggregateBuilder aggregateBuilder = new RandomSegmentBuilder(0, durations, 0,
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