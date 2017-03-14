package cp.model.harmony;

import cp.model.note.Note;
import cp.model.rhythm.DurationConstants;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static cp.model.note.NoteBuilder.note;
import static org.junit.Assert.assertEquals;

public class CpHarmonyTest {
	
	private CpHarmony cpHarmony;

	@Before
	public void setUp() throws Exception {
		List<Note> notes = new ArrayList<>();
		notes.add(note().pos(0).pc(0).pitch(60).ocatve(5).positionWeight(3.0).build());
		notes.add(note().pos(DurationConstants.QUARTER).pc(4).pitch(76).ocatve(6).positionWeight(1.0).build());
		notes.add(note().pos(DurationConstants.THREE_EIGHTS).pc(7).pitch(79).ocatve(6).positionWeight(3.0).build());
		cpHarmony = new CpHarmony(notes, 0);
	}

	@Test
	public void testGetHarmonyWeight() {
		double harmonyWeight = cpHarmony.getHarmonyWeight();
		assertEquals(7.0, harmonyWeight, 0);
	}

	@Test
	public void testGetChord() {
		cpHarmony.toChord();
		Chord chord = cpHarmony.getChord();
		assertEquals(ChordType.MAJOR, chord.getChordType());
	}

	@Test
	public void testRegister() {
		List<Note> notes = new ArrayList<>();
		notes.add(note().pos(0).pc(5).pitch(65).ocatve(5).positionWeight(3.0).build());
		notes.add(note().pos(DurationConstants.QUARTER).pc(11).pitch(59).ocatve(4).positionWeight(1.0).build());
		notes.add(note().pos(DurationConstants.THREE_EIGHTS).pc(0).pitch(60).ocatve(5).positionWeight(3.0).build());
		cpHarmony = new CpHarmony(notes, 0);
		double register = cpHarmony.getRegister(64);
		assertEquals(1.0, register, 0.0);
	}

}
