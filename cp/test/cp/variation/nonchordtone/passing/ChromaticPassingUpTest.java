package cp.variation.nonchordtone.passing;

import static cp.model.note.NoteBuilder.note;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import cp.DefaultConfig;
import cp.VariationConfig;
import cp.model.note.Note;
import cp.variation.AbstractVariationTest;
import cp.variation.pattern.PassingVariationPattern;
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {DefaultConfig.class, VariationConfig.class})
public class ChromaticPassingUpTest extends AbstractVariationTest{

	@Autowired
	private PassingUp passingUp;
	@Autowired
	private PassingVariationPattern passingVariationPattern;
	private double[][] passingPattern =  new double[][]{{0.5, 0.5}};

	@Before
	public void setUp() throws Exception {
		variation = passingUp;
		variationPattern = passingVariationPattern;
		pattern = passingPattern;
		setVariation();
	}

	@Test
	public void testCreateVariation() {
		Note firstNote = note().pc(2).pitch(62).pos(12).len(12).ocatve(5).build();
		List<Note> notes = variation.createVariation(firstNote, null);
		assertEquals(firstNote.getPitch(), notes.get(0).getPitch());
		assertEquals(firstNote.getPitch() + 2, notes.get(1).getPitch());
		
		assertEquals(6, notes.get(0).getLength());
		assertEquals(6, notes.get(1).getLength());
	}
	
	@Test
	public void testCreateVariationNotAllowedLength() {
		List<Note> notes = testNotAllowedLength();
		assertTrue(notes.size() == 1);
		assertEquals(64, notes.get(0).getPitch());
	}

}
