package cp.composition.timesignature;

import cp.composition.beat.BeatGroup;
import cp.composition.beat.BeatGroupFactory;
import org.springframework.beans.factory.annotation.Autowired;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;

public abstract class TimeConfig {

	protected final List<BeatGroup> beats = new ArrayList<>();
	protected final List<BeatGroup> beatsDoubleLength = new ArrayList<>();
	protected final List<BeatGroup> beatsAll = new ArrayList<>();
	
	protected double minimumRhythmFilterLevel = 3.0; //levels pitch, crest/keel, ...
	protected int minimumLength;

	protected int[] distance;

	protected int offset;
	
	@Autowired
	protected BeatGroupFactory beatGroupFactory;
	
	public abstract boolean randomBeatGroup();//composite time signatures
	
	public abstract boolean randomCombination();//fixed rhythm patterns
	
	@PostConstruct
	public void init() {
	}
	
	public List<BeatGroup> getAllBeats() {
		return beatsAll;
	}
	
	public List<BeatGroup> getBeats() {
		return beats;
	}
	
	public List<BeatGroup> getBeatsDoubleLength() {
		return beatsDoubleLength;
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

	public abstract List<BeatGroup> getFixedBeatGroup();
	
	public abstract List<BeatGroup> getHomophonicBeatGroup();

	public int getMinimumLength() {
		return minimumLength;
	}
}
