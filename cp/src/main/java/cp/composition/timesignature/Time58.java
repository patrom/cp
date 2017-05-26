package cp.composition.timesignature;

import cp.combination.RhythmCombination;
import cp.composition.beat.BeatGroup;
import cp.model.rhythm.DurationConstants;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Component(value="time58")
//@ConditionalOnProperty(name = "composition.timesignature", havingValue = "5/8")
public class Time58 extends TimeConfig{

	@Override
	public boolean randomBeatGroup() {
		return false;
	}

	@Override
	public Map<Integer, List<RhythmCombination>> getAllRhythmCombinations() {
		return defaultUnEvenCombinations;
	}
	
	@Override
	public void init() {
		super.init();
        for (Integer noteSize : defaultEvenCombinations.keySet()) {
            BeatGroup defaultGroup6 = beatGroupFactory.getBeatGroupEven(DurationConstants.EIGHT, noteSize);
            allBeatgroups.add(defaultGroup6);
        }
        for (Integer noteSize : defaultUnEvenCombinations.keySet()) {
            BeatGroup defaultGroup6 = beatGroupFactory.getBeatGroupUneven(DurationConstants.EIGHT, noteSize);
            allBeatgroups.add(defaultGroup6);
        }
		minimumLength = DurationConstants.EIGHT;
		distance = new int[]{2,5,7,10,12,15,17,20};
		offset = 5 * DurationConstants.EIGHT;
	}

	@Override
	public boolean randomCombination() {
		return true;
	}

    @Override
    public List<RhythmCombination> getFixedBeatGroup() {
        ArrayList<RhythmCombination> combinations = new ArrayList<>();
        combinations.addAll(fixedEven);
        combinations.addAll(fixedUneven);
        return combinations;
    }

    @Override
    public List<RhythmCombination> getHomophonicBeatGroup() {
        ArrayList<RhythmCombination> combinations = new ArrayList<>();
        combinations.addAll(homophonicEven);
        combinations.addAll(homophonicUneven);
        return combinations;
    }

    @Override
    public BeatGroup getBeatGroup(int index) {
        int size = allBeatgroups.size();
        return allBeatgroups.get(index % size);
    }
	
}


