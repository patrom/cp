package cp.objective.meter;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

import org.apache.commons.lang.ArrayUtils;

import cp.model.note.Note;


public class InnerMetricWeightFunctions {
	
	private static final int BEAT_FACTOR = 2;//1/8 = 2 - 1/16 = 4
	private static final double POWER = 2.0;
	private static final int MINIMUM_SIZE = 3; //size of local meter 
	
	public static int[] convertToDynamics(double[] vector){
		int[] dynamics = new int[vector.length];
		for (int i = 0; i < vector.length; i++) {
			dynamics[i] = (int)Math.ceil(vector[i]/10);
		}
		return dynamics;
	}
	
	public static double[] normalize(double[] vector, double maxValue, int length){
		double[] normalizedVector = new double[length];
		int j = 0 ;
		for (int i = 0; i < vector.length; i++) {
			if (vector[i] != 0.0) {
				normalizedVector[j] =  Math.round((vector[i] / maxValue) * 100);
				j++;
			} 
		}
		return normalizedVector;
	}
	
	public static double[] normalize(Collection<Double> values){
		double maxValue = Collections.max(values);
		int l = values.size();
		double[] normalizedVector = new double[l];
		int i = 0;
		for (Double value : values) {
			normalizedVector[i] =  Math.round((value / maxValue) * 100);
			i++;
		}
		return normalizedVector;
	}
	
	public static double getMaxValue(double[] numbers){  
	    double maxValue = numbers[0];  
	    for(int i=1;i<numbers.length;i++){  
	        if(numbers[i] > maxValue){  
	            maxValue = numbers[i];  
	        }  
	    }  
	    return maxValue;  
	}  
	
	public static List<List<Integer>> getLocalMeters(Integer[] onSet){
		List<Integer> onSetList = Arrays.asList(onSet);
		List<List<Integer>> localMeters = new ArrayList<List<Integer>>();
//		int[] distanceArr = {1,2,3,4,5,6,7,8,9,10};// pulse = 0.5 of 0.25
		int[] distanceArr = {2,3,4,5,6,8,9,10,12,14,15,16,18,20,21,22,24,26,27,28,30,32};//atomic beat = 12
//		int[] distanceArr = {2,4,8,10,12,14,15,16,18,20,21,22,24,26,27,28,30,32};//atomic beat = 12
		for (int j = 0; j < distanceArr.length - 1; j++) {
			for (int start = 0; start < onSet.length; start++) {
				int i = onSet[start] + distanceArr[j];
				if (!onSetList.contains(i)) {
					continue;
				}else{
					List<Integer> sublist = new ArrayList<Integer>();
					sublist.add(onSet[start]);
					while (onSetList.contains(i)) {
						sublist.add(i);
						i = i + distanceArr[j];
					}
					if (sublist.size() >= MINIMUM_SIZE) {
						if (localMeters.isEmpty()) {//first time
							localMeters.add(sublist);
						} else {
							boolean isSubSet = false;
							for (List<Integer> localMeter : localMeters) {
								if (localMeter.containsAll(sublist)) {
									isSubSet = true;
								}
							}
							if (!isSubSet) {
								localMeters.add(sublist);
							}	
						}	
					}
				}
			}
//			start = 0;
		}
		return localMeters;
	}
	
	public static Map<Integer, Double> getInnerMetricWeight(List<List<Integer>> localMeters, Integer[] onSet){
		Map<Integer, Double> map = new TreeMap<Integer, Double>();
		for (int i = 0; i < onSet.length; i++) {
			for (List<Integer> localMeter : localMeters) {
				if (!localMeter.contains(onSet[i])) {
					continue;
				} else {
					Integer key = onSet[i];
					double value = Math.pow(localMeter.size()-1, POWER);
					if (map.containsKey(key)) {
						double oldValue = map.get(key);
						map.put(key, value + oldValue);
					} else {
						map.put(key, value);
					}
				}
			}
		}
		return map;
	}
	
	private static Map<Integer, Double> normalizeMap(Map<Integer, Double> map){
		double maxValue = Collections.max(map.values());
		Set<Integer> keys = map.keySet();
		for (Integer key : keys) {
			double value = map.get(key)/ maxValue;
			map.put(key, value);
		}
		return map;
	}
	
