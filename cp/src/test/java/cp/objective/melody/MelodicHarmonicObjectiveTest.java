package cp.objective.melody;

import cp.DefaultConfig;
import cp.config.MelodyConfig;
import cp.model.harmony.Chord;
import cp.model.note.Note;
import cp.model.note.Scale;
import cp.model.rhythm.DurationConstants;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.ArrayList;
import java.util.List;

import static cp.model.note.NoteBuilder.note;
import static cp.model.rhythm.DurationConstants.EIGHT;
import static cp.model.rhythm.DurationConstants.HALF;
import static org.mockito.Mockito.when;

@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = DefaultConfig.class)
@ExtendWith(SpringExtension.class)
public class MelodicHarmonicObjectiveTest {

    @Autowired
    private MelodicHarmonicObjective melodicHarmonicObjective;
    @MockBean
    private MelodyConfig melodyConfig;
    @Autowired
    private MelodyHarmoniceTriChordalDissonance melodyHarmoniceTriChordalDissonance;

    @BeforeEach
    public void setup(){
    }

    @Test
    public void extractTriads() {
        List<Note> notes = new ArrayList<>();
        notes.add(note().pos(0).rest().len(HALF + EIGHT).len(HALF + EIGHT).build());
        notes.add(note().pos(HALF + EIGHT).pc(5).len(DurationConstants.EIGHT).build());
        notes.add(note().pos(DurationConstants.HALF + DurationConstants.QUARTER).pc(4).len(DurationConstants.SIXTEENTH).build());
        notes.add(note().pos(DurationConstants.HALF + DurationConstants.QUARTER + DurationConstants.SIXTEENTH).pc(8).len(DurationConstants.SIXTEENTH).build());
        notes.add(note().pos(DurationConstants.HALF + DurationConstants.QUARTER + DurationConstants.EIGHT).pc(11).len(DurationConstants.SIXTEENTH).build());
        notes.add(note().pos(DurationConstants.HALF + DurationConstants.QUARTER + DurationConstants.EIGHT + DurationConstants.SIXTEENTH).pc(10).len(DurationConstants.SIXTEENTH).build());

        notes.add(note().pos(DurationConstants.WHOLE).pc(2).len(3).len(DurationConstants.QUARTER).build());
        notes.add(note().pos(DurationConstants.WHOLE + DurationConstants.QUARTER).pc(10).len(DurationConstants.QUARTER).build());
        notes.add(note().pos(DurationConstants.WHOLE + DurationConstants.HALF).pc(11).len(DurationConstants.EIGHT).build());
        notes.add(note().pos(DurationConstants.WHOLE + DurationConstants.HALF + DurationConstants.EIGHT).pc(2).len(DurationConstants.EIGHT).build());
        notes.add(note().pos(DurationConstants.WHOLE + DurationConstants.HALF + DurationConstants.QUARTER).pc(11).len(DurationConstants.QUARTER).build());
        List<Chord> chords = melodicHarmonicObjective.extractMelodicChords(notes, 3);
        chords.forEach(chord -> System.out.println(chord.getForteName()));
    }

    @Test
    public void extractTetra() {
        List<Note> notes = new ArrayList<>();
        notes.add(note().pos(0).rest().len(HALF + EIGHT).len(HALF + EIGHT).build());
        notes.add(note().pos(HALF + EIGHT).pc(5).len(DurationConstants.EIGHT).build());
        notes.add(note().pos(DurationConstants.HALF + DurationConstants.QUARTER).pc(4).len(DurationConstants.SIXTEENTH).build());
        notes.add(note().pos(DurationConstants.HALF + DurationConstants.QUARTER + DurationConstants.SIXTEENTH).pc(8).len(DurationConstants.SIXTEENTH).build());
        notes.add(note().pos(DurationConstants.HALF + DurationConstants.QUARTER + DurationConstants.EIGHT).pc(11).len(DurationConstants.SIXTEENTH).build());
        notes.add(note().pos(DurationConstants.HALF + DurationConstants.QUARTER + DurationConstants.EIGHT + DurationConstants.SIXTEENTH).pc(10).len(DurationConstants.SIXTEENTH).build());

        notes.add(note().pos(DurationConstants.WHOLE).pc(2).len(3).len(DurationConstants.QUARTER).build());
        notes.add(note().pos(DurationConstants.WHOLE + DurationConstants.QUARTER).pc(10).len(DurationConstants.QUARTER).build());
        notes.add(note().pos(DurationConstants.WHOLE + DurationConstants.HALF).pc(11).len(DurationConstants.EIGHT).build());
        notes.add(note().pos(DurationConstants.WHOLE + DurationConstants.HALF + DurationConstants.EIGHT).pc(2).len(DurationConstants.EIGHT).build());
        notes.add(note().pos(DurationConstants.WHOLE + DurationConstants.HALF + DurationConstants.QUARTER).pc(11).len(DurationConstants.QUARTER).build());
        List<Chord> chords = melodicHarmonicObjective.extractMelodicChords(notes, 4);
        chords.forEach(chord -> System.out.println(chord.getForteName()));
    }

