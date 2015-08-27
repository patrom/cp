package cp.generator;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class TonalChords {

	public static List<int[]> getTriads(int key){	
		List<int[]> chords = new ArrayList<>();
		chords.add(new int[]{0, 4 ,7});
		chords.add(new int[]{2, 5 ,9});
		chords.add(new int[]{4, 7 ,11});
		chords.add(new int[]{5, 9 ,0});
		chords.add(new int[]{7, 11 ,2});
		chords.add(new int[]{9, 0 ,4});
		chords.add(new int[]{11, 2 ,5});
		transposeChords(key, chords);
		return chords;
	}

	private static void transposeChords(int key, List<int[]> chords) {
		for (int[] chord : chords) {
			for (int i = 0; i < chord.length; i++) {
				chord[i] = (chord[i] + key) % 12;
			}
		}
	}
	
	public static List<int[]> getSeventhChords(int key){
		List<int[]> chords = new ArrayList<>();
		chords.add(new int[]{0, 4 ,7, 11});
		chords.add(new int[]{2, 5 ,9, 0});
		chords.add(new int[]{4, 7 ,11, 2});
		chords.add(new int[]{5, 9 ,0, 4});
		chords.add(new int[]{7, 11 ,2, 5});
		chords.add(new int[]{9, 0 ,4, 7});
		chords.add(new int[]{11, 2 ,5 ,9});
		transposeChords(key, chords);
		return chords;
	}
	
	public static List<int[]> getTriadsAndSeventhChords(int key){
		List<int[]> chords = new ArrayList<>();
		chords.addAll(getTriads(key));
		chords.addAll(getSeventhChords(key));
		return chords;
	}
	
	public static void main(String[] args) {
		List<int[]> chords = TonalChords.getTriadsAndSeventhChords(7);
		for (int[] is : chords) {
			System.out.println(Arrays.toString(is));
		}
	}
	
	public static List<int[]> getSecondaryDominantsChords(int key){
		List<int[]> chords = new ArrayList<>();
		chords.add(new int[]{0, 4 ,7, 9});//I7
		chords.add(new int[]{2, 6 ,9, 0});//II7
		chords.add(new int[]{4, 8 ,11, 2});//III7
		chords.add(new int[]{9, 1 ,4, 7});//VI7
		chords.add(new int[]{11, 3 ,6 ,9});//VII7
		transposeChords(key, chords);
		return chords;
	}
	
	public static List<int[]> getTriadsAndSeventhAndSecDominantChords(int key){
		List<int[]> chords = new ArrayList<>();
		chords.addAll(getTriads(key));
		chords.addAll(getSeventhChords(key));
		chords.addAll(getSecondaryDominantsChords(key));
		Collections.shuffle(chords);
		return chords;
	}
}