	public static InnerMetricWeight getInnerMetricWeight(int[] rhythmPattern, double pulse){
		InnerMetricWeight innerMetricWeight = new InnerMetricWeight();
		Map<Integer, Double> map = getNormalizedInnerMetricWeight(rhythmPattern, pulse);
		innerMetricWeight.setInnerMetricWeightMap(map);
		return innerMetricWeight;
	}
	
	public static InnerMetricWeight getInnerMetricWeight(List<Note> notes, double pulse){
		InnerMetricWeight innerMetricWeight = new InnerMetricWeight();
		Map<Integer, Double> map = getNormalizedInnerMetricWeight(notes, pulse);
		innerMetricWeight.setInnerMetricWeightMap(map);
		return innerMetricWeight;
	}
	
	public static Map<Integer, Double> getNormalizedInnerMetricWeight(int[] rhythmPattern, double pulse){
		Integer[] onSet = extractOnset(rhythmPattern, pulse);
		List<List<Integer>> localMeters = getLocalMeters(onSet);
		Map<Integer, Double> map = getInnerMetricWeight(localMeters, onSet);
		if (!map.isEmpty()) {
			return normalizeMap(map);
		} else {
			return Collections.emptyMap();
		}
	}
	
	public static Integer[] extractOnset(int[] rhythmPattern, double pulse){
		List<Integer> temp = new ArrayList<>();
		for (int i = 0; i < rhythmPattern.length; i++) {
			double onSet = rhythmPattern[i] / pulse;
			if(onSet % 1 == 0){
				temp.add((int) onSet);
			}
		}
		Integer[] arr = new Integer[temp.size()];
		return temp.toArray(arr);
	}
	
	protected static Integer[] extractOnsetNotes(List<Note> notes, double pulse) {
		List<Integer> temp = new ArrayList<>();
		for (int i = 0; i < notes.size(); i++) {
			double onSet = notes.get(i).getPosition() / pulse;
			if(onSet % 1 == 0){
				temp.add((int) onSet);
			}
		}
		Integer[] arr = new Integer[temp.size()];
		return temp.toArray(arr);
	}
	
	public static double[] createCorrelationVector(Map<Integer, Double> map, double length){
		double[] innerMetricWeightVector = new double[(int) (length * BEAT_FACTOR)];//1/8 = 2 - 1/16 = 4
		Set<Integer> keys = map.keySet();
		for (Integer key : keys) {
			innerMetricWeightVector[key] = map.get(key);
		}
		return innerMetricWeightVector;
	}
	
	public static double getLength(double[] rhythmPattern){
		double total = 0;
		for (double d : rhythmPattern) {
			total = total + d;
		}
		return total;
	}

	public static Map<Integer, Double> getNormalizedInnerMetricWeight(List<Note> notes, double pulse) {
		Integer[] onSet = extractOnsetNotes(notes, pulse);
		List<List<Integer>> localMeters = getLocalMeters(onSet);
		Map<Integer, Double> map = getInnerMetricWeight(localMeters, onSet);
		if (!map.isEmpty()) {
			return normalizeMap(map);
		} else {
			return Collections.emptyMap();
		}
	}

	private static Integer[] extendArray(Integer[] rhythmArray) {
		int length = rhythmArray.length * 3;
		Integer[] rArray = new Integer[length];
		for (int i = 0; i < length; i++) {
			rArray[i] = rhythmArray[i % rhythmArray.length];
		}
		return rArray;
	}

	
	public static Map<Integer, Double> getNormalizedInnerMetricWeight(Integer[] onSet) {
		List<List<Integer>> localMeters = getLocalMeters(onSet);
		Map<Integer, Double> map = getInnerMetricWeight(localMeters, onSet);
		if (!map.isEmpty()) {
			return normalizeNavigableMap(map);
		} else {
			return map;
		}
	}
	
	private static Map<Integer, Double> normalizeNavigableMap(Map<Integer, Double> map){
		double maxValue = Collections.max(map.values());
		Set<Integer> keys = map.keySet();
		for (Integer key : keys) {
			double value = map.get(key);
			double normalizedValue = value / maxValue;
			map.put(key, normalizedValue);
		}
		return map;
	}
	
}
