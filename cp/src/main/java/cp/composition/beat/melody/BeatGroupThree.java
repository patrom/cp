package cp.composition.beat.melody;

import cp.combination.RhythmCombination;
import cp.composition.beat.BeatGroup;
import cp.composition.voice.NoteSizeValueObject;
import cp.model.note.Note;
import cp.util.RandomUtil;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.List;
import java.util.Random;

import static java.util.Collections.emptyList;

@Component
public class BeatGroupThree extends BeatGroup {

	@Override
	public int getType() {
		return 3;
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

    @PostConstruct
    public void init() {
//        Map<Integer, List<RhythmCombination>> map = new HashMap<>();
//        List<RhythmCombination> beatGroups = new ArrayList<>();
//        beatGroups.add(rhythmCombinations.oneNoteEven::pos1);
//        map.put(1, beatGroups);
        rhythmCombinationMap = defaultUnEvenCombinations;
//        timeLineKeys.add(new TimeLineKey(keys.C, Scale.MELODY));
        pitchClassGenerators.add(orderPitchClasses::updatePitchClasses);
    }

}
