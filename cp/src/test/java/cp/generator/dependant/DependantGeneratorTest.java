package cp.generator.dependant;

import cp.model.TimeLine;
import cp.model.TimeLineKey;
import cp.model.harmony.ChordType;
import cp.model.harmony.DependantHarmony;
import cp.model.melody.CpMelody;
import cp.model.melody.MelodyBlock;
import cp.model.note.Note;
import cp.model.note.Scale;
import cp.model.rhythm.DurationConstants;
import cp.out.print.note.D;
import cp.out.print.note.Key;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static cp.model.note.NoteBuilder.note;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

/**
 * Created by prombouts on 27/01/2017.
 */

public class DependantGeneratorTest {

    private DependantGenerator dependantGenerator;

    private TimeLine timeLine;

    private Key D;

    @Before
    public void setUp() {
        D = new D();
        TimeLineKey timeLineKey = new TimeLineKey(D, Scale.MAJOR_SCALE, 0, 500);
        timeLine = new TimeLine();
        this.timeLine.addKeysForVoice(Collections.singletonList(timeLineKey), 1);
        dependantGenerator = new DependantGenerator(this.timeLine, 1,2,3);
    }

    @Test
    public void getDependantPitchClass() {
        Note note = note().voice(1).pc(2).build();
        int pitchClass = dependantGenerator.getDependantPitchClass(note , 2);
        assertEquals(6, pitchClass);

        pitchClass = dependantGenerator.getDependantPitchClass(note , 5);
        assertEquals(11, pitchClass);

        note = note().voice(1).pc(6).build();
        pitchClass = dependantGenerator.getDependantPitchClass(note , 2);
        assertEquals(9, pitchClass);

        note = note().voice(1).pc(11).build();
        pitchClass = dependantGenerator.getDependantPitchClass(note , 2);
        assertEquals(2, pitchClass);
    }

    @Test
    public void getInterval() {
        int pitchClass = dependantGenerator.getIntervalClockWise(0 , 9);
        assertEquals(9, pitchClass);

        pitchClass = dependantGenerator.getIntervalClockWise(7 , 4);
        assertEquals(9, pitchClass);
    }

    @Test
    public void singleNoteDependency() {
        Note note = note().voice(1).pc(2).pitch(62).build();
        DependantHarmony dependantHarmony = new DependantHarmony();
        dependantHarmony.setChordType(ChordType.CH2_GROTE_TERTS);
        note.setDependantHarmony(dependantHarmony);
        Note clone = dependantGenerator.singleNoteDependency(note);
        assertEquals(66, clone.getPitch());
    }

    @Test
    public void multiNoteDependency() {
        Note note = note().voice(1).pc(2).pitch(62).build();
        DependantHarmony dependantHarmony = new DependantHarmony();
        dependantHarmony.setChordType(ChordType.MAJOR_1);
        note.setDependantHarmony(dependantHarmony);
        NoteTuple tuple = dependantGenerator.multiNoteDependency(note);
        assertEquals(66, tuple.getFirst().getPitch());
        assertEquals(71, tuple.getSecond().getPitch());
    }

    @Test
    public void multiNoteDependencyChromatic() {
        Note note = note().voice(1).pc(0).pitch(60).build();
        DependantHarmony dependantHarmony = new DependantHarmony();
        dependantHarmony.setChordType(ChordType.MAJOR_1_CHR);
        note.setDependantHarmony(dependantHarmony);
        NoteTuple tuple = dependantGenerator.multiNoteDependency(note);
        assertEquals(63, tuple.getFirst().getPitch());
        assertEquals(68, tuple.getSecond().getPitch());
    }

    @Test
    public void generateDependantHarmonies() {
        List<Note> notes = new ArrayList<>();
        Note note = note().voice(1).pc(2).pitch(62).pos(0).build();
        DependantHarmony dependantHarmony = new DependantHarmony();
        dependantHarmony.setChordType(ChordType.CH2_GROTE_TERTS);
        note.setDependantHarmony(dependantHarmony);
        notes.add(note);

        note = note().voice(1).pc(1).pitch(61).pos(DurationConstants.QUARTER).build();
        dependantHarmony = new DependantHarmony();
        dependantHarmony.setChordType(ChordType.MAJOR_1);
        note.setDependantHarmony(dependantHarmony);
        notes.add(note);

        CpMelody melody = new CpMelody(notes,1,0,DurationConstants.HALF);
        MelodyBlock melodyBlock = new MelodyBlock(5, 1);
        melodyBlock.addMelodyBlock(melody);

        ArrayList<MelodyBlock> melodyBlocks = new ArrayList<>();
        melodyBlocks.add(melodyBlock);

        MelodyBlock dependantMelodyBlock = new MelodyBlock(5, 2);
        dependantMelodyBlock.addMelodyBlock(new CpMelody(new ArrayList<>(),2,0,DurationConstants.HALF));
        melodyBlocks.add(dependantMelodyBlock);

        MelodyBlock secondDependantMelodyBlock = new MelodyBlock(5, 3);
        secondDependantMelodyBlock.addMelodyBlock(new CpMelody(new ArrayList<>(),3,0,DurationConstants.HALF));
        melodyBlocks.add(secondDependantMelodyBlock);

        dependantGenerator.generateDependantHarmonies(melodyBlocks);
        MelodyBlock dependant1 = melodyBlocks.get(1);
        List<Note> dependantNotes = dependant1.getMelodyBlockNotesWithRests();
        assertEquals(66, dependantNotes.get(0).getPitch());
        assertEquals(64, dependantNotes.get(1).getPitch());

        MelodyBlock dependant2 = melodyBlocks.get(2);
        List<Note> dependantNotes2 = dependant2.getMelodyBlockNotesWithRests();
        assertTrue(dependantNotes2.get(0).isRest());
        assertEquals(69, dependantNotes2.get(1).getPitch());
    }

}