package cp.model.twelve;

import cp.DefaultConfig;
import cp.combination.RhythmCombinations;
import cp.model.note.Note;
import cp.model.note.Scale;
import cp.model.rhythm.DurationConstants;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static cp.model.note.NoteBuilder.note;
import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = DefaultConfig.class)
@ExtendWith(SpringExtension.class)
public class TwelveToneBuilderTest {

    private TwelveToneBuilder twelveToneBuilder;

    @Autowired
    protected RhythmCombinations rhythmCombinations;

    private  List<Integer> durations;

    @BeforeEach
    public void setUp(){
        durations = Stream.of(DurationConstants.QUARTER, DurationConstants.QUARTER, DurationConstants.QUARTER, DurationConstants.QUARTER).collect(Collectors.toList());
    }

    @Test
    public void grid() {
        TwelveToneBuilder twelveToneBuilder = new TwelveToneBuilder(0, durations, 0,
                Scale.ALL_INTERVAL_TRETRACHORD1.getPitchClasses(),
                rhythmCombinations.twoNoteEven::pos13,
                rhythmCombinations.twoNoteEven::pos13,
                rhythmCombinations.twoNoteEven::pos13,
                rhythmCombinations.twoNoteEven::pos13);
        twelveToneBuilder.createGridrepeat();

        twelveToneBuilder.getGridNotes().forEach(note -> System.out.println(note.getPosition()));
    }

    @Test
    public void testFirstBeforePosition() {
        TwelveToneBuilder twelveToneBuilder = new TwelveToneBuilder(0, durations, 0,
                Scale.ALL_INTERVAL_TRETRACHORD1.getPitchClasses(),
                rhythmCombinations.twoNoteEven::pos13,
                rhythmCombinations.twoNoteEven::pos13,
                rhythmCombinations.twoNoteEven::pos13,
                rhythmCombinations.twoNoteEven::pos13);
        twelveToneBuilder.createGridrepeat();
        Optional<Integer> firstPositionAfter = twelveToneBuilder.getFirstPositionBefore(DurationConstants.HALF);
        assertEquals(DurationConstants.QUARTER + DurationConstants.EIGHT, firstPositionAfter.get().intValue());
    }

    @Test
    public void testFirstAfterPosition() {
        TwelveToneBuilder twelveToneBuilder = new TwelveToneBuilder(0, durations, 0,
                Scale.ALL_INTERVAL_TRETRACHORD1.getPitchClasses(),
                rhythmCombinations.twoNoteEven::pos13,
                rhythmCombinations.twoNoteEven::pos13,
                rhythmCombinations.twoNoteEven::pos13,
                rhythmCombinations.twoNoteEven::pos13);
        twelveToneBuilder.createGridrepeat();
        Optional<Integer> firstPositionAfter = twelveToneBuilder.getFirstPositionAfter(DurationConstants.HALF);
        assertEquals(DurationConstants.HALF + DurationConstants.EIGHT, firstPositionAfter.get().intValue());
    }

    @Test
    public void testFirstRandom() {
        TwelveToneBuilder twelveToneBuilder = new TwelveToneBuilder(0, durations, 0,
                Scale.ALL_INTERVAL_TRETRACHORD1.getPitchClasses(),
                rhythmCombinations.twoNoteEven::pos13,
                rhythmCombinations.twoNoteEven::pos13,
                rhythmCombinations.twoNoteEven::pos13,
                rhythmCombinations.twoNoteEven::pos13);
        twelveToneBuilder.createGridrepeat();
        Integer position = twelveToneBuilder.getRandomPositionBeforeOrAfter(DurationConstants.QUARTER);
        System.out.println(position);
    }

    @Test
    public void testBuild(){
        TwelveToneBuilder twelveToneBuilder = new TwelveToneBuilder(0, durations, 0,
                Scale.ALL_INTERVAL_TRETRACHORD1.getPitchClasses(),
                rhythmCombinations.twoNoteEven::pos13,
                rhythmCombinations.twoNoteEven::pos13,
                rhythmCombinations.twoNoteEven::pos13,
                rhythmCombinations.twoNoteEven::pos13);
        twelveToneBuilder.createGridrepeat();
        twelveToneBuilder.getGridNotes().forEach(note -> System.out.println(note.getPosition()));
    }

