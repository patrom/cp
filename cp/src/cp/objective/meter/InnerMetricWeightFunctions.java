package cp.objective.meter;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import cp.generator.MusicProperties;
import cp.model.note.Note;

@Component
public class InnerMetricWeightFunctions {
	
	private final int BEAT_FACTOR = 2;//1/8 = 2 - 1/16 = 4
	private final double POWER = 2.0;
	private final int MINIMUM_SIZE = 3; //size of local meter 
	
	@Autowired
	private MusicProperties musicProperties;
	
	public int[] convertToDynamics(double[] vector){
		int[] dynamics = new int[vector.length];
		for (int i = 0; i < vector.length; i++) {
			dynamics[i] = (int)Math.ceil(vector[i]/10);
		}
		return dynamics;
	}
	
	public double[] normalize(double[] vector, double maxValue, int length){
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
	
	public double[] normalize(Collection<Double> values){
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
	
	public double getMaxValue(double[] numbers){  
	    double maxValue = numbers[0];  
	    for(int i=1;i<numbers.length;i++){  
	        if(numbers[i] > maxValue){  
	            maxValue = numbers[i];  
	        }  
	    }  
	    return maxValue;  
	}  
	
	public List<List<Integer>> getLocalMeters(Integer[] onSet, int[] distance){
		List<Integer> onSetList = Arrays.asList(onSet);
		List<List<Integer>> localMeters = new ArrayList<List<Integer>>();
		for (int j = 0; j < distance.length - 1; j++) {
			for (int start = 0; start < onSet.length; start++) {
				int i = onSet[start] + distance[j];
				if (!onSetList.contains(i)) {
					continue;
				}else{
					List<Integer> sublist = new ArrayList<Integer>();
					sublist.add(onSet[start]);
					while (onSetList.contains(i)) {
						sublist.add(i);
						i = i + distance[j];
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
	
	public Map<Integer, Double> getInnerMetricWeight(List<List<Integer>> localMeters, Integer[] onSet){
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
	
	private Map<Integer, Double> normalizeMap(Map<Integer, Double> map){
		double maxValue = Collections.max(map.values());
		Set<Integer> keys = map.keySet();
		for (Integer key : keys) {
			double value = map.get(key)/ maxValue;
			map.put(key, value);
		}
		return map;
	}
	
	public InnerMetricWeight getInnerMetricWeight(int[] rhythmPattern, double pulse, int[] distance){
		InnerMetricWeight innerMetricWeight = new InnerMetricWeight();
		Map<Integer, Double> map = getNormalizedInnerMetricWeight(rhythmPattern, pulse, distance);
		innerMetricWeight.setInnerMetricWeightMap(map);
		return innerMetricWeight;
	}
	
	public InnerMetricWeight getInnerMetricWeight(List<Note> notes, double pulse, int[] distance){
		InnerMetricWeight innerMetricWeight = new InnerMetricWeight();
		Map<Integer, Double> map = getNormalizedInnerMetricWeight(notes, pulse, distance);
		innerMetricWeight.setInnerMetricWeightMap(map);
		return innerMetricWeight;
	}
	
	public Map<Integer, Double> getNormalizedInnerMetricWeight(int[] rhythmPattern, double pulse, int[] distance){
		Integer[] onSet = extractOnset(rhythmPattern, pulse);
		List<List<Integer>> localMeters = getLocalMeters(onSet, distance);
		Map<Integer, Double> map = getInnerMetricWeight(localMeters, onSet);
		if (!map.isEmpty()) {
			return normalizeMap(map);
		} else {
			return Collections.emptyMap();
		}
	}
	
	public Integer[] extractOnset(int[] rhythmPattern, double pulse){
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
	
	protected Integer[] extractOnsetNotes(List<Note> notes, double pulse) {
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
	
	public double[] createCorrelationVector(Map<Integer, Double> map, double length){
		double[] innerMetricWeightVector = new double[(int) (length * BEAT_FACTOR)];//1/8 = 2 - 1/16 = 4
		Set<Integer> keys = map.keySet();
		for (Integer key : keys) {
			innerMetricWeightVector[key] = map.get(key);
		}
		return innerMetricWeightVector;
	}
	
	public double getLength(double[] rhythmPattern){
		double total = 0;
		for (double d : rhythmPattern) {
			total = total + d;
		}
		return total;
	}

	public Map<Integer, Double> getNormalizedInnerMetricWeight(List<Note> notes, double pulse, int[] distance) {
		Integer[] onSet = extractOnsetNotes(notes, pulse);
		List<List<Integer>> localMeters = getLocalMeters(onSet, distance);
		Map<Integer, Double> map = getInnerMetricWeight(localMeters, onSet);
		if (!map.isEmpty()) {
			return normalizeMap(map);
		} else {
			return Collections.emptyMap();
		}
	}

	private Integer[] extendArray(Integer[] rhythmArray) {
		int length = rhythmArray.length * 3;
		Integer[] rArray = new Integer[length];
		for (int i = 0; i < length; i++) {
			rArray[i] = rhythmArray[i % rhythmArray.length];
		}
		return rArray;
	}

	
	public Map<Integer, Double> getNormalizedInnerMetricWeight(Integer[] onSet, int[] distance) {
		List<List<Integer>> localMeters = getLocalMeters(onSet, distance);
		Map<Integer, Double> map = getInnerMetricWeight(localMeters, onSet);
		if (!map.isEmpty()) {
			return normalizeNavigableMap(map);
		} else {
			return map;
		}
	}
	
	private Map<Integer, Double> normalizeNavigableMap(Map<Integer, Double> map){
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
