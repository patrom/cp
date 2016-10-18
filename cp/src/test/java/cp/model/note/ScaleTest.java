package cp.model.note;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

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

}