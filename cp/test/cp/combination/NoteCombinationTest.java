package cp.combination;

import static org.junit.Assert.*;

import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import cp.DefaultConfig;
import cp.VariationConfig;
import cp.model.note.Note;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = {DefaultConfig.class, VariationConfig.class})
public class NoteCombinationTest {
	
	@Autowired
	private NoteCombination noteCombination;
	@Autowired
	@Qualifier("FixedEven")
	private Combination combination;

	@Before
	public void setUp() throws Exception {
		noteCombination.setCombinations(1, combination);
	}

	@Test
	public void testGetNotes() {
		fail("Not yet implemented");
	}

	@Test
	public void testGetNotesFixed() {
		List<Note> notes = noteCombination.getNotesFixed(24, 1);
		for (Note note : notes) {
			System.out.println(note.getPosition());
		}
	}

}
