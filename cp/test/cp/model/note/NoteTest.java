package cp.model.note;

import static cp.model.note.NoteBuilder.note;
import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;

public class NoteTest {
	
	private Note note;

	@Before
	public void setUp() throws Exception {
		note = note().pc(11).pitch(59).ocatve(4).build();
	}

	@Test
	public void testUpdateNoteInt() {
		note.updateNote(5);
		assertEquals(71, note.getPitch());
		assertEquals(5, note.getOctave());
	}

	@Test
	public void testTranspose() {
		note.transpose(2);
		assertEquals(1, note.getPitchClass());
		assertEquals(61, note.getPitch());
		assertEquals(5, note.getOctave());
	}
	
	@Test
	public void testTransposeDown() {
		note.transpose(-2);
		assertEquals(9, note.getPitchClass());
		assertEquals(57, note.getPitch());
		assertEquals(4, note.getOctave());
	}

}
