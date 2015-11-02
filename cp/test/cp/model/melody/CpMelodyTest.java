package cp.model.melody;

import static cp.model.note.NoteBuilder.note;
import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import cp.evaluation.FitnessEvaluationTemplateTest;
import cp.model.note.Note;
import cp.model.note.Scale;
import cp.out.instrument.Instrument;

public class CpMelodyTest {
	
	private static Logger LOGGER = LoggerFactory.getLogger(CpMelodyTest.class);
	
	private CpMelody melody;

	@Before
	public void setUp() throws Exception {
	}

	@Test
	public void testUpdatePitches() {
		List<Note> notes = new ArrayList<>();
		notes.add(note().pos(0).pc(0).build());
		notes.add(note().pos(12).pc(2).build());
		notes.add(note().pos(18).pc(11).build());
		notes.add(note().pos(24).pc(5).build());
		notes.add(note().pos(48).pc(7).build());
		melody = new CpMelody(notes, Scale.MAJOR_SCALE, 0);
		melody.updatePitchesFromContour();
		assertEquals(0, notes.get(0).getPitch() % 12);
		assertEquals(2, notes.get(1).getPitch() % 12);
		assertEquals(11, notes.get(2).getPitch() % 12);
		assertEquals(5, notes.get(3).getPitch() % 12);
		assertEquals(7, notes.get(4).getPitch() % 12);
	}
	
	@Test
	public void testAddRandomRhythmNote() {
		List<Note> notes = new ArrayList<>();
		notes.add(note().pos(0).pc(0).build());
		notes.add(note().pos(18).pc(11).build());
		notes.add(note().pos(24).pc(5).build());
		notes.add(note().pos(48).pc(7).build());
		melody = new CpMelody(notes, Scale.MAJOR_SCALE, 0);
		melody.updatePitchesFromContour();
		melody.addRandomRhythmNote(6);
		notes.forEach(n -> System.out.println(n.getPosition() + ", " +  n.getPitch()));
	}
	
	@Test
	public void testUpdateMelodyBetween() {
		List<Note> notes = new ArrayList<>();
		notes.add(note().pos(0).pc(0).pitch(48).ocatve(4).build());
		notes.add(note().pos(18).pc(11).pitch(71).ocatve(5).build());
		notes.add(note().pos(24).pc(5).pitch(89).ocatve(7).build());
		notes.add(note().pos(48).pc(7).pitch(55).ocatve(4).build());
		melody = new CpMelody(notes, Scale.MAJOR_SCALE, 0);
		Instrument instrument = new Instrument();
		instrument.setLowest(60);
		instrument.setHighest(80);
		melody.setInstrument(instrument);
		melody.updateMelodyBetween();
		assertEquals(60, notes.get(0).getPitch());
		assertEquals(71, notes.get(1).getPitch());
		assertEquals(77, notes.get(2).getPitch());
		assertEquals(6, notes.get(2).getOctave());
		assertEquals(67, notes.get(3).getPitch());
		assertEquals(5, notes.get(3).getOctave());
	}
	
	@Test
	public void testInsertRhythm() {
		List<Note> notes = new ArrayList<>();
		notes.add(note().pos(0).pc(0).pitch(60).ocatve(5).build());
		notes.add(note().pos(12).pc(2).pitch(62).ocatve(5).build());
		notes.add(note().pos(18).pc(11).pitch(71).ocatve(5).build());
		notes.add(note().pos(24).pc(5).pitch(65).ocatve(5).build());
		notes.add(note().pos(48).pc(7).pitch(67).ocatve(5).build());
		melody = new CpMelody(notes, Scale.MAJOR_SCALE, 0);
		melody.insertRhythm(6, 7);
		assertEquals(6 ,notes.size());
		assertEquals(0, notes.get(0).getPitchClass());
		assertEquals(6, notes.get(1).getPosition() );
//		assertEquals(67, notes.get(1).getPitch());
		assertEquals(2, notes.get(2).getPitchClass());
		
//		assertEquals(62, notes.get(2).getPitch());
	}
	
