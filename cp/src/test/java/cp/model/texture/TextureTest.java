package cp.model.texture;

import cp.DefaultConfig;
import cp.config.TextureConfig;
import cp.model.TimeLine;
import cp.model.TimeLineKey;
import cp.model.harmony.ChordType;
import cp.model.harmony.DependantHarmony;
import cp.model.harmony.VoicingType;
import cp.model.note.Note;
import cp.model.note.NoteBuilder;
import cp.model.note.Scale;
import cp.model.setclass.Set;
import cp.model.setclass.TnTnIType;
import cp.out.print.Keys;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Collections;
import java.util.List;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.when;

/**
 * Created by prombouts on 26/05/2017.
 */
@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = DefaultConfig.class)
@TestPropertySource(properties = "composition.voices=4")
public class TextureTest {

    private static final Logger LOGGER = LoggerFactory.getLogger(TextureTest.class.getName());

    @Autowired
    private Texture texture;

    @MockBean
    private TimeLine timeLine;

    @Autowired
    private TextureConfig textureConfig;

    @Autowired
    private Keys keys;

    @Autowired
    private TnTnIType tnTnIType;

    @BeforeEach
    public void setUp(){
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

    @ParameterizedTest
    @MethodSource("chordTypeProvider")
    void multiNoteDependencyBelow(ChordType chordType) {
        DependantHarmony dependantHarmony = new DependantHarmony();
        dependantHarmony.setChordType(chordType);
        Note note = NoteBuilder.note().pc(1).pitch(60).octave(5).dep(dependantHarmony).build();
        List<Note> notes = texture.multiNoteDependency(note, false);
        notes.forEach(n -> System.out.println(n.getPitch()));
    }

    private static Stream chordTypeProvider() {
        return Stream.of(ChordType.MAJOR_CHR);
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

    @Test
    public void symmetryNoteDependencyBelow() throws Exception {
        DependantHarmony dependantHarmony = new DependantHarmony(ChordType.SYMMETRY, 0,0);
        Note note = NoteBuilder.note().pc(1).pitch(61).octave(5).dep(dependantHarmony).build();
        Note dependantNote = texture.symmetryNoteDependencyBelow(note);
        assertEquals(11, dependantNote.getPitchClass());
        assertEquals(59, dependantNote.getPitch());
        assertEquals(4, dependantNote.getOctave());
    }

    @Test
    public void symmetryNoteDependencyAbove() throws Exception {
        DependantHarmony dependantHarmony = new DependantHarmony(ChordType.SYMMETRY, 0,0);
        Note note = NoteBuilder.note().pc(11).pitch(71).octave(5).dep(dependantHarmony).build();
        Note dependantNote = texture.symmetryNoteDependencyAbove(note);
        assertEquals(1, dependantNote.getPitchClass());
        assertEquals(73, dependantNote.getPitch());
        assertEquals(6, dependantNote.getOctave());
    }

    @Test
    public void symmetryNoteDependencyAboveTwoNoteAxis() throws Exception {
        DependantHarmony dependantHarmony = new DependantHarmony(ChordType.SYMMETRY, 0,11);
        Note note = NoteBuilder.note().pc(11).pitch(71).octave(5).dep(dependantHarmony).build();
        Note dependantNote = texture.symmetryNoteDependencyAbove(note);
        assertEquals(0, dependantNote.getPitchClass());
        assertEquals(72, dependantNote.getPitch());
        assertEquals(6, dependantNote.getOctave());
    }

    @Test
    public void symmetryNoteDependencyAboveTwoNoteAxis2() throws Exception {
        DependantHarmony dependantHarmony = new DependantHarmony(ChordType.SYMMETRY, 0,11);
        Note note = NoteBuilder.note().pc(1).pitch(61).octave(5).dep(dependantHarmony).build();
        Note dependantNote = texture.symmetryNoteDependencyAbove(note);
        assertEquals(10, dependantNote.getPitchClass());
        assertEquals(70, dependantNote.getPitch());
        assertEquals(5, dependantNote.getOctave());
    }

    @Test
    public void getAllRowMatrix() {
        Set set3_1 = tnTnIType.getPrimeByName("3-1");
        List<DependantHarmony> allRowMatrix = textureConfig.getAllRowMatrix(set3_1.tntnitype, VoicingType.CLOSE);
        for (DependantHarmony dependantHarmony : allRowMatrix) {
            Note note = NoteBuilder.note().pc(0).pitch(72).octave(6).dep(dependantHarmony).build();
            List<Note> textureForNoteAbove = texture.getTextureForNoteBelow(note);
            textureForNoteAbove.forEach(n -> System.out.println(n.getPitch()));
            System.out.println("-------------------");
        }
    }

    @Test
    public void getAllRowMatrixTetraChordalSet() {
        Set set4_27 = tnTnIType.getPrimeByName("4-27");
        List<DependantHarmony> allRowMatrix = textureConfig.getAllRowMatrix(set4_27.tntnitype, VoicingType.CLOSE);
        for (DependantHarmony dependantHarmony : allRowMatrix) {
            Note note = NoteBuilder.note().pc(0).pitch(72).octave(6).dep(dependantHarmony).build();
            List<Note> textureForNoteAbove = texture.getTextureForNoteBelow(note);
            textureForNoteAbove.forEach(n -> System.out.println(n.getPitch()));
            System.out.println("-------------------");
        }
    }

    @Test
    public void getAllRowMatrixAboveTetraChordalSet() {
        Set set4_27 = tnTnIType.getPrimeByName("4-27");
        List<DependantHarmony> allRowMatrix = textureConfig.getAllRowMatrix(set4_27.tntnitype, VoicingType.UP_2);
        for (DependantHarmony dependantHarmony : allRowMatrix) {
            Note note = NoteBuilder.note().pc(0).pitch(60).octave(5).dep(dependantHarmony).build();
            List<Note> textureForNoteAbove = texture.getTextureForNoteAbove(note);
            textureForNoteAbove.forEach(n -> System.out.println(n.getPitch()));
            System.out.println("-------------------");
        }
    }

    //not always generating thirds when not in not in scale!!
    @Test
    public void getTextureChromatic() throws Exception {
        TimeLineKey timeLineKey = new TimeLineKey(keys.C, Scale.MAJOR_SCALE, 0, 0);
        when(timeLine.getTimeLineKeyAtPosition(anyInt(), anyInt())).thenReturn(timeLineKey);
        DependantHarmony dependantHarmony = new DependantHarmony();
        dependantHarmony.setChordType(ChordType.CH2_GROTE_TERTS );
        Scale chromaticScale = Scale.CHROMATIC_SCALE;
        int[] pitchClasses = chromaticScale.getPitchClasses();
        for (int pitchClass : pitchClasses) {
            Note note = NoteBuilder.note().pc(pitchClass).pitch(60 + pitchClass).octave(5).dep(dependantHarmony).build();
            List<Note> dependantNotes = texture.getTextureForNoteAbove(note);
            System.out.print(note.getPitch() + ", ");
            dependantNotes.forEach(depNote1 -> System.out.print(depNote1.getPitch() + ", "));
            System.out.println();
        }
    }

    //not always generating triads when not in not in scale!!
    @Test
    public void getTextureChromaticChords() throws Exception {
        TimeLineKey timeLineKey = new TimeLineKey(keys.C, Scale.MAJOR_SCALE, 0, 0);
        when(timeLine.getTimeLineKeyAtPosition(anyInt(), anyInt())).thenReturn(timeLineKey);
        DependantHarmony dependantHarmony = new DependantHarmony();
        dependantHarmony.setChordType(ChordType.MAJOR);
        Scale chromaticScale = Scale.CHROMATIC_SCALE;
        int[] pitchClasses = chromaticScale.getPitchClasses();
        for (int pitchClass : pitchClasses) {
            Note note = NoteBuilder.note().pc(pitchClass).pitch(60 + pitchClass).octave(5).dep(dependantHarmony).build();
            List<Note> dependantNotes = texture.getTextureForNoteAbove(note);
            System.out.print(note.getPitch() + ", ");
            dependantNotes.forEach(depNote1 -> System.out.print(depNote1.getPitch() + ", "));
            System.out.println();
        }
    }

    @Test
    public void getDependantNotesBelowCompositionPitchClasses() {
        DependantHarmony dependantHarmony = new DependantHarmony(new int[]{1,2,4}, VoicingType.DROP_2);
        Scale chromaticScale = Scale.CHROMATIC_SCALE;
        int[] pitchClasses = chromaticScale.getPitchClasses();
        for (int pitchClass : pitchClasses) {
            Note note = NoteBuilder.note().pc(pitchClass).pitch(60 + pitchClass).octave(5).dep(dependantHarmony).build();
            List<Note> dependantNotes = texture.getDependantNotesBelowCompositionPitchClasses(note);
            System.out.print(note.getPitch() + ": ");
            dependantNotes.forEach(depNote1 -> System.out.print(depNote1.getPitch() + ", "));
            System.out.println();
        }
    }

    @Test
    public void getDependantNotesAboveCompositionPitchClasses() {
        DependantHarmony dependantHarmony = new DependantHarmony(new int[]{1,2,4}, VoicingType.CLOSE);
        Scale chromaticScale = Scale.CHROMATIC_SCALE;
        int[] pitchClasses = chromaticScale.getPitchClasses();
        for (int pitchClass : pitchClasses) {
            Note note = NoteBuilder.note().pc(pitchClass).pitch(60 + pitchClass).octave(5).dep(dependantHarmony).build();
            List<Note> dependantNotes = texture.getDependantNotesAboveCompositionPitchClasses(note);
            System.out.print(note.getPitch() + ": ");
            dependantNotes.forEach(depNote1 -> System.out.print(depNote1.getPitch() + ", "));
            System.out.println();
        }
    }

}