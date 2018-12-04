package cp.composition.beat.melody;

import cp.combination.RhythmCombination;
import cp.composition.beat.BeatGroup;
import cp.composition.voice.NoteSizeValueObject;
import cp.generator.pitchclass.PitchClassGenerator;
import cp.model.note.Note;
import cp.util.RandomUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Random;

import static java.util.Collections.emptyList;

public class BeatGroupMelody extends BeatGroup {

    public BeatGroupMelody(int length, Map<Integer, List<RhythmCombination>> rhythmCombinationMap, List<PitchClassGenerator> pitchClassGenerators) {
        super(length, rhythmCombinationMap, pitchClassGenerators);
    }

    public BeatGroupMelody(int length, int pulse, Map<Integer, List<RhythmCombination>> rhythmCombinationMap, List<PitchClassGenerator> pitchClassGenerators) {
        super(length, pulse, rhythmCombinationMap, pitchClassGenerators);
    }

    public List<Note> getRhythmNotesForBeatgroupType(int size){
        List<RhythmCombination> rhythmCombinations = new ArrayList<>();
        rhythmCombinations = this.rhythmCombinationMap.get(size);
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
        return new NoteSizeValueObject(key, rhythmCombination.getNotes(this.length, this.pulse));
    }

}