package cp.objective.melody;

import cp.DefaultConfig;
import cp.config.MelodyConfig;
import cp.model.harmony.Chord;
import cp.model.note.Note;
import cp.model.note.Scale;
import cp.model.rhythm.DurationConstants;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.ArrayList;
import java.util.List;

import static cp.model.note.NoteBuilder.note;
import static cp.model.rhythm.DurationConstants.EIGHT;
import static cp.model.rhythm.DurationConstants.HALF;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = DefaultConfig.class)
public class MelodicHarmonicObjectiveTest {

    @InjectMocks
    @Autowired
    private MelodicHarmonicObjective melodicHarmonicObjective;
    @Mock
    private MelodyConfig melodyConfig;
    @Autowired
    private MelodyHarmoniceTriChordalDissonance melodyHarmoniceTriChordalDissonance;

    @Before
    public void setup(){
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void extractChords() {
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
        List<Chord> chords = melodicHarmonicObjective.extractChords(notes, 4);
        chords.forEach(chord -> System.out.println(chord.getForteName()));
    }

    @Test
    public void testMelodyDissonance() {
        Mockito.when(melodyConfig.getMelodyHarmonicDissonanceForVoice(Mockito.anyInt())).thenReturn(melodyHarmoniceTriChordalDissonance);
        List<Note> notes = new ArrayList<>();
        notes.add(note().pos(0).rest().len(HALF + EIGHT).len(HALF + EIGHT).build());
        notes.add(note().pos(HALF + EIGHT).pc(5).len(DurationConstants.EIGHT).build());
        notes.add(note().pos(DurationConstants.HALF + DurationConstants.QUARTER).pc(3).len(DurationConstants.SIXTEENTH).build());
        notes.add(note().pos(DurationConstants.HALF + DurationConstants.QUARTER + DurationConstants.SIXTEENTH).pc(8).len(DurationConstants.SIXTEENTH).build());
        notes.add(note().pos(DurationConstants.HALF + DurationConstants.QUARTER + DurationConstants.EIGHT).pc(11).len(DurationConstants.SIXTEENTH).build());
        notes.add(note().pos(DurationConstants.HALF + DurationConstants.QUARTER + DurationConstants.EIGHT + DurationConstants.SIXTEENTH).pc(10).len(DurationConstants.SIXTEENTH).build());

        notes.add(note().pos(DurationConstants.WHOLE).pc(2).len(3).len(DurationConstants.QUARTER).build());
        notes.add(note().pos(DurationConstants.WHOLE + DurationConstants.QUARTER).pc(10).len(DurationConstants.QUARTER).build());
        notes.add(note().pos(DurationConstants.WHOLE + DurationConstants.HALF).pc(11).len(DurationConstants.EIGHT).build());
        notes.add(note().pos(DurationConstants.WHOLE + DurationConstants.HALF + DurationConstants.EIGHT).pc(2).len(DurationConstants.EIGHT).build());
        notes.add(note().pos(DurationConstants.WHOLE + DurationConstants.HALF + DurationConstants.QUARTER).pc(11).len(DurationConstants.QUARTER).build());

        double melodyDissonance = melodicHarmonicObjective.getMelodyDissonance(notes, 0);
        System.out.println(melodyDissonance);
    }

    @Test
    public void testMelodyDissonance2() {
        Mockito.when(melodyConfig.getMelodyHarmonicDissonanceForVoice(Mockito.anyInt())).thenReturn(melodyHarmoniceTriChordalDissonance);
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
        Mockito.when(melodyConfig.getMelodyHarmonicDissonanceForVoice(Mockito.anyInt())).thenReturn(melodyHarmoniceTriChordalDissonance);
        int[] pitchClasses = Scale.VARIATIONS_FOR_ORCHESTRA_OP31_HEXA1.getPitchClasses();
        List<Note> notes = new ArrayList<>();
        for (int i = 0; i < pitchClasses.length; i++) {
            int pitchClass = pitchClasses[i];
            notes.add(note().pos(0).pc(pitchClass).build());
        }
        double melodyDissonance = melodicHarmonicObjective.getMelodyDissonance(notes, 0);
        System.out.println(melodyDissonance);
    }
}