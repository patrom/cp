package cp.model.harmony;

import cp.model.note.Note;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

import static cp.model.note.NoteBuilder.note;
import static java.util.stream.Collectors.toList;
import static org.junit.Assert.*;

@RunWith(MockitoJUnitRunner.class)
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

	@Test
	public void contains2NoteAnchor() {
		List<Note> notes = new ArrayList<>();
		notes.add(note().pos(0).pitch(80).octave(5).build());
		notes.add(note().pos(0).pitch(60).octave(4).build());
		notes.add(note().pos(0).pitch(70).octave(5).build());
		notes.add(note().pos(0).pitch(64).octave(5).build());
		notes.add(note().pos(0).pitch(66).octave(5).build());
		cpHarmony = new CpHarmony(notes, 0);
		boolean contains2NoteAnchor = cpHarmony.contains2NoteAnchor();
		assertTrue(contains2NoteAnchor);
	}

	@Test
	public void containsNo2NoteAnchor() {
		List<Note> notes = new ArrayList<>();
		notes.add(note().pos(0).pitch(60).octave(4).build());
		notes.add(note().pos(0).pitch(74).octave(5).build());
		notes.add(note().pos(0).pitch(64).octave(5).build());
		notes.add(note().pos(0).pitch(66).octave(5).build());
		cpHarmony = new CpHarmony(notes, 0);
		boolean contains2NoteAnchor = cpHarmony.contains2NoteAnchor();
		assertFalse(contains2NoteAnchor);
	}

	@Test
	public void contains2NoteAnchor2() {
		List<Note> notes = new ArrayList<>();
		notes.add(note().pos(0).pitch(60).octave(4).build());
		notes.add(note().pos(0).pitch(71).octave(5).build());
		notes.add(note().pos(0).pitch(64).octave(5).build());
		cpHarmony = new CpHarmony(notes, 0);
		boolean contains2NoteAnchor = cpHarmony.contains2NoteAnchor();
		assertTrue(contains2NoteAnchor);
	}

	@Test
	public void containsMinorSecond() {
        List<Integer> pitches = Stream.of(60,64,65).collect(toList());
		boolean contains2NoteAnchor = cpHarmony.containsMinorSecond(pitches);
		assertTrue(contains2NoteAnchor);
	}

	@Test
	public void containsNoMinorSecond() {
	    List<Integer> pitches = Stream.of(60,64,66).collect(toList());
		boolean contains2NoteAnchor = cpHarmony.containsMinorSecond(pitches);
		assertFalse(contains2NoteAnchor);
	}

	@Test
	public void containsNoMinorSecondFlatNinth() {
		List<Integer> pitches = Stream.of(60,64,73).collect(toList());
		boolean contains2NoteAnchor = cpHarmony.containsMinorSecond(pitches);
		assertFalse(contains2NoteAnchor);
	}

	@Test
	public void containsMinorSecond2() {
        List<Integer> pitches = Stream.of(60,63,64,62,69).sorted().collect(toList());
		boolean contains2NoteAnchor = cpHarmony.containsMinorSecond(pitches);
		assertTrue(contains2NoteAnchor);
	}

	@Test
	public void containsMinorSecondOctave() {
		List<Integer> pitches = Stream.of(60,64,77).collect(toList());
		boolean contains2NoteAnchor = cpHarmony.containsMinorSecond(pitches);
		assertTrue(contains2NoteAnchor);
	}

	@Test
	public void containsMinorSecondRoot() {
		List<Integer> pitches = Stream.of(60,61,77).collect(toList());
		boolean contains2NoteAnchor = cpHarmony.containsMinorSecond(pitches);
		assertTrue(contains2NoteAnchor);
	}

	@Test
	public void containsConsecutiveSeconds() {
        List<Integer> pitches = Stream.of(60,63,64,66,68).sorted().collect(toList());
		boolean contains2NoteAnchor = cpHarmony.containsConsecutiveSeconds(pitches);
		assertTrue(contains2NoteAnchor);
	}

	@Test
	public void containsNoConsecutiveSeconds() {
        List<Integer> pitches = Stream.of(60,63,64,66,69).sorted().collect(toList());
		boolean contains2NoteAnchor = cpHarmony.containsConsecutiveSeconds(pitches);
		assertFalse(contains2NoteAnchor);
	}

	@Test
	public void isAdditiveHarmony() {
		List<Note> notes = new ArrayList<>();
		notes.add(note().pos(0).pc(0).pitch(60).octave(4).build());
		notes.add(note().pos(0).pc(3).pitch(63).octave(5).build());
		notes.add(note().pos(0).pc(5).pitch(65).octave(5).build());
		notes.add(note().pos(0).pc(11).pitch(71).octave(5).build());
		cpHarmony = new CpHarmony(notes, 0);
        ChordType additiveChord = cpHarmony.getAdditiveChord();
        assertEquals(ChordType.ANCHOR_11, additiveChord);
	}

	@Test
	public void isNotAdditiveHarmony() {
		List<Note> notes = new ArrayList<>();
		notes.add(note().pos(0).pc(0).pitch(60).octave(4).build());
		notes.add(note().pos(0).pc(3).pitch(63).octave(5).build());
		notes.add(note().pos(0).pc(2).pitch(62).octave(5).build());
		notes.add(note().pos(0).pc(11).pitch(71).octave(5).build());
		cpHarmony = new CpHarmony(notes, 0);
        ChordType additiveChord = cpHarmony.getAdditiveChord();
        assertNull(additiveChord);
	}

	@Test
	public void isNotAdditiveHarmony2() {
		List<Note> notes = new ArrayList<>();
		notes.add(note().pos(0).pc(0).pitch(60).octave(4).build());
		notes.add(note().pos(0).pc(4).pitch(64).octave(5).build());
		notes.add(note().pos(0).pc(2).pitch(62).octave(5).build());
		notes.add(note().pos(0).pc(11).pitch(71).octave(5).build());
		cpHarmony = new CpHarmony(notes, 0);
        ChordType additiveChord = cpHarmony.getAdditiveChord();
        assertNull(additiveChord);
	}

	@Test
	public void isNotAdditiveHarmony3() {
		List<Note> notes = new ArrayList<>();
		notes.add(note().pos(0).pc(0).pitch(60).octave(4).build());
		notes.add(note().pos(0).pc(4).pitch(64).octave(5).build());
		notes.add(note().pos(0).pc(9).pitch(69).octave(5).build());
		cpHarmony = new CpHarmony(notes, 0);
        ChordType additiveChord = cpHarmony.getAdditiveChord();
        assertNull(additiveChord);
	}

	@Test
	public void isAnchor() {
		List<Note> notes = new ArrayList<>();
		notes.add(note().pos(0).pc(0).pitch(48).build());
		notes.add(note().pos(0).pc(4).pitch(64).build());
		notes.add(note().pos(0).pc(9).pitch(69).build());
		notes.add(note().pos(0).pc(5).pitch(75).build());
		cpHarmony = new CpHarmony(notes, 0);
		ChordType additiveChord = cpHarmony.getAdditiveChord();
		assertEquals(ChordType.ANCHOR_49_MIN, additiveChord);
	}

	@Test
	public void isNotAdditiveHarmony4() {
		List<Note> notes = new ArrayList<>();
		notes.add(note().pos(0).pc(0).pitch(60).build());
		notes.add(note().pos(0).pc(7).pitch(67).build());
		notes.add(note().pos(0).pc(0).pitch(72).build());
		cpHarmony = new CpHarmony(notes, 0);
		ChordType additiveChord = cpHarmony.getAdditiveChord();
		assertNull(additiveChord);
	}

	@Test
	public void isAdditiveHarmonyOctaveDoubling() {
		List<Note> notes = new ArrayList<>();
		notes.add(note().pos(0).pc(0).pitch(48).build());
		notes.add(note().pos(0).pc(0).pitch(60).build());
		notes.add(note().pos(0).pc(1).pitch(61).build());
		notes.add(note().pos(0).pc(10).pitch(58).build());
		cpHarmony = new CpHarmony(notes, 0);
		ChordType additiveChord = cpHarmony.getAdditiveChord();
		assertEquals(ChordType.ANCHOR_10, additiveChord);
	}

}