    @Test
    public void testMelodyDissonance() {
//        MelodyHarmonicDissonance melodyHarmonicDissonanceMock = Mockito.mock(MelodyHarmonicDissonance.class);
        when(melodyConfig.getMelodyHarmonicDissonanceForVoice(Mockito.anyInt())).thenReturn(melodyHarmoniceTriChordalDissonance);
//        when(melodyHarmonicDissonanceMock.getChordSize()).thenReturn(4);
        List<Note> notes = new ArrayList<>();
        notes.add(note().pos(0).rest().len(HALF + EIGHT).len(HALF + EIGHT).build());
        notes.add(note().pos(HALF + EIGHT).pc(7).len(DurationConstants.EIGHT).build());
        notes.add(note().pos(DurationConstants.HALF + DurationConstants.QUARTER).pc(3).len(DurationConstants.SIXTEENTH).build());
        notes.add(note().pos(DurationConstants.HALF + DurationConstants.QUARTER + DurationConstants.SIXTEENTH).pc(2).len(DurationConstants.SIXTEENTH).build());
        notes.add(note().pos(DurationConstants.HALF + DurationConstants.QUARTER + DurationConstants.EIGHT).pc(11).len(DurationConstants.SIXTEENTH).build());
        notes.add(note().pos(DurationConstants.HALF + DurationConstants.QUARTER + DurationConstants.EIGHT + DurationConstants.SIXTEENTH).pc(6).len(DurationConstants.SIXTEENTH).build());

        notes.add(note().pos(DurationConstants.WHOLE).pc(2).len(3).len(DurationConstants.QUARTER).build());
        notes.add(note().pos(DurationConstants.WHOLE + DurationConstants.QUARTER).pc(7).len(DurationConstants.QUARTER).build());
        notes.add(note().pos(DurationConstants.WHOLE + DurationConstants.HALF).pc(11).len(DurationConstants.EIGHT).build());
        notes.add(note().pos(DurationConstants.WHOLE + DurationConstants.HALF + DurationConstants.EIGHT).pc(2).len(DurationConstants.EIGHT).build());
        notes.add(note().pos(DurationConstants.WHOLE + DurationConstants.HALF + DurationConstants.QUARTER).pc(11).len(DurationConstants.QUARTER).build());

        double melodyDissonance = melodicHarmonicObjective.getMelodyDissonance(notes, 0);
        System.out.println(melodyDissonance);
    }

    @Test
    public void testMelodyDissonance2() {
        when(melodyConfig.getMelodyHarmonicDissonanceForVoice(Mockito.anyInt())).thenReturn(melodyHarmoniceTriChordalDissonance);
        List<Note> notes = new ArrayList<>();
        notes.add(note().pos(0).rest().len(HALF + EIGHT).len(HALF + EIGHT).build());
        notes.add(note().pos(HALF + EIGHT).pc(7).len(DurationConstants.EIGHT).build());
        notes.add(note().pos(DurationConstants.HALF + DurationConstants.QUARTER).pc(3).len(DurationConstants.SIXTEENTH).build());
        notes.add(note().pos(DurationConstants.HALF + DurationConstants.QUARTER + DurationConstants.SIXTEENTH).pc(0).len(DurationConstants.SIXTEENTH).build());
        notes.add(note().pos(DurationConstants.HALF + DurationConstants.QUARTER + DurationConstants.EIGHT).pc(8).len(DurationConstants.SIXTEENTH).build());

        double melodyDissonance = melodicHarmonicObjective.getMelodyDissonance(notes, 0);
        System.out.println(melodyDissonance);
    }

