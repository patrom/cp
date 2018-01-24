package cp.model.twelve;

import cp.DefaultConfig;
import cp.combination.RhythmCombinations;
import cp.model.note.Note;
import cp.model.note.Scale;
import cp.model.rhythm.DurationConstants;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.List;
import java.util.Optional;

import static org.junit.Assert.assertEquals;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = DefaultConfig.class)
public class TwelveToneBuilderTest {

    private TwelveToneBuilder twelveToneBuilder;

    @Autowired
    protected RhythmCombinations rhythmCombinations;


    @Test
    public void grid() {
        TwelveToneBuilder twelveToneBuilder = new TwelveToneBuilder(0, DurationConstants.QUARTER, 0,
                Scale.ALL_INTERVAL_TRETRACHORD1, 4,
                rhythmCombinations.twoNoteEven::pos13,
                rhythmCombinations.twoNoteEven::pos13,
                rhythmCombinations.twoNoteEven::pos13,
                rhythmCombinations.twoNoteEven::pos13);
        twelveToneBuilder.createGridrepeat();

        twelveToneBuilder.getGridNotes().forEach(note -> System.out.println(note.getPosition()));
    }

    @Test
    public void testFirstBeforePosition() {
        TwelveToneBuilder twelveToneBuilder = new TwelveToneBuilder(0, DurationConstants.QUARTER, 0,
                Scale.ALL_INTERVAL_TRETRACHORD1, 4,
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
        TwelveToneBuilder twelveToneBuilder = new TwelveToneBuilder(0, DurationConstants.QUARTER, 0,
                Scale.ALL_INTERVAL_TRETRACHORD1, 4,
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
        TwelveToneBuilder twelveToneBuilder = new TwelveToneBuilder(0, DurationConstants.QUARTER, 0,
                Scale.ALL_INTERVAL_TRETRACHORD1, 4,
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
        TwelveToneBuilder twelveToneBuilder = new TwelveToneBuilder(0, DurationConstants.QUARTER, 0,
                Scale.ALL_INTERVAL_TRETRACHORD1, 4,
                rhythmCombinations.twoNoteEven::pos13,
                rhythmCombinations.twoNoteEven::pos13,
                rhythmCombinations.twoNoteEven::pos13,
                rhythmCombinations.twoNoteEven::pos13);
        twelveToneBuilder.createGridrepeat();
        twelveToneBuilder.getGridNotes().forEach(note -> System.out.println(note.getPosition()));
    }

    @Test
    public void testRandomBuild(){
        TwelveToneBuilder twelveToneBuilder = new TwelveToneBuilder(0, DurationConstants.QUARTER, 0,
                Scale.ALL_INTERVAL_TRETRACHORD1, 4,
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
        TwelveToneBuilder twelveToneBuilder = new TwelveToneBuilder(0, DurationConstants.QUARTER, 0,
                Scale.ALL_INTERVAL_TRETRACHORD1, 4,
                rhythmCombinations.twoNoteEven::pos12,
                rhythmCombinations.twoNoteEven::pos13,
                rhythmCombinations.oneNoteEven::pos3,
                rhythmCombinations.threeNoteUneven::pos123);
        twelveToneBuilder.createGridrepeat();
        List<Note> notes = twelveToneBuilder.getGridNotes();
        twelveToneBuilder.notesLargerOrEqualThanScale(Scale.ALL_INTERVAL_TRETRACHORD1);
        System.out.println(notes.stream().filter(note -> !note.isRest()).count());
        notes.forEach(note -> System.out.println(note.getPosition() + ", " + note.getPitchClass() + ", " + note.isRest()));
    }

    @Test
    public void testNotesSmallerThanScale(){
        TwelveToneBuilder twelveToneBuilder = new TwelveToneBuilder(0, DurationConstants.QUARTER, 0,
                Scale.ALL_INTERVAL_TRETRACHORD1, 2,
                rhythmCombinations.twoNoteEven::pos12,
                rhythmCombinations.oneNoteEven::rest,
                rhythmCombinations.oneNoteEven::pos3);
        twelveToneBuilder.createGridrepeat();
        List<Note> notes = twelveToneBuilder.getGridNotes();
        twelveToneBuilder.notesSmallerThanScale(Scale.ALL_INTERVAL_TRETRACHORD1);
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
}