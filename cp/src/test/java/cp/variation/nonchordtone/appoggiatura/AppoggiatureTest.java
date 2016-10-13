package cp.variation.nonchordtone.appoggiatura;

import cp.DefaultConfig;
import cp.VariationConfig;
import cp.model.note.Note;
import cp.model.rhythm.DurationConstants;
import cp.variation.AbstractVariationTest;
import cp.variation.pattern.AppogiatureVariationPattern;
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
public class AppoggiatureTest extends AbstractVariationTest {

	@Autowired
	private AppoggiatureScaleDown appoggiature;
	@Autowired
	private AppogiatureVariationPattern appogiatureVariationPattern;
	private final double[][] appogiaturePattern =  new double[][]{{0.5, 0.5}};
	
	@Before
	public void setUp() throws Exception {
		variation = appoggiature;
		variationPattern = appogiatureVariationPattern;
		pattern = appogiaturePattern;
		setVariation();
	}

	@Test
	public void testCreateVariation() {
		Note firstNote = note().pc(4).pitch(64).pos(0).len(DurationConstants.QUARTER).ocatve(5).build();
		Note secondNote = note().pc(0).pitch(60).pos(DurationConstants.QUARTER).len(DurationConstants.QUARTER).ocatve(5).build();
		List<Note> notes = variation.createVariation(firstNote, secondNote);
		assertEquals(firstNote.getPitch(), notes.get(0).getPitch());
		assertEquals(secondNote.getPitch() + 2, notes.get(1).getPitch());
		assertEquals(secondNote.getPitch(), notes.get(2).getPitch());
		
		assertEquals(DurationConstants.QUARTER, notes.get(0).getLength());
		assertEquals(DurationConstants.EIGHT, notes.get(1).getLength());
		assertEquals(DurationConstants.EIGHT, notes.get(2).getLength());
	}
	
	@Test
	public void testCreateVariationNotAllowedLength() {
		List<Note> notes = testNotAllowedLength();
		assertTrue(notes.size() == 1);
		assertEquals(64, notes.get(0).getPitch());
	}

}
