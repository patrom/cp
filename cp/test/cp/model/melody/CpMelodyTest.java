package cp.model.melody;

import static cp.model.note.NoteBuilder.note;
import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import cp.model.note.Note;
import cp.model.note.Scale;

public class CpMelodyTest {
	
	private static Logger LOGGER = LoggerFactory.getLogger(CpMelodyTest.class);
	
	private CpMelody melody;

	@Before
	public void setUp() throws Exception {
	}

	@Test
	public void testRandomAscDesc(){
		List<Note> notes = new ArrayList<>();
		notes.add(note().pos(0).pc(0).build());
		notes.add(note().pos(12).pc(4).build());
		notes.add(note().pos(18).pc(11).build());
		notes.add(note().pos(24).pc(7).build());
		notes.add(note().pos(48).pc(9).build());
		melody = new CpMelody(notes, Scale.MAJOR_SCALE, 1);
		LOGGER.info("Contour: " + melody.getContour());
		assertEquals(5, melody.getContour().size());
	}
	
	@Test
	public void testRandomAscDescRest(){
		List<Note> notes = new ArrayList<>();
		notes.add(note().pos(0).pc(0).build());
		notes.add(note().pos(12).rest().build());
		notes.add(note().pos(18).rest().build());
		notes.add(note().pos(24).pc(7).build());
		notes.add(note().pos(48).pc(9).build());
		melody = new CpMelody(notes, Scale.MAJOR_SCALE, 1);
		LOGGER.info("Contour: " + melody.getContour());
		assertEquals(3, melody.getContour().size());
	}
	
	@Test
	public void testUpdateNextContour(){
		List<Note> notes = new ArrayList<>();
		notes.add(note().pos(0).pc(0).build());
		notes.add(note().pos(12).pc(4).build());
		notes.add(note().pos(18).pc(11).build());
		notes.add(note().pos(24).pc(7).build());
		notes.add(note().pos(48).pc(9).build());
		melody = new CpMelody(notes, Scale.MAJOR_SCALE, 1);
		melody.updateNextContour(3, 1);
		LOGGER.info("Contour: " + melody.getContour());
		assertEquals(5, melody.getContour().size());
		assertEquals(1, melody.getContour().get(3).intValue());
	}
	
	@Test
	public void testUpdatePreviousContour(){
		List<Note> notes = new ArrayList<>();
		notes.add(note().pos(0).pc(0).build());
		notes.add(note().pos(12).pc(4).build());
		notes.add(note().pos(18).pc(11).build());
		notes.add(note().pos(24).pc(7).build());
		notes.add(note().pos(48).pc(9).build());
		melody = new CpMelody(notes, Scale.MAJOR_SCALE, 1);
		melody.updatePreviousContour(1, 1);
		LOGGER.info("Contour: " + melody.getContour());
		assertEquals(5, melody.getContour().size());
		assertEquals(1, melody.getContour().get(0).intValue());
	}
	
	@Test
	public void testRemoveContour(){
		List<Note> notes = new ArrayList<>();
		notes.add(note().pos(0).pc(0).build());
		notes.add(note().pos(12).pc(4).build());
		notes.add(note().pos(18).pc(11).build());
		notes.add(note().pos(24).pc(7).build());
		notes.add(note().pos(48).pc(9).build());
		melody = new CpMelody(notes, Scale.MAJOR_SCALE, 1);
		melody.removeContour(3, 1);
		LOGGER.info("Contour: " + melody.getContour());
		assertEquals(4, melody.getContour().size());
		assertEquals(1, melody.getContour().get(2).intValue());
	}
	
	@Test
	public void testUpdateNotes(){
		List<Note> notes = new ArrayList<>();
		notes.add(note().pos(0).pc(0).build());
		notes.add(note().pos(12).pc(4).build());
		notes.add(note().pos(18).pc(11).build());
		notes.add(note().pos(24).pc(7).build());
		melody = new CpMelody(notes, Scale.MAJOR_SCALE, 0, 48);
		notes = new ArrayList<>();
		notes.add(note().pos(12).pc(2).build());
		notes.add(note().pos(24).pc(3).build());
		melody.updateNotes(notes);
		LOGGER.info("Contour: " + melody.getContour());
		assertEquals(2, melody.getContour().size());
		assertEquals(2, melody.getNotes().size());
	}
	
