package cp.composition.timesignature;

import javax.annotation.PostConstruct;

public abstract class TimeConfig {

	protected double minimumRhythmFilterLevel = 3.0; //levels pitch, crest/keel, ...

	protected int minimumLength;

	protected int[] distance;

	protected int offset;

	protected int measureDuration;

	@PostConstruct
	public void init() {
	}

	public int getOffset() {
		return offset;
	}
	
	public int[] getDistance() {
		return distance;
	}
	
	public double getMinimumRhythmFilterLevel() {
		return minimumRhythmFilterLevel;
	}

	public int getMinimumLength() {
		return minimumLength;
	}

	public int getMeasureDuration(){
		return measureDuration;
	}
}
