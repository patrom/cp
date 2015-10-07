package cp.model.melody;

import static cp.model.note.NoteBuilder.note;
import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import cp.model.note.Note;
import cp.model.note.Scale;
import cp.out.instrument.Instrument;

public class CpMelodyTest {

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
		melody.updatePitches(6);
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
		melody.updatePitches(6);
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
	
	@Test
	public void testUpdateNote() {
		List<Note> notes = new ArrayList<>();
		notes.add(note().pos(0).pc(0).pitch(60).ocatve(5).build());
		notes.add(note().pos(12).pc(2).pitch(62).ocatve(5).build());
		Note note = note().pos(18).pc(11).pitch(71).ocatve(5).build();
		notes.add(note);
		notes.add(note().pos(24).pc(5).pitch(65).ocatve(5).build());
		notes.add(note().pos(48).pc(7).pitch(67).ocatve(5).build());
		melody = new CpMelody(notes, Scale.MAJOR_SCALE, 0);
		melody.updateNote(note, 10, 0);
		assertEquals(58, notes.get(2).getPitch());
		assertEquals(4, notes.get(2).getOctave());
		
//		assertEquals(65, notes.get(3).getPitch());
	}
	
	@Test
	public void testUpdateNoteFirst() {
		List<Note> notes = new ArrayList<>();
		Note note = note().pos(0).pc(0).pitch(60).ocatve(5).build();
		notes.add(note);
		notes.add(note().pos(12).pc(2).pitch(62).ocatve(5).build());
		notes.add(note().pos(18).pc(11).pitch(71).ocatve(5).build());
		notes.add(note().pos(24).pc(5).pitch(65).ocatve(5).build());
		notes.add(note().pos(48).pc(7).pitch(67).ocatve(5).build());
		melody = new CpMelody(notes, Scale.MAJOR_SCALE, 0);
		melody.updateNote(note, 10, 0);
		assertEquals(70, note.getPitch());
		assertEquals(5, note.getOctave());
		assertEquals(71, notes.get(2).getPitch());
		assertEquals(5, notes.get(2).getOctave());
	}
	
	@Test
	public void testUpdateNoteLast() {
		List<Note> notes = new ArrayList<>();
		notes.add(note().pos(0).pc(0).pitch(60).ocatve(5).build());
		notes.add(note().pos(12).pc(2).pitch(62).ocatve(5).build());
		notes.add(note().pos(18).pc(11).pitch(71).ocatve(5).build());
		notes.add(note().pos(24).pc(5).pitch(65).ocatve(5).build());
		Note note = note().pos(48).pc(7).pitch(67).ocatve(5).build();
		notes.add(note);
		melody = new CpMelody(notes, Scale.MAJOR_SCALE, 0);
		melody.updateNote(note, 10, 0);
		assertEquals(58, note.getPitch());
		assertEquals(4, note.getOctave());
	}
	
	@Test
	public void testUpdateNotePenultimate() {
		List<Note> notes = new ArrayList<>();
		notes.add(note().pos(0).pc(0).pitch(60).ocatve(5).build());
		notes.add(note().pos(12).pc(2).pitch(62).ocatve(5).build());
		notes.add(note().pos(18).pc(11).pitch(71).ocatve(5).build());
		Note note = note().pos(24).pc(5).pitch(65).ocatve(5).build();
		notes.add(note);
		notes.add(note().pos(48).pc(7).pitch(67).ocatve(5).build());
		melody = new CpMelody(notes, Scale.MAJOR_SCALE, 0);
		melody.updateNote(note, 10, 0);
		assertEquals(70, note.getPitch());
		assertEquals(5, note.getOctave());
		
//		assertEquals(67, notes.get(4).getPitch());
	}
	
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
	
	@Test
	public void testCopyMelodyAbsolute(){
		List<Note> notes = new ArrayList<>();
		notes.add(note().pos(0).pc(0).pitch(60).ocatve(5).build());
		notes.add(note().pos(12).pc(2).pitch(62).ocatve(5).build());
		notes.add(note().pos(18).pc(11).pitch(71).ocatve(5).build());
		notes.add(note().pos(24).pc(5).pitch(65).ocatve(5).build());
		notes.add(note().pos(48).pc(7).pitch(67).ocatve(5).build());
		melody = new CpMelody(notes, Scale.MAJOR_SCALE, 1);
		CpMelody copy = new CpMelody(Scale.MAJOR_SCALE, 0, 12, 48);
		copy.copyMelody(melody, -12, Transposition.ABSOLUTE);
		assertEquals(4, copy.getNotes().size());
		assertEquals(12, copy.getNotes().get(0).getPosition());
		assertEquals(48, copy.getNotes().get(0).getPitch());
		assertEquals(0, copy.getNotes().get(0).getPitchClass());
		assertEquals(0, copy.getNotes().get(0).getVoice());
		assertEquals(4, copy.getNotes().get(0).getOctave());
		assertEquals(36, copy.getNotes().get(3).getPosition());
	}
	
	@Test
	public void testCopyMelodyRelative(){
		List<Note> notes = new ArrayList<>();
		notes.add(note().pos(0).pc(0).pitch(60).ocatve(5).build());
		notes.add(note().pos(12).pc(4).pitch(64).ocatve(5).build());
		notes.add(note().pos(18).pc(11).pitch(71).ocatve(5).build());
		notes.add(note().pos(24).pc(7).pitch(67).ocatve(5).build());
		notes.add(note().pos(48).pc(9).pitch(69).ocatve(5).build());
		melody = new CpMelody(notes, Scale.MAJOR_SCALE, 1);
		CpMelody copy = new CpMelody(Scale.HARMONIC_MINOR_SCALE, 0, 12, 48);
		copy.copyMelody(melody, 2, Transposition.RELATIVE);
		assertEquals(4, copy.getNotes().size());
		assertEquals(12, copy.getNotes().get(0).getPosition());
		assertEquals(62, copy.getNotes().get(0).getPitch());
		assertEquals(2, copy.getNotes().get(0).getPitchClass());
		assertEquals(0, copy.getNotes().get(0).getVoice());
		assertEquals(73, copy.getNotes().get(2).getPitch());
		assertEquals(6, copy.getNotes().get(2).getOctave());
	}

}
