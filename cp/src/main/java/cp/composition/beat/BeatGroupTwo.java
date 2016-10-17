package cp.composition.beat;

import cp.combination.RhythmCombination;

import java.util.List;

public class BeatGroupTwo extends BeatGroup {

	public BeatGroupTwo(int length, List<RhythmCombination> rhythmCombinations) {
		super(length, rhythmCombinations);
	}

	@Override
	public BeatGroup clone(int length) {
		return new BeatGroupTwo(length, rhythmCombinations);
	}

	@Override
	public int getType() {
		return 2;
	}
}
