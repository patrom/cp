package cp.model.melody;

import cp.DefaultConfig;
import cp.VariationConfig;
import cp.model.TimeLine;
import cp.model.TimeLineKey;
import cp.model.note.Note;
import cp.model.note.Scale;
import cp.model.rhythm.DurationConstants;
import cp.out.print.note.Key;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static cp.model.note.NoteBuilder.note;
import static org.junit.Assert.assertEquals;
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = {DefaultConfig.class, VariationConfig.class})
public class CpMelodyTest {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(CpMelodyTest.class);
	
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

	@Test
	public void testRandomAscDesc(){
		List<Note> notes = new ArrayList<>();
		notes.add(note().pos(0).pc(0).build());
		notes.add(note().pos(DurationConstants.QUARTER).pc(4).build());
		notes.add(note().pos(DurationConstants.THREE_EIGHTS).pc(11).build());
		notes.add(note().pos(DurationConstants.HALF).pc(7).build());
		notes.add(note().pos(DurationConstants.WHOLE).pc(9).build());
		melody = new CpMelody(notes, 1, 0, DurationConstants.WHOLE + DurationConstants.QUARTER);
		LOGGER.info("Contour: " + melody.getContour());
		assertEquals(5, melody.getContour().size());
	}
	
	@Test
	public void testRandomAscDescRest(){
		List<Note> notes = new ArrayList<>();
		notes.add(note().pos(0).pc(0).build());
		notes.add(note().pos(DurationConstants.QUARTER).rest().build());
		notes.add(note().pos(DurationConstants.THREE_EIGHTS).rest().build());
		notes.add(note().pos(DurationConstants.HALF).pc(7).build());
		notes.add(note().pos(DurationConstants.WHOLE).pc(9).build());
		melody = new CpMelody(notes, 1, 0, DurationConstants.WHOLE + DurationConstants.QUARTER);
		LOGGER.info("Contour: " + melody.getContour());
		assertEquals(3, melody.getContour().size());
	}
	
	@Test
	public void testUpdateNextContour(){
		List<Note> notes = new ArrayList<>();
		notes.add(note().pos(0).pc(0).build());
		notes.add(note().pos(DurationConstants.QUARTER).pc(4).build());
		notes.add(note().pos(DurationConstants.THREE_EIGHTS).pc(11).build());
		notes.add(note().pos(DurationConstants.HALF).pc(7).build());
		notes.add(note().pos(DurationConstants.WHOLE).pc(9).build());
		melody = new CpMelody(notes, 1, 0, DurationConstants.WHOLE + DurationConstants.QUARTER);
		melody.updateNextContour(3, 1);
		LOGGER.info("Contour: " + melody.getContour());
		assertEquals(5, melody.getContour().size());
		assertEquals(1, melody.getContour().get(3).intValue());
	}
	
	@Test
	public void testUpdatePreviousContour(){
		List<Note> notes = new ArrayList<>();
		notes.add(note().pos(0).pc(0).build());
		notes.add(note().pos(DurationConstants.QUARTER).pc(4).build());
		notes.add(note().pos(DurationConstants.THREE_EIGHTS).pc(11).build());
		notes.add(note().pos(DurationConstants.HALF).pc(7).build());
		notes.add(note().pos(DurationConstants.WHOLE).pc(9).build());
		melody = new CpMelody(notes, 1, 0, DurationConstants.WHOLE + DurationConstants.QUARTER);
		melody.updatePreviousContour(1, 1);
		LOGGER.info("Contour: " + melody.getContour());
		assertEquals(5, melody.getContour().size());
		assertEquals(1, melody.getContour().get(0).intValue());
	}
	
	@Test
	public void testRemoveContour(){
		List<Note> notes = new ArrayList<>();
		notes.add(note().pos(0).pc(0).build());
		notes.add(note().pos(DurationConstants.QUARTER).pc(4).build());
		notes.add(note().pos(DurationConstants.THREE_EIGHTS).pc(11).build());
		notes.add(note().pos(DurationConstants.HALF).pc(7).build());
		notes.add(note().pos(DurationConstants.WHOLE).pc(9).build());
		melody = new CpMelody(notes, 1, 0, DurationConstants.WHOLE + DurationConstants.QUARTER);
		melody.removeContour(3, 1);
		LOGGER.info("Contour: " + melody.getContour());
		assertEquals(4, melody.getContour().size());
		assertEquals(1, melody.getContour().get(2).intValue());
	}
	
	@Test
	public void testUpdateNotes(){
		List<Note> notes = new ArrayList<>();
		notes.add(note().pos(0).pc(0).build());
		notes.add(note().pos(DurationConstants.QUARTER).pc(4).build());
		notes.add(note().pos(DurationConstants.THREE_EIGHTS).pc(11).build());
		notes.add(note().pos(DurationConstants.HALF).pc(7).build());
		melody = new CpMelody(notes, 0, 0, DurationConstants.WHOLE + DurationConstants.QUARTER);
		notes = new ArrayList<>();
		notes.add(note().pos(DurationConstants.QUARTER).pc(2).build());
		notes.add(note().pos(DurationConstants.HALF).pc(3).build());
		melody.updateNotes(notes);
		LOGGER.info("Contour: " + melody.getContour());
		assertEquals(2, melody.getContour().size());
		assertEquals(2, melody.getNotes().size());
	}
	
