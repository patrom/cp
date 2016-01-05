package cp.objective.meter;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import javax.swing.JFrame;

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
import org.springframework.boot.test.SpringApplicationContextLoader;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import cp.DefaultConfig;
import cp.VariationConfig;
import cp.generator.MelodyGenerator;
import cp.generator.MusicProperties;
import cp.model.note.Note;
import cp.model.note.NoteBuilder;
import cp.model.rhythm.RhythmWeight;
import cp.out.print.ScoreUtilities;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {DefaultConfig.class, VariationConfig.class}, loader = SpringApplicationContextLoader.class)
public class InnerMetricWeightTest extends JFrame {
	
	private static Logger LOGGER = LoggerFactory.getLogger(InnerMetricWeightTest.class.getName());
	private int[] rhythmPattern = {0, 6, 9 , 12, 18, 30, 36};
	private double minimumRhythmicValue = 3;
	
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
		notes.add(NoteBuilder.note().pos(6).build());
		notes.add(NoteBuilder.note().pos(9).build());
		notes.add(NoteBuilder.note().pos(12).build());
		notes.add(NoteBuilder.note().pos(18).build());
		notes.add(NoteBuilder.note().pos(30).build());
		notes.add(NoteBuilder.note().pos(36).build());
		return notes;
	}
	
	@Test
	public void testExtractOnsetNotes() {
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
		Assert.assertEquals(21.0, innerMetricWeights.get(6).doubleValue(), 0);
	}
	
	@Test
	public void testGetInnerMetricWeightNotes() {
		List<Note> notes = createMelody();
		InnerMetricWeight innerMetricWeights = innerMetricWeightFunctions.getInnerMetricWeight(notes, minimumRhythmicValue, distance);
		LOGGER.info(innerMetricWeights.getInnerMetricWeightMap().toString());
	}
	
	
	@Test
	public void testQuarter() {
		rhythmPattern = new int[]{0, 12, 24, 36, 48, 60, 72, 84, 96};
		minimumRhythmicValue = 12;
		calculateInnerMetricWeight();
		
		minimumRhythmicValue = 6;
		calculateInnerMetricWeight();
	}
	
	@Test
	public void testWhole() {
		rhythmPattern = new int[]{0, 48, 96, 144, 192};
		minimumRhythmicValue = 12;
		calculateInnerMetricWeight();
	}
	
	@Test
	public void testMeasureThreeFour() {
		distance = new int[]{3,6,9,12,15,18,20,21,22,24,26,27,28,30,32};//minimumRhythmicValue = 12 - measure 3/4
		minimumRhythmicValue = 12;
		
		rhythmPattern = new int[]{0, 48, 96, 144, 192};
		calculateInnerMetricWeight();
		
		rhythmPattern = new int[]{0, 36, 72, 108, 144, 180};
		calculateInnerMetricWeight();
	}
	
	@Test
	public void testMeasureFourFour() {
		distance = new int[]{2,4,8,10,12,14,16,18,20,21,22,24,26,27,28,30,32};//minimumRhythmicValue = 12 4/4
		minimumRhythmicValue = 12;
		
		rhythmPattern = new int[]{0, 48, 96, 144, 192};
		calculateInnerMetricWeight();
		
		rhythmPattern = new int[]{0, 36, 72, 108, 144, 180};
		calculateInnerMetricWeight();
	}
	
	@Test
	public void testMeasureDefault() {
		minimumRhythmicValue = 12;
		
		rhythmPattern = new int[]{0, 48, 96, 144, 192};
		calculateInnerMetricWeight();
		
		rhythmPattern = new int[]{0, 36, 72, 108, 144, 180};
		calculateInnerMetricWeight();
	}
	
	@Test
	public void testThreeQuarter() {
		rhythmPattern = new int[]{0, 36, 72, 108, 144, 180};
		minimumRhythmicValue = 12;
		calculateInnerMetricWeight();
	}
	
	@Test
	public void testDisplacement() {
		rhythmPattern = new int[]{0, 12, 36, 60, 84, 108, 120};
		minimumRhythmicValue = 12;
		Map<Integer, Double> normalizedMap = innerMetricWeightFunctions.getNormalizedInnerMetricWeight(rhythmPattern , minimumRhythmicValue, distance);
		LOGGER.info(normalizedMap.toString());
	}
	
	@Test
	public void test5() {
		rhythmPattern = new int[]{0, 60, 120, 180, 240, 300, 360};
		minimumRhythmicValue = 12;
		calculateInnerMetricWeight();
	}
	
	@Test
	public void test5is2and3() {
		rhythmPattern = new int[]{0, 24, 60, 84, 120, 144, 180, 204, 240, 264, 300};
		minimumRhythmicValue = 12;
		calculateInnerMetricWeight();
	}
	
	@Test
	public void test6() {
		rhythmPattern = new int[]{0, 72, 144, 216, 288, 360};
		minimumRhythmicValue = 12;
		calculateInnerMetricWeight();
	}
	
	@Test
	public void testHalf() {
		rhythmPattern = new int[]{0, 24, 48, 72, 96, 120, 144, 168, 192};
		minimumRhythmicValue = 12;
		calculateInnerMetricWeight();
	}
	
	@Test
	public void test() {
		rhythmPattern = new int[]{6, 24, 48};
		minimumRhythmicValue = 12;
		calculateInnerMetricWeight();
	}
	
	@Test
	public void testRandom() {
		rhythmPattern = new int[]{0, 24, 36, 84, 96,120, 156, 192};
		minimumRhythmicValue = 12;
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
		int[] harmony = {0, 96};
		int max = 8;
		rhythmPattern = melodyGenerator.generateMelodyPositions(harmony, 12, max);
		minimumRhythmicValue = 12;
		System.out.println(Arrays.toString(rhythmPattern));
		calculateInnerMetricWeight();
		List<Note> notes = new ArrayList<Note>();
		for (int i = 0; i < rhythmPattern.length; i++) {
			notes.add(NoteBuilder.note().pos(rhythmPattern[i]).pitch(60).len(6).build());
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
		rhythmPattern = new int[]{0, 12, 60,  96};
		minimumRhythmicValue = 12;
		calculateInnerMetricWeight();
	}
	
}
