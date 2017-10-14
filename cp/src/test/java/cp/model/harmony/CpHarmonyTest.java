package cp.model.harmony;

import cp.model.note.Note;
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
		notes.add(note().pos(0).pc(0).pitch(60).octave(5).positionWeight(3.0).build());
		notes.add(note().pos(0).pc(4).pitch(76).octave(6).positionWeight(1.0).build());
		notes.add(note().pos(0).pc(7).pitch(79).octave(6).positionWeight(3.0).build());
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
		notes.add(note().pos(0).pc(5).pitch(65).octave(5).positionWeight(3.0).build());
		notes.add(note().pos(0).pc(11).pitch(59).octave(4).positionWeight(1.0).build());
		notes.add(note().pos(0).pc(0).pitch(60).octave(5).positionWeight(3.0).build());
		cpHarmony = new CpHarmony(notes, 0);
		double register = cpHarmony.getRegister(64);
		assertEquals(1.0, register, 0.0);
	}

	@Test
	public void testGetBassNote() {
		List<Note> notes = new ArrayList<>();
		notes.add(note().pos(0).pc(5).pitch(65).octave(5).positionWeight(3.0).build());
		notes.add(note().pos(0).pc(11).pitch(59).octave(4).positionWeight(1.0).build());
		notes.add(note().pos(0).pc(0).pitch(60).octave(5).positionWeight(3.0).build());
		cpHarmony = new CpHarmony(notes, 0);
		int bassNote = cpHarmony.getBassNote();
		assertEquals(11, bassNote);
	}

	@Test
	public void testToChordSize() {
		List<Note> notes = new ArrayList<>();
		notes.add(note().pos(0).pc(9).pitch(69).octave(5).build());
		notes.add(note().pos(0).pc(6).pitch(66).octave(4).build());
		notes.add(note().pos(0).pc(2).pitch(62).octave(5).build());
		notes.add(note().pos(0).pc(0).pitch(72).octave(5).build());
		cpHarmony = new CpHarmony(notes, 0);
		cpHarmony.toChord(3);
		assertEquals(ChordType.MAJOR, cpHarmony.getLowestChord().getChordType());
	}

	@Test
	public void testToChordSize8() {
		List<Note> notes = new ArrayList<>();
		notes.add(note().pos(0).pc(11).pitch(71).octave(5).build());
		notes.add(note().pos(0).pc(0).pitch(60).octave(4).build());
		notes.add(note().pos(0).pc(2).pitch(74).octave(5).build());
		notes.add(note().pos(0).pc(4).pitch(64).octave(5).build());
		notes.add(note().pos(0).pc(7).pitch(67).octave(5).build());
		cpHarmony = new CpHarmony(notes, 0);
		cpHarmony.toChord(3);
		assertEquals(ChordType.MAJOR, cpHarmony.getLowestChord().getChordType());
	}

}
