package cp.model.note;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;

import cp.model.note.Scale;

public class ScaleTest {
	
	private Scale scale;
	
	@Before
	public void setUp() {
		scale = new Scale(Scale.MAJOR_SCALE);
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

}
