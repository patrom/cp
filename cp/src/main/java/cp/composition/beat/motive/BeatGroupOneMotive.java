package cp.composition.beat.motive;

import cp.combination.RhythmCombination;
import cp.composition.beat.BeatGroup;
import cp.composition.voice.NoteSizeValueObject;
import cp.model.melody.Tonality;
import cp.model.note.Note;
import cp.model.note.Scale;
import cp.model.rhythm.DurationConstants;
import cp.util.RandomUtil;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.*;

import static java.util.Collections.emptyList;

@Component
public class BeatGroupOneMotive extends BeatGroup {

    @Override
    public int getType() {
        return 1;
    }

    public List<Note> getRhythmNotesForBeatgroupType(int size){
        List<RhythmCombination> rhythmCombinations = this.rhythmCombinationsMotiveMap.get(size);
        if(rhythmCombinations == null){
            LOGGER.info("No (provided) combination found for size: " + size);
            return emptyList();
        }
        return getNotes(rhythmCombinations);
    }

    public NoteSizeValueObject getRandomRhythmNotesForBeatgroupType(){
        Object[] keys = rhythmCombinationsMotiveMap.keySet().toArray();
        Integer key = (Integer) keys[new Random().nextInt(keys.length)];
        List<RhythmCombination> rhythmCombinations = rhythmCombinationsMotiveMap.get(key);
        RhythmCombination rhythmCombination = RandomUtil.getRandomFromList(rhythmCombinations);
        return new NoteSizeValueObject(key, rhythmCombination);
    }

    @Override
    public int getRandomNoteSize() {
        return RandomUtil.getRandomFromSet(rhythmCombinationsMotiveMap.keySet());
    }

    @Override
    public int getBeatLength() {
        return getType() * DurationConstants.QUARTER;
    }

    @PostConstruct
    public void init() {
        super.init();
        Map<Integer, List<RhythmCombination>> map = new HashMap<>();
        List<RhythmCombination> beatGroups = new ArrayList<>();
        beatGroups.add(rhythmCombinations.threeNoteEven::pos123);

        map.put(3, beatGroups);

//        map.put(2, Collections.singletonList(rhythmCombinations.twoNoteEven::pos12));
        rhythmCombinationsMotiveMap = map;
        motiveScale = Scale.MAJOR_SCALE;

        tonality = Tonality.ATONAL;
//        timeLineKeys.add(new TimeLineKey(keys.C, Scale.MAJOR_SCALE));
//        timeLineKeys.add(new TimeLineKey(keys.F, Scale.MAJOR_SCALE));
//        timeLineKeys.add(new TimeLineKey(keys.A, Scale.HARMONIC_MINOR_SCALE));
//        timeLineKeys.add(new TimeLineKey(keys.D, Scale.HARMONIC_MINOR_SCALE));
        pitchClassGenerators.add(orderPitchClasses::updatePitchClasses);
//        pitchClassGenerators.add(passingPitchClasses::updatePitchClasses);
        Scale scale = new Scale(new int[]{0, 4, 3});
//        Scale scale = new Scale(new int[]{0, 2, 3, 4});
        motivePitchClasses.add(scale);

        extractIndexes();

    }

}

