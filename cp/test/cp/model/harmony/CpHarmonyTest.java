package cp.model.harmony;

import static cp.model.note.NoteBuilder.note;
import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import cp.model.note.Note;

public class CpHarmonyTest {
	
	private CpHarmony cpHarmony;

	@Before
	public void setUp() throws Exception {
		List<Note> notes = new ArrayList<>();
		notes.add(note().pos(0).pc(0).pitch(60).ocatve(5).positionWeight(3.0).build());
		notes.add(note().pos(12).pc(4).pitch(76).ocatve(6).positionWeight(1.0).build());
		notes.add(note().pos(18).pc(7).pitch(79).ocatve(6).positionWeight(3.0).build());
		cpHarmony = new CpHarmony(notes);
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

}
