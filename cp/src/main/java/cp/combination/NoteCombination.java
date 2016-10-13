package cp.combination;

import cp.model.note.Note;
import cp.model.rhythm.DurationConstants;
import cp.util.RandomUtil;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import static java.util.stream.Collectors.toList;

@Component
public class NoteCombination {

	@Resource(name="defaultEvenCombinations")
	private List<RhythmCombination> defaultEvenCombinations;
	@Resource(name="defaultUnevenCombinations")
	private List<RhythmCombination> defaultUnEvenCombinations;
//	@Resource(name="combinationsEvenBeat")
//	private Map<Integer, List<RhythmCombination>> combinationsEvenBeat;
//	@Resource(name="combinationsEvenBeat12")
//	private Map<Integer, List<RhythmCombination>> combinationsEvenBeat12;
//	@Resource(name="combinationsUnevenBeat")
//	private Map<Integer, List<RhythmCombination>> combinationsUnevenBeat;
	
	private final Map<Integer, List<RhythmCombination>> combinations =  new TreeMap<>();
	
	public List<Note> getNotes(int beat, int voice){
		List<RhythmCombination> rhythmCombinations = null;
//		//uneven division
		if (beat == DurationConstants.THREE_EIGHTS || beat == DurationConstants.SIX_EIGHTS) {
			rhythmCombinations = combinations.getOrDefault(voice, defaultUnEvenCombinations);
		} else {
			//even division
			if (beat == DurationConstants.QUARTER || beat == DurationConstants.HALF) {
				rhythmCombinations = combinations.getOrDefault(voice, defaultEvenCombinations);
			} 
		}
		RhythmCombination rhythmCombination = RandomUtil.getRandomFromList(rhythmCombinations);
		return rhythmCombination.getNotes(beat);
	}
	
	public List<Note> getNotesFixed(int beat, int voice){
		List<RhythmCombination> rhythmCombinations = null;
//		//uneven division
		if (beat == DurationConstants.THREE_EIGHTS || beat == DurationConstants.SIX_EIGHTS) {
			rhythmCombinations = combinations.getOrDefault(voice, defaultUnEvenCombinations);
		} else {
			//even division
			if (beat == DurationConstants.QUARTER || beat == DurationConstants.HALF) {
				rhythmCombinations = combinations.getOrDefault(voice, defaultEvenCombinations);
			} 
		}
		return rhythmCombinations.stream().flatMap(comb -> comb.getNotes(beat).stream()).collect(toList());
	}

//	public void setDefaultCombinations(Combination combination) {
//		this.defaultEvenCombinations = combination.getCombination();
//	}
//
//	public void setCombinations(int voice, Combination combination) {
//		this.combinations.put(voice, combination.getCombination());
//	}

//	public void setCombinationsEvenBeat12(int voice, Combination combination) {
//		this.combinationsEvenBeat12.put(voice, combination.getCombination());
//	}
//
//	public void setCombinationsUnevenBeat(int voice, Combination combination) {
//		this.combinationsUnevenBeat.put(voice, combination.getCombination());
//	}

}
