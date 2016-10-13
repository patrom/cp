package cp.objective.meter;

import cp.DefaultConfig;
import cp.VariationConfig;
import cp.generator.MelodyGenerator;
import cp.generator.MusicProperties;
import cp.model.note.Note;
import cp.model.note.NoteBuilder;
import cp.model.rhythm.DurationConstants;
import cp.model.rhythm.RhythmWeight;
import cp.out.print.ScoreUtilities;
import jm.music.data.Part;
import jm.music.data.Phrase;
import jm.music.data.Score;
import jm.util.Play;
import jm.util.View;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.swing.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import static cp.model.rhythm.DurationConstants.*;
import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertTrue;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = {DefaultConfig.class, VariationConfig.class})
public class InnerMetricWeightTest extends JFrame {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(InnerMetricWeightTest.class.getName());
	private int[] rhythmPattern = {0, EIGHT, EIGHT + SIXTEENTH ,
			QUARTER, THREE_EIGHTS, HALF + EIGHT, SIX_EIGHTS};
	private double minimumRhythmicValue = SIXTEENTH;
	
	@Autowired
	private InnerMetricWeightFunctions innerMetricWeightFunctions;
	@Autowired
	private MelodyGenerator melodyGenerator;
	@Autowired
	private ScoreUtilities scoreUtilities;
	@Autowired
	private RhythmWeight rhythmWeight;
	@Autowired
	private MusicProperties musicProperties;
	
	private int[] distance;
	
	@Before
	public void setUp() {
		distance = new int[]{2,3,4,5,6,8,9,10,12,14,15,16,18,20,21,22,24,26,27,28,30,32};//atomic beat = 12
		musicProperties.setDistance(distance);
	}

	@Test
	public void testGetLocalMeters() {
		Integer[] onSetArr = {0, 2, 3, 4, 6, 10, 12};
		List<List<Integer>> localMeters = innerMetricWeightFunctions.getLocalMeters(onSetArr, distance);
		List<Integer> localMeter = new ArrayList<>();
		localMeter.add(0);
		localMeter.add(3);
		localMeter.add(6);
		assertTrue(localMeters.contains(localMeter));
		LOGGER.info(localMeter.toString());
	}

	@Test
	public void testGetNormalizedInnerMetricWeight() {
		Map<Integer, Double> normalizedMap = innerMetricWeightFunctions.getNormalizedInnerMetricWeight(rhythmPattern , minimumRhythmicValue, distance);
		LOGGER.info(normalizedMap.toString());
	}
	
	@Test
	public void testGetNormalizedInnerMetricWeightNotes() {
		List<Note> notes = createMelody();
		Map<Integer, Double> normalizedMap = innerMetricWeightFunctions.getNormalizedInnerMetricWeight(notes, minimumRhythmicValue, distance);
		LOGGER.info(normalizedMap.toString());
	}

	private List<Note> createMelody() {
		List<Note> notes = new ArrayList<>();
		notes.add(NoteBuilder.note().pos(0).build());
		notes.add(NoteBuilder.note().pos(DurationConstants.EIGHT).build());
		notes.add(NoteBuilder.note().pos(DurationConstants.EIGHT + DurationConstants.SIXTEENTH).build());
		notes.add(NoteBuilder.note().pos(DurationConstants.QUARTER).build());
		notes.add(NoteBuilder.note().pos(DurationConstants.THREE_EIGHTS).build());
		notes.add(NoteBuilder.note().pos(DurationConstants.HALF + DurationConstants.EIGHT).build());
		notes.add(NoteBuilder.note().pos(DurationConstants.SIX_EIGHTS).build());
		return notes;
	}
	
	@Test
	public void s() {
		List<Note> notes = createMelody();
		Integer[] onSetArr = innerMetricWeightFunctions.extractOnsetNotes(notes, minimumRhythmicValue);
		Integer[] expected = {0, 2, 3, 4, 6, 10, 12};
		assertArrayEquals(expected, onSetArr);
	}

	@Test
	public void testExtractOnset() {
		Integer[] onSetArr = innerMetricWeightFunctions.extractOnset(rhythmPattern , minimumRhythmicValue);
		Integer[] expected = {0, 2, 3, 4, 6, 10, 12};
		assertArrayEquals(expected, onSetArr);
	}
	
	@Test
	public void testGetInnerMetricWeight() {
		Integer[] onSetArr = {0, 2, 3, 4, 6, 10, 12};
		List<List<Integer>> localMeters = innerMetricWeightFunctions.getLocalMeters(onSetArr, distance);
		Map<Integer, Double> innerMetricWeights = innerMetricWeightFunctions.getInnerMetricWeight(localMeters, onSetArr);
		LOGGER.info(innerMetricWeights.toString());
		Assert.assertEquals(21.0, innerMetricWeights.get(6), 0);
	}
	
