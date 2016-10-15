package cp.out.print;

import cp.model.note.Note;
import cp.model.rhythm.DurationConstants;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static cp.model.note.NoteBuilder.note;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class BeatMapTest {
	
	private BeatMap beatMap;

	@Before
	public void setUp() throws Exception {
		beatMap = new BeatMap();
	}

	@Test
	public void testTies(){
		List<Note> notes = new ArrayList<>();
		notes.add(note().pc(0).pitch(60).len(DurationConstants.THREE_EIGHTS).pos(0).build());
		notes.add(note().pc(2).pitch(62).len(DurationConstants.EIGHT).pos(DurationConstants.THREE_EIGHTS).build());
		beatMap.createBeatMap(notes, DurationConstants.QUARTER);
		List<Note> ties = beatMap.createTies();
		assertEquals(3, ties.size());
		Note tieNote = ties.get(0);
		assertEquals(DurationConstants.QUARTER, tieNote.getDisplayLength());
		assertEquals(0, tieNote.getPosition());
		assertTrue(tieNote.isTieStart());
		tieNote = ties.get(1);
		assertEquals(DurationConstants.EIGHT, tieNote.getDisplayLength());
		assertEquals(DurationConstants.QUARTER, tieNote.getPosition());
		assertTrue(tieNote.isTieEnd());
	}

	@Test
	public void testTiesHalf(){
		List<Note> notes = new ArrayList<>();
		notes.add(note().pc(0).pitch(60).len(DurationConstants.HALF).pos(0).build());
		notes.add(note().pc(2).pitch(62).len(DurationConstants.HALF).pos(DurationConstants.HALF).build());
		beatMap.createBeatMap(notes, DurationConstants.QUARTER);
		List<Note> ties = beatMap.createTies();
		assertEquals(4, ties.size());
		Note tieNote = ties.get(0);
		assertEquals(DurationConstants.QUARTER, tieNote.getDisplayLength());
		assertEquals(0, tieNote.getPosition());
		assertTrue(tieNote.isTieStart());
		tieNote = ties.get(1);
		assertEquals(DurationConstants.QUARTER, tieNote.getDisplayLength());
		assertEquals(DurationConstants.QUARTER, tieNote.getPosition());
		assertTrue(tieNote.isTieEnd());
		tieNote = ties.get(2);
		assertEquals(DurationConstants.QUARTER, tieNote.getDisplayLength());
		assertEquals(DurationConstants.HALF, tieNote.getPosition());
		assertTrue(tieNote.isTieStart());
		tieNote = ties.get(3);
		assertEquals(DurationConstants.QUARTER, tieNote.getDisplayLength());
		assertEquals(DurationConstants.HALF + DurationConstants.QUARTER, tieNote.getPosition());
		assertTrue(tieNote.isTieEnd());
	}
	
	@Test
	public void testTies3(){
		List<Note> notes = new ArrayList<>();
		notes.add(note().pc(0).pitch(60).len(DurationConstants.EIGHT_TRIPLET).pos(0).build());
		notes.add(note().pc(2).pitch(62).len(DurationConstants.EIGHT_TRIPLET).pos(DurationConstants.EIGHT_TRIPLET).build());
		notes.add(note().pc(3).pitch(63).len(DurationConstants.EIGHT_TRIPLET).pos(DurationConstants.EIGHT_TRIPLET * 2).build());
		beatMap.createBeatMap(notes, DurationConstants.QUARTER);
		List<Note> ties = beatMap.createTies();
		assertEquals(3, ties.size());
		Note tieNote = ties.get(0);
		assertEquals(DurationConstants.EIGHT_TRIPLET, tieNote.getDisplayLength());
		assertEquals(0, tieNote.getPosition());
	}
	
	@Test
	public void testTiesEight2(){
		List<Note> notes = new ArrayList<>();
		notes.add(note().pc(0).pitch(60).len(DurationConstants.EIGHT).pos(0).build());
		notes.add(note().pc(2).pitch(62).len(DurationConstants.QUARTER + DurationConstants.EIGHT).pos(DurationConstants.EIGHT).build());
		beatMap.createBeatMap(notes, DurationConstants.QUARTER);
		List<Note> ties = beatMap.createTies();
		assertEquals(3, ties.size());
		assertEquals(DurationConstants.EIGHT, ties.get(0).getDisplayLength());
		assertEquals(DurationConstants.EIGHT, ties.get(1).getDisplayLength());
		assertEquals(DurationConstants.QUARTER, ties.get(2).getDisplayLength());
	}
	
	@Test
	public void testUpdateTies(){
		List<Note> notes = new ArrayList<>();
		notes.add(note().len(DurationConstants.QUARTER + DurationConstants.SIXTEENTH).pc(4).pitch(64).ocatve(4).pos(0).build());
		notes.add(note().len(DurationConstants.SIXTEENTH).pc(2).pitch(62).ocatve(5).pos(DurationConstants.QUARTER + DurationConstants.SIXTEENTH).build());
		notes.add(note().len(DurationConstants.QUARTER + DurationConstants.EIGHT).pc(11).pitch(59).ocatve(4).pos(DurationConstants.QUARTER + DurationConstants.EIGHT).build());
		beatMap.createBeatMap(notes, DurationConstants.QUARTER);
		List<Note> ties = beatMap.createTies();
		assertEquals(5, ties.size());
		assertTrue(ties.get(0).isTieStart());
		assertTrue(ties.get(1).isTieEnd());
		assertTrue(ties.get(3).isTieStart());
		assertTrue(ties.get(4).isTieEnd());
	}

}
