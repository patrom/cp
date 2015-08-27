package cp.variation.pattern;

import java.util.List;

public class VariationPattern {

	protected List<Integer> noteLengths;
	protected List<Integer> secondNoteLengths;
	protected double[][] patterns;
	
	public List<Integer> getNoteLengths() {
		return noteLengths;
	}
	public void setNoteLengths(List<Integer> noteLengths) {
		this.noteLengths = noteLengths;
	}
	public double[][] getPatterns() {
		return patterns;
	}
	public void setPatterns(double[][] patterns) {
		this.patterns = patterns;
	}
	public List<Integer> getSecondNoteLengths() {
		return secondNoteLengths;
	}
	public void setSecondNoteLengths(List<Integer> secondNoteLengths) {
		this.secondNoteLengths = secondNoteLengths;
	}
	
}