	@Test
	public void testInsertRhythmOctaveChange() {
		List<Note> notes = new ArrayList<>();
		notes.add(note().pos(0).pc(0).pitch(60).ocatve(5).build());
		notes.add(note().pos(12).pc(4).pitch(76).ocatve(6).build());
		notes.add(note().pos(18).pc(11).pitch(71).ocatve(6).build());
		notes.add(note().pos(24).pc(5).pitch(77).ocatve(6).build());
		notes.add(note().pos(48).pc(7).pitch(79).ocatve(6).build());
		melody = new CpMelody(notes, Scale.MAJOR_SCALE, 0);
		melody.insertRhythm(6, 2);
		assertEquals(6 ,notes.size());
		assertEquals(0, notes.get(0).getPitchClass());
		assertEquals(6, notes.get(1).getPosition() );
//		assertEquals(62, notes.get(1).getPitch());
//		assertEquals(64, notes.get(2).getPitch());
//		assertEquals(5, notes.get(2).getOctave());
	}
	
//	@Test
//	public void testUpdateNote() {
//		List<Note> notes = new ArrayList<>();
//		notes.add(note().pos(0).pc(0).pitch(60).ocatve(5).build());
//		notes.add(note().pos(12).pc(2).pitch(62).ocatve(5).build());
//		Note note = note().pos(18).pc(10).pitch(70).ocatve(5).build();
//		notes.add(note);
//		notes.add(note().pos(24).pc(5).pitch(65).ocatve(5).build());
//		notes.add(note().pos(48).pc(7).pitch(67).ocatve(5).build());
//		melody = new CpMelody(notes, Scale.MAJOR_SCALE, 0);
//		melody.updateNote(note, 0);
//		assertEquals(58, notes.get(2).getPitch());
//		assertEquals(4, notes.get(2).getOctave());
//		
////		assertEquals(65, notes.get(3).getPitch());
//	}
//	
//	@Test
//	public void testUpdateNoteFirst() {
//		List<Note> notes = new ArrayList<>();
//		Note note = note().pos(0).pc(10).pitch(70).ocatve(5).build();
//		notes.add(note);
//		notes.add(note().pos(12).pc(2).pitch(62).ocatve(5).build());
//		notes.add(note().pos(18).pc(11).pitch(71).ocatve(5).build());
//		notes.add(note().pos(24).pc(5).pitch(65).ocatve(5).build());
//		notes.add(note().pos(48).pc(7).pitch(67).ocatve(5).build());
//		melody = new CpMelody(notes, Scale.MAJOR_SCALE, 0);
//		melody.updateNote(note, 0);
//		assertEquals(70, note.getPitch());
//		assertEquals(5, note.getOctave());
//		assertEquals(71, notes.get(2).getPitch());
//		assertEquals(5, notes.get(2).getOctave());
//	}
//	
//	@Test
//	public void testUpdateNoteLast() {
//		List<Note> notes = new ArrayList<>();
//		notes.add(note().pos(0).pc(0).pitch(60).ocatve(5).build());
//		notes.add(note().pos(12).pc(2).pitch(62).ocatve(5).build());
//		notes.add(note().pos(18).pc(11).pitch(71).ocatve(5).build());
//		notes.add(note().pos(24).pc(5).pitch(65).ocatve(5).build());
//		Note note = note().pos(48).pc(10).pitch(70).ocatve(5).build();
//		notes.add(note);
//		melody = new CpMelody(notes, Scale.MAJOR_SCALE, 0);
//		melody.updateNote(note, 0);
//		assertEquals(58, note.getPitch());
//		assertEquals(4, note.getOctave());
//	}
//	
//	@Test
//	public void testUpdateNotePenultimate() {
//		List<Note> notes = new ArrayList<>();
//		notes.add(note().pos(0).pc(0).pitch(60).ocatve(5).build());
//		notes.add(note().pos(12).pc(2).pitch(62).ocatve(5).build());
//		notes.add(note().pos(18).pc(11).pitch(71).ocatve(5).build());
//		Note note = note().pos(24).pc(10).pitch(70).ocatve(5).build();
//		notes.add(note);
//		notes.add(note().pos(48).pc(7).pitch(67).ocatve(5).build());
//		melody = new CpMelody(notes, Scale.MAJOR_SCALE, 0);
//		melody.updateNote(note, 0);
//		assertEquals(70, note.getPitch());
//		assertEquals(5, note.getOctave());
//		
////		assertEquals(67, notes.get(4).getPitch());
//	}
	
