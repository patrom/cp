package cp.variation;

import cp.DefaultConfig;
import cp.VariationConfig;
import cp.composition.beat.BeatGroupConfig;
import cp.model.note.Note;
import cp.model.note.Scale;
import cp.model.rhythm.DurationConstants;
import cp.variation.nonchordtone.Variation;
import cp.variation.nonchordtone.neighbor.NeighborScaleDown;
import cp.variation.nonchordtone.passing.ChromaticPassingUp;
import cp.variation.nonchordtone.suspension.Suspension;
import cp.variation.pattern.VariationPattern;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static cp.model.note.NoteBuilder.note;
import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = {DefaultConfig.class, VariationConfig.class, BeatGroupConfig.class})
@ExtendWith(SpringExtension.class)
public class EmbellishingTest {
	
	@Autowired
	private Embellisher embellishing;
	@Autowired
	private NeighborScaleDown neighborScaleDown;
	@Autowired
	private Suspension suspension;
	@Autowired
	private ChromaticPassingUp chromaticPassingUp;
	@Autowired
	@Qualifier(value="NeigborVariationPattern")
	private VariationPattern variationPattern;
	
	@MockBean
	private VariationSelector variationSelector;

	@BeforeEach
	public void setUp() throws Exception {
		neighborScaleDown.setExcludedVoices(Collections.singletonList(0));
		suspension.setSecondNoteChanged(false);
	}

	@Test
	public void testEmbellishChromaticPassingUp() {
		setVariation(chromaticPassingUp, new double[][]{{0.5, 0.5}});
		List<Note> notes = new ArrayList<>();
		Note note = note().pc(0).pitch(60).pos(0).len(DurationConstants.QUARTER).octave(5).voice(3).build();
		notes.add(note);
		note = note().pc(2).pitch(62).pos(DurationConstants.QUARTER).len(DurationConstants.QUARTER).octave(5).voice(3).build();
		notes.add(note);
		note = note().pc(4).pitch(64).pos(DurationConstants.HALF).len(DurationConstants.QUARTER).octave(5).voice(3).build();
		notes.add(note);
		List<Note> embellishedMelody = embellishing.embellish(notes);
		assertEquals(5, embellishedMelody.size());
		assertEquals(60, embellishedMelody.get(0).getPitch());
		assertEquals(61, embellishedMelody.get(1).getPitch());
		assertEquals(62, embellishedMelody.get(2).getPitch());
		assertEquals(63, embellishedMelody.get(3).getPitch());
		assertEquals(64, embellishedMelody.get(4).getPitch());
		
		assertEquals(DurationConstants.EIGHT, embellishedMelody.get(0).getLength());
		assertEquals(DurationConstants.EIGHT, embellishedMelody.get(1).getLength());
		assertEquals(DurationConstants.EIGHT, embellishedMelody.get(2).getLength());
		assertEquals(DurationConstants.EIGHT, embellishedMelody.get(3).getLength());
		assertEquals(DurationConstants.QUARTER, embellishedMelody.get(4).getLength());
	}
	
	@Test
	public void testEmbellishNeigborScaleDown() {
		setVariation(neighborScaleDown, new double[][]{{0.5, 0.25, 0.25}});
		List<Note> notes = new ArrayList<>();
		Note note = note().pc(4).pitch(64).pos(0).len(DurationConstants.QUARTER).octave(5).voice(3).build();
		notes.add(note);
		note = note().pc(0).pitch(60).pos(DurationConstants.QUARTER).len(DurationConstants.QUARTER).octave(5).voice(3).build();
		notes.add(note);
		List<Note> embellishedMelody = embellishing.embellish(notes);
		assertEquals(4, embellishedMelody.size());
		assertEquals(64, embellishedMelody.get(0).getPitch());
		assertEquals(62, embellishedMelody.get(1).getPitch());
		assertEquals(64, embellishedMelody.get(2).getPitch());
		assertEquals(60, embellishedMelody.get(3).getPitch());
		
		assertEquals(DurationConstants.EIGHT, embellishedMelody.get(0).getLength());
		assertEquals(DurationConstants.SIXTEENTH, embellishedMelody.get(1).getLength());
		assertEquals(DurationConstants.SIXTEENTH, embellishedMelody.get(2).getLength());
		assertEquals(DurationConstants.QUARTER, embellishedMelody.get(3).getLength());
	}
	
