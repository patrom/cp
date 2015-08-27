package cp.variation.nonchordtone.suspension;

import static cp.model.note.NoteBuilder.note;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.SpringApplicationContextLoader;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import cp.DefaultConfig;
import cp.VariationConfig;
import cp.model.note.Note;
import cp.model.note.Scale;
import cp.variation.AbstractVariationTest;
import cp.variation.nonchordtone.Variation;
import cp.variation.pattern.NeigborVariationPattern;
import cp.variation.pattern.VariationPattern;
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {DefaultConfig.class, VariationConfig.class}, loader = SpringApplicationContextLoader.class)
public class SuspensionTest extends AbstractVariationTest{

	@Autowired
	private Suspension suspension;
	@Autowired
	private NeigborVariationPattern neigborVariationPattern;
	private double[][] neigbborPattern =  new double[][]{{0.5, 0.5}};
	
	@Before
	public void setUp() throws Exception {
		variation = suspension;
		variationPattern = neigborVariationPattern;
		pattern = neigbborPattern;
		setVariation();
	}

	@Test
	public void testCreateVariation() {
		Note firstNote = note().pc(2).pitch(62).pos(0).len(12).ocatve(5).build();
		Note secondNote = note().pc(0).pitch(60).pos(12).len(12).ocatve(5).build();
		List<Note> notes = variation.createVariation(firstNote, secondNote);
		assertEquals(firstNote.getPitch(), notes.get(0).getPitch());
		assertEquals(secondNote.getPitch(), notes.get(1).getPitch());
		
		assertEquals(18, notes.get(0).getLength());
		assertEquals(6, notes.get(1).getLength());
		assertEquals(18, notes.get(1).getPosition());
	}
	
	@Test
	public void testCreateVariationNotAllowedLength() {
		List<Note> notes = testNotAllowedLength();
		assertTrue(notes.size() == 1);
		assertEquals(64, notes.get(0).getPitch());
	}

}
