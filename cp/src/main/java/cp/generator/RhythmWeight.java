package cp.generator;

import java.util.Map;
import java.util.TreeMap;

public class RhythmWeight {

	public static Map<Integer, Double> generateRhythmWeight(int bars, double[] barWeights){
		Map<Integer, Double> rhythmWeightValues = new TreeMap<>();
		int factor = getFactor(barWeights.length);
		int length = bars * barWeights.length;
		for (int i = 0; i < length ; i++) {
			rhythmWeightValues.put(i * factor, barWeights[i % barWeights.length]);
		}
		return rhythmWeightValues;
	}

	private static int getFactor(int length) {
		switch (length) {
		case 3:
			return 12;
		case 4:
			return 12;
		case 6:
			return 6;
		case 8:
			return 6;
		case 12:
			return 3;
		case 16:
			return 3;
		default:
			throw new IllegalStateException("Add factor for rhythm weight");
		}
	}
	
	public static void main(String[] args) {
		double[] barWeights = {1.0, 0.5, 0.75, 0.5};
		Map<Integer, Double> map = generateRhythmWeight(5, barWeights);
		System.out.println(map);
	}
}
