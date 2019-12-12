package cp.variation.nonchordtone.suspension;

import cp.DefaultConfig;
import cp.VariationConfig;
import cp.config.BeatGroupConfig;
import cp.model.note.Note;
import cp.model.rhythm.DurationConstants;
import cp.variation.AbstractVariationTest;
import cp.variation.pattern.FreeSuspensionVariationPattern;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;

import static cp.model.note.NoteBuilder.note;
import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = {DefaultConfig.class, VariationConfig.class, BeatGroupConfig.class})
@TestPropertySource(properties = "composition.voices=4")
public class FreeSuspensionScaleDownTest extends AbstractVariationTest{

	@Autowired
	private FreeSuspensionScaleDown freeSuspensionScaleDown;
	@Autowired
	private FreeSuspensionVariationPattern freeSuspensionVariationPattern;
	private final double[][] suspensionPattern =  new double[][]{{0.5, 0.5}};
	
	@BeforeEach
	public void setUp() throws Exception {
		variation = freeSuspensionScaleDown;
		variationPattern = freeSuspensionVariationPattern;
		pattern = suspensionPattern;
		setVariation();
	}

	@Test
	public void testCreateVariation() {
		Note firstNote = note().pc(2).pitch(62).pos(0).len(DurationConstants.QUARTER).octave(5).build();
		List<Note> notes = variation.createVariation(firstNote, null);
        assertEquals(firstNote.getPitch() + 2, notes.get(0).getPitch());
		assertEquals(firstNote.getPitch(), notes.get(1).getPitch());
		
		assertEquals(DurationConstants.EIGHT, notes.get(0).getLength());
		assertEquals(DurationConstants.EIGHT, notes.get(1).getLength());
	}
	
	@Test
	public void testCreateVariationNotAllowedLength() {
		List<Note> notes = testNotAllowedLength();
        Assertions.assertTrue(notes.size() == 1);
		assertEquals(64, notes.get(0).getPitch());
	}

}
