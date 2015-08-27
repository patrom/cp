package cp.objective.meter;

import java.util.Map;

public class InnerMetricWeight {

	private Map<Integer, Double> innerMetricWeightMap;

	public Map<Integer, Double> getInnerMetricWeightMap() {
		return innerMetricWeightMap;
	}

	public void setInnerMetricWeightMap(
			Map<Integer, Double> innerMetricWeightMap) {
		this.innerMetricWeightMap = innerMetricWeightMap;
	}

	public double getInnerMetricWeightAverage() {
		return innerMetricWeightMap.values().stream().mapToDouble(Double::doubleValue).average().orElse(0.0);
	}

}
