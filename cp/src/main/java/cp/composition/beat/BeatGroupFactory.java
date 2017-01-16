package cp.composition.beat;

import cp.combination.RhythmCombination;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.List;

@Component
public class BeatGroupFactory {

	@Resource(name = "defaultUnevenCombinations")
	private List<RhythmCombination> defaultUnEvenCombinations;

	@Resource(name = "defaultEvenCombinations")
	private List<RhythmCombination> defaultEvenCombinations;

	@Resource(name = "homophonicEven")
	private List<RhythmCombination> homophonicEven;

	@Resource(name = "homophonicUneven")
	private List<RhythmCombination> homophonicUneven;
	
	@Resource(name = "fixedEven")
	private List<RhythmCombination> fixedEven;
	
	@Resource(name = "fixedUneven")
	private List<RhythmCombination> fixedUneven;

	public BeatGroup getBeatGroup(int type, int length, String groupName) {
		if (type == 2) {
			return getBeatGroupEven(length, groupName);
		} else if (type == 3) {
			return getBeatGroupUneven(length, groupName);
		}
		throw new IllegalArgumentException("BeatGroup not found for: type = " + type + ", name: " + groupName);

	}

	public BeatGroup getBeatGroupUneven(int length, String groupName) {
		switch (groupName) {
		case "homophonic":
			return new BeatGroupThree(length, homophonicUneven);
		case "fixed":
			return new BeatGroupThree(length, fixedUneven);
		default:
			break;
		}
		return new BeatGroupThree(length, defaultUnEvenCombinations);
	}

	public BeatGroup getBeatGroupEven(int length, String groupName) {
		switch (groupName) {
		case "homophonic":
			return new BeatGroupTwo(length, homophonicEven);
		case "fixed":
			return new BeatGroupTwo(length, fixedEven);
		default:
			break;
		}
		return new BeatGroupTwo(length, defaultEvenCombinations);
	}
}
