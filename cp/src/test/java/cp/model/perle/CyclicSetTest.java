package cp.model.perle;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;

public class CyclicSetTest {
	
	private CyclicSet cyclicSet;
	private IntervalCycle intervalCycle;

	@BeforeEach
	public void setUp() throws Exception {
		intervalCycle = IntervalCycle.P_IC7;
		cyclicSet = new CyclicSet(intervalCycle , 0);
	}

	@Test
	public void testGetCyclicSet() {
		assertArrayEquals(new int[]{0, 0, 7, 5, 2, 10, 9, 3, 4, 8, 11, 1, 6, 6, 1, 11, 8, 4, 3, 9, 10, 2, 5, 7}
			, cyclicSet.getCyclicSet());
	}

	@Test
	public void testGetLeftTonicSum() {
		assertEquals(0, cyclicSet.getLeftTonicSum());
	}

	@Test
	public void testGetRightTonicSum() {
		assertEquals(7, cyclicSet.getRightTonicSum());
	}

	@Test
	public void testGetTonicSums() {
		assertArrayEquals(new int[]{0, 7}, cyclicSet.getTonicSums());
	}

	@Test
	public void testGetName() {
		assertEquals("p0p7", cyclicSet.getName());
	}

	@Test
	public void testGetCyclicInterval() {
		assertEquals(intervalCycle.getInterval(), cyclicSet.getCyclicInterval());
	}

	@Test
	public void testTranspose() {
		cyclicSet.transpose(1);
		assertArrayEquals(new int[]{1, 1, 8, 6, 3, 11, 10, 4, 5, 9, 0, 2, 7, 7, 2, 0, 9, 5, 4, 10, 11, 3, 6, 8}
			, cyclicSet.getCyclicSet());
		assertEquals("p2p9", cyclicSet.getName());
	}

	@Test
	public void testSemiTranspose() {
		cyclicSet.semiTranspose(1);
		assertArrayEquals(new int[]{1, 2, 8, 7, 3, 0, 10, 5, 5, 10, 0, 3, 7, 8, 2, 1, 9, 6, 4, 11, 11, 4, 6, 9}
			, cyclicSet.getCyclicSet());
		assertEquals("i3i10", cyclicSet.getName());
	}

	@Test
	public void testInverse() {
		cyclicSet.inverse(1);
		assertArrayEquals(new int[]{1, 1, 6, 8, 11, 3, 4, 10, 9, 5, 2, 0, 7, 7, 0, 2, 5, 9, 10, 4, 3, 11, 8, 6}
			, cyclicSet.getCyclicSet());
		assertEquals("p2p7", cyclicSet.getName());
	}

}
