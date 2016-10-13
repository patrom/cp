package cp.out.instrument;

import cp.model.note.Note;
import cp.model.note.Scale;
import cp.out.instrument.register.InstrumentRegister;
import org.junit.Before;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;

import static cp.model.note.NoteBuilder.note;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class InstrumentTest {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(InstrumentTest.class);
	
	private final Instrument instrument = new Instrument();
	private List<Note> notes;

	@Before
	public void setUp() throws Exception {
		instrument.setInstrumentRegister(new InstrumentRegister(50,55));
		notes = new ArrayList<>();
		notes.add(note().pitch(50).build());
		notes.add(note().pitch(48).build());
		notes.add(note().pitch(52).build());
		notes.add(note().pitch(55).build());
		notes.add(note().pitch(56).build());
	}

	@Test
	public void testUpdateMelodyInRange() {
		instrument.setInstrumentRegister(new InstrumentRegister(50,80));
		instrument.updateMelodyInRange(notes);
		assertEquals(50, notes.get(0).getPitch());
		assertEquals(60, notes.get(1).getPitch());
	}

	@Test
	public void testRemoveMelodyNotIn() {
		List<Note> inRangeNotes = instrument.removeMelodyNotInRange(notes);
		assertTrue(inRangeNotes.size() == 3);
	}

	@Test
	public void testUpdateInQualityRange() {
		notes.clear();
		notes.add(note().pitch(64).build());
		notes.add(note().pitch(57).build());
		notes.add(note().pitch(52).build());
		notes.add(note().pitch(30).build());
		notes.add(note().pitch(48).build());
		notes.add(note().pitch(68).build());
		List<Note> inRangeNotes = instrument.updateInQualityRange(notes);
		assertEquals(52, inRangeNotes.get(0).getPitch());
		assertEquals(57, inRangeNotes.get(1).getPitch());
		assertEquals(52, inRangeNotes.get(2).getPitch());
		assertEquals(54, inRangeNotes.get(3).getPitch());
		assertEquals(48, inRangeNotes.get(4).getPitch());
		assertEquals(56, inRangeNotes.get(5).getPitch());
	}

	@Test
	public void testPickRandomNoteFromRange() {
		int pitch = instrument.pickRandomNoteFromRange(Scale.MAJOR_SCALE);
		LOGGER.info("pitch: " + pitch);
	}

	@Test
	public void testPickRandomOctaveFromRange() {
		int octave = instrument.pickRandomOctaveFromRange();
		assertEquals(4, octave);
	}

}
