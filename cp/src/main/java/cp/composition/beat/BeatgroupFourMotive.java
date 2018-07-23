package cp.composition.beat;

import cp.combination.RhythmCombination;
import cp.composition.voice.NoteSizeValueObject;
import cp.model.TimeLineKey;
import cp.model.melody.Tonality;
import cp.model.note.Note;
import cp.model.note.Scale;
import cp.model.rhythm.DurationConstants;
import cp.util.RandomUtil;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.List;
import java.util.Random;

import static java.util.Collections.emptyList;

@Component
public class BeatgroupFourMotive extends BeatGroup {

    @Override
    public int getType() {
        return 4;
    }

    public List<Note> getRhythmNotesForBeatgroupType(int size){
        List<RhythmCombination> rhythmCombinations = this.fixedEven.get(size);
        if(rhythmCombinations == null){
            LOGGER.info("No (provided) combination found for size: " + size);
            return emptyList();
        }
        return getNotes(rhythmCombinations);
    }

    public NoteSizeValueObject getRandomRhythmNotesForBeatgroupType(){
        Object[] keys = fixedEven.keySet().toArray();
        Integer key = (Integer) keys[new Random().nextInt(keys.length)];
        List<RhythmCombination> rhythmCombinations = fixedEven.get(key);
        RhythmCombination rhythmCombination = RandomUtil.getRandomFromList(rhythmCombinations);
        return new NoteSizeValueObject(key, rhythmCombination);
    }

    @Override
    public int getRandomNoteSize() {
        return RandomUtil.getRandomFromSet(fixedEven.keySet());
    }

    @Override
    public int getBeatLength() {
        return getType() * DurationConstants.QUARTER;
    }

    @PostConstruct
    public void init() {
        motiveScale = Scale.MAJOR_SCALE;
        tonalities.add(Tonality.TONAL);
        timeLineKeys.add(new TimeLineKey(keys.C, Scale.MAJOR_SCALE));
        timeLineKeys.add(new TimeLineKey(keys.F, Scale.MAJOR_SCALE));
        timeLineKeys.add(new TimeLineKey(keys.A, Scale.HARMONIC_MINOR_SCALE));
        timeLineKeys.add(new TimeLineKey(keys.D, Scale.HARMONIC_MINOR_SCALE));
        pitchClassGenerators.add(orderPitchClasses::updatePitchClasses);
        motivePitchClasses.add(new int[]{0,2,5,4});

        for (int[] motivePitchClass : motivePitchClasses) {
            int[] indexes = getMotiveScale().getIndexes(motivePitchClass);
            indexesMotivePitchClasses.add(indexes);
            int[] inversedIndexes = new int[indexes.length];
            for (int i = 0; i < indexes.length; i++) {
                int index = indexes[i];
                int inversedIndex = Scale.MAJOR_SCALE.getInversedIndex(1, index);
                inversedIndexes[i] = inversedIndex;
            }
            inverseIndexesMotivePitchClasses.add(inversedIndexes);
        }
    }
}
