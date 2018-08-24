package cp.composition.beat.motive;

import cp.combination.RhythmCombination;
import cp.composition.beat.BeatGroup;
import cp.composition.voice.NoteSizeValueObject;
import cp.generator.pitchclass.PitchClassGenerator;
import cp.model.note.Note;
import cp.util.RandomUtil;

import javax.annotation.PostConstruct;
import java.util.List;
import java.util.Map;
import java.util.Random;

import static java.util.Collections.emptyList;

public class BeatgroupMotive extends BeatGroup {

    public BeatgroupMotive(int type, Map<Integer, List<RhythmCombination>> rhythmCombinationMap, List<PitchClassGenerator> pitchClassGenerators) {
        super(type, rhythmCombinationMap, pitchClassGenerators);
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

    @PostConstruct
    public void init() {
        extractIndexes();
    }

}