	@Test
	public void testUpdateRandomNote(){
		List<Note> notes = new ArrayList<>();
		notes.add(note().pos(0).pc(0).build());
		notes.add(note().pos(12).rest().build());
		notes.add(note().pos(18).pc(11).build());
		notes.add(note().pos(24).pc(7).build());
		melody = new CpMelody(notes, Scale.MAJOR_SCALE, 0, 48);
		melody.getNotes().forEach(n -> System.out.println( n.getPitchClass()));
		LOGGER.info("Contour: " + melody.getContour());
		melody.updateRandomNote();
		melody.getNotes().forEach(n -> System.out.println( n.getPitchClass()));
		LOGGER.info("Contour: " + melody.getContour());
	}
	
	@Test
	public void testUpdatePitchClasses(){
		List<Note> notes = new ArrayList<>();
		notes.add(note().pos(0).pc(0).build());
		notes.add(note().pos(12).rest().build());
		notes.add(note().pos(18).pc(2).build());
		notes.add(note().pos(24).pc(4).build());
		melody = new CpMelody(notes, Scale.MAJOR_SCALE, 0, 48);
		melody.getNotes().forEach(n -> System.out.println( n.getPitchClass()));
		melody.transposePitchClasses(2);
		List<Note> updatedNotes = melody.getNotes();
		assertEquals(4, updatedNotes.get(0).getPitchClass());
		assertEquals(5, updatedNotes.get(2).getPitchClass());
		assertEquals(7, updatedNotes.get(3).getPitchClass());
	}
	
	@Test
	public void testTransposePitchClassesInKeyD(){
		List<Note> notes = new ArrayList<>();
		notes.add(note().pos(0).pc(2).build());
		notes.add(note().pos(12).rest().build());
		notes.add(note().pos(18).pc(4).build());
		notes.add(note().pos(24).pc(6).build());
		melody = new CpMelody(notes, Scale.MAJOR_SCALE, 0, 48);
		melody.setKey(2);
		melody.getNotes().forEach(n -> System.out.println( n.getPitchClass()));
		melody.transposePitchClasses(2);
		List<Note> updatedNotes = melody.getNotes();
		assertEquals(6, updatedNotes.get(0).getPitchClass());
		assertEquals(7, updatedNotes.get(2).getPitchClass());
		assertEquals(9, updatedNotes.get(3).getPitchClass());
	}
	
	@Test
	public void testInversePitchClasses(){
		List<Note> notes = new ArrayList<>();
		notes.add(note().pos(0).pc(0).build());
		notes.add(note().pos(12).rest().build());
		notes.add(note().pos(18).pc(2).build());
		notes.add(note().pos(24).pc(4).build());
		melody = new CpMelody(notes, Scale.MAJOR_SCALE, 0, 48);
		melody.setKey(0);
		melody.getNotes().forEach(n -> System.out.println( n.getPitchClass()));
		melody.inversePitchClasses(1);
		List<Note> updatedNotes = melody.getNotes();
		assertEquals(0, updatedNotes.get(0).getPitchClass());
		assertEquals(11, updatedNotes.get(2).getPitchClass());
		assertEquals(9, updatedNotes.get(3).getPitchClass());
	}
	
	@Test
	public void testInversePitchClassesDegree2(){
		List<Note> notes = new ArrayList<>();
		notes.add(note().pos(0).pc(2).build());
		notes.add(note().pos(12).rest().build());
		notes.add(note().pos(18).pc(1).build());
		notes.add(note().pos(24).pc(6).build());
		melody = new CpMelody(notes, Scale.MAJOR_SCALE, 0, 48);
		melody.setKey(2);
		melody.getNotes().forEach(n -> System.out.println( n.getPitchClass()));
		melody.inversePitchClasses(2);
		List<Note> updatedNotes = melody.getNotes();
		assertEquals(6, updatedNotes.get(0).getPitchClass());
		assertEquals(7, updatedNotes.get(2).getPitchClass());
		assertEquals(2, updatedNotes.get(3).getPitchClass());
	}
	
}