	@Test
	public void testGetOctaveIndirectionDown() {
		List<Note> notes = new ArrayList<>();
		Note note = note().pc(11).build();
		notes.add(note);
		Note noteToUpdate = note().pc(2).pitch(62).build();
		melody = new CpMelody(notes, Scale.MAJOR_SCALE, 0);
		int octave = melody.getOctaveIndirection(note, noteToUpdate, 0);
		assertEquals(0, octave);
	}
	
	@Test
	public void testGetOctaveIndirectionUp() {
		List<Note> notes = new ArrayList<>();
		Note note = note().pc(11).build();
		notes.add(note);
		Note noteToUpdate = note().pc(2).pitch(62).build();
		melody = new CpMelody(notes, Scale.MAJOR_SCALE, 0);
		int octave = melody.getOctaveIndirection(note, noteToUpdate, 1);
		assertEquals(1, octave);
	}
	
	@Test
	public void testGetOctaveIndirectionDown2() {
		List<Note> notes = new ArrayList<>();
		Note note = note().pc(0).build();
		notes.add(note);
		Note noteToUpdate = note().pc(2).build();
		melody = new CpMelody(notes, Scale.MAJOR_SCALE, 0);
		int octave = melody.getOctaveIndirection(note, noteToUpdate, 0);
		assertEquals(-1, octave);
	}
	
	@Test
	public void testGetOctaveIndirectionSame() {
		List<Note> notes = new ArrayList<>();
		Note note = note().pc(0).pitch(60).ocatve(5).build();
		notes.add(note);
		Note noteToUpdate = note().pc(0).pitch(60).build();
		melody = new CpMelody(notes, Scale.MAJOR_SCALE, 0);
		int octave = melody.getOctaveIndirection(note, noteToUpdate, 1);
		assertEquals(0, octave);
	}
	
	@Test
	public void testUpdatePitchFromDown() {
		List<Note> notes = new ArrayList<>();
		Note note = note().pc(0).pitch(60).ocatve(5).build();
		notes.add(note);
		Note noteToUpdate = note().pc(2).pitch(62).build();
		melody = new CpMelody(notes, Scale.MAJOR_SCALE, 0);
		melody.updatePitchFrom(note, noteToUpdate, 0);
		assertEquals(4, noteToUpdate.getOctave());
		assertEquals(50, noteToUpdate.getPitch());
	}
	
	@Test
	public void testUpdatePitchFromUp() {
		List<Note> notes = new ArrayList<>();
		Note note = note().pc(0).pitch(60).ocatve(5).build();
		notes.add(note);
		Note noteToUpdate = note().pc(2).pitch(62).build();
		melody = new CpMelody(notes, Scale.MAJOR_SCALE, 0);
		melody.updatePitchFrom(note, noteToUpdate, 1);
		assertEquals(5, noteToUpdate.getOctave());
		assertEquals(62, noteToUpdate.getPitch());
	}
	
//	@Test
//	public void testUpdatePitchFromListUp() {
//		List<Note> notes = new ArrayList<>();
//		Note note = note().pc(7).pitch(67).ocatve(5).build();
//		
//		notes.add(note().pos(12).pc(2).pitch(62).ocatve(5).build());
//		notes.add(note().pos(18).pc(11).pitch(71).ocatve(5).build());
//		notes.add(note().pos(24).pc(5).pitch(65).ocatve(5).build());
//		notes.add(note().pos(48).pc(7).pitch(67).ocatve(5).build());
//		melody = new CpMelody(notes, Scale.MAJOR_SCALE, 0);
//		melody.updatePitchFrom(note, notes, 1);
//		assertEquals(6, notes.get(0).getOctave());
//		assertEquals(74, notes.get(0).getPitch());
//		assertEquals(6, notes.get(1).getOctave());
//		assertEquals(83, notes.get(1).getPitch());
//	}
//	
	
