package cp.composition.timesignature;

import cp.combination.RhythmCombination;
import cp.composition.beat.BeatGroup;
import cp.composition.beat.BeatGroupFactory;
import org.springframework.beans.factory.annotation.Autowired;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public abstract class TimeConfig {

	@Resource(name = "defaultUnevenCombinations")
	protected Map<Integer, List<RhythmCombination>> defaultUnEvenCombinations;

	@Resource(name = "defaultEvenCombinations")
	protected Map<Integer, List<RhythmCombination>> defaultEvenCombinations;

	@Resource(name = "homophonicEven")
	protected List<RhythmCombination> homophonicEven;

	@Resource(name = "homophonicUneven")
	protected List<RhythmCombination> homophonicUneven;

	@Resource(name = "fixedEven")
	protected List<RhythmCombination> fixedEven;

	@Resource(name = "fixedUneven")
	protected List<RhythmCombination> fixedUneven;
	
	protected double minimumRhythmFilterLevel = 3.0; //levels pitch, crest/keel, ...
	protected int minimumLength;

	protected int[] distance;

	protected int offset;

	protected List<BeatGroup> beatGroups2 = new ArrayList<>();
	protected List<BeatGroup> beatGroups3 = new ArrayList<>();

	@Autowired
	protected BeatGroupFactory beatGroupFactory;
	
	public abstract boolean randomBeatGroup();//composite time signatures
	
	public abstract boolean randomCombination();//fixed rhythm patterns
	
	@PostConstruct
	public void init() {
	}

	public abstract Map<Integer, List<RhythmCombination>>  getAllRhythmCombinations();

	public int getOffset() {
		return offset;
	}
	
	public int[] getDistance() {
		return distance;
	}
	
	public double getMinimumRhythmFilterLevel() {
		return minimumRhythmFilterLevel;
	}

	public abstract List<RhythmCombination>getFixedBeatGroup();
	
	public abstract List<RhythmCombination> getHomophonicBeatGroup();

	public int getMinimumLength() {
		return minimumLength;
	}

	public abstract BeatGroup getBeatGroup(int index);
}