	@Test
	public void testGetInnerMetricWeightNotes() {
		List<Note> notes = createMelody();
		InnerMetricWeight innerMetricWeights = innerMetricWeightFunctions.getInnerMetricWeight(notes, minimumRhythmicValue, distance);
		LOGGER.info(innerMetricWeights.getInnerMetricWeightMap().toString());
	}
	
	
	@Test
	public void testQuarter() {
		rhythmPattern = new int[]{0, QUARTER, HALF, SIX_EIGHTS, WHOLE, WHOLE + QUARTER, WHOLE + HALF, WHOLE + HALF + QUARTER, WHOLE * 2};
		minimumRhythmicValue = QUARTER;
		calculateInnerMetricWeight();
		
		minimumRhythmicValue = EIGHT;
		calculateInnerMetricWeight();
	}
	
	@Test
	public void testWhole() {
		rhythmPattern = new int[]{0, WHOLE, WHOLE * 2, WHOLE * 3, WHOLE * 4};
		minimumRhythmicValue = QUARTER;
		calculateInnerMetricWeight();
	}
	
	@Test
	public void testMeasureThreeFour() {
		distance = new int[]{3,6,9,12,15,18,20,21,22,24,26,27,28,30,32};//minimumRhythmicValue = 12 - measure 3/4
		minimumRhythmicValue = QUARTER;
		
		rhythmPattern = new int[]{0, WHOLE, WHOLE * 2, WHOLE * 3, WHOLE * 4};
		calculateInnerMetricWeight();
		
		rhythmPattern = new int[]{0, SIX_EIGHTS, WHOLE + HALF, WHOLE * 2 + QUARTER, WHOLE * 3, WHOLE * 3 + HALF + QUARTER};
		calculateInnerMetricWeight();
	}
	
	@Test
	public void testMeasureFourFour() {
		distance = new int[]{2,4,8,10,12,14,16,18,20,21,22,24,26,27,28,30,32};//minimumRhythmicValue = 12 4/4
		minimumRhythmicValue = QUARTER;
		
		rhythmPattern = new int[]{0, WHOLE, WHOLE * 2, WHOLE * 3, WHOLE * 4};
		calculateInnerMetricWeight();
		
		rhythmPattern = new int[]{0, SIX_EIGHTS, WHOLE + HALF, WHOLE * 2 + QUARTER, WHOLE * 3, WHOLE * 3 + HALF + QUARTER};
		calculateInnerMetricWeight();
	}
	
	@Test
	public void testMeasureDefault() {
		minimumRhythmicValue = QUARTER;
		
		rhythmPattern = new int[]{0, WHOLE, WHOLE * 2, WHOLE * 3, WHOLE * 4};
		calculateInnerMetricWeight();
		
		rhythmPattern = new int[]{0, SIX_EIGHTS, WHOLE + HALF, WHOLE * 2 + QUARTER, WHOLE * 3, WHOLE * 3 + HALF + QUARTER};
		calculateInnerMetricWeight();
	}
	
	@Test
	public void testThreeQuarter() {
		rhythmPattern = new int[]{0, SIX_EIGHTS, WHOLE + HALF, WHOLE * 2 + QUARTER, WHOLE * 3, WHOLE * 3 + HALF + QUARTER};
		minimumRhythmicValue = QUARTER;
		calculateInnerMetricWeight();
	}
	
	@Test
	public void testDisplacement() {
		rhythmPattern = new int[]{0, QUARTER, SIX_EIGHTS, WHOLE + QUARTER, WHOLE + HALF + QUARTER, WHOLE * 2 + QUARTER, WHOLE * 2 + HALF};
		minimumRhythmicValue = QUARTER;
		Map<Integer, Double> normalizedMap = innerMetricWeightFunctions.getNormalizedInnerMetricWeight(rhythmPattern , minimumRhythmicValue, distance);
		LOGGER.info(normalizedMap.toString());
	}
	
	@Test
	public void test5() {
		distance = new int[]{5,10,15,20};//atomic beat = 12
		rhythmPattern = new int[]{0, WHOLE + QUARTER, WHOLE * 2 + HALF, WHOLE * 3 + HALF + QUARTER, WHOLE * 5, WHOLE * 6 + QUARTER, WHOLE * 7 + HALF};
		minimumRhythmicValue = QUARTER;
		calculateInnerMetricWeight();
		
		rhythmPattern = new int[]{0, WHOLE, WHOLE * 2, WHOLE * 3, WHOLE * 4};//4
		calculateInnerMetricWeight();
		
		rhythmPattern = new int[]{0, SIX_EIGHTS, WHOLE + HALF, WHOLE * 2 + QUARTER, WHOLE * 3, WHOLE * 3 + HALF + QUARTER};//3
		calculateInnerMetricWeight();
	}
	
