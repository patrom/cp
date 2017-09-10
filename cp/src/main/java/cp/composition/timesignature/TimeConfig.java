package cp.composition.timesignature;

import cp.composition.beat.BeatGroup;
import cp.composition.beat.BeatGroupFactory;
import cp.util.RandomUtil;
import org.springframework.beans.factory.annotation.Autowired;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;

public abstract class TimeConfig {
	
	protected double minimumRhythmFilterLevel = 3.0; //levels pitch, crest/keel, ...
	protected int minimumLength;

	protected int[] distance;

	protected int offset;

	protected int measureDuration;

	protected List<BeatGroup> allBeatgroups = new ArrayList<>();

	@Autowired
	protected BeatGroupFactory beatGroupFactory;

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

	public BeatGroup getRandomBeatgroup(){
		if(allBeatgroups.size() > 1){
			return RandomUtil.getRandomFromList(allBeatgroups);
		}
		return allBeatgroups.get(0);
	}

	public List<BeatGroup> getBeatGroups() {
		return allBeatgroups;
	}

	public int getMeasureDuration(){
		return measureDuration;
	}
}