	@Test
	public void testChangeIntervalFrom() {
		List<Note> notes = new ArrayList<>();
		notes.add(note().pos(0).pc(0).pitch(60).ocatve(5).build());
		notes.add(note().pos(12).pc(2).pitch(62).ocatve(5).build());
		notes.add(note().pos(18).pc(11).pitch(71).ocatve(5).build());
		notes.add(note().pos(24).pc(5).pitch(65).ocatve(5).build());
		notes.add(note().pos(48).pc(7).pitch(67).ocatve(5).build());
		melody = new CpMelody(notes, Scale.MAJOR_SCALE, 0);
		melody.changeIntervalFrom(2);
		assertEquals(60, notes.get(0).getPitch());
		assertEquals(62, notes.get(1).getPitch() );
		assertEquals(59, notes.get(2).getPitch());
		assertEquals(4, notes.get(2).getOctave());
		assertEquals(53, notes.get(3).getPitch());
		assertEquals(55, notes.get(4).getPitch());
	}
	
	@Test
	public void testChangeIntervalFromUp() {
		List<Note> notes = new ArrayList<>();
		notes.add(note().pos(0).pc(0).pitch(60).ocatve(5).build());
		notes.add(note().pos(12).pc(2).pitch(62).ocatve(5).build());
		notes.add(note().pos(18).pc(11).pitch(71).ocatve(5).build());
		notes.add(note().pos(24).pc(5).pitch(65).ocatve(5).build());
		notes.add(note().pos(48).pc(7).pitch(67).ocatve(5).build());
		melody = new CpMelody(notes, Scale.MAJOR_SCALE, 0);
		melody.changeIntervalFrom(3);
		assertEquals(60, notes.get(0).getPitch());
		assertEquals(62, notes.get(1).getPitch());
		assertEquals(71, notes.get(2).getPitch());
		assertEquals(6, notes.get(3).getOctave());
		assertEquals(77, notes.get(3).getPitch());
		assertEquals(79, notes.get(4).getPitch());
	}
	
//	@Test
//	public void testCopyMelodyAbsolute(){
//		List<Note> notes = new ArrayList<>();
//		notes.add(note().pos(0).pc(0).pitch(60).ocatve(5).build());
//		notes.add(note().pos(12).pc(2).pitch(62).ocatve(5).build());
//		notes.add(note().pos(18).pc(11).pitch(71).ocatve(5).build());
//		notes.add(note().pos(24).pc(5).pitch(65).ocatve(5).build());
//		notes.add(note().pos(48).pc(7).pitch(67).ocatve(5).build());
//		melody = new CpMelody(notes, Scale.MAJOR_SCALE, 1);
//		CpMelody copy = new CpMelody(Scale.MAJOR_SCALE, 0, 12, 48);
//		copy.setOperatorType(new OperatorType(-12, Operator.T));
//		copy.transformDependingOn(melody);
//		assertEquals(4, copy.getNotes().size());
//		assertEquals(12, copy.getNotes().get(0).getPosition());
//		assertEquals(48, copy.getNotes().get(0).getPitch());
//		assertEquals(0, copy.getNotes().get(0).getPitchClass());
//		assertEquals(0, copy.getNotes().get(0).getVoice());
//		assertEquals(4, copy.getNotes().get(0).getOctave());
//		assertEquals(36, copy.getNotes().get(3).getPosition());
//	}
//	
//	@Test
//	public void testCopyMelodyRelative(){
//		List<Note> notes = new ArrayList<>();
//		notes.add(note().pos(0).pc(0).pitch(60).ocatve(5).build());
//		notes.add(note().pos(12).pc(4).pitch(64).ocatve(5).build());
//		notes.add(note().pos(18).pc(11).pitch(71).ocatve(5).build());
//		notes.add(note().pos(24).pc(7).pitch(67).ocatve(5).build());
//		notes.add(note().pos(48).pc(9).pitch(69).ocatve(5).build());
//		melody = new CpMelody(notes, Scale.MAJOR_SCALE, 1);
//		CpMelody copy = new CpMelody(Scale.HARMONIC_MINOR_SCALE, 0, 12, 48);
//		copy.setOperatorType(new OperatorType(2, Operator.RELATIVE));
//		copy.transformDependingOn(melody);
//		assertEquals(4, copy.getNotes().size());
//		assertEquals(12, copy.getNotes().get(0).getPosition());
//		assertEquals(62, copy.getNotes().get(0).getPitch());
//		assertEquals(2, copy.getNotes().get(0).getPitchClass());
//		assertEquals(0, copy.getNotes().get(0).getVoice());
//		assertEquals(73, copy.getNotes().get(2).getPitch());
//		assertEquals(6, copy.getNotes().get(2).getOctave());
//	}
//	
//	@Test
//	public void testCopyMelodyInversion(){
//		List<Note> notes = new ArrayList<>();
//		notes.add(note().pos(0).pc(0).pitch(60).ocatve(5).build());
//		notes.add(note().pos(12).pc(4).pitch(64).ocatve(5).build());
//		notes.add(note().pos(18).pc(11).pitch(71).ocatve(5).build());
//		notes.add(note().pos(24).pc(7).pitch(67).ocatve(5).build());
//		notes.add(note().pos(48).pc(9).pitch(69).ocatve(5).build());
//		melody = new CpMelody(notes, Scale.MAJOR_SCALE, 1);
//		CpMelody copy = new CpMelody(Scale.MAJOR_SCALE, 0, 0, 48);
//		copy.setOperatorType(new OperatorType(0, Operator.I));
//		copy.transformDependingOn(melody);
//		assertEquals(5, copy.getNotes().size());
//		assertEquals(0, copy.getNotes().get(0).getPosition());
//		assertEquals(60, copy.getNotes().get(0).getPitch());
//		assertEquals(0, copy.getNotes().get(0).getPitchClass());
//		assertEquals(0, copy.getNotes().get(0).getVoice());
//		assertEquals(8, copy.getNotes().get(1).getPitchClass());
//		assertEquals(1, copy.getNotes().get(2).getPitchClass());
//	}
	
