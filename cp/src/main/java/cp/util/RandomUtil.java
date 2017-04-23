package cp.util;

import cp.model.note.Note;
import cp.model.rhythm.DurationConstants;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;
import java.util.stream.IntStream;

import static cp.model.note.NoteBuilder.note;

public class RandomUtil {
	
	private static final Random random = new Random();
	
	public static <T> T getRandomFromList(List<T> list) {
		return list.get(randomInt(0, list.size()));
	}

	public static <T> List<T> getRandomListFromList(List<T> list) {
		int from = getRandomNumberInRange(0, list.size() - 1);
		int to = getRandomNumberInRange(1, list.size());
		if (from < to){
			return list.subList(from, to);
		}
		return Collections.emptyList();
	}
	
	public static <T> int getRandomIndex(List<T> list) {
		return randomInt(0, list.size());
	}
	
	public static int getRandomFromIntArray(int[] array){
		return array[random(array.length)];
	}
	
	public static <T> T getRandomFromArray(T[] array){
		return array[random(array.length)];
	}
	
	public static double[] getRandomFromDoubleArray(double[][] array){
		return array[random(array.length)];
	}
	
	public static int randomInt(int origin, int boundExclusive){
		return random.ints(origin, boundExclusive).findFirst().getAsInt();
	}
	
	public static int random(int size){
		return random.nextInt(size);
	}

	public static int getRandomNumberInRange(int minInclusive, int maxInclusive) {
		if (minInclusive >= maxInclusive) {
			throw new IllegalArgumentException("max must be greater than min");
		}
		return random.nextInt((maxInclusive - minInclusive) + 1) + minInclusive;
	}
	
	public static IntStream range(int size){
		int start = randomInt(0, size);
		int end = randomInt(start + 1, size + 1);
		return IntStream.range(start, end);
	}

	public static boolean toggleSelection(){
		return random.nextBoolean();
	}

	public static int toggleDirection(){
		if (random.nextBoolean()) {
			return 1;
		} else {
			return -1;
		}
	}
	
	public static int randomAscendingOrDescending(){
		if (random.nextBoolean()) {
			return 1;
		} else {
			return -1;
		}
	}
	
	public static void main(String[] args) {
		List<Note> notes = new ArrayList<>();
		notes.add(note().pitch(60).pc(0).pos(0).len(DurationConstants.EIGHT).build());
		notes.add(note().pitch(62).pc(2).pos(6).len(DurationConstants.EIGHT).build());
		notes.add(note().pitch(64).pc(4).pos(DurationConstants.QUARTER).len(DurationConstants.EIGHT).build());
		notes.add(note().pitch(65).pc(5).pos(DurationConstants.THREE_EIGHTS).len(DurationConstants.EIGHT).build());
		List<Note> sublist = getRandomListFromList(notes);
		sublist.forEach(n -> System.out.println(n));
		sublist.forEach(n -> n.setPitch(n.getPitch() + 10));
        sublist.forEach(n -> System.out.println(n));
		notes.forEach(n -> System.out.println( "test" + n));
	}
	
}
