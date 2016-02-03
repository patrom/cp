package cp.model.melody;

import static cp.model.note.NoteBuilder.note;
import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationContextLoader;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import cp.DefaultConfig;
import cp.VariationConfig;
import cp.model.note.Note;
import cp.model.note.Scale;
import cp.out.print.note.NoteStep;
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {DefaultConfig.class, VariationConfig.class}, loader = SpringApplicationContextLoader.class)
public class CpMelodyTest {
	
	private static Logger LOGGER = LoggerFactory.getLogger(CpMelodyTest.class);
	
	private CpMelody melody;
	
	@Autowired
	private NoteStep C;
	@Autowired
	private NoteStep D;
	@Autowired
	private NoteStep F;
	@Autowired
	private NoteStep G;

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
		melody.setNoteStep(C);
		melody.updateRandomNote();
		melody.getNotes().forEach(n -> System.out.println( n.getPitchClass()));
		LOGGER.info("Contour: " + melody.getContour());
	}
	
	@Test
	public void testInversePitchClasses(){
		List<Note> notes = new ArrayList<>();
		notes.add(note().pos(0).pc(0).build());
		notes.add(note().pos(12).rest().build());
		notes.add(note().pos(18).pc(2).build());
		notes.add(note().pos(24).pc(4).build());
		melody = new CpMelody(notes, Scale.MAJOR_SCALE, 0, 48);
		melody.setNoteStep(C);
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
		melody.setNoteStep(D);
		melody.getNotes().forEach(n -> System.out.println( n.getPitchClass()));
		melody.inversePitchClasses(2);
		List<Note> updatedNotes = melody.getNotes();
		assertEquals(6, updatedNotes.get(0).getPitchClass());
		assertEquals(7, updatedNotes.get(2).getPitchClass());
		assertEquals(2, updatedNotes.get(3).getPitchClass());
	}
	
	@Test
	public void testTransposeInKeySameScale() {
		List<Note> notes = new ArrayList<>();
		melody = new CpMelody(notes, Scale.MAJOR_SCALE, 0, 48);
		melody.setNoteStep(G);
		notes = new ArrayList<>();
		CpMelody dependingMelody = new CpMelody(notes, Scale.MAJOR_SCALE, 0, 48);
		dependingMelody.setNoteStep(C);
		int convertedPC = melody.convertPitchClass(0, dependingMelody, 0);
		assertEquals(5, convertedPC);
	}
	
	@Test
	public void testTransposeInKeySameScale2() {
		List<Note> notes = new ArrayList<>();
		melody = new CpMelody(notes, Scale.MAJOR_SCALE, 0, 48);
		melody.setNoteStep(D);
		notes = new ArrayList<>();
		CpMelody dependingMelody = new CpMelody(notes, Scale.MAJOR_SCALE, 0, 48);
		dependingMelody.setNoteStep(G);
		int convertedPC = melody.convertPitchClass(1, dependingMelody, 0);
		assertEquals(6, convertedPC);
	}
	
	@Test
	public void testTransposeInKeyDifferentScale() {
		List<Note> notes = new ArrayList<>();
		melody = new CpMelody(notes, Scale.MAJOR_SCALE, 0, 48);
		melody.setNoteStep(C);
		notes = new ArrayList<>();
		CpMelody dependingMelody = new CpMelody(notes, Scale.HARMONIC_MINOR_SCALE, 0, 48);
		dependingMelody.setNoteStep(G);
		int convertedPC = melody.convertPitchClass(4, dependingMelody, 0);
		assertEquals(10, convertedPC);
	}
	
	@Test
	public void testTransposeInKeyDifferentScale2() {
		List<Note> notes = new ArrayList<>();
		melody = new CpMelody(notes, Scale.MAJOR_SCALE, 0, 48);
		melody.setNoteStep(D);
		notes = new ArrayList<>();
		CpMelody dependingMelody = new CpMelody(notes, Scale.HARMONIC_MINOR_SCALE, 0, 48);
		dependingMelody.setNoteStep(G);
		int convertedPC = melody.convertPitchClass(6, dependingMelody, 0);
		assertEquals(10, convertedPC);
	}
	
	@Test
	public void testTransposePitchClass() {
		List<Note> notes = new ArrayList<>();
		melody = new CpMelody(notes, Scale.MAJOR_SCALE, 0, 48);
		melody.setNoteStep(C);
		notes = new ArrayList<>();
		CpMelody dependingMelody = new CpMelody(notes, Scale.MAJOR_SCALE, 0, 48);
		dependingMelody.setNoteStep(G);
		int convertedPC = melody.transposePitchClass(11, dependingMelody);
		assertEquals(11, convertedPC);
	}
	
	@Test
	public void testTransposePitchClassReverseKey() {
		List<Note> notes = new ArrayList<>();
		melody = new CpMelody(notes, Scale.MAJOR_SCALE, 0, 48);
		melody.setNoteStep(G);
		notes = new ArrayList<>();
		CpMelody dependingMelody = new CpMelody(notes, Scale.MAJOR_SCALE, 0, 48);
		dependingMelody.setNoteStep(C);
		int convertedPC = melody.transposePitchClass(11, dependingMelody);
		assertEquals(11, convertedPC);
	}
	
	@Test
	public void testTransposePitchClass2() {
		List<Note> notes = new ArrayList<>();
		melody = new CpMelody(notes, Scale.MAJOR_SCALE, 0, 48);
		melody.setNoteStep(G);
		notes = new ArrayList<>();
		CpMelody dependingMelody = new CpMelody(notes, Scale.MAJOR_SCALE, 0, 48);
		dependingMelody.setNoteStep(D);
		int convertedPC = melody.transposePitchClass(0, dependingMelody);
		assertEquals(1, convertedPC);
	}
	
	@Test
	public void testTransposePitchClassDiffScale() {
		List<Note> notes = new ArrayList<>();
		melody = new CpMelody(notes, Scale.MAJOR_SCALE, 0, 48);
		melody.setNoteStep(G);
		notes = new ArrayList<>();
		CpMelody dependingMelody = new CpMelody(notes, Scale.HARMONIC_MINOR_SCALE, 0, 48);
		dependingMelody.setNoteStep(D);
		int convertedPC = melody.transposePitchClass(6, dependingMelody);
		assertEquals(5, convertedPC);
	}
	
}