	@Test
	public void testT(){
		List<Note> notes = new ArrayList<>();
		notes.add(note().pos(0).pc(0).pitch(60).ocatve(5).build());
		notes.add(note().pos(12).pc(4).pitch(64).ocatve(5).build());
		notes.add(note().pos(18).pc(11).pitch(71).ocatve(5).build());
		notes.add(note().pos(24).pc(7).pitch(67).ocatve(5).build());
		notes.add(note().pos(48).pc(9).pitch(69).ocatve(5).build());
		melody = new CpMelody(notes, Scale.MAJOR_SCALE, 1);
		melody.T(2);
		assertEquals(2, melody.getNotes().get(0).getPitchClass());
		assertEquals(6, melody.getNotes().get(1).getPitchClass());
		assertEquals(1, melody.getNotes().get(2).getPitchClass());
	}
	
	@Test
	public void testI(){
		List<Note> notes = new ArrayList<>();
		notes.add(note().pos(0).pc(0).pitch(60).ocatve(5).build());
		notes.add(note().pos(12).pc(4).pitch(64).ocatve(5).build());
		notes.add(note().pos(18).pc(11).pitch(71).ocatve(5).build());
		notes.add(note().pos(24).pc(7).pitch(67).ocatve(5).build());
		notes.add(note().pos(48).pc(9).pitch(69).ocatve(5).build());
		melody = new CpMelody(notes, Scale.MAJOR_SCALE, 1);
		melody.I();
		assertEquals(0, melody.getNotes().get(0).getPitchClass());
		assertEquals(8, melody.getNotes().get(1).getPitchClass());
		assertEquals(1, melody.getNotes().get(2).getPitchClass());
	}
	
	@Test
	public void testM(){
		List<Note> notes = new ArrayList<>();
		notes.add(note().pos(0).pc(0).pitch(60).ocatve(5).build());
		notes.add(note().pos(12).pc(4).pitch(64).ocatve(5).build());
		notes.add(note().pos(18).pc(11).pitch(71).ocatve(5).build());
		notes.add(note().pos(24).pc(7).pitch(67).ocatve(5).build());
		notes.add(note().pos(48).pc(9).pitch(69).ocatve(5).build());
		melody = new CpMelody(notes, Scale.MAJOR_SCALE, 1);
		melody.M(5);
		assertEquals(0, melody.getNotes().get(0).getPitchClass());
		assertEquals(8, melody.getNotes().get(1).getPitchClass());
		assertEquals(7, melody.getNotes().get(2).getPitchClass());
	}
	