	@Test
	public void testEmbellishSuspension() {
		setVariation(suspension, new double[][]{{0.5, 0.5}});
		List<Note> notes = new ArrayList<>();
		Note note = note().pc(2).pitch(62).pos(0).len(DurationConstants.QUARTER).octave(5).voice(3).build();
		notes.add(note);
		note = note().pc(0).pitch(60).pos(DurationConstants.QUARTER).len(DurationConstants.QUARTER).octave(5).voice(3).build();
		notes.add(note);
		List<Note> embellishedMelody = embellishing.embellish(notes);
		assertEquals(2, embellishedMelody.size());
		assertEquals(62, embellishedMelody.get(0).getPitch());
		assertEquals(60, embellishedMelody.get(1).getPitch());
		
		assertEquals(DurationConstants.THREE_EIGHTS, embellishedMelody.get(0).getLength());
		assertEquals(DurationConstants.EIGHT, embellishedMelody.get(1).getLength());
	}
	
	@Test
	public void testEmbellishSuspensionNotAllowedLength() {
		setVariation(suspension, new double[][]{{0.5, 0.5}});
		List<Note> notes = new ArrayList<>();
		Note note = note().pc(2).pitch(62).pos(0).len(DurationConstants.SIXTEENTH).octave(5).voice(3).build();
		notes.add(note);
		note = note().pc(0).pitch(60).pos(DurationConstants.QUARTER).len(DurationConstants.QUARTER).octave(5).voice(3).build();
		notes.add(note);
		List<Note> embellishedMelody = embellishing.embellish(notes);
		assertEquals(2, embellishedMelody.size());
		assertEquals(62, embellishedMelody.get(0).getPitch());
		assertEquals(60, embellishedMelody.get(1).getPitch());
		
		assertEquals(DurationConstants.SIXTEENTH, embellishedMelody.get(0).getLength());
		assertEquals(DurationConstants.QUARTER, embellishedMelody.get(1).getLength());
	}

	@Test
	public void testEmbellishExcludeVoice() {
		setVariation(neighborScaleDown, new double[][]{{0.5, 0.25, 0.25}});
		List<Note> notes = new ArrayList<>();
		Note note = note().pc(4).pitch(64).pos(0).len(DurationConstants.QUARTER).octave(5).voice(0).build();
		notes.add(note);
		note = note().pc(0).pitch(60).pos(DurationConstants.QUARTER).len(DurationConstants.QUARTER).octave(5).voice(0).build();
		notes.add(note);
		List<Note> embellishedMelody = embellishing.embellish(notes);
		assertEquals(2, embellishedMelody.size());
		assertEquals(64, embellishedMelody.get(0).getPitch());
		assertEquals(60, embellishedMelody.get(1).getPitch());
		
		assertEquals(DurationConstants.QUARTER, embellishedMelody.get(0).getLength());
		assertEquals(DurationConstants.QUARTER, embellishedMelody.get(1).getLength());
	}

	private void setVariation(Variation variation, double[][] pattern) {
		variation.setScales(Collections.singletonList(Scale.MAJOR_SCALE));
		variationPattern.setPatterns(pattern);
		List<Integer> allowedLengths = new ArrayList<>();
		allowedLengths.add(DurationConstants.QUARTER);
		variationPattern.setNoteLengths(allowedLengths);
		variationPattern.setSecondNoteLengths(allowedLengths);
		variation.setVariationPattern(variationPattern);
		when(variationSelector.selectVariation(Mockito.anyInt())).thenReturn(variation);
	}

}
