package cp.composition.beat.melody;

import cp.combination.RhythmCombination;
import cp.composition.beat.BeatGroup;
import cp.composition.voice.NoteSizeValueObject;
import cp.model.note.Note;
import cp.model.rhythm.DurationConstants;
import cp.util.RandomUtil;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.List;
import java.util.Random;

import static java.util.Collections.emptyList;

@Component
public class BeatGroupFour extends BeatGroup {

    @Override
    public int getType() {
        return 4;
    }

    public List<Note> getRhythmNotesForBeatgroupType(int size){
        List<RhythmCombination> rhythmCombinations = this.rhythmCombinationMap.get(size);
        if(rhythmCombinations == null){
            LOGGER.info("No (provided) combination found for size: " + size);
            return emptyList();
        }
        return getNotes(rhythmCombinations);
    }

    public NoteSizeValueObject getRandomRhythmNotesForBeatgroupType(){
        Object[] keys = rhythmCombinationMap.keySet().toArray();
        Integer key = (Integer) keys[new Random().nextInt(keys.length)];
        List<RhythmCombination> rhythmCombinations = rhythmCombinationMap.get(key);
        RhythmCombination rhythmCombination = RandomUtil.getRandomFromList(rhythmCombinations);
        return new NoteSizeValueObject(key, rhythmCombination);
    }

    @Override
    public int getRandomNoteSize() {
        return RandomUtil.getRandomFromSet(rhythmCombinationMap.keySet());
    }

    @Override
    public int getBeatLength() {
        return getType() * DurationConstants.QUARTER;
    }

    @PostConstruct
    public void init() {
        rhythmCombinationMap = defaultEvenCombinations;
    }

}
