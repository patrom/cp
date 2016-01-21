package cp.util;

import static java.lang.System.arraycopy;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Util {

	private static int mod = 12;

	public static int[] rotateArray(int[] array, int index) {
		int[] result = new int[array.length];
		arraycopy(array, index, result, 0, array.length - index);
		arraycopy(array, 0, result, array.length - index, index);
		return result;
	}

	public static BigInteger lcm(BigInteger a, BigInteger b) {
		return a.multiply(b.divide(a.gcd(b)));
	}

	public static int intervalClass(int c) {
		int ic = posMod(c);
		if (ic > mod / 2) {
			ic = mod - ic;
		}
		return ic;
	}

	private static int posMod(int c) {
		return ((c % mod) + mod) % mod;
	}

	public static <T> T selectFromListProbability(List<T> selections,
			int[] profile) {
		if (selections.size() != profile.length) {
			throw new IllegalArgumentException(
					"selections and profile have different size");
		}
		int[] weightSum = new int[profile.length];
		int i, k;
		Random Rnd = new Random();
		weightSum[0] = profile[0];
		for (i = 1; i < profile.length; i++) {
			weightSum[i] = weightSum[i - 1] + profile[i];
		}
		k = Rnd.nextInt(weightSum[profile.length - 1]);
		for (i = 0; k > weightSum[i]; i++)
			;
		return selections.get(i);
	}
	
	public static int calculateInterval(int direction, int difference){
		if(isAscending(direction) && difference < 0){
			return difference + 12;
		}
		if(!isAscending(direction) && difference > 0){
			return difference - 12;
		}
		return difference;
	}
	
	private static boolean isAscending(int direction) {
		if (direction == 1) {
			return true;
		} else {
			return false;
		}
	}

	public static void main(String[] args) {
//		List<Integer> list = new ArrayList<Integer>();
//		list.add(1);
//		list.add(2);
//		list.add(3);
//		for (int i = 0; i < 10; i++) {
//			System.out.println(selectFromListProbability(list, new int[] { 10,
//					10, 10 }));
//		}
	}
}
