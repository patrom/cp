package cp.composition.beat;

import cp.combination.RhythmCombination;
import cp.composition.voice.NoteSizeValueObject;
import cp.model.note.Note;
import cp.util.RandomUtil;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Random;

import static java.util.Collections.emptyList;

@Component
public class BeatGroupTwo extends BeatGroup {

	@Override
	public int getType() {
		return 2;
	}

    public List<Note> getRhythmNotesForBeatgroupType(int size){
        List<RhythmCombination> rhythmCombinations;
        rhythmCombinations = this.defaultEvenCombinations.get(size);
        if(rhythmCombinations == null){
            LOGGER.info("No (provided) combination found for size: " + size);
            return emptyList();
        }
        return getNotes(rhythmCombinations);
    }

    public NoteSizeValueObject getRandomRhythmNotesForBeatgroupType(){
        Object[] keys = defaultEvenCombinations.keySet().toArray();
        Integer key = (Integer) keys[new Random().nextInt(keys.length)];
        List<RhythmCombination> rhythmCombinations = defaultEvenCombinations.get(key);
        RhythmCombination rhythmCombination = RandomUtil.getRandomFromList(rhythmCombinations);
        return new NoteSizeValueObject(key, rhythmCombination);
    }

    @Override
    public int getRandomNoteSize() {
        return RandomUtil.getRandomFromSet(defaultEvenCombinations.keySet());
    }
}
