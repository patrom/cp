package cp.model.harmony;

import cp.model.note.Note;
import cp.model.setclass.Set;
import cp.model.setclass.TnTnIType;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.ArrayList;
import java.util.List;

import static cp.model.note.NoteBuilder.note;
import static org.junit.Assert.assertEquals;

@RunWith(MockitoJUnitRunner.class)
public class DependantHarmonyTest {

    private DependantHarmony dependantHarmony;

    private TnTnIType tnTnIType ;
    private Set set ;

    @Before
    public void setUp() {
        tnTnIType = new TnTnIType();
        set = tnTnIType.getPrimeByName("3-1");
    }

    @Test
    public void updatePitchClassesBelow() {
        dependantHarmony = new DependantHarmony(set.tntnitype, VoicingType.CLOSE);
        Note note = note().pc(11).build();
        dependantHarmony.updatePitchClassesBelow(note);
        List<Note> notes = dependantHarmony.getNotes();
        notes.forEach(n -> System.out.println(n.getPitchClass()));
        assertEquals(10, notes.get(0).getPitchClass());
        assertEquals(9, notes.get(1).getPitchClass());
    }

    @Test
    public void updatePitchClassesAbove() {
        dependantHarmony = new DependantHarmony(set.tntnitype, VoicingType.CLOSE);
        Note note = note().pc(11).build();
        dependantHarmony.updatePitchClassesAbove(note);
        List<Note> notes = dependantHarmony.getNotes();
        notes.forEach(n -> System.out.println(n.getPitchClass()));
        assertEquals(0, notes.get(0).getPitchClass());
        assertEquals(1, notes.get(1).getPitchClass());
    }

    @Test
    public void updateDependantNotesBelow() {
        dependantHarmony = new DependantHarmony(set.tntnitype, VoicingType.CLOSE);
        List<Note> notes = new ArrayList<>();
        notes.add(note().pc(11).octave(1).build());
        notes.add(note().pc(9).octave(4).build());
        notes.add(note().pc(10).octave(8).build());
        dependantHarmony.setNotes(notes);
        Note topNote = note().pitch(63).octave(5).build();
        dependantHarmony.sortDependantNotesCloseBelow(topNote);
        notes = dependantHarmony.getNotes();
        notes.forEach(n -> System.out.println(n.getPitch() + ", " + n.getOctave()));
    }

    @Test
    public void updateDependantNotesAbove() {
        dependantHarmony = new DependantHarmony(set.tntnitype, VoicingType.CLOSE);
        List<Note> notes = new ArrayList<>();
        notes.add(note().pc(0).octave(8).build());
        notes.add(note().pc(9).octave(2).build());
        notes.add(note().pc(11).octave(4).build());
        dependantHarmony.setNotes(notes);
        Note topNote = note().pitch(71).octave(5).build();
        dependantHarmony.sortDependantNotesCloseAbove(topNote);
        notes = dependantHarmony.getNotes();
        notes.forEach(n -> System.out.println(n.getPitch() + ", " + n.getOctave()));
    }

    @Test
    public void drop2Voicing() {
        dependantHarmony = new DependantHarmony(set.tntnitype, VoicingType.CLOSE);
        List<Note> notes = new ArrayList<>();
        notes.add(note().pitch(69).octave(5).build());
        notes.add(note().pitch(65).octave(5).build());
        notes.add(note().pitch(63).octave(5).build());
        dependantHarmony.setNotes(notes);
        dependantHarmony.drop2Voicing();
        notes = dependantHarmony.getNotes();
        notes.forEach(n -> System.out.println(n.getPitch() + ", " + n.getOctave()));
        assertEquals(57, notes.get(2).getPitch());
    }

    @Test
    public void drop3Voicing() {
        dependantHarmony = new DependantHarmony(set.tntnitype, VoicingType.CLOSE);
        List<Note> notes = new ArrayList<>();
        notes.add(note().pitch(69).octave(5).build());
        notes.add(note().pitch(65).octave(5).build());
        notes.add(note().pitch(63).octave(5).build());
        dependantHarmony.setNotes(notes);
        dependantHarmony.drop3Voicing();
        notes = dependantHarmony.getNotes();
        notes.forEach(n -> System.out.println(n.getPitch() + ", " + n.getOctave()));
        assertEquals(53, notes.get(2).getPitch());
    }

    @Test
    public void drop2And4Voicing() {
        dependantHarmony = new DependantHarmony(set.tntnitype, VoicingType.CLOSE);
        List<Note> notes = new ArrayList<>();
        notes.add(note().pitch(71).octave(5).build());
        notes.add(note().pitch(65).octave(5).build());
        notes.add(note().pitch(60).octave(5).build());
        dependantHarmony.setNotes(notes);
        dependantHarmony.drop2And4Voicing();
        notes = dependantHarmony.getNotes();
        notes.forEach(n -> System.out.println(n.getPitch() + ", " + n.getOctave()));
        assertEquals(59, notes.get(1).getPitch());
        assertEquals(48, notes.get(2).getPitch());
    }

    @Test
    public void dependantBelow() {
        dependantHarmony = new DependantHarmony(set.tntnitype, VoicingType.CLOSE);
        Note topNote = note().pc(11).pitch(71).octave(5).build();
        dependantHarmony.dependantBelow(topNote);
        List<Note> notes = dependantHarmony.getNotes();
        notes.forEach(n -> System.out.println(n.getPitch() + ", " + n.getOctave()));
        assertEquals(70, notes.get(0).getPitch());
        assertEquals(69, notes.get(1).getPitch());
    }

}