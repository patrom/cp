package cp.model.perle;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.EnumSet;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class AxisDyadArrayTest {
	
	private AxisDyadArray axisDyadArray;

	@BeforeEach
	public void setUp() throws Exception {
		 axisDyadArray = new AxisDyadArray(new CyclicSet(IntervalCycle.P_IC7, 0), 0,
				new CyclicSet(IntervalCycle.P_IC7, 2), 0);
	}
	
	@Test
	public void allTonicAxisDyadChords(){
		AxisDyadArray axisDyadArray = new AxisDyadArray(new CyclicSet(IntervalCycle.P_IC1, 5), 0,
				new CyclicSet(IntervalCycle.P_IC1, 3), 0);
		System.out.print(axisDyadArray);
		System.out.println(axisDyadArray.printArray());
		List<List<Integer>> tonicAxisDyadChords = axisDyadArray.getAllTonicAxisDyadChords();
		for (List<Integer> list : tonicAxisDyadChords) {
			System.out.println(list);
		}
	}
	
	@Test
	public void allAxisDyadArray(){
		List<Integer> chord = new ArrayList<>();
		chord.add(2);
		chord.add(1);
		chord.add(0);
		chord.add(9);
		
		for (int i = 0; i < 24; i++) {
//			for (int j = 0; j < 24; j++) {
				AxisDyadArray axisDyadArray = new AxisDyadArray(new CyclicSet(IntervalCycle.P_IC7, 0), 0,
						new CyclicSet(IntervalCycle.P_IC7, 9), i);
				System.out.print(axisDyadArray);
				System.out.println(axisDyadArray.printArray());
//				List<List<Integer>> chords = axisDyadArray.getAllSumTetraChordsLeft();
//				for (List<Integer> list : chords) {
//					System.out.println(list);
//				}
//				List<List<Integer>> chords2 = axisDyadArray.getAllSumTetraChordsRight();
//				for (List<Integer> list : chords2) {
//					System.out.println(list);
//				}
//				System.out.println(axisDyadArray.containsSumTetraChord(chord));
//			}
		}
	}
	
	private List<Integer> toChord(int...notes ){
		List<Integer> chords = new ArrayList<>();
		for (int i = 0; i < notes.length; i++) {
			chords.add(notes[i]);
		}
		return chords;
	}
	
	@Test
	public void allArraysInRange(){
		List<List<Integer>> chords = new ArrayList<>();
		//ms1
//		chords.add(toChord(11,8,3,7));
//		chords.add(toChord(0,9,4,7));
//		chords.add(toChord(0,9,4,6));
//		chords.add(toChord(11,8,3,6));
//		chords.add(toChord(10,7,2,8));
//		chords.add(toChord(8,4,1,11));
//		chords.add(toChord(8,3,0,5));
		
		//ms 23
//		List<Integer> chordB = new ArrayList<>();
//		chords.add(toChord(1,11,4));
//		chords.add(toChord(6,1,9,4));
//		chords.add(toChord(6,0,9,4));
//		chords.add(toChord(2,11,7,4));
//		chords.add(toChord(1,9,6,4));
//		chords.add(toChord(7,0,9,4));
//		chords.add(toChord(4,11,9,2));
//		chords.add(toChord(4,11,7,2));
//		chords.add(toChord(10,7,2));
//		chords.add(toChord(9,5,2));
//		chords.add(toChord(4,7,2));
//		chords.add(toChord(11,7,8,6));
//		chords.add(toChord(3,1,6));
//		chords.add(toChord(3,11,6));
//		chords.add(toChord(11,6,2));
//		chords.add(toChord(1,9,6));
		
		//ms 26
//		chords.add(toChord(4,1,9,6));
//		chords.add(toChord(3,1,6));
//		chords.add(toChord(10,1,3,6));
//		chords.add(toChord(0,1,3,6));
//		chords.add(toChord(8,3,11,6));
//		chords.add(toChord(7,3,11,6));
//		chords.add(toChord(9,2,11,6));
//		
//		chords.add(toChord(7,11,8,6));
//		chords.add(toChord(3,11,8,6));
//		chords.add(toChord(4,1,9,6));
//		chords.add(toChord(8,1,11,4));
//		chords.add(toChord(4,0,9));
//		
//		chords.add(toChord(2,11,7,4));
//		chords.add(toChord(4,1,9,6));
//		chords.add(toChord(2,11,7,4));
//		chords.add(toChord(4,11,7,4));
//		chords.add(toChord(4,11,7,2));
//		chords.add(toChord(5,10,7,2));
//		chords.add(toChord(8,10,7,2));
//		
//		chords.add(toChord(0,9,5,2));
//		chords.add(toChord(11,7,4,2));
//		chords.add(toChord(6,2));
		
		//ms 34
		chords.add(toChord(3,8,5));
		chords.add(toChord(6,8,5));
		chords.add(toChord(3,7,11));
		chords.add(toChord(3,7,10));
		chords.add(toChord(2,5,9));
		
		chords.add(toChord(1,5,8));
		chords.add(toChord(1,4,9));
		chords.add(toChord(1,4,10));
		chords.add(toChord(1,4,6));
		chords.add(toChord(1,4,9,5));
		
		chords.add(toChord(10,5,4));
		chords.add(toChord(9,5,4,6));
		chords.add(toChord(10,5,4,2));
		
		chords.add(toChord(1,8,10,4));
		
		chords.add(toChord(6,1,5,9));
		
		chords.add(toChord(0,9,2,4,7));
		
		chords.add(toChord(11,3,8,6));
		
		EnumSet<IntervalCycle> set = EnumSet.range(IntervalCycle.P_IC1, IntervalCycle.P_IC7);
		for (IntervalCycle intervalCycle : set) {
			for (IntervalCycle intervalCycle2 : set) {
				for (int i = 0; i < intervalCycle2.getIntervalCycle().length; i++) {
					for (int j = 0; j < intervalCycle.getIntervalCycle().length; j++) {
						AxisDyadArray axisDyadArray = new AxisDyadArray(new CyclicSet(intervalCycle, j), 0,
								new CyclicSet(intervalCycle2, i), 0);
//						System.out.print(axisDyadArray);
						if (axisDyadArray.containsAllAxisDyadChords(chords)) {
							System.out.print(axisDyadArray);
							System.out.print(axisDyadArray.printArray());
							System.out.println();
						}
//						if (axisDyadArray.isCognateSet()) {
//							System.out.print(axisDyadArray);
//							System.out.println();
//						}
//						System.out.println(axisDyadArray.printArray());
					}
//					System.out.println("-----------------------------------------------------------------------");
				}
//				System.out.println("------------------------------------------------------");
			}
		}
	}

	@Test
	public void testGetName() {
        assertEquals("p0p7/p2p9", axisDyadArray.getName());
	}

	@Test
	public void testGetIntervalSystem() {
		assertEquals("7,7", axisDyadArray.getIntervalSystem());
	}

	@Test
	public void testGetSynopticMode() {
		assertEquals(0, axisDyadArray.getSynopticMode());
	}

	@Test
	public void testGetSynopticKey() {
		assertEquals(0, axisDyadArray.getSynopticMode());
	}

	@Test
	public void testIsDifferenceAlignment() {
		assertTrue(axisDyadArray.isDifferenceAlignment());
	}

	@Test
	public void testGetDifferences() {
		assertArrayEquals(new int[]{2, 0, 2}, axisDyadArray.getDifferences());
	}

	@Test
	public void testGetSums() {
		Assertions.assertNull(axisDyadArray.getSums());
	}

	@Test
	public void testGetMode() {
		assertEquals("10,10", axisDyadArray.getMode());
	}

	@Test
	public void testGetKey() {
		assertEquals("2,4", axisDyadArray.getKey());
	}

	@Test
	public void testGetAxisDyadChord() {
		List<Integer> axisDyadChord = axisDyadArray.getAxisDyadChord(3);
		List<Integer> chord = new ArrayList<>();
		chord.add(9);
		chord.add(5);
		chord.add(7);
		chord.add(4);
		chord.add(2);
		assertTrue(axisDyadChord.containsAll(chord));
	}

	@Test
	public void testGetCyclicChord() {
		List<Integer> cyclicChord = axisDyadArray.getCyclicChord(3);
		List<Integer> chord = new ArrayList<>();
		chord.add(9);
		chord.add(7);
		chord.add(4);
		chord.add(2);
		assertTrue(cyclicChord.containsAll(chord));
	}

	@Test
	public void testGetSumTetraChordLeft() {
		List<Integer> sumTetraChord = axisDyadArray.getSumTetraChordLeft(3);
		List<Integer> chord = new ArrayList<>();
		chord.add(9);
		chord.add(7);
		chord.add(5);
		assertTrue(sumTetraChord.containsAll(chord));
	}

	@Test
	public void testGetSumTetraChordRight() {
		List<Integer> sumTetraChord = axisDyadArray.getSumTetraChordRight(3);
		List<Integer> chord = new ArrayList<>();
		chord.add(2);
		chord.add(4);
		chord.add(5);
		assertTrue(sumTetraChord.containsAll(chord));
	}

	@Test
	public void testGetAllAxisDyadChords() {
		List<List<Integer>> axisDyadChords = axisDyadArray.getAllAxisDyadChords();
		assertEquals(12, axisDyadChords.size());
	}

	@Test
	public void testGetAllCyclicChords() {
		List<List<Integer>> cyclicChords = axisDyadArray.getAllCyclicChords();
		assertEquals(12, cyclicChords.size());
	}

	@Test
	public void testGetAllSumTetraChordsLeft() {
		List<List<Integer>> sumTetraChords = axisDyadArray.getAllSumTetraChordsLeft();
		assertEquals(12, sumTetraChords.size());
	}

	@Test
	public void testGetAllSumTetraChordsRight() {
		List<List<Integer>> sumTetraChords = axisDyadArray.getAllSumTetraChordsRight();
		assertEquals(12, sumTetraChords.size());
	}

	@Test
	public void testGetAggregateSum() {
		assertEquals(6, axisDyadArray.getAggregateSum());
	}

	@Test
	public void testGetTonality() {
		assertEquals(2, axisDyadArray.getTonality());
	}

	@Test
	public void testIsCognateSet() {
		assertFalse(axisDyadArray.isCognateSet());
	}

	@Test
	public void testTranspose() {
		axisDyadArray.transpose(1);
		assertEquals("p2p9/p4p11", axisDyadArray.getName());
	}

	@Test
	public void testSemiTranspose() {
		axisDyadArray.semiTranspose(1);
		assertEquals("i3i10/i5i0", axisDyadArray.getName());
	}

	@Test
	public void testInverse() {
		axisDyadArray.inverse(1);
		assertEquals("p2p7/p0p5", axisDyadArray.getName());
	}

	@Test
	public void testContainsAxisDyadChord() {
		List<Integer> chord = new ArrayList<>();
		chord.add(2);
		chord.add(4);
		chord.add(7);
		chord.add(5);
//		assertFalse(axisDyadArray.containsAxisDyadChord(chord));
		chord.add(9);
		assertTrue(axisDyadArray.containsAxisDyadChord(chord));
	}
	
	@Test
	public void testContainsAllAxisDyadChord() {
		ArrayList<List<Integer>> chords = new ArrayList<>();
		List<Integer> chord = new ArrayList<>();
		chord.add(9);
		chord.add(3);
		chord.add(4);
		chord.add(11);
		chord.add(6);
		chords.add(chord);
		
		chord = new ArrayList<>();
		chord.add(2);
		chord.add(4);
		chord.add(7);
		chord.add(5);
		chord.add(9);
		chords.add(chord);
		assertTrue(axisDyadArray.containsAllAxisDyadChords(chords));
	}

	@Test
	public void testContainsSumTetraChord() {
		List<Integer> chord = new ArrayList<>();
		chord.add(2);
		chord.add(4);
		assertFalse(axisDyadArray.containsSumTetraChord(chord));
		chord.add(10);
		assertTrue(axisDyadArray.containsSumTetraChord(chord));
	}

	@Test
	public void testContainsCyclicChord() {
		List<Integer> chord = new ArrayList<>();
		chord.add(2);
		chord.add(4);
		chord.add(7);
		assertFalse(axisDyadArray.containsCyclicChord(chord));
		chord.add(9);
		assertTrue(axisDyadArray.containsCyclicChord(chord));
	}

}
