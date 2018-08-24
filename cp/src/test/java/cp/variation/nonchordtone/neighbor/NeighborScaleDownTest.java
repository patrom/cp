package cp.variation.nonchordtone.neighbor;

import cp.DefaultConfig;
import cp.VariationConfig;
import cp.composition.beat.BeatGroupConfig;
import cp.model.note.Note;
import cp.model.rhythm.DurationConstants;
import cp.variation.AbstractVariationTest;
import cp.variation.pattern.NeigborVariationPattern;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

import static cp.model.note.NoteBuilder.note;
import static org.junit.Assert.assertEquals;
@RunWith(SpringRunner.class)
@SpringBootTest(classes = {DefaultConfig.class, VariationConfig.class, BeatGroupConfig.class})
public class NeighborScaleDownTest extends AbstractVariationTest {
	
	@Autowired
	private NeighborScaleDown neigborScaleDown;
	@Autowired
	private NeighborScaleUp neighborScaleUp;
	@Autowired
	@Qualifier(value="NeigborVariationPattern")
	private NeigborVariationPattern neigborVariationPattern;
	private final double[][] neighborPattern = new double[][]{{0.5, 0.25, 0.25}};
	private final double[] patt = new double[]{0.5, 0.25, 0.25};
	
	@Before
	public void setUp() throws Exception {
		variationPattern = neigborVariationPattern;
		super.pattern = neighborPattern;
	}

	@Test
	public void testCreateVariation() {
		variation = neigborScaleDown;
		setVariation();
		Note firstNote = note().pc(2).pitch(62).pos(DurationConstants.QUARTER).len(DurationConstants.QUARTER).octave(5).build();
		List<Note> notes = variation.createVariation(firstNote ,null);
		assertEquals(firstNote.getPitch(), notes.get(0).getPitch());
		assertEquals(firstNote.getPitch() - 2, notes.get(1).getPitch());
		assertEquals(firstNote.getPitch(), notes.get(2).getPitch());
		
		assertEquals(DurationConstants.EIGHT, notes.get(0).getLength());
		assertEquals(DurationConstants.SIXTEENTH, notes.get(1).getLength());
		assertEquals(DurationConstants.SIXTEENTH, notes.get(2).getLength());
	}

	@Test
	public void testGenerateNeigborNote() {
		variation = neigborScaleDown;
		setVariation();
		Note note = note().pc(0).pitch(60).pos(DurationConstants.QUARTER).len(DurationConstants.QUARTER).octave(5).build();
		List<Note> notes = neigborScaleDown.generateNeighborNote(note, 11, 59, patt);
		assertEquals(note.getPitch(), notes.get(0).getPitch());
		assertEquals(note.getPitch() - 1, notes.get(1).getPitch());
		assertEquals(note.getPitch(), notes.get(2).getPitch());
		
		assertEquals(DurationConstants.EIGHT, notes.get(0).getLength());
		assertEquals(DurationConstants.SIXTEENTH, notes.get(1).getLength());
		assertEquals(DurationConstants.SIXTEENTH, notes.get(2).getLength());
	}
	
	@Test
	public void testCreateVariationUp() {
		variation = neighborScaleUp;
		setVariation();
		Note firstNote = note().pc(2).pitch(62).pos(DurationConstants.QUARTER).len(DurationConstants.QUARTER).octave(5).build();
		List<Note> notes = variation.createVariation(firstNote, null);
		assertEquals(firstNote.getPitch(), notes.get(0).getPitch());
		assertEquals(firstNote.getPitch() + 2, notes.get(1).getPitch());
		assertEquals(firstNote.getPitch(), notes.get(2).getPitch());
		
		assertEquals(DurationConstants.EIGHT, notes.get(0).getLength());
		assertEquals(DurationConstants.SIXTEENTH, notes.get(1).getLength());
		assertEquals(DurationConstants.SIXTEENTH, notes.get(2).getLength());
	}
	
	@Test
	public void testCreateVariationNotAllowedLength() {
		variation = neighborScaleUp;
		setVariation();
		List<Note> notes = testNotAllowedLength();
		assertEquals(1, notes.size());
		assertEquals(64, notes.get(0).getPitch());
	}

}
