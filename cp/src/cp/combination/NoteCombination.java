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
	@Resource(name="combinationsUnevenBeat")
	private Map<Integer, List<RhythmCombination>> combinationsUnevenBeat;
	
	public List<Note> getNotes(int beat, int voice){
		List<RhythmCombination> rhythmCombinations ;
		if (beat == 18 || beat == 36) {
			rhythmCombinations = combinationsUnevenBeat.getOrDefault(voice, defaultCombinations);
		} else {
			rhythmCombinations = combinationsEvenBeat.getOrDefault(voice, defaultCombinations);
		}
		RhythmCombination rhythmCombination = RandomUtil.getRandomFromList(rhythmCombinations);
		return rhythmCombination.getNotes(beat);
	}

}
