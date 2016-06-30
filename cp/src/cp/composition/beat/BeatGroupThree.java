package cp.composition.beat;

import java.util.List;

import cp.combination.RhythmCombination;

public class BeatGroupThree extends BeatGroup {
	

	public BeatGroupThree(int length, List<RhythmCombination> rhythmCombinations) {
		super(length, rhythmCombinations);
	}

	@Override
	public int getType() {
		return 3;
	}

}
