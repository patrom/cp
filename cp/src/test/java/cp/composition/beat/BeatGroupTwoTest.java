package cp.composition.beat;

import cp.DefaultConfig;
import cp.VariationConfig;
import cp.combination.RhythmCombination;
import cp.combination.even.TwoNoteEven;
import cp.model.note.Note;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = {DefaultConfig.class, VariationConfig.class})
public class BeatGroupTwoTest {
	
	private BeatGroup beatGroup;
	@Autowired
	private TwoNoteEven twoNoteEven;

	@Before
	public void setUp() throws Exception {
		List<RhythmCombination> rhythmCombinations = new ArrayList<>();
		rhythmCombinations.add(twoNoteEven::pos13);
		rhythmCombinations.add(twoNoteEven::pos12);
		beatGroup = new BeatGroupTwo(12, rhythmCombinations);
	}

	@Test
	public void testGetNotes() {
		List<Note> notes = beatGroup.getNotes();
		assertEquals(0, notes.get(0).getPosition());
		assertEquals(6, notes.get(1).getPosition());
		assertEquals(12, notes.get(2).getPosition());
		assertEquals(15, notes.get(3).getPosition());
	}

}
