package cp.out.orchestration.orchestra;

import cp.DefaultConfig;
import cp.model.note.Note;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.ArrayList;
import java.util.List;

import static cp.model.note.NoteBuilder.note;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = DefaultConfig.class)
public class OrchestraTest {
	
	private final ClassicalOrchestra orchestra = new ClassicalOrchestra();

	@Test
	public void testSetViolin1ListOfNoteInstrumentUpdate() {
		List<Note> notes = new ArrayList<>();
		notes.add(note().pitch(48).build());
		notes.add(note().pitch(66).build());
		orchestra.setViolin1(notes, orchestra.getViolin1().getInstrument()::removeMelodyNotInRange);
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