	@Test
	public void testTI(){
		List<Note> notes = new ArrayList<>();
		notes.add(note().pos(0).pc(0).pitch(60).ocatve(5).build());
		notes.add(note().pos(12).pc(4).pitch(64).ocatve(5).build());
		notes.add(note().pos(18).pc(11).pitch(71).ocatve(5).build());
		notes.add(note().pos(24).pc(7).pitch(67).ocatve(5).build());
		notes.add(note().pos(48).pc(9).pitch(69).ocatve(5).build());
		melody = new CpMelody(notes, Scale.MAJOR_SCALE, 1);
		melody.I().T(2);
		assertEquals(2, melody.getNotes().get(0).getPitchClass());
		assertEquals(10, melody.getNotes().get(1).getPitchClass());
		assertEquals(3, melody.getNotes().get(2).getPitchClass());
	}
	
	@Test
	public void testR(){
		List<Note> notes = new ArrayList<>();
		notes.add(note().pos(0).pc(0).pitch(60).ocatve(5).build());
		notes.add(note().pos(12).pc(4).pitch(64).ocatve(5).build());
		notes.add(note().pos(18).pc(11).pitch(71).ocatve(5).build());
		notes.add(note().pos(24).pc(7).pitch(67).ocatve(5).build());
		notes.add(note().pos(48).pc(9).pitch(69).ocatve(5).build());
		melody = new CpMelody(notes, Scale.MAJOR_SCALE, 1);
		melody.R();
		assertEquals(9, melody.getNotes().get(0).getPitchClass());
		assertEquals(7, melody.getNotes().get(1).getPitchClass());
		assertEquals(11, melody.getNotes().get(2).getPitchClass());
	}
	
