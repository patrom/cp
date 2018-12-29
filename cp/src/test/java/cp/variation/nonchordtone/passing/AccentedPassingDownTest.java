package cp.variation.nonchordtone.passing;

import cp.DefaultConfig;
import cp.VariationConfig;
import cp.config.BeatGroupConfig;
import cp.model.note.Note;
import cp.model.rhythm.DurationConstants;
import cp.variation.AbstractVariationTest;
import cp.variation.pattern.PassingVariationPattern;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

import static cp.model.note.NoteBuilder.note;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
@RunWith(SpringRunner.class)
@SpringBootTest(classes = {DefaultConfig.class, VariationConfig.class, BeatGroupConfig.class})
@ExtendWith(SpringExtension.class)
public class AccentedPassingDownTest extends AbstractVariationTest{

	@Autowired
	private AccentedPassingDown accentedPassingDown;
	@Autowired
	private PassingVariationPattern passingVariationPattern;
	private final double[][] passingPattern =  new double[][]{{0.5, 0.5}};

	@BeforeEach
	public void setUp() throws Exception {
		variation = accentedPassingDown;
		variationPattern = passingVariationPattern;
		pattern = passingPattern;
		setVariation();
	}

	@Test
	public void testCreateVariation() {
		Note firstNote = note().pc(4).pitch(64).pos(0).len(DurationConstants.QUARTER).octave(5).build();
		Note secondNote = note().pc(0).pitch(60).pos(DurationConstants.QUARTER).len(DurationConstants.QUARTER).octave(5).build();
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
