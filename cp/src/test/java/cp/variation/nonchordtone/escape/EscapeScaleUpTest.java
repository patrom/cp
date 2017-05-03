package cp.variation.nonchordtone.escape;

import cp.DefaultConfig;
import cp.VariationConfig;
import cp.model.note.Note;
import cp.model.rhythm.DurationConstants;
import cp.variation.AbstractVariationTest;
import cp.variation.pattern.EscapeVariationPattern;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.List;

import static cp.model.note.NoteBuilder.note;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = {DefaultConfig.class, VariationConfig.class})
public class EscapeScaleUpTest extends AbstractVariationTest{

	@Autowired
	private EscapeScaleUp escapeScaleUp;
	@Autowired
	private EscapeVariationPattern escapeVariationPattern;
	private final double[][] escapePattern =  new double[][]{{0.5, 0.5}};

	@Before
	public void setUp() throws Exception {
		variation = escapeScaleUp;
		variationPattern = escapeVariationPattern;
		pattern = escapePattern;
		setVariation();
	}
	
	@Test
	public void testCreateVariation() {
		Note firstNote = note().pc(4).pitch(64).pos(0).len(DurationConstants.QUARTER).octave(5).build();
		List<Note> notes = variation.createVariation(firstNote, null);
		assertEquals(firstNote.getPitch(), notes.get(0).getPitch());
		assertEquals(firstNote.getPitch() + 1, notes.get(1).getPitch());
		
		assertEquals(DurationConstants.EIGHT, notes.get(0).getLength());
		assertEquals(DurationConstants.EIGHT, notes.get(1).getLength());
	}
	
	@Test
	public void testCreateVariationNotAllowedLength() {
		List<Note> notes = testNotAllowedLength();
		assertTrue(notes.size() == 1);
		assertEquals(64, notes.get(0).getPitch());
	}
	
}
