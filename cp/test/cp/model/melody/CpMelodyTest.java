package cp.model.melody;

import static cp.model.note.NoteBuilder.note;
import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.Collections;
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
import cp.model.TimeLine;
import cp.model.TimeLineKey;
import cp.model.note.Note;
import cp.model.note.Scale;
import cp.out.print.note.Key;
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {DefaultConfig.class, VariationConfig.class}, loader = SpringApplicationContextLoader.class)
public class CpMelodyTest {
	
	private static Logger LOGGER = LoggerFactory.getLogger(CpMelodyTest.class);
	
	private CpMelody melody;
	
	@Autowired
	private Key C;
	@Autowired
	private Key D;
	@Autowired
	private Key F;
	@Autowired
	private Key G;
	@Autowired
	private TimeLine timeLine;

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
		timeLine.setKeys(Collections.singletonList(new TimeLineKey(C, 0, 48)));
		List<Note> notes = new ArrayList<>();
		notes.add(note().pos(0).pc(0).build());
		notes.add(note().pos(12).rest().build());
		notes.add(note().pos(18).pc(11).build());
		notes.add(note().pos(24).pc(7).build());
		melody = new CpMelody(notes, Scale.MAJOR_SCALE, 0, 48);
		melody.getNotes().forEach(n -> System.out.println( n.getPitchClass()));
		LOGGER.info("Contour: " + melody.getContour());
		melody.updateRandomNote(timeLine);
		melody.getNotes().forEach(n -> System.out.println( n.getPitchClass()));
		LOGGER.info("Contour: " + melody.getContour());
	}
	
	@Test
	public void testInversePitchClasses(){
		timeLine.setKeys(Collections.singletonList(new TimeLineKey(C, 0, 48)));
		List<Note> notes = new ArrayList<>();
		notes.add(note().pos(0).pc(0).build());
		notes.add(note().pos(12).rest().build());
		notes.add(note().pos(18).pc(2).build());
		notes.add(note().pos(24).pc(4).build());
		melody = new CpMelody(notes, Scale.MAJOR_SCALE, 0, 48);
		melody.getNotes().forEach(n -> System.out.println( n.getPitchClass()));
		melody.inversePitchClasses(1);
		List<Note> updatedNotes = melody.getNotes();
		assertEquals(0, updatedNotes.get(0).getPitchClass());
		assertEquals(11, updatedNotes.get(2).getPitchClass());
		assertEquals(9, updatedNotes.get(3).getPitchClass());
	}
	
	@Test
	public void testInversePitchClassesDegree2(){
		timeLine.setKeys(Collections.singletonList(new TimeLineKey(D, 0, 48)));
		List<Note> notes = new ArrayList<>();
		notes.add(note().pos(0).pc(2).build());
		notes.add(note().pos(12).rest().build());
		notes.add(note().pos(18).pc(1).build());
		notes.add(note().pos(24).pc(6).build());
		melody = new CpMelody(notes, Scale.MAJOR_SCALE, 0, 48);
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
		int convertedPC = melody.convertPitchClass(0, Scale.MAJOR_SCALE, 0, G.getInterval(), C.getInterval());
		assertEquals(5, convertedPC);
	}
	
	@Test
	public void testTransposeInKeySameScale2() {
		List<Note> notes = new ArrayList<>();
		melody = new CpMelody(notes, Scale.MAJOR_SCALE, 0, 48);
		int convertedPC = melody.convertPitchClass(1, Scale.MAJOR_SCALE, 0, D.getInterval(), G.getInterval());
		assertEquals(6, convertedPC);
	}
	
	@Test
	public void testTransposeInKeyDifferentScale() {
		List<Note> notes = new ArrayList<>();
		melody = new CpMelody(notes, Scale.MAJOR_SCALE, 0, 48);
		int convertedPC = melody.convertPitchClass(4, Scale.HARMONIC_MINOR_SCALE, 0, C.getInterval(), G.getInterval());
		assertEquals(10, convertedPC);
	}
	
	@Test
	public void testTransposeInKeyDifferentScale2() {
		List<Note> notes = new ArrayList<>();
		melody = new CpMelody(notes, Scale.MAJOR_SCALE, 0, 48);
		int convertedPC = melody.convertPitchClass(6, Scale.HARMONIC_MINOR_SCALE, 0, D.getInterval(), G.getInterval());
		assertEquals(10, convertedPC);
	}
	
	@Test
	public void testTransposePitchClass() {
		List<Note> notes = new ArrayList<>();
		melody = new CpMelody(notes, Scale.MAJOR_SCALE, 0, 48);
		int convertedPC = melody.transposePitchClass(11, Scale.MAJOR_SCALE, C.getInterval(), G.getInterval());
		assertEquals(11, convertedPC);
	}
	
	@Test
	public void testTransposePitchClassReverseKey() {
		List<Note> notes = new ArrayList<>();
		melody = new CpMelody(notes, Scale.MAJOR_SCALE, 0, 48);
		int convertedPC = melody.transposePitchClass(11, Scale.MAJOR_SCALE, G.getInterval(), G.getInterval());
		assertEquals(11, convertedPC);
	}
	
	@Test
	public void testTransposePitchClass2() {
		List<Note> notes = new ArrayList<>();
		melody = new CpMelody(notes, Scale.MAJOR_SCALE, 0, 48);
		int convertedPC = melody.transposePitchClass(0, Scale.MAJOR_SCALE, G.getInterval(), D.getInterval());
		assertEquals(1, convertedPC);
	}
	
	@Test
	public void testTransposePitchClassDiffScale() {
		List<Note> notes = new ArrayList<>();
		melody = new CpMelody(notes, Scale.MAJOR_SCALE, 0, 48);
		int convertedPC = melody.transposePitchClass(6, Scale.HARMONIC_MINOR_SCALE, G.getInterval(), D.getInterval());
		assertEquals(5, convertedPC);
	}
	
}
