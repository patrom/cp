package cp.out.print;

import static cp.model.note.NoteBuilder.note;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;

import org.junit.Before;
import org.junit.Test;

import cp.model.note.Note;
import cp.model.rhythm.DurationConstants;

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
		assertEquals(DurationConstants.QUARTER, tieNote.getLength());
		assertEquals(0, tieNote.getPosition());
		assertTrue(tieNote.isTieStart());
		tieNote = ties.get(1);
		assertEquals(DurationConstants.EIGHT, tieNote.getLength());
		assertEquals(DurationConstants.QUARTER, tieNote.getPosition());
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
		assertEquals(DurationConstants.EIGHT_TRIPLET, tieNote.getLength());
		assertEquals(0, tieNote.getPosition());
	}
	
//	@Test
//	public void testTiesHalfNote(){
//		List<Note> notes = new ArrayList<>();
//		notes.add(note().pc(0).pitch(60).len(DurationConstants.HALF).pos(0).build());
//		notes.add(note().pc(2).pitch(62).len(DurationConstants.QUARTER).pos(DurationConstants.HALF).build());
//		notes.add(note().pc(3).pitch(63).len(DurationConstants.QUARTER).pos(DurationConstants.SIX_EIGHTS).build());
//		beatMap.createBeatMap(notes, 12);
//		Predicate<Note> filteredNotes = n -> n.getLength() != 24 || !( n.getLength() == 24 && (n.getPosition() == 0 || n.getPosition() == 12 || n.getPosition() == 24));
//		List<Note> ties = beatMap.createTies(filteredNotes);
//		assertEquals(3, ties.size());
//		Note noTie = ties.get(0);
//		assertEquals(24, noTie.getLength());
//		assertEquals(0, noTie.getPosition());
//	}
	
	@Test
	public void testTiesHalfNote2(){
		List<Note> notes = new ArrayList<>();
		notes.add(note().pc(0).pitch(60).len(DurationConstants.HALF).pos(6).build());
		notes.add(note().pc(2).pitch(62).len(DurationConstants.EIGHT).pos(30).build());
		notes.add(note().pc(3).pitch(63).len(DurationConstants.QUARTER).pos(DurationConstants.SIX_EIGHTS).build());
		beatMap.createBeatMap(notes, DurationConstants.QUARTER);
		List<Note> ties = beatMap.createTies();
		assertEquals(5, ties.size());
		assertEquals(DurationConstants.EIGHT, ties.get(0).getLength());
		assertEquals(DurationConstants.QUARTER, ties.get(1).getLength());
		assertEquals(DurationConstants.EIGHT, ties.get(2).getLength());
	}
	
	@Test
	public void testUpdateTies(){
		List<Note> notes = new ArrayList<>();
		notes.add(note().len(15).pc(4).pitch(64).ocatve(4).pos(0).build());
		notes.add(note().len(3).pc(2).pitch(62).ocatve(5).pos(15).build());
		notes.add(note().len(DurationConstants.QUARTER).pc(11).pitch(59).ocatve(4).pos(DurationConstants.THREE_EIGHTS).build());
		notes.add(note().len(DurationConstants.HALF).pc(7).pitch(55).ocatve(4).pos(30).build());
		
		notes.add(note().len(DurationConstants.EIGHT).pc(2).pitch(62).ocatve(5).pos(56).build());
//		notes.add(note().len(2).pc(11).pitch(59).ocatve(4).pos(8).build());
//		notes.add(note().len(2).pc(7).pitch(55).ocatve(4).pos(10).build());
//		notes.add(note().len(DurationConstants.HALF).pc(4).pitch(64).ocatve(4).pos(DurationConstants.QUARTER).build());
		beatMap.createBeatMap(notes, DurationConstants.QUARTER);
		List<Note> ties = beatMap.createTies();
		assertEquals(9, ties.size());
		assertTrue(ties.get(0).isTieStart());
		assertTrue(ties.get(1).isTieEnd());
		assertTrue(ties.get(3).isTieStart());
		assertTrue(ties.get(4).isTieEnd());
		assertTrue(ties.get(5).isTieStart());
		assertTrue(ties.get(6).isTieEnd());
		assertTrue(ties.get(6).isTieStart());
		assertTrue(ties.get(7).isTieEnd());
		ties.forEach(n -> System.out.println(n.getPosition() + ", " + n.isTieStart() + " ," + n.isTieEnd()));
	}

}