	@Test
	public void testUpdateRandomNote(){
		timeLine.addKeysForVoice(Collections.singletonList(new TimeLineKey(C, Scale.MAJOR_SCALE, 0, DurationConstants.WHOLE)), 0);
		List<Note> notes = new ArrayList<>();
		notes.add(note().pos(0).pc(0).build());
		notes.add(note().pos(DurationConstants.QUARTER).rest().build());
		notes.add(note().pos(DurationConstants.THREE_EIGHTS).pc(11).build());
		notes.add(note().pos(DurationConstants.HALF).pc(7).build());
		melody = new CpMelody(notes, 0, 0, DurationConstants.WHOLE + DurationConstants.QUARTER);
		melody.getNotes().forEach(n -> System.out.println( n.getPitchClass()));
		LOGGER.info("Contour: " + melody.getContour());
		melody.updateRandomNote(timeLine);
		melody.getNotes().forEach(n -> System.out.println( n.getPitchClass()));
		LOGGER.info("Contour: " + melody.getContour());
	}
	
	@Test
	public void testTransposeInKeySameScale() {
		List<Note> notes = new ArrayList<>();
		melody = new CpMelody(notes, 0, 0, DurationConstants.WHOLE + DurationConstants.QUARTER);
		int convertedPC = melody.convertPitchClass(0,Scale.MAJOR_SCALE, Scale.MAJOR_SCALE, 0, G.getInterval(), C.getInterval());
		assertEquals(5, convertedPC);
	}
	
	@Test
	public void testTransposeInKeySameScale2() {
		List<Note> notes = new ArrayList<>();
		melody = new CpMelody(notes, 0, 0, DurationConstants.WHOLE + DurationConstants.QUARTER);
		int convertedPC = melody.convertPitchClass(1,Scale.MAJOR_SCALE, Scale.MAJOR_SCALE, 0, D.getInterval(), G.getInterval());
		assertEquals(6, convertedPC);
	}
	
	@Test
	public void testTransposeInKeyDifferentScale() {
		List<Note> notes = new ArrayList<>();
		melody = new CpMelody(notes, 0, 0, DurationConstants.WHOLE + DurationConstants.QUARTER);
		int convertedPC = melody.convertPitchClass(4,Scale.MAJOR_SCALE, Scale.HARMONIC_MINOR_SCALE, 0, C.getInterval(), G.getInterval());
		assertEquals(10, convertedPC);
	}
	
	@Test
	public void testTransposeInKeyDifferentScale2() {
		List<Note> notes = new ArrayList<>();
		melody = new CpMelody(notes, 0, 0, DurationConstants.WHOLE + DurationConstants.QUARTER);
		int convertedPC = melody.convertPitchClass(6,Scale.MAJOR_SCALE, Scale.HARMONIC_MINOR_SCALE, 0, D.getInterval(), G.getInterval());
		assertEquals(10, convertedPC);
	}
	
	@Test
	public void testTransposePitchClass() {
		List<Note> notes = new ArrayList<>();
		melody = new CpMelody(notes, 0, 0, DurationConstants.WHOLE + DurationConstants.QUARTER);
		int convertedPC = melody.transposePitchClass(11,Scale.MAJOR_SCALE, Scale.MAJOR_SCALE, C.getInterval(), G.getInterval(), 0);
		assertEquals(11, convertedPC);
	}
	
	@Test
	public void testTransposePitchClassReverseKey() {
		List<Note> notes = new ArrayList<>();
		melody = new CpMelody(notes, 0, 0, DurationConstants.WHOLE + DurationConstants.QUARTER);
		int convertedPC = melody.transposePitchClass(11,Scale.MAJOR_SCALE, Scale.MAJOR_SCALE, G.getInterval(), G.getInterval(), 0);
		assertEquals(11, convertedPC);
	}
	
	@Test
	public void testTransposePitchClass2() {
		List<Note> notes = new ArrayList<>();
		melody = new CpMelody(notes, 0, 0, DurationConstants.WHOLE + DurationConstants.QUARTER);
		int convertedPC = melody.transposePitchClass(0,Scale.MAJOR_SCALE, Scale.MAJOR_SCALE, G.getInterval(), D.getInterval(), 0);
		assertEquals(1, convertedPC);
	}
	
	@Test
	public void testTransposePitchClassDiffScale() {
		List<Note> notes = new ArrayList<>();
		melody = new CpMelody(notes, 0, 0, DurationConstants.WHOLE + DurationConstants.QUARTER);
		int convertedPC = melody.transposePitchClass(6,Scale.MAJOR_SCALE, Scale.HARMONIC_MINOR_SCALE, G.getInterval(), D.getInterval(), 0);
		assertEquals(5, convertedPC);
	}
	
	@Test
	public void testTransposePitchClassDiffScaleSteps() {
		List<Note> notes = new ArrayList<>();
		melody = new CpMelody(notes, 0, 0, DurationConstants.WHOLE + DurationConstants.QUARTER);
		int convertedPC = melody.transposePitchClass(6,Scale.MAJOR_SCALE, Scale.HARMONIC_MINOR_SCALE, G.getInterval(), D.getInterval(), 1);
		assertEquals(7, convertedPC);
	}
	
	@Test
	public void testInvertInKeySameScale() {
		List<Note> notes = new ArrayList<>();
		melody = new CpMelody(notes, 0, 0, DurationConstants.WHOLE + DurationConstants.QUARTER);
		int convertedPC = melody.invertPitchClass(1, 2,Scale.MAJOR_SCALE, Scale.MAJOR_SCALE, C.getInterval(), G.getInterval());
		assertEquals(6, convertedPC);
	}
	
	@Test
	public void testInvertInKeySameDiffScale() {
		List<Note> notes = new ArrayList<>();
		melody = new CpMelody(notes, 0, 0, DurationConstants.WHOLE + DurationConstants.QUARTER);
		int convertedPC = melody.invertPitchClass(1, 4,Scale.MAJOR_SCALE, Scale.HARMONIC_MINOR_SCALE, C.getInterval(), G.getInterval());
		assertEquals(3, convertedPC);
	}
	
}
