package cp.composition.beat;

import cp.combination.RhythmCombination;
import cp.composition.voice.NoteSizeValueObject;
import cp.model.note.Note;
import cp.util.RandomUtil;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import static java.util.Collections.emptyList;

@Component
public class BeatGroupOne extends BeatGroup {

    @Override
    public int getType() {
        return 1;
    }

    public List<Note> getRhythmNotesForBeatgroupType(int size){
        List<RhythmCombination> rhythmCombinations = new ArrayList<>();
        rhythmCombinations = this.customCombi.get(size);
        if(rhythmCombinations == null){
            LOGGER.info("No (provided) combination found for size: " + size);
            return emptyList();
        }
        return getNotes(rhythmCombinations);
    }

    public NoteSizeValueObject getRandomRhythmNotesForBeatgroupType(){
        Object[] keys = customCombi.keySet().toArray();
        Integer key = (Integer) keys[new Random().nextInt(keys.length)];
        List<RhythmCombination> rhythmCombinations = customCombi.get(key);
        RhythmCombination rhythmCombination = RandomUtil.getRandomFromList(rhythmCombinations);
        return new NoteSizeValueObject(key, rhythmCombination);
    }

    @Override
    public int getRandomNoteSize() {
        return RandomUtil.getRandomFromSet(customCombi.keySet());
    }

    @PostConstruct
    public void init() {
        customCombi = getEvenBeatGroups();
//        timeLineKeys.add(new TimeLineKey(keys.C, Scale.MELODY));
        pitchClassGenerators.add(passingPitchClasses::updatePitchClasses);
    }

}