    @Test
    public void testScale() {
        when(melodyConfig.getMelodyHarmonicDissonanceForVoice(Mockito.anyInt())).thenReturn(melodyHarmoniceTriChordalDissonance);
        int[] pitchClasses = Scale.VARIATIONS_FOR_ORCHESTRA_OP31_HEXA1.getPitchClasses();
        List<Note> notes = new ArrayList<>();
        for (int i = 0; i < pitchClasses.length; i++) {
            int pitchClass = pitchClasses[i];
            notes.add(note().pos(0).pc(pitchClass).build());
        }
        double melodyDissonance = melodicHarmonicObjective.getMelodyDissonance(notes, 0);
        System.out.println(melodyDissonance);
    }

    @Test
    public void extractConsecutiveTriads() {
        List<Note> notes = new ArrayList<>();
        notes.add(note().pos(0).rest().len(HALF + EIGHT).len(HALF + EIGHT).build());
        notes.add(note().pos(HALF + EIGHT).pc(4).len(DurationConstants.EIGHT).build());
        notes.add(note().pos(DurationConstants.HALF + DurationConstants.QUARTER).pc(7).len(DurationConstants.SIXTEENTH).build());
        notes.add(note().pos(DurationConstants.HALF + DurationConstants.QUARTER + DurationConstants.SIXTEENTH).pc(7).len(DurationConstants.SIXTEENTH).build());
        notes.add(note().pos(DurationConstants.HALF + DurationConstants.QUARTER + DurationConstants.EIGHT).pc(11).len(DurationConstants.SIXTEENTH).build());
        notes.add(note().pos(DurationConstants.HALF + DurationConstants.QUARTER + DurationConstants.EIGHT + DurationConstants.SIXTEENTH).pc(2).len(DurationConstants.SIXTEENTH).build());

        notes.add(note().pos(DurationConstants.WHOLE).pc(2).len(3).len(DurationConstants.QUARTER).build());
        notes.add(note().pos(DurationConstants.WHOLE + DurationConstants.QUARTER).pc(10).len(DurationConstants.QUARTER).build());
        notes.add(note().pos(DurationConstants.WHOLE + DurationConstants.HALF).pc(6).len(DurationConstants.EIGHT).build());
        notes.add(note().pos(DurationConstants.WHOLE + DurationConstants.HALF + DurationConstants.EIGHT).pc(2).len(DurationConstants.EIGHT).build());
        notes.add(note().pos(DurationConstants.WHOLE + DurationConstants.HALF + DurationConstants.QUARTER).pc(11).len(DurationConstants.QUARTER).build());
        List<Chord> chords = melodicHarmonicObjective.extractConsecutiveMelodicChords(notes, 3);
        chords.forEach(chord -> System.out.println(chord.getForteName()));
    }

    @Test
    public void extractConsecutiveTetra() {
        List<Note> notes = new ArrayList<>();
        notes.add(note().pos(0).rest().len(HALF + EIGHT).len(HALF + EIGHT).build());
        notes.add(note().pos(HALF + EIGHT).pc(4).len(DurationConstants.EIGHT).build());
        notes.add(note().pos(DurationConstants.HALF + DurationConstants.QUARTER).pc(7).len(DurationConstants.SIXTEENTH).build());
        notes.add(note().pos(DurationConstants.HALF + DurationConstants.QUARTER + DurationConstants.SIXTEENTH).pc(4).len(DurationConstants.SIXTEENTH).build());
        notes.add(note().pos(DurationConstants.HALF + DurationConstants.QUARTER + DurationConstants.EIGHT).pc(11).len(DurationConstants.SIXTEENTH).build());
        notes.add(note().pos(DurationConstants.HALF + DurationConstants.QUARTER + DurationConstants.EIGHT + DurationConstants.SIXTEENTH).pc(2).len(DurationConstants.SIXTEENTH).build());

        notes.add(note().pos(DurationConstants.WHOLE).pc(7).len(3).len(DurationConstants.QUARTER).build());
        notes.add(note().pos(DurationConstants.WHOLE + DurationConstants.QUARTER).pc(5).len(DurationConstants.QUARTER).build());
        notes.add(note().pos(DurationConstants.WHOLE + DurationConstants.HALF).pc(11).len(DurationConstants.EIGHT).build());
        notes.add(note().pos(DurationConstants.WHOLE + DurationConstants.HALF + DurationConstants.EIGHT).pc(2).len(DurationConstants.EIGHT).build());
        notes.add(note().pos(DurationConstants.WHOLE + DurationConstants.HALF + DurationConstants.QUARTER).pc(6).len(DurationConstants.QUARTER).build());
        List<Chord> chords = melodicHarmonicObjective.extractConsecutiveMelodicChords(notes, 4);
        chords.forEach(chord -> System.out.println(chord.getForteName()));
    }

}