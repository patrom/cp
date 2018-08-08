package cp.composition.beat.harmony;

import cp.combination.RhythmCombination;
import cp.composition.beat.BeatGroup;
import cp.composition.voice.NoteSizeValueObject;
import cp.model.melody.Tonality;
import cp.model.note.Note;
import cp.model.note.Scale;
import cp.model.rhythm.DurationConstants;
import cp.util.RandomUtil;
import cp.util.RowMatrix;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.*;

import static java.util.Collections.emptyList;
import static java.util.stream.Collectors.toList;

@Component
public class BeatgroupHarmonyTwo extends BeatGroup {

    @Override
    public int getType() {
        return 2;
    }

    public List<Note> getRhythmNotesForBeatgroupType(int size){
        List<RhythmCombination> rhythmCombinations = this.rhythmCombinationsHarmonyMap.get(size);
        if(rhythmCombinations == null){
            LOGGER.info("No (provided) combination found for size: " + size);
            return emptyList();
        }
        return getNotes(rhythmCombinations);
    }

    public NoteSizeValueObject getRandomRhythmNotesForBeatgroupType(){
        Object[] keys = rhythmCombinationsHarmonyMap.keySet().toArray();
        Integer key = (Integer) keys[new Random().nextInt(keys.length)];
        List<RhythmCombination> rhythmCombinations = rhythmCombinationsHarmonyMap.get(key);
        RhythmCombination rhythmCombination = RandomUtil.getRandomFromList(rhythmCombinations);
        return new NoteSizeValueObject(key, rhythmCombination);
    }

    @Override
    public int getRandomNoteSize() {
        return RandomUtil.getRandomFromSet(rhythmCombinationsHarmonyMap.keySet());
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
        beatGroups.add(rhythmCombinations.fourNoteEven::pos1234);
//        beatGroups.add(rhythmCombinations.twoNoteEven::pos14);
        map.put(4, beatGroups);
        rhythmCombinationsHarmonyMap = map;

        motiveScale = Scale.MAJOR_SCALE;

        tonality = Tonality.TONAL;
//        timeLineKeys.add(new TimeLineKey(keys.C, Scale.MAJOR_SCALE));
//        timeLineKeys.add(new TimeLineKey(keys.F, Scale.MAJOR_SCALE));
//        timeLineKeys.add(new TimeLineKey(keys.A, Scale.HARMONIC_MINOR_SCALE));
//        timeLineKeys.add(new TimeLineKey(keys.D, Scale.HARMONIC_MINOR_SCALE));
//        pitchClassGenerators.add(orderPitchClasses::updatePitchClasses);
//        pitchClassGenerators.add(passingPitchClasses::updatePitchClasses);
        pitchClassGenerators.add(orderPitchClasses::updatePitchClasses);
        Scale scale = new Scale(new int[]{0, 4, 0, 4});
        motivePitchClasses.add(scale);
        scale = new Scale(new int[]{0, 5, 0, 5});
        motivePitchClasses.add(scale);

        int[] setClass = chordGenerator.generatePitchClasses("2-5");
        List<Integer> pitchClasses = Arrays.stream(setClass).boxed().collect(toList());
        RowMatrix rowMatrix = new RowMatrix(setClass.length, pitchClasses);

        extractIndexes();
    }

    private Map<Integer, List<RhythmCombination>> getBeatGroups(){
        Map<Integer, List<RhythmCombination>> map = new HashMap<>();
        List<RhythmCombination> beatGroups2 = new ArrayList<>();
        beatGroups2.add(rhythmCombinations.twoNoteEven::pos13);
//        beatGroups2.add(rhythmCombinations.twoNoteEven::pos14);
        map.put(2, beatGroups2);

//        List<RhythmCombination> beatGroups4 = new ArrayList<>();
//        beatGroups4.add(rhythmCombinations.fourNoteEven::pos1234);
//        beatGroups4.add(rhythmCombinations.combiNoteEven::pos23pos12);
//        map.put(4, beatGroups4);
        return map;
    }

}

