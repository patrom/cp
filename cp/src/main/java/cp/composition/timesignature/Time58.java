package cp.composition.timesignature;

import cp.combination.RhythmCombination;
import cp.composition.beat.BeatGroup;
import cp.model.rhythm.DurationConstants;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component(value="time58")
//@ConditionalOnProperty(name = "composition.timesignature", havingValue = "5/8")
public class Time58 extends TimeConfig{

	@Override
	public boolean randomBeatGroup() {
		return false;
	}

	@Override
	public List<RhythmCombination> getAllBeats() {
        ArrayList<RhythmCombination> combinations = new ArrayList<>();
        combinations.addAll(defaultEvenCombinations);
        combinations.addAll(defaultUnEvenCombinations);
		return combinations;
	}
	
	@Override
	public void init() {
		super.init();
		BeatGroup defaultGroupUneven = beatGroupFactory.getBeatGroupUneven(DurationConstants.EIGHT);
		BeatGroup defaultGroupEven = beatGroupFactory.getBeatGroupEven(DurationConstants.EIGHT);
		beatGroups.add(defaultGroupEven); // 2 + 3
		beatGroups.add(defaultGroupUneven);
//		beatsDoubleLength.add(defaultGroupEven);
//		beatsDoubleLength.add(defaultGroupUneven);
//		beatsAll.add(defaultGroupEven);
//		beatsAll.add(defaultGroupUneven);
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
        int size = beatGroups.size();
        return beatGroups.get(index % size);
    }
	
}