	@Test
	public void test5is2and3() {
		distance = new int[]{2,5,7,10,12,15,17,20};//atomic beat = 12
		rhythmPattern = new int[]{0, HALF, WHOLE + QUARTER, WHOLE + HALF + QUARTER, WHOLE * 2 + HALF, WHOLE * 3, WHOLE * 3 + HALF + QUARTER, WHOLE * 4 + QUARTER, WHOLE * 5, WHOLE * 5 + HALF, WHOLE * 6 + QUARTER};
		minimumRhythmicValue = QUARTER;
		calculateInnerMetricWeight();
		
		rhythmPattern = new int[]{0, WHOLE + QUARTER, WHOLE * 2 + HALF, WHOLE * 3 + HALF + QUARTER, WHOLE * 5, WHOLE * 6 + QUARTER, WHOLE * 7 + HALF};
		minimumRhythmicValue = QUARTER;
		calculateInnerMetricWeight();
		
		rhythmPattern = new int[]{0, WHOLE, WHOLE * 2, WHOLE * 3, WHOLE * 4};//4
		calculateInnerMetricWeight();
		
		rhythmPattern = new int[]{0, SIX_EIGHTS, WHOLE + HALF, WHOLE * 2 + QUARTER, WHOLE * 3, WHOLE * 3 + HALF + QUARTER};//3
		calculateInnerMetricWeight();
	}
	
	@Test
	public void test6() {
		rhythmPattern = new int[]{0, WHOLE + HALF, WHOLE * 3, WHOLE * 4 + HALF, WHOLE * 6, WHOLE * 7 + HALF};
		minimumRhythmicValue = EIGHT;
		calculateInnerMetricWeight();
		
		distance = new int[]{3,6,9,12,15,18,20,21,22,24,26,27,28,30,32};//minimumRhythmicValue = 12 - measure 3/4 - 6/8
		rhythmPattern = new int[]{0, WHOLE + HALF, WHOLE * 3, WHOLE * 4 + HALF, WHOLE * 6, WHOLE * 7 + HALF};
		minimumRhythmicValue = EIGHT;
		calculateInnerMetricWeight();
	}
	
	@Test
	public void testHalf() {
		rhythmPattern = new int[]{0, HALF, WHOLE, WHOLE + HALF, WHOLE * 2, WHOLE * 2 + HALF, WHOLE * 3, WHOLE * 3 + HALF, WHOLE * 4};
		minimumRhythmicValue = QUARTER;
		calculateInnerMetricWeight();
	}
	
	@Test
	public void test() {
		rhythmPattern = new int[]{EIGHT, HALF, WHOLE};
		minimumRhythmicValue = QUARTER;
		calculateInnerMetricWeight();
	}
	
	@Test
	public void testRandom() {
		rhythmPattern = new int[]{0, HALF, SIX_EIGHTS, WHOLE + HALF + QUARTER, WHOLE * 2,WHOLE * 2 + HALF, WHOLE * 3 + QUARTER, WHOLE * 4};
		minimumRhythmicValue = QUARTER;
		calculateInnerMetricWeight();
	}

	private void calculateInnerMetricWeight() {
		LOGGER.info("Distance: " + Arrays.toString(distance));
		InnerMetricWeight innerMetricWeight = innerMetricWeightFunctions.getInnerMetricWeight(rhythmPattern , minimumRhythmicValue, distance);
		LOGGER.info(innerMetricWeight.getInnerMetricWeightMap().toString());
		LOGGER.info(String.valueOf(innerMetricWeight.getInnerMetricWeightAverage()));
	}
	
	@Test
	public void testGenerateMelody() {
		int[] harmony = {0, WHOLE * 2};
		int max = 8;
		rhythmPattern = melodyGenerator.generateMelodyPositions(harmony, QUARTER, max);
		minimumRhythmicValue = QUARTER;
		System.out.println(Arrays.toString(rhythmPattern));
		calculateInnerMetricWeight();
		List<Note> notes = new ArrayList<>();
		for (int i = 0; i < rhythmPattern.length; i++) {
			notes.add(NoteBuilder.note().pos(rhythmPattern[i]).pitch(60).len(DurationConstants.EIGHT).build());
		}
		
		Score score = new Score();
		Phrase phrase = scoreUtilities.createPhrase(notes);	
		Part part = new Part(phrase);
		score.add(part);
		View.notate(score);
		Play.midi(score, true);
	}
	
	@Test
	public void testDefault() {
		rhythmPattern = new int[]{0, QUARTER, WHOLE + QUARTER,  WHOLE * 2};
		minimumRhythmicValue = QUARTER;
		calculateInnerMetricWeight();
	}
	
}
