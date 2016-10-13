package cp.util;

import java.util.List;
import java.util.Random;
import java.util.stream.IntStream;

public class RandomUtil {
	
	private static final Random random = new Random();
	
	public static <T> T getRandomFromList(List<T> list) {
		return list.get(randomInt(0, list.size()));
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
	
	public static IntStream range(int size){
		int start = randomInt(0, size);
		int end = randomInt(start + 1, size + 1);
		return IntStream.range(start, end);
	}
	
	public static boolean toggleSelection(){
		return random.nextBoolean();
	}
	
	public static int randomAscendingOrDescending(){
		if (random.nextBoolean()) {
			return 1;
		} else {
			return -1;
		}
	}
	
	public static void main(String[] args) {
		for (int i = 0; i < 10; i++) {
			System.out.println(randomAscendingOrDescending());
		}
	}
	
}