	@Test
	public void testRTI(){
		List<Note> notes = new ArrayList<>();
		notes.add(note().pos(0).pc(0).pitch(60).ocatve(5).build());
		notes.add(note().pos(12).pc(4).pitch(64).ocatve(5).build());
		notes.add(note().pos(18).pc(11).pitch(71).ocatve(5).build());
		notes.add(note().pos(24).pc(7).pitch(67).ocatve(5).build());
		notes.add(note().pos(48).pc(9).pitch(69).ocatve(5).build());
		melody = new CpMelody(notes, Scale.MAJOR_SCALE, 1);
		melody.I().T(2).R();
		assertEquals(5, melody.getNotes().get(0).getPitchClass());
		assertEquals(7, melody.getNotes().get(1).getPitchClass());
		assertEquals(3, melody.getNotes().get(2).getPitchClass());
	}
	
//	@Test
//	public void testInsert(){
//		melody = new CpMelody(Scale.MAJOR_SCALE, 1, 0 , 48);
//		melody.insert(24, 12, new Integer[]{0,1});
//		assertEquals(30, melody.getNotes().get(0).getPosition());
//	}
//	
//	@Test
//	public void testRemove(){
//		List<Note> notes = new ArrayList<>();
//		notes.add(note().pos(0).pc(0).pitch(60).ocatve(5).build());
//		notes.add(note().pos(12).pc(4).pitch(64).ocatve(5).build());
//		notes.add(note().pos(18).pc(11).pitch(71).ocatve(5).build());
//		notes.add(note().pos(24).pc(7).pitch(67).ocatve(5).build());
//		notes.add(note().pos(48).pc(9).pitch(69).ocatve(5).build());
//		melody = new CpMelody(notes, Scale.MAJOR_SCALE, 1);
//		melody.remove(12, 12);
//		assertEquals(3, melody.getNotes().size());
//		assertEquals(0, melody.getNotes().get(0).getPosition());
//		assertEquals(24, melody.getNotes().get(1).getPosition());
//	}
//	
//	@Test
//	public void testInsertNotes(){
//		List<Note> notes = new ArrayList<>();
//		notes.add(note().pos(0).pc(0).pitch(60).ocatve(5).build());
//		notes.add(note().pos(12).pc(4).pitch(64).ocatve(5).build());
//		notes.add(note().pos(18).pc(11).pitch(71).ocatve(5).build());
//		notes.add(note().pos(24).pc(7).pitch(67).ocatve(5).build());
//		notes.add(note().pos(48).pc(9).pitch(69).ocatve(5).build());
//		melody = new CpMelody(notes, Scale.MAJOR_SCALE, 1);
//		melody.insertNotes(12, 12, new Integer[]{0,1});
//		assertEquals(4, melody.getNotes().size());
//		assertEquals(0, melody.getNotes().get(0).getPosition());
//		assertEquals(18, melody.getNotes().get(1).getPosition());
//		assertEquals(24, melody.getNotes().get(2).getPosition());
//	}
	
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
		assertEquals(4, melody.getContour().size());
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
		assertEquals(4, melody.getContour().size());
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
		assertEquals(4, melody.getContour().size());
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
		assertEquals(3, melody.getContour().size());
		assertEquals(1, melody.getContour().get(2).intValue());
	}
	
	@Test
	public void testCalculateInterval(){
		melody = new CpMelody(Scale.MAJOR_SCALE,0,0, 12);
		int interval = melody.calculateInterval(1, 4);
		assertEquals(4, interval);
		interval = melody.calculateInterval(-1, 4);
		assertEquals(-8, interval);
		interval = melody.calculateInterval(-1, -4);
		assertEquals(-4, interval);
		interval = melody.calculateInterval(1, -4);
		assertEquals(8, interval);
	}
	
	@Test
	public void testUpdatePitchesFromContour(){
		List<Note> notes = new ArrayList<>();
		notes.add(note().pos(0).pc(0).build());
		notes.add(note().pos(12).pc(4).build());
		notes.add(note().pos(18).pc(11).build());
		notes.add(note().pos(24).pc(7).build());
		notes.add(note().pos(48).pc(9).build());
		melody = new CpMelody(notes, Scale.MAJOR_SCALE, 1);
		melody.setStartOctave(5);
		melody.getContour().set(0, 1);
		melody.getContour().set(1, -1);
		melody.getContour().set(2, 1);
		melody.getContour().set(3, -1);
		melody.updatePitchesFromContour();
		LOGGER.info("Contour: " + melody.getContour());
		assertEquals(60, melody.getNotes().get(0).getPitch());
		assertEquals(5, melody.getNotes().get(0).getOctave());
		assertEquals(64, melody.getNotes().get(1).getPitch());
		assertEquals(5, melody.getNotes().get(1).getOctave());
		assertEquals(59, melody.getNotes().get(2).getPitch());
		assertEquals(4, melody.getNotes().get(2).getOctave());
		assertEquals(67, melody.getNotes().get(3).getPitch());
		assertEquals(5, melody.getNotes().get(3).getOctave());
		assertEquals(57, melody.getNotes().get(4).getPitch());
		assertEquals(4, melody.getNotes().get(4).getOctave());
	}
	
	@Test
	public void testUpdatePitchesFromContourRepeat(){
		List<Note> notes = new ArrayList<>();
		notes.add(note().pos(0).pc(0).build());
		notes.add(note().pos(12).pc(4).build());
		notes.add(note().pos(18).pc(4).build());
		notes.add(note().pos(24).pc(7).build());
		notes.add(note().pos(48).pc(7).build());
		melody = new CpMelody(notes, Scale.MAJOR_SCALE, 1);
		melody.setStartOctave(5);
		melody.getContour().set(0, 1);
		melody.getContour().set(1, 1);
		melody.getContour().set(2, 1);
		melody.getContour().set(3, -1);
		melody.updatePitchesFromContour();
		LOGGER.info("Contour: " + melody.getContour());
		assertEquals(60, melody.getNotes().get(0).getPitch());
		assertEquals(5, melody.getNotes().get(0).getOctave());
		assertEquals(64, melody.getNotes().get(1).getPitch());
		assertEquals(5, melody.getNotes().get(1).getOctave());
		assertEquals(64, melody.getNotes().get(2).getPitch());
		assertEquals(5, melody.getNotes().get(2).getOctave());
		assertEquals(67, melody.getNotes().get(3).getPitch());
		assertEquals(5, melody.getNotes().get(3).getOctave());
		assertEquals(67, melody.getNotes().get(4).getPitch());
		assertEquals(5, melody.getNotes().get(4).getOctave());
	}


}
