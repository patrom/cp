package cp.out.orchestration;

import cp.DefaultConfig;
import cp.combination.even.FourNoteEven;
import cp.composition.Composition;
import cp.composition.accomp.AccompGroup;
import cp.composition.beat.BeatGroup;
import cp.composition.beat.BeatGroupTwo;
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
import java.util.Collections;
import java.util.List;
import java.util.function.Predicate;

import static cp.model.note.NoteBuilder.note;
import static org.junit.Assert.assertTrue;
import static org.mockito.Matchers.any;
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
    @Autowired
    private FourNoteEven fourNoteEven;

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
        notes.add(note().pos(0).pitch(60).pc(0).octave(5).voice(5).build());
        notes.add(note().pos(0).pitch(64).pc(4).octave(5).voice(5).build());
        CpHarmony harmony = new CpHarmony(notes, 0);
        harmony.setEnd(DurationConstants.QUARTER);
        harmonies.add(harmony);

        notes = new ArrayList<>();
        notes.add(note().pos(DurationConstants.QUARTER).pitch(59).pc(1).octave(5).build());
        notes.add(note().pos(DurationConstants.QUARTER).pitch(53).pc(5).octave(4).build());
        harmony = new CpHarmony(notes, DurationConstants.QUARTER);
        harmony.setEnd(DurationConstants.HALF);
        harmonies.add(harmony);

        notes = new ArrayList<>();
        notes.add(note().pos(DurationConstants.HALF).pitch(62).pc(2).octave(5).build());
        notes.add(note().pos(DurationConstants.HALF).pitch(66).pc(6).octave(5).build());
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

    @Test
    public void testUpdateAccomp() throws Exception {
        List<CpHarmony> harmonies = new ArrayList<>();
        List<Note> notes = new ArrayList<>();
        notes.add(note().pos(0).pc(0).pitch(60).voice(5).octave(5).build());
        notes.add(note().pos(0).pc(4).pitch(64).voice(5).octave(5).build());
        CpHarmony harmony = new CpHarmony(notes, 0);
        harmony.setEnd(DurationConstants.QUARTER);
        harmonies.add(harmony);

        notes = new ArrayList<>();
        notes.add(note().pos(DurationConstants.QUARTER).pc(1).pitch(61).octave(5).build());
        notes.add(note().pos(DurationConstants.QUARTER).pc(5).pitch(53).octave(4).build());
        harmony = new CpHarmony(notes, DurationConstants.QUARTER);
        harmony.setEnd(DurationConstants.HALF);
        harmonies.add(harmony);

        notes = new ArrayList<>();
        notes.add(note().pos(DurationConstants.HALF).pc(2).pitch(62).octave(5).build());
        notes.add(note().pos(DurationConstants.HALF).pc(6).pitch(66).octave(5).build());
        harmony = new CpHarmony(notes, DurationConstants.HALF);
        harmony.setEnd(DurationConstants.WHOLE);
        harmonies.add(harmony);

        MelodyBlock melodyBlock = new MelodyBlock(0,1);

        List<Note> accompNotes = new ArrayList<>();
        accompNotes.add(note().pos(0).build());
        accompNotes.add(note().pos(DurationConstants.EIGHT).build());
        CpMelody melody = new CpMelody(accompNotes, 1, 0 , DurationConstants.QUARTER);
        List<Integer> contour = new ArrayList<>();
        contour.add(-1);
        melody.setContour(contour);
        melodyBlock.addMelodyBlock(melody);
        accompNotes = new ArrayList<>();
        accompNotes.add(note().pos(DurationConstants.QUARTER).build());
        accompNotes.add(note().pos(DurationConstants.QUARTER + DurationConstants.EIGHT).build());
         melody = new CpMelody(accompNotes, 1, DurationConstants.QUARTER , DurationConstants.HALF);
        melody.setContour(contour);
        melodyBlock.addMelodyBlock(melody);
        accompNotes = new ArrayList<>();
        accompNotes.add(note().pos(DurationConstants.HALF).build());
        accompNotes.add(note().pos(DurationConstants.HALF + DurationConstants.EIGHT).build());
         melody = new CpMelody(accompNotes, 1, DurationConstants.HALF , DurationConstants.HALF + DurationConstants.QUARTER);
        melody.setContour(contour);
        melodyBlock.addMelodyBlock(melody);
        accompNotes = new ArrayList<>();
        accompNotes.add(note().pos(DurationConstants.HALF + DurationConstants.QUARTER).build());
        accompNotes.add(note().pos(DurationConstants.HALF + DurationConstants.QUARTER + DurationConstants.EIGHT).build());
         melody = new CpMelody(accompNotes, 1, DurationConstants.HALF + DurationConstants.QUARTER , DurationConstants.WHOLE);
        melody.setContour(contour);
        melodyBlock.addMelodyBlock(melody);

        when(melodyGenerator.generateMelodyBlockWithoutPitchClassGenerator(anyInt(), any(), anyInt())).thenReturn(melodyBlock);

        List<MelodyBlock> melodyBlocks = new ArrayList<>();
        melodyBlocks.add(melodyBlock);
        Motive motive = new Motive(melodyBlocks);
        motive.setHarmonies(harmonies);
        Predicate<Note> harmonyFilter = n -> n.getVoice() != 4;
        ArrayList<Integer> accompContour = new ArrayList<>();
        accompContour.add(1);
        BeatGroup beatGroup = new BeatGroupTwo(DurationConstants.EIGHT, Collections.singletonList(fourNoteEven::pos1234));
        AccompGroup accompGroup = new AccompGroup(beatGroup, accompContour);
        harmonyOrchestrator.updateAccomp(motive, accompGroup,  1, harmonyFilter);
        List<Note> melodyBlockNotes = motive.getMelodyBlock(1).getMelodyBlockNotes();
        melodyBlockNotes.forEach(n -> System.out.println(n));
//        for (Note melodyBlockNote : melodyBlockNotes) {
//            assertTrue(melodyBlockNote.getPitch() >= 48 || melodyBlockNote.getPitch() <= 50);
//        }
    }


