package cp.model.texture;

import cp.DefaultConfig;
import cp.model.TimeLine;
import cp.model.TimeLineKey;
import cp.model.harmony.ChordType;
import cp.model.harmony.DependantHarmony;
import cp.model.note.Note;
import cp.model.note.NoteBuilder;
import cp.model.note.Scale;
import cp.out.print.Keys;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.Collections;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.mockito.Matchers.anyInt;
import static org.mockito.Mockito.when;

/**
 * Created by prombouts on 26/05/2017.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = DefaultConfig.class)
public class TextureTest {

    @Autowired
    @InjectMocks
    private Texture texture;

    @Mock
    private TimeLine timeLine;

    @Autowired
    private Keys keys;

    @Before
    public void setUp(){
        MockitoAnnotations.initMocks(this);
        TimeLineKey timeLineKey = new TimeLineKey(keys.E, Scale.MAJOR_SCALE, 0, 0);
        when(timeLine.getTimeLineKeyAtPosition(anyInt(), anyInt())).thenReturn(timeLineKey);
    }

    @Test
    public void getTextureNotes() throws Exception {
        DependantHarmony dependantHarmony = new DependantHarmony();
        dependantHarmony.setChordType(ChordType.MAJOR);
        Note note = NoteBuilder.note().pc(1).pitch(61).voice(0).octave(5).dep(dependantHarmony).build();
        List<Note> dependantNotes = texture.getTextureNotes(Collections.singletonList(note));
        Note dependantNote = dependantNotes.get(0);
        assertEquals(4, dependantNote.getPitchClass());
        assertEquals(64, dependantNote.getPitch());
        assertEquals(5, dependantNote.getOctave());

        dependantNote = dependantNotes.get(1);
        assertEquals(8, dependantNote.getPitchClass());
        assertEquals(68, dependantNote.getPitch());
        assertEquals(5, dependantNote.getOctave());
    }

    @Test
    public void getTextureForNote() throws Exception {
        DependantHarmony dependantHarmony = new DependantHarmony();
        dependantHarmony.setChordType(ChordType.CH2_GROTE_TERTS);
        Note note = NoteBuilder.note().pc(1).pitch(61).octave(5).dep(dependantHarmony).build();
        List<Note> dependantNotes = texture.getTextureForNoteBelow(note);
        Note dependantNote = dependantNotes.get(0);
        assertEquals(4, dependantNote.getPitchClass());
        assertEquals(52, dependantNote.getPitch());
        assertEquals(4, dependantNote.getOctave());
    }

    @Test
    public void multiNoteDependency() throws Exception {
        DependantHarmony dependantHarmony = new DependantHarmony();
        dependantHarmony.setChordType(ChordType.MAJOR);
        Note note = NoteBuilder.note().pc(1).pitch(61).octave(5).dep(dependantHarmony).build();
        List<Note> dependantNotes = texture.multiNoteDependency(note, true);
        Note dependantNote = dependantNotes.get(0);
        assertEquals(4, dependantNote.getPitchClass());
        assertEquals(52, dependantNote.getPitch());
        assertEquals(4, dependantNote.getOctave());

        dependantNote = dependantNotes.get(1);
        assertEquals(8, dependantNote.getPitchClass());
        assertEquals(56, dependantNote.getPitch());
        assertEquals(4, dependantNote.getOctave());
    }

    @Test
    public void singleNoteDependencyBelow() throws Exception {
        DependantHarmony dependantHarmony = new DependantHarmony();
        dependantHarmony.setChordType(ChordType.CH2_GROTE_TERTS);
        Note note = NoteBuilder.note().pc(1).pitch(61).octave(5).dep(dependantHarmony).build();
        Note dependantNote = texture.singleNoteDependency(note, true);
        assertEquals(4, dependantNote.getPitchClass());
        assertEquals(52, dependantNote.getPitch());
        assertEquals(4, dependantNote.getOctave());
    }

    @Test
    public void singleNoteDependencyAbove() throws Exception {
        DependantHarmony dependantHarmony = new DependantHarmony();
        dependantHarmony.setChordType(ChordType.CH2_GROTE_SIXT);
        Note note = NoteBuilder.note().pc(1).pitch(61).octave(5).dep(dependantHarmony).build();
        Note dependantNote = texture.singleNoteDependency(note, false);
        assertEquals(9, dependantNote.getPitchClass());
        assertEquals(69, dependantNote.getPitch());
        assertEquals(5, dependantNote.getOctave());
    }


    @Test
    public void getIntervalClockWise() throws Exception {
    }

    @Test
    public void getDependantPitchClass() throws Exception {
        int dependantPitchClass = texture.getDependantPitchClass(NoteBuilder.note().pc(1).build(), 1);
        assertEquals(3, dependantPitchClass);
    }

}