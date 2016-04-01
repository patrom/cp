package cp.combination;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;

import cp.model.note.Note;
import cp.util.RandomUtil;

@Component
public class NoteCombination {

	@Resource(name="defaultCombinations")
	private List<RhythmCombination> defaultCombinations;
	@Resource(name="combinationsEvenBeat")
	private Map<Integer, List<RhythmCombination>> combinationsEvenBeat;
	@Resource(name="combinationsEvenBeat12")
	private Map<Integer, List<RhythmCombination>> combinationsEvenBeat12;
	@Resource(name="combinationsUnevenBeat")
	private Map<Integer, List<RhythmCombination>> combinationsUnevenBeat;
	
	public List<Note> getNotes(int beat, int voice){
		List<RhythmCombination> rhythmCombinations ;
		//3 division
		if (beat == 18 || beat == 36) {
			rhythmCombinations = combinationsUnevenBeat.getOrDefault(voice, defaultCombinations);
		} else {
			//2 division
			if (beat == 12) {
				rhythmCombinations = combinationsEvenBeat12.getOrDefault(voice, defaultCombinations);
			} else {
				rhythmCombinations = combinationsEvenBeat.getOrDefault(voice, defaultCombinations);
			}
		}
		RhythmCombination rhythmCombination = RandomUtil.getRandomFromList(rhythmCombinations);
		return rhythmCombination.getNotes(beat);
	}

	public void setDefaultCombinations(List<RhythmCombination> defaultCombinations) {
		this.defaultCombinations = defaultCombinations;
	}

	public void setCombinationsEvenBeat(int voice, List<RhythmCombination> combinationsEvenBeat) {
		this.combinationsEvenBeat.put(voice, combinationsEvenBeat);
	}

	public void setCombinationsEvenBeat12(int voice, List<RhythmCombination> combinationsEvenBeat12) {
		this.combinationsEvenBeat12.put(voice, combinationsEvenBeat12);
	}

	public void setCombinationsUnevenBeat(int voice, List<RhythmCombination> combinationsUnevenBeat) {
		this.combinationsUnevenBeat.put(voice, combinationsUnevenBeat);
	}

}
