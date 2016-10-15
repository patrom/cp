package cp.out.orchestration.orchestra;

import cp.DefaultConfig;
import cp.model.note.Note;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.ArrayList;
import java.util.List;

import static cp.model.note.NoteBuilder.note;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = DefaultConfig.class)
public class OrchestraTest {
	
	private final ClassicalOrchestra orchestra = new ClassicalOrchestra();

	@Test
	public void testSetViolin1ListOfNoteInstrumentUpdate() {
		List<Note> notes = new ArrayList<>();
		notes.add(note().pitch(48).build());
		notes.add(note().pitch(66).build());
		orchestra.setViolin1(notes, orchestra.getViolin1()::removeMelodyNotInRange);
		assertTrue(orchestra.getOrchestra().get(orchestra.getViolin1()).size() == 1);
		assertEquals(66, orchestra.getOrchestra().get(orchestra.getViolin1()).get(0).getPitch());
	}

	@Test
	public void testDuplicate() {
		List<Note> notes = new ArrayList<>();
		notes.add(note().pitch(60).build());
		notes.add(note().pitch(66).build());
		orchestra.setFlute(notes);
		List<Note> duplicates = orchestra.duplicate(orchestra.getFlute(), -12);
		assertTrue(duplicates.size() == 2);
		assertEquals(48, duplicates.get(0).getPitch());
		assertEquals(54, duplicates.get(1).getPitch());
	}

}
