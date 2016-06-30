package cp.composition.beat;

import java.util.List;

import cp.combination.RhythmCombination;

public class BeatGroupTwo extends BeatGroup {

	public BeatGroupTwo(int length, List<RhythmCombination> rhythmCombinations) {
		super(length, rhythmCombinations);
	}

	@Override
	public int getType() {
		return 2;
	}
}
