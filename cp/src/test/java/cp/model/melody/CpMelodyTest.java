package cp.model.melody;

import cp.DefaultConfig;
import cp.VariationConfig;
import cp.model.TimeLine;
import cp.model.TimeLineKey;
import cp.model.note.Note;
import cp.model.note.Scale;
import cp.model.rhythm.DurationConstants;
import cp.out.print.Keys;
import cp.out.print.note.Key;
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
import static org.junit.Assert.assertTrue;

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
	private Key A;
	@Autowired
	private Key Bflat;
	@Autowired
	private Keys keys;

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
		timeLine = new TimeLine();
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

	@Test
	public void testTransposePitchClasses() {
		List<Note> notes = new ArrayList<>();
		notes.add(note().pos(0).pc(0).voice(0).build());
		notes.add(note().pos(DurationConstants.QUARTER).pc(4).voice(0).build());
		notes.add(note().pos(DurationConstants.HALF).pc(5).voice(0).build());
		notes.add(note().pos(DurationConstants.WHOLE + DurationConstants.QUARTER).pc(7).voice(0).build());
		melody = new CpMelody(notes, 0, 0, DurationConstants.WHOLE + DurationConstants.QUARTER);

		List<TimeLineKey> keys = new ArrayList<>();
		keys.add(new TimeLineKey(C, Scale.MAJOR_SCALE, 0, DurationConstants.WHOLE));
		keys.add(new TimeLineKey(D, Scale.MAJOR_SCALE, DurationConstants.WHOLE, 3 * DurationConstants.WHOLE));
		timeLine = new TimeLine();
		timeLine.addKeysForVoice(keys, 0);
		melody.transposePitchClasses(0, DurationConstants.WHOLE, timeLine);
		List<Note> transposednotes = melody.getNotes();
		assertEquals(1, transposednotes.get(0).getPitchClass());
		assertEquals(4, transposednotes.get(1).getPitchClass());
		assertEquals(6, transposednotes.get(2).getPitchClass());
		assertEquals(7, transposednotes.get(3).getPitchClass());
	}

	@Test
	public void testTransposePitchClasses2() {
		List<Note> notes = new ArrayList<>();
		notes.add(note().pos(0).pc(0).voice(0).build());
		notes.add(note().pos(DurationConstants.QUARTER).pc(4).voice(0).build());
		notes.add(note().pos(DurationConstants.HALF).pc(5).voice(0).build());
		notes.add(note().pos(DurationConstants.HALF + DurationConstants.QUARTER).pc(7).voice(0).build());
		melody = new CpMelody(notes, 0, 0, DurationConstants.WHOLE + DurationConstants.QUARTER);

		List<TimeLineKey> keys = new ArrayList<>();
		keys.add(new TimeLineKey(C, Scale.MAJOR_SCALE, 0, DurationConstants.WHOLE));
		keys.add(new TimeLineKey(D, Scale.MAJOR_SCALE, DurationConstants.WHOLE, 3 * DurationConstants.WHOLE));
		timeLine = new TimeLine();
		timeLine.addKeysForVoice(keys, 0);
		melody.transposePitchClasses(3, DurationConstants.WHOLE, timeLine);
		List<Note> transposednotes = melody.getNotes();
		assertEquals(6, transposednotes.get(0).getPitchClass());
		assertEquals(9, transposednotes.get(1).getPitchClass());
		assertEquals(11, transposednotes.get(2).getPitchClass());
		assertEquals(1, transposednotes.get(3).getPitchClass());
	}

	@Test
	public void testInversePitchClasses() {
		List<Note> notes = new ArrayList<>();
		notes.add(note().pos(0).pc(0).voice(0).build());
		notes.add(note().pos(DurationConstants.QUARTER).pc(4).voice(0).build());
		notes.add(note().pos(DurationConstants.HALF).pc(5).voice(0).build());
		notes.add(note().pos(DurationConstants.HALF + DurationConstants.QUARTER).pc(7).voice(0).build());
		melody = new CpMelody(notes, 0, 0, DurationConstants.WHOLE + DurationConstants.QUARTER);

		List<TimeLineKey> keys = new ArrayList<>();
		keys.add(new TimeLineKey(C, Scale.MAJOR_SCALE, 0, DurationConstants.WHOLE));
		keys.add(new TimeLineKey(D, Scale.MAJOR_SCALE, DurationConstants.WHOLE, 3 * DurationConstants.WHOLE));
		timeLine = new TimeLine();
		timeLine.addKeysForVoice(keys, 0);
		melody.inversePitchClasses(2, DurationConstants.WHOLE, timeLine);
		List<Note> inverseNotes = melody.getNotes();
		assertEquals(6, inverseNotes.get(0).getPitchClass());
		assertEquals(2, inverseNotes.get(1).getPitchClass());
		assertEquals(1, inverseNotes.get(2).getPitchClass());
		assertEquals(11, inverseNotes.get(3).getPitchClass());
	}

	@Test
	public void testInversePitchClasses2() {
		//A major
		List<Note> notes = new ArrayList<>();
		notes.add(note().pos(0).pc(9).voice(0).build());
		notes.add(note().pos(DurationConstants.QUARTER).pc(8).voice(0).build());
		notes.add(note().pos(DurationConstants.HALF).pc(6).voice(0).build());
		notes.add(note().pos(DurationConstants.HALF + DurationConstants.QUARTER).pc(1).voice(0).build());
		melody = new CpMelody(notes, 0, 0, DurationConstants.WHOLE + DurationConstants.QUARTER);
		//to B flat major
		List<TimeLineKey> keys = new ArrayList<>();
		keys.add(new TimeLineKey(A, Scale.MAJOR_SCALE, 0, DurationConstants.WHOLE));
		keys.add(new TimeLineKey(Bflat, Scale.MAJOR_SCALE, DurationConstants.WHOLE, 2 * DurationConstants.WHOLE));
		timeLine = new TimeLine();
		timeLine.addKeysForVoice(keys, 0);

		melody.inversePitchClasses(1, DurationConstants.WHOLE, timeLine);
		List<Note> inverseNotes = melody.getNotes();
		assertEquals(10, inverseNotes.get(0).getPitchClass());
		assertEquals(0, inverseNotes.get(1).getPitchClass());
		assertEquals(2, inverseNotes.get(2).getPitchClass());
		assertEquals(7, inverseNotes.get(3).getPitchClass());
	}

	@Test
	public void testInversePitchClasses3() {
		//A major
		List<Note> notes = new ArrayList<>();
		notes.add(note().pos(0).pc(9).voice(0).build());
		notes.add(note().pos(DurationConstants.QUARTER).pc(11).voice(0).build());
		notes.add(note().pos(DurationConstants.HALF).pc(1).voice(0).build());
		notes.add(note().pos(DurationConstants.HALF + DurationConstants.QUARTER).pc(2).voice(0).build());
		melody = new CpMelody(notes, 0, 0, DurationConstants.WHOLE + DurationConstants.QUARTER);
		melody.setTonality(Tonality.TONAL);
		melody.setTimeLineKey(new TimeLineKey(A, Scale.MAJOR_SCALE, 0, DurationConstants.WHOLE));
		notes.forEach(n -> System.out.println(n.toString()));
		System.out.println();

		timeLine = new TimeLine();
		timeLine.addKeysForVoice(Collections.singletonList(new TimeLineKey(D, Scale.HARMONIC_MINOR_SCALE, 0, DurationConstants.WHOLE)), 0);

		melody.inversePitchClasses(timeLine);

		List<Note> inverseNotes = melody.getNotes();
		inverseNotes.forEach(n -> System.out.println(n.toString()));
		System.out.println();
	}

	@Test
	public void testInverse(){
		List<Note> notes = new ArrayList<>();
		notes.add(note().pos(0).pc(0).build());
		notes.add(note().pos(DurationConstants.QUARTER).pc(1).build());
		notes.add(note().pos(DurationConstants.THREE_EIGHTS).pc(2).build());
		notes.add(note().pos(DurationConstants.HALF).pc(3).build());
		melody = new CpMelody(notes, 0, 0, DurationConstants.WHOLE);
		melody.I();
		melody.getNotes().forEach(n -> System.out.println(n));
	}

	@Test
	public void testT(){
		int[] pitchClasses = Scale.MAJOR_SCALE.getPitchClasses();
		for (int i = 0; i < pitchClasses.length; i++) {
			List<Note> notes = new ArrayList<>();
			notes.add(note().pos(0).pc(0).build());
			notes.add(note().pos(DurationConstants.QUARTER).pc(1).build());
			notes.add(note().pos(DurationConstants.THREE_EIGHTS).pc(2).build());
			notes.add(note().pos(DurationConstants.HALF).pc(3).build());
			melody = new CpMelody(notes, 0, 0, DurationConstants.WHOLE);
			int pitchClass = pitchClasses[i];
			melody.T(pitchClass);
			melody.getNotes().forEach(n -> System.out.println(n));
			System.out.println();
		}
	}

	@Test
	public void testIT(){
		int[] pitchClasses = Scale.MAJOR_SCALE.getPitchClasses();
		for (int i = 0; i < pitchClasses.length; i++) {
			List<Note> notes = new ArrayList<>();
			notes.add(note().pos(0).pc(0).build());
			notes.add(note().pos(DurationConstants.QUARTER).pc(1).build());
			notes.add(note().pos(DurationConstants.THREE_EIGHTS).pc(2).build());
			notes.add(note().pos(DurationConstants.HALF).pc(3).build());
			melody = new CpMelody(notes, 0, 0, DurationConstants.WHOLE);
			int pitchClass = pitchClasses[i];
			melody.I().T(pitchClass);
			melody.getNotes().forEach(n -> System.out.println(n));
			System.out.println();
		}
	}

	@Test
	public void testRT(){
			List<Note> notes = new ArrayList<>();
			notes.add(note().pos(0).pc(0).build());
			notes.add(note().pos(DurationConstants.QUARTER).pc(1).build());
			notes.add(note().pos(DurationConstants.THREE_EIGHTS).pc(2).build());
			notes.add(note().pos(DurationConstants.HALF).pc(3).build());
			CpMelody melody = new CpMelody(notes, 0, 0, DurationConstants.WHOLE);
			List<Integer> contour = new ArrayList<>();
			contour.add(-1);
			contour.add(-1);
			contour.add(1);
			contour.add(-1);
            melody.setContour(contour);
			melody.R().T(2);
			melody.getNotes().forEach(n -> System.out.println(n));
			melody.getContour().forEach(n -> System.out.println(n));
			System.out.println();

	}

	@Test
	public void testR(){
		List<Note> notes = new ArrayList<>();
		notes.add(note().pos(0).pc(0).build());
		notes.add(note().pos(DurationConstants.QUARTER).pc(1).build());
		notes.add(note().pos(DurationConstants.QUARTER + DurationConstants.EIGHT).pc(2).build());
		notes.add(note().pos(DurationConstants.HALF).pc(3).build());
		CpMelody melody = new CpMelody(notes, 0, 0, DurationConstants.WHOLE);
		melody.getNotes().forEach(n -> System.out.println(n));
        List<Integer> contour = new ArrayList<>();
        contour.add(-1);
        contour.add(-1);
        contour.add(1);
        contour.add(-1);
        melody.setContour(contour);
		melody.R();
		melody.getNotes().forEach(n -> System.out.println(n));
		melody.getContour().forEach(n -> System.out.println(n));
		System.out.println();
	}

	@Test
	public void updateRhythmNotes(){
		List<Note> notes = new ArrayList<>();
		notes.add(note().pos(DurationConstants.QUARTER).pc(4).build());
		notes.add(note().pos(DurationConstants.QUARTER + DurationConstants.EIGHT).pc(11).build());
		notes.add(note().pos(DurationConstants.QUARTER + DurationConstants.EIGHT + DurationConstants.SIXTEENTH).pc(7).build());
		melody = new CpMelody(notes, 1, DurationConstants.QUARTER, DurationConstants.WHOLE + DurationConstants.QUARTER);

		List<Note> rhythmNotes = new ArrayList<>();
		rhythmNotes.add(note().pos(0).rest().build());
		rhythmNotes.add(note().pos(DurationConstants.SIXTEENTH).pc(1).build());
		rhythmNotes.add(note().pos(DurationConstants.EIGHT).pc(8).build());
		rhythmNotes.add(note().pos(DurationConstants.EIGHT + DurationConstants.SIXTEENTH).pc(4).build());
		melody.updateRhythmNotes(rhythmNotes);
		List<Note> melodyNotes = melody.getNotes();
		assertEquals(melody.getStart(),melodyNotes.get(0).getPosition());
		assertTrue(melodyNotes.get(0).isRest());
		assertEquals(DurationConstants.QUARTER+ DurationConstants.SIXTEENTH ,melodyNotes.get(1).getPosition());
		assertEquals(4,melodyNotes.get(1).getPitchClass());
		assertEquals(DurationConstants.QUARTER + DurationConstants.EIGHT,melodyNotes.get(2).getPosition());
		assertEquals(DurationConstants.QUARTER + DurationConstants.EIGHT + DurationConstants.SIXTEENTH,melodyNotes.get(3).getPosition());
	}

	@Test
	public void testInvert(){
		timeLine = new TimeLine();
		timeLine.addKeysForVoice(Collections.singletonList(new TimeLineKey(D, Scale.MAJOR_SCALE, 0, DurationConstants.WHOLE)), 0);
		List<Note> notes = new ArrayList<>();
		notes.add(note().pos(0).pc(1).build());
		notes.add(note().pos(DurationConstants.QUARTER).pc(2).build());
		notes.add(note().pos(DurationConstants.QUARTER + DurationConstants.EIGHT).pc(6).build());
		notes.add(note().pos(DurationConstants.HALF).pc(4).build());
		CpMelody melody = new CpMelody(notes, 0, 0, DurationConstants.WHOLE);
		melody.getNotes().forEach(n -> System.out.println(n));
		for (int i = 1; i < 7; i++) {
			System.out.println("degree: " + i);
			melody.inversePitchClasses(i, timeLine);
			melody.getNotes().forEach(n -> System.out.println(n));
			System.out.println();
		}
	}

    @Test
    public void testTranspose(){
        timeLine = new TimeLine();
        timeLine.addKeysForVoice(Collections.singletonList(new TimeLineKey(D, Scale.MAJOR_SCALE, 0, DurationConstants.WHOLE)), 0);
        List<Note> notes = new ArrayList<>();
        notes.add(note().pos(0).pc(1).build());
        notes.add(note().pos(DurationConstants.QUARTER).pc(2).build());
        notes.add(note().pos(DurationConstants.QUARTER + DurationConstants.EIGHT).pc(6).build());
        notes.add(note().pos(DurationConstants.HALF).pc(4).build());
        CpMelody melody = new CpMelody(notes, 0, 0, DurationConstants.WHOLE);
        melody.getNotes().forEach(n -> System.out.println(n));
        for (int i = 0; i < 7; i++) {
            System.out.println("steps: " + i);
            melody.transposePitchClasses(i, timeLine);
            melody.getNotes().forEach(n -> System.out.println(n));
            System.out.println();
        }
    }

	@Test
	public void testConvertToKey(){
		timeLine = new TimeLine();
		timeLine.addKeysForVoice(Collections.singletonList(new TimeLineKey(D, Scale.MAJOR_SCALE, 0, DurationConstants.WHOLE)), 0);
		List<Note> notes = new ArrayList<>();
		notes.add(note().pos(0).pc(0).build());
		notes.add(note().pos(DurationConstants.QUARTER).pc(2).build());
		notes.add(note().pos(DurationConstants.QUARTER + DurationConstants.EIGHT).pc(4).build());
		notes.add(note().pos(DurationConstants.HALF).pc(5).build());
		CpMelody melody = new CpMelody(notes, 0, 0, DurationConstants.WHOLE);
		melody.setTimeLineKey(new TimeLineKey(keys.C, Scale.MAJOR_SCALE));
		melody.getNotes().forEach(n -> System.out.println(n));
		System.out.println();

		melody.convertToTimelineKey(timeLine);
		melody.getNotes().forEach(n -> System.out.println(n));
		System.out.println();
	}

	@Test
	public void testConvertToKeyDtoE(){
		timeLine = new TimeLine();
		timeLine.addKeysForVoice(Collections.singletonList(new TimeLineKey(keys.E, Scale.MAJOR_SCALE, 0, DurationConstants.WHOLE)), 0);
		List<Note> notes = new ArrayList<>();
		notes.add(note().pos(0).pc(1).build());
		notes.add(note().pos(DurationConstants.QUARTER).pc(2).build());
		notes.add(note().pos(DurationConstants.QUARTER + DurationConstants.EIGHT).pc(4).build());
		notes.add(note().pos(DurationConstants.HALF).pc(6).build());
		CpMelody melody = new CpMelody(notes, 0, 0, DurationConstants.WHOLE);
        melody.setTimeLineKey(new TimeLineKey(keys.D, Scale.MAJOR_SCALE));
		melody.getNotes().forEach(n -> System.out.println(n));
		System.out.println();

		melody.convertToTimelineKey(timeLine);
		melody.getNotes().forEach(n -> System.out.println(n));
		System.out.println();
        assertEquals(keys.E.getStep(), melody.getTimeLineKey().getKey().getStep());
        assertEquals(Scale.MAJOR_SCALE, melody.getTimeLineKey().getScale());
    }

    @Test
    public void testConvertToKeyDtoEminor(){
        timeLine = new TimeLine();
        timeLine.addKeysForVoice(Collections.singletonList(new TimeLineKey(keys.E, Scale.HARMONIC_MINOR_SCALE, 0, DurationConstants.WHOLE)), 0);
        List<Note> notes = new ArrayList<>();
        notes.add(note().pos(0).pc(1).build());
        notes.add(note().pos(DurationConstants.QUARTER).pc(2).build());
        notes.add(note().pos(DurationConstants.QUARTER + DurationConstants.EIGHT).pc(4).build());
        notes.add(note().pos(DurationConstants.HALF).pc(6).build());
        CpMelody melody = new CpMelody(notes, 0, 0, DurationConstants.WHOLE);
        melody.setTimeLineKey(new TimeLineKey(keys.D, Scale.MAJOR_SCALE));
        melody.getNotes().forEach(n -> System.out.println(n));
        System.out.println();

        melody.convertToTimelineKey(timeLine);
        melody.getNotes().forEach(n -> System.out.println(n));
        System.out.println();
        assertEquals(keys.E.getStep(), melody.getTimeLineKey().getKey().getStep());
        assertEquals(Scale.HARMONIC_MINOR_SCALE, melody.getTimeLineKey().getScale());
    }

	@Test
	public void testTransposeScales(){
		Scale scale = Scale.OCTATCONIC_HALF;
		timeLine = new TimeLine();
		timeLine.addKeysForVoice(Collections.singletonList(new TimeLineKey(C, scale , 0, DurationConstants.WHOLE)), 0);
		List<Note> notes = new ArrayList<>();
		notes.add(note().pos(0).pc(scale.pickRandomPitchClass()).build());
		notes.add(note().pos(DurationConstants.QUARTER).pc(scale.pickRandomPitchClass()).build());
		notes.add(note().pos(DurationConstants.QUARTER + DurationConstants.EIGHT).pc(scale.pickRandomPitchClass()).build());
		notes.add(note().pos(DurationConstants.HALF).pc(scale.pickRandomPitchClass()).build());
		CpMelody melody = new CpMelody(notes, 0, 0, DurationConstants.WHOLE);
		melody.getNotes().forEach(n -> System.out.println(n));
		for (int i = 0; i < scale.getPitchClasses().length; i++) {
			CpMelody clonedMelody = melody.clone();
			System.out.println("steps: " + i);
			clonedMelody.transposePitchClasses(i, timeLine);
			clonedMelody.getNotes().forEach(n -> System.out.println(n));
			System.out.println();
		}
	}

	@Test
	public void testInverseScales(){
		Scale scale = Scale.MAJOR_SCALE;
		timeLine = new TimeLine();
		timeLine.addKeysForVoice(Collections.singletonList(new TimeLineKey(C, scale , 0, DurationConstants.WHOLE)), 0);
		List<Note> notes = new ArrayList<>();
		notes.add(note().pos(0).pc(scale.pickRandomPitchClass()).build());
		notes.add(note().pos(DurationConstants.QUARTER).pc(scale.pickRandomPitchClass()).build());
		notes.add(note().pos(DurationConstants.QUARTER + DurationConstants.EIGHT).pc(scale.pickRandomPitchClass()).build());
		notes.add(note().pos(DurationConstants.HALF).pc(scale.pickRandomPitchClass()).build());
		CpMelody melody = new CpMelody(notes, 0, 0, DurationConstants.WHOLE);
		melody.getNotes().forEach(n -> System.out.println(n));
		for (int i = 0; i < scale.getPitchClasses().length; i++) {
//			CpMelody clonedMelody = melody.clone();
			int pitchClass = melody.getNotes().get(0).getPitchClass();
			System.out.println("Pc: " + pitchClass);
			int functionalIndex = scale.getIndex(pitchClass);
			System.out.println("steps: " + functionalIndex );
			melody.inversePitchClasses(functionalIndex, timeLine);
			melody.getNotes().forEach(n -> System.out.println(n));
			System.out.println();
		}
	}

	@Test
	public void testTransposePitchClassesRandom(){
		Scale scale = Scale.OCTATCONIC_HALF;
		timeLine = new TimeLine();
		timeLine.addKeysForVoice(Collections.singletonList(new TimeLineKey(C, scale , 0, DurationConstants.WHOLE)), 0);
		List<Note> notes = new ArrayList<>();
		notes.add(note().pos(0).pc(scale.pickRandomPitchClass()).build());
		notes.add(note().pos(DurationConstants.QUARTER).pc(scale.pickRandomPitchClass()).build());
		notes.add(note().pos(DurationConstants.QUARTER + DurationConstants.EIGHT).pc(scale.pickRandomPitchClass()).build());
		notes.add(note().pos(DurationConstants.HALF).pc(scale.pickRandomPitchClass()).build());
		CpMelody melody = new CpMelody(notes, 0, 0, DurationConstants.WHOLE);
		melody.getNotes().forEach(n -> System.out.println(n));
        System.out.println();
		CpMelody clonedMelody = melody.clone();
		clonedMelody.transposePitchClasses(timeLine);
		clonedMelody.getNotes().forEach(n -> System.out.println(n));
		System.out.println();
	}

	@Test
	public void testInverse2(){
		Scale scale = Scale.HARMONIC_MINOR_SCALE;
		Scale newScale = Scale.MAJOR_SCALE;
		List<Note> notes = new ArrayList<>();
		notes.add(note().pos(0).pc(scale.pickRandomPitchClass()).build());
		notes.add(note().pos(DurationConstants.QUARTER).pc(scale.pickRandomPitchClass()).build());
		notes.add(note().pos(DurationConstants.QUARTER + DurationConstants.EIGHT).pc(scale.pickRandomPitchClass()).build());
		notes.add(note().pos(DurationConstants.HALF).pc(scale.pickRandomPitchClass()).build());
		CpMelody melody = new CpMelody(notes, 0, 0, DurationConstants.WHOLE);
		melody.getNotes().forEach(n -> System.out.println(n));
		System.out.println();
		CpMelody clonedMelody = melody.clone();
		clonedMelody.inverseRandom(scale, newScale);
		clonedMelody.getNotes().forEach(n -> System.out.println(n));
		System.out.println();
	}
	
}
