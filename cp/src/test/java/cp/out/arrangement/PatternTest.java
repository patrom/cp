package cp.out.arrangement;

import cp.model.note.Note;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class PatternTest {

	@Test
	public void testWaltz() {
		List<Note> pattern = Pattern.waltz(6, 6);
		assertEquals(6, pattern.get(0).getPosition());
		assertEquals(12, pattern.get(1).getPosition());
	}

	@Test
	public void testRepeat() {
		List<Note> pattern = Pattern.repeat(6, 24);
		pattern.forEach(note -> assertTrue(note.getLength() == 6));
		assertTrue(pattern.size() == 4);
		assertEquals(18, pattern.get(3).getPosition());
	}

}
