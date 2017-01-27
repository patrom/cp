package cp.util;

import java.math.BigInteger;
import java.util.List;
import java.util.Random;

import static java.lang.System.arraycopy;

public class Util {

	private static final int mod = 12;

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
		return direction == 1;
	}
	
	public static int getSteps(int scaleDistance){
		switch (scaleDistance) {
		case 0:
			return 0;
		case 1:
		case 2:
			return 6;
		case 3:
		case 4:
			return 5;
		case 5:
		case 6:
			return 4;
		case 7:
			return 3;
		case 8:
		case 9:
			return 2;
		case 10:
		case 11:
			return 1;
		case -1:
		case -2:
			return -6;
		case -3:
		case -4:
			return -5;
		case -5:
		case -6:
			return -4;
		case -7:
			return -3;
		case -8:
		case -9:
			return -2;
		case -10:
		case -11:
			return -1;
		default:
			break;
		}
		throw new IllegalArgumentException("No steps for scale distance: " + scaleDistance);
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

	public static int convertToKeyOfC(int pitchClass, int key) {
		return (12 + pitchClass - key) % 12;
	}
}