    @Test
    public void testRandomBuild(){
        TwelveToneBuilder twelveToneBuilder = new TwelveToneBuilder(0,durations, 0,
                Scale.ALL_INTERVAL_TRETRACHORD1.getPitchClasses(),
                rhythmCombinations.oneNoteEven::pos3,
                rhythmCombinations.oneNoteEven::rest,
                rhythmCombinations.twoNoteEven::pos13,
                rhythmCombinations.oneNoteEven::rest);
        twelveToneBuilder.createGridrepeat();
        List<Note> notes = twelveToneBuilder.getGridNotes();
        System.out.println(notes.stream().filter(note -> !note.isRest()).count());
        notes.forEach(note -> System.out.println(note.getPosition() + ", " + note.isRest() + ", " + note.isTriplet()));
    }

    @Test
    public void testNotesLargerThanScale(){
        TwelveToneBuilder twelveToneBuilder = new TwelveToneBuilder(0, durations, 0,
                Scale.ALL_INTERVAL_TRETRACHORD1.getPitchClasses(),
                rhythmCombinations.twoNoteEven::pos12,
                rhythmCombinations.twoNoteEven::pos13,
                rhythmCombinations.oneNoteEven::pos3,
                rhythmCombinations.threeNoteUneven::pos123);
        twelveToneBuilder.createGridrepeat();
        List<Note> notes = twelveToneBuilder.getGridNotes();
        twelveToneBuilder.notesLargerOrEqualThanScale(Scale.ALL_INTERVAL_TRETRACHORD1.getPitchClasses());
        System.out.println(notes.stream().filter(note -> !note.isRest()).count());
        notes.forEach(note -> System.out.println(note.getPosition() + ", " + note.getPitchClass() + ", " + note.isRest()));
    }

    @Test
    public void testNotesSmallerThanScale(){
        TwelveToneBuilder twelveToneBuilder = new TwelveToneBuilder(0, Collections.singletonList(DurationConstants.QUARTER), 0,
                Scale.ALL_INTERVAL_TRETRACHORD1.getPitchClasses(),
                rhythmCombinations.twoNoteEven::pos12,
                rhythmCombinations.oneNoteEven::rest,
                rhythmCombinations.oneNoteEven::pos3);
        twelveToneBuilder.createGridrepeat();
        List<Note> notes = twelveToneBuilder.getGridNotes();
        twelveToneBuilder.notesSmallerThanScale(Scale.ALL_INTERVAL_TRETRACHORD1.getPitchClasses());
        System.out.println(notes.stream().filter(note -> !note.isRest()).count());
        for (Note note : notes) {
            System.out.println(note.getPosition() + ", " + note.getPitchClass()
                    + ", " + note.isRest());
            if (note.getDependantHarmony() != null) {
                List<Note> notes1 = note.getDependantHarmony().getNotes();
                for (Note note1 : notes1) {
                    System.out.println("dependant: " + note1.getPosition() + ", " + note1.getPitchClass());
                }
            }
        }
    }


    @Test
    public void testAddDependentNotes(){
        TwelveToneBuilder twelveToneBuilder = new TwelveToneBuilder(0, Collections.singletonList(DurationConstants.HALF), 0,
                Scale.ALL_INTERVAL_TRETRACHORD1.getPitchClasses(),
                rhythmCombinations.twoNoteEven::pos13);
        twelveToneBuilder.createGridrepeat();
        List<Note> notes = new ArrayList<>();
        notes.add(note().pos(0).pc(3).build());
        notes.add(note().pos(0).pc(6).build());
        notes.add(note().pos(DurationConstants.EIGHT).pc(4).build());
        notes.add(note().pos(DurationConstants.EIGHT).pc(1).build());
        notes.add(note().pos(DurationConstants.EIGHT).pc(2).build());
        notes.add(note().pos(DurationConstants.QUARTER).pc(7).build());
        List<Note> dependentNotes = twelveToneBuilder.createNoteDependencies(notes);

        assertEquals(1,  dependentNotes.get(0).getDependantHarmony().getNotes().size());
        assertEquals(2,  dependentNotes.get(1).getDependantHarmony().getNotes().size());
        Assertions.assertNull(dependentNotes.get(2).getDependantHarmony());

        System.out.println(dependentNotes.stream().filter(note -> !note.isRest()).count());
        for (Note note : dependentNotes) {
            System.out.println(note.getPosition() + ", " + note.getPitchClass()
                    + ", " + note.isRest());
            if (note.getDependantHarmony() != null) {
                List<Note> notes1 = note.getDependantHarmony().getNotes();
                for (Note note1 : notes1) {
                    System.out.println("dependant: " + note1.getPosition() + ", " + note1.getPitchClass());
                }
            }
        }
    }
}