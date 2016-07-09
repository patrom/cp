package cp.composition.timesignature;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;

import cp.composition.beat.BeatGroup;
import cp.composition.beat.BeatGroupFactory;

public abstract class TimeConfig {

	protected List<BeatGroup> beats = new ArrayList<>();
	protected List<BeatGroup> beatsDoubleLength = new ArrayList<>();
	protected List<BeatGroup> beatsAll = new ArrayList<>();
	
	protected int minimumRhythmFilterLevel = 12; //levels pitch, crest/keel, ...
	
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
	
	public int getMinimumRhythmFilterLevel() {
		return minimumRhythmFilterLevel;
	}
	
	public abstract List<BeatGroup> getFixedBeatGroup();
	
}
