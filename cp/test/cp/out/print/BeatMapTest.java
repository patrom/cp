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

public class BeatMapTest {
	
	private BeatMap beatMap;

	@Before
	public void setUp() throws Exception {
		beatMap = new BeatMap();
	}

	@Test
	public void testTies(){
		List<Note> notes = new ArrayList<>();
		notes.add(note().pc(0).pitch(60).len(18).pos(0).build());
		notes.add(note().pc(2).pitch(62).len(6).pos(18).build());
		beatMap.createBeatMap(notes, 12);
		List<Note> ties = beatMap.createTies();
		assertEquals(3, ties.size());
		Note tieNote = ties.get(0);
		assertEquals(12, tieNote.getLength());
		assertEquals(0, tieNote.getPosition());
		assertTrue(tieNote.isTieStart());
		tieNote = ties.get(1);
		assertEquals(6, tieNote.getLength());
		assertEquals(12, tieNote.getPosition());
		assertTrue(tieNote.isTieEnd());
	}
	
	@Test
	public void testTies3(){
		List<Note> notes = new ArrayList<>();
		notes.add(note().pc(0).pitch(60).len(4).pos(0).build());
		notes.add(note().pc(2).pitch(62).len(4).pos(4).build());
		notes.add(note().pc(3).pitch(63).len(4).pos(8).build());
		beatMap.createBeatMap(notes, 12);
		List<Note> ties = beatMap.createTies();
		assertEquals(3, ties.size());
		Note tieNote = ties.get(0);
		assertEquals(4, tieNote.getLength());
		assertEquals(0, tieNote.getPosition());
	}
	
//	@Test
//	public void testTiesHalfNote(){
//		List<Note> notes = new ArrayList<>();
//		notes.add(note().pc(0).pitch(60).len(24).pos(0).build());
//		notes.add(note().pc(2).pitch(62).len(12).pos(24).build());
//		notes.add(note().pc(3).pitch(63).len(12).pos(36).build());
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
		notes.add(note().pc(0).pitch(60).len(24).pos(6).build());
		notes.add(note().pc(2).pitch(62).len(6).pos(30).build());
		notes.add(note().pc(3).pitch(63).len(12).pos(36).build());
		beatMap.createBeatMap(notes, 12);
		List<Note> ties = beatMap.createTies();
		assertEquals(5, ties.size());
		assertEquals(6, ties.get(0).getLength());
		assertEquals(12, ties.get(1).getLength());
		assertEquals(6, ties.get(2).getLength());
	}
	
	@Test
	public void testUpdateTies(){
		List<Note> notes = new ArrayList<>();
		notes.add(note().len(15).pc(4).pitch(64).ocatve(4).pos(0).build());
		notes.add(note().len(3).pc(2).pitch(62).ocatve(5).pos(15).build());
		notes.add(note().len(12).pc(11).pitch(59).ocatve(4).pos(18).build());
		notes.add(note().len(24).pc(7).pitch(55).ocatve(4).pos(30).build());
		
		notes.add(note().len(6).pc(2).pitch(62).ocatve(5).pos(56).build());
//		notes.add(note().len(2).pc(11).pitch(59).ocatve(4).pos(8).build());
//		notes.add(note().len(2).pc(7).pitch(55).ocatve(4).pos(10).build());
//		notes.add(note().len(24).pc(4).pitch(64).ocatve(4).pos(12).build());
		beatMap.createBeatMap(notes, 12);
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
