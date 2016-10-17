package cp.composition.beat;

import cp.combination.RhythmCombination;

import java.util.List;

public class BeatGroupThree extends BeatGroup {
	

	public BeatGroupThree(int length, List<RhythmCombination> rhythmCombinations) {
		super(length, rhythmCombinations);
	}

	@Override
	public BeatGroup clone(int length) {
		return new BeatGroupThree(length, rhythmCombinations);
	}

	@Override
	public int getType() {
		return 3;
	}

}
