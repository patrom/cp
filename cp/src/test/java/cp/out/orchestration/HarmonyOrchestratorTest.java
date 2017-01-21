package cp.out.orchestration;

import cp.DefaultConfig;
import cp.composition.Composition;
import cp.generator.MelodyGenerator;
import cp.model.Motive;
import cp.model.TimeLine;
import cp.model.contour.Contour;
import cp.model.harmony.CpHarmony;
import cp.model.melody.CpMelody;
import cp.model.melody.MelodyBlock;
import cp.model.note.Note;
import cp.model.rhythm.DurationConstants;
import cp.out.instrument.keyboard.Piano;
import cp.out.instrument.register.InstrumentRegister;
import cp.out.play.InstrumentConfig;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;

import static cp.model.note.NoteBuilder.note;
import static org.junit.Assert.assertTrue;
import static org.mockito.Matchers.anyInt;
import static org.mockito.Mockito.when;

/**
 * Created by prombouts on 13/01/2017.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = DefaultConfig.class)
public class HarmonyOrchestratorTest {

    @Autowired
    @InjectMocks
    private HarmonyOrchestrator harmonyOrchestrator;
    @Mock
    private Composition composition;
    @Mock
    private InstrumentConfig instrumentConfig;
    @Mock
    private MelodyGenerator melodyGenerator;
    @Mock
    private TimeLine timeLine;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
        int voice = 5;
        when(composition.getStart()).thenReturn(0);
        when(composition.getEnd()).thenReturn(DurationConstants.WHOLE);
        when(instrumentConfig.getInstrumentForVoice(anyInt())).thenReturn(new Piano(new InstrumentRegister(48,60)));
        MelodyBlock melodyBlock = new MelodyBlock(5,voice);
        List<Note> notes = new ArrayList<>();
        notes.add(note().pos(DurationConstants.EIGHT).pc(9).build());
        notes.add(note().pos(DurationConstants.QUARTER).pc(9).build());
        notes.add(note().pos(DurationConstants.HALF).pc(9).build());
        notes.add(note().pos(DurationConstants.HALF + DurationConstants.QUARTER).pc(9).build());
        CpMelody melody = new CpMelody(notes, voice, 0 , DurationConstants.WHOLE);
        melodyBlock.addMelodyBlock(melody);
        when(melodyGenerator.generateMelodyBlockConfig(anyInt(), anyInt())).thenReturn(melodyBlock);
        when(melodyGenerator.generateMelodyBlockConfig(anyInt(), anyInt(), anyInt(), anyInt())).thenReturn(melodyBlock);
        Contour contour = new Contour(0, DurationConstants.WHOLE, 1);
        when(timeLine.getContourAtPosition(anyInt(), anyInt())).thenReturn(contour);
    }

    @Test
    public void varyRandomHarmonyNote() throws Exception {
        List<CpHarmony> harmonies = new ArrayList<>();
        List<Note> notes = new ArrayList<>();
        notes.add(note().pos(0).pc(0).voice(5).build());
        notes.add(note().pos(0).pc(4).voice(5).build());
        CpHarmony harmony = new CpHarmony(notes, 0);
        harmony.setEnd(DurationConstants.QUARTER);
        harmonies.add(harmony);

        notes = new ArrayList<>();
        notes.add(note().pos(DurationConstants.QUARTER).pc(1).build());
        notes.add(note().pos(DurationConstants.QUARTER).pc(5).build());
        harmony = new CpHarmony(notes, DurationConstants.QUARTER);
        harmony.setEnd(DurationConstants.HALF);
        harmonies.add(harmony);

        notes = new ArrayList<>();
        notes.add(note().pos(DurationConstants.HALF).pc(2).build());
        notes.add(note().pos(DurationConstants.HALF).pc(6).build());
        harmony = new CpHarmony(notes, DurationConstants.HALF);
        harmony.setEnd(DurationConstants.WHOLE);
        harmonies.add(harmony);

        List<MelodyBlock> melodyBlocks = new ArrayList<>();
        MelodyBlock melodyBlock = new MelodyBlock(5,1);
        melodyBlocks.add(melodyBlock);
        Motive motive = new Motive(melodyBlocks);
        motive.setHarmonies(harmonies);
        Predicate<Note> harmonyFilter = n -> n.getVoice() != 4;
        MelodyBlock block = harmonyOrchestrator.varyNextHarmonyNote(motive, 0, 1, harmonyFilter);
        List<Note> melodyBlockNotes = block.getMelodyBlockNotes();
        for (Note melodyBlockNote : melodyBlockNotes) {
                assertTrue(melodyBlockNote.getPitch() >= 48 || melodyBlockNote.getPitch() <= 50);
        }
    }

    @Test
    public void varyHarmonyRhythmDependant() throws Exception {
        List<CpHarmony> harmonies = new ArrayList<>();
        List<Note> notes = new ArrayList<>();
        notes.add(note().pos(0).pc(0).voice(5).build());
        notes.add(note().pos(0).pc(4).voice(5).build());
        CpHarmony harmony = new CpHarmony(notes, 0);
        harmony.setEnd(DurationConstants.QUARTER);
        harmonies.add(harmony);

        notes = new ArrayList<>();
        notes.add(note().pos(DurationConstants.QUARTER).pc(1).build());
        notes.add(note().pos(DurationConstants.QUARTER).pc(5).build());
        harmony = new CpHarmony(notes, DurationConstants.QUARTER);
        harmony.setEnd(DurationConstants.HALF);
        harmonies.add(harmony);

        notes = new ArrayList<>();
        notes.add(note().pos(DurationConstants.HALF).pc(2).build());
        notes.add(note().pos(DurationConstants.HALF).pc(6).build());
        harmony = new CpHarmony(notes, DurationConstants.HALF);
        harmony.setEnd(DurationConstants.WHOLE);
        harmonies.add(harmony);

        List<MelodyBlock> melodyBlocks = new ArrayList<>();
        MelodyBlock melodyBlock = new MelodyBlock(5,1);
        melodyBlocks.add(melodyBlock);
        Motive motive = new Motive(melodyBlocks);
        motive.setHarmonies(harmonies);
        Predicate<Note> harmonyFilter = n -> n.getVoice() != 4;
        List<MelodyBlock> blocks = harmonyOrchestrator.varyHarmonyRhythmDependant(motive, 0, 1, harmonyFilter, 3);
        for (MelodyBlock block : blocks) {
            List<Note> melodyBlockNotes = block.getMelodyBlockNotes();
            for (Note melodyBlockNote : melodyBlockNotes) {
                assertTrue(melodyBlockNote.getPitch() >= 48 || melodyBlockNote.getPitch() <= 50);
            }
        }
    }

    @Test
    public void varyOriginalNote() throws Exception {
        int voice = 2;
        MelodyBlock melodyBlock = new MelodyBlock(5,voice);
        List<Note> notes = new ArrayList<>();
        notes.add(note().pos(0).pc(1).build());
        notes.add(note().pos(DurationConstants.QUARTER).pc(2).build());
        notes.add(note().pos(DurationConstants.HALF + DurationConstants.EIGHT).pc(3).build());
        notes.add(note().pos(DurationConstants.HALF + DurationConstants.QUARTER).pc(4).build());
        CpMelody melody = new CpMelody(notes, voice, 0 , DurationConstants.WHOLE);
        melodyBlock.addMelodyBlock(melody);
        List<MelodyBlock> melodyBlocks = new ArrayList<>();
        melodyBlocks.add(melodyBlock);
        Motive motive = new Motive(melodyBlocks);

        MelodyBlock block = harmonyOrchestrator.varyOriginalNote(motive, voice , 5);
        List<Note> melodyBlockNotes = block.getMelodyBlockNotes();
        for (Note melodyBlockNote : melodyBlockNotes) {
            assertTrue(melodyBlockNote.getPitch() >= 48 || melodyBlockNote.getPitch() <= 50);
        }
    }

}