//    @Test
//    public void getChordsRhythmDependant() throws Exception {
//        List<CpHarmony> harmonies = new ArrayList<>();
//        List<Note> notes = new ArrayList<>();
//        notes.add(note().pos(0).pc(0).voice(0).build());
//        notes.add(note().pos(0).pc(4).voice(1).build());
//        CpHarmony harmony = new CpHarmony(notes, 0);
//        harmony.setEnd(DurationConstants.QUARTER);
//        harmonies.add(harmony);
//
//        notes = new ArrayList<>();
//        notes.add(note().pos(DurationConstants.QUARTER).pc(1).voice(0).build());
//        notes.add(note().pos(DurationConstants.QUARTER).pc(5).voice(1).build());
//        harmony = new CpHarmony(notes, DurationConstants.QUARTER);
//        harmony.setEnd(DurationConstants.HALF);
//        harmonies.add(harmony);
//
//        notes = new ArrayList<>();
//        notes.add(note().pos(DurationConstants.HALF).pc(2).voice(0).build());
//        notes.add(note().pos(DurationConstants.HALF).pc(6).voice(1).build());
//        harmony = new CpHarmony(notes, DurationConstants.HALF);
//        harmony.setEnd(DurationConstants.WHOLE);
//        harmonies.add(harmony);
//
//        List<MelodyBlock> melodyBlocks = new ArrayList<>();
//        MelodyBlock melodyBlock = new MelodyBlock(5,3);
//        melodyBlocks.add(melodyBlock);
//        Motive motive = new Motive(melodyBlocks);
//        motive.setHarmonies(harmonies);
//        Predicate<Note> harmonyFilter = n -> n.getVoice() != 3;
//        MelodyBlock block = harmonyOrchestrator.getChordsRhythmDependant(motive,  2, harmonyFilter, 3);
//        List<Note> melodyBlockNotesWithRests = block.getMelodyBlockNotesWithRests();
//        for (Note note : melodyBlockNotesWithRests) {
//            assertEquals(2, note.getVoice());
//
//        }
//
//    }


}