package cp.model.note;

import cp.util.RandomUtil;
import org.junit.Before;
import org.junit.Test;

import java.util.Arrays;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class ScaleTest {
	
	private Scale scale;
	
	@Before
	public void setUp() {
		scale = Scale.MAJOR_SCALE;
	}

	@Test
	public void testTransposePitchClass() {
		int transposed = scale.transposePitchClass(4, 2);
		assertEquals(7, transposed);
	}

	@Test
	public void testPickNextPitchFromScale() {
		int next = scale.pickNextPitchFromScale(4);
		assertEquals(5, next);
	}
	
	@Test
	public void testPickNextPitchFromScaleNoteNotInScale() {
		int next = scale.pickNextPitchFromScale(10);
		assertEquals(11, next);
	}

	@Test
	public void testPickPreviousPitchFromScale() {
		int previous = scale.pickPreviousPitchFromScale(4);
		assertEquals(2, previous);
	}
	
	@Test
	public void testPickPreviousPitchFromScaleNoteNotInScale() {
		int previous = scale.pickPreviousPitchFromScale(6);
		assertEquals(5, previous);
	}

	@Test
	public void testPickLowerStepFromScale() {
		int lower = scale.pickLowerStepFromScale(4, 3);
		assertEquals(11, lower);
	}
	
	@Test
	public void testInversedPitchClass() {
		int pc = scale.getInversedPitchClass(1,2);
		assertEquals(11, pc);
		pc = scale.getInversedPitchClass(1,4);
		assertEquals(9, pc);
		pc = scale.getInversedPitchClass(1,5);
		assertEquals(7, pc);
		
		pc = scale.getInversedPitchClass(2,4);
		assertEquals(0, pc);
		pc = scale.getInversedPitchClass(2,5);
		assertEquals(11, pc);
		
		pc = scale.getInversedPitchClass(7,0);
		assertEquals(9, pc);
		pc = scale.getInversedPitchClass(7,2);
		assertEquals(7, pc);
		
		scale = Scale.HARMONIC_MINOR_SCALE;
		pc = scale.getInversedPitchClass(2,3);
		assertEquals(0, pc);
		pc = scale.getInversedPitchClass(2,5);
		assertEquals(11, pc);
	}

	@Test
	public void testTransposePitchClassAll(){
		scale = Scale.MAJOR_SCALE;
		int[] transpositions = new int[scale.getPitchClasses().length];
		for (int i = 0; i < scale.getPitchClasses().length; i++) {
			int transposed = scale.transposePitchClass(4, i);
			System.out.println(transposed);
			transpositions[i] = transposed;
		}
		Arrays.sort(transpositions);
		assertTrue(Arrays.equals(transpositions, scale.getPitchClasses()));
	}

	@Test
	public void testPickHigerStepFromScale() {
		int higher = scale.pickHigerStepFromScale(4, 3);
		assertEquals(9, higher);

		higher = scale.pickHigerStepFromScale(9, 3);
		assertEquals(2, higher);

		higher = scale.pickHigerStepFromScale(4, 2);
		assertEquals(7, higher);
	}

	@Test
	public void testInverse() {
		Scale minorScale = Scale.HARMONIC_MINOR_SCALE;
		int functionalDegreeCenter = RandomUtil.getRandomNumberInRange(1,scale.getPitchClasses().length);
		System.out.println("functionalDegreeCenter : " + functionalDegreeCenter);
		for (int pc : scale.getPitchClasses()) {
			System.out.println("pc: " + pc);
			int inversePc = minorScale.inverse(scale , pc,  functionalDegreeCenter);//key of E
			System.out.println("inverse " + inversePc);
		}
	}

	@Test
	public void getPitchClassesRandomized(){
		scale = Scale.ALL_COMBINATORIAL_HEXAHCORD_C;
		int[] pitchClassesRandomized = scale.getPitchClassesRandomized();
		Arrays.stream(pitchClassesRandomized).forEach(n -> System.out.println(n));
		assertEquals(6, pitchClassesRandomized.length);
	}

}
