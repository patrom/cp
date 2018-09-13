package cp.composition.beat.harmony;

import cp.combination.RhythmCombination;
import cp.composition.beat.BeatGroup;
import cp.composition.voice.NoteSizeValueObject;
import cp.generator.pitchclass.PitchClassGenerator;
import cp.model.TimeLineKey;
import cp.model.harmony.ChordType;
import cp.model.melody.Tonality;
import cp.model.note.Note;
import cp.model.note.Scale;
import cp.util.RandomUtil;
import cp.util.RowMatrix;

import javax.annotation.PostConstruct;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Random;

import static java.util.Collections.emptyList;
import static java.util.stream.Collectors.toList;

public class BeatgroupHarmony extends BeatGroup {

    public BeatgroupHarmony(int type, Map<Integer, List<RhythmCombination>> rhythmCombinationMap, List<PitchClassGenerator> pitchClassGenerators) {
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
        timeLineKeys.add(new TimeLineKey(keys.C, Scale.MAJOR_SCALE));
//        timeLineKeys.add(new TimeLineKey(keys.F, Scale.MAJOR_SCALE));
//        timeLineKeys.add(new TimeLineKey(keys.A, Scale.HARMONIC_MINOR_SCALE));
//        timeLineKeys.add(new TimeLineKey(keys.D, Scale.HARMONIC_MINOR_SCALE));

        motiveScale = Scale.MAJOR_SCALE;

        tonality = Tonality.ATONAL;

        Scale scale = new Scale(new int[]{4, 0, 7, 3});
        motivePitchClasses.add(scale);
//        scale = new Scale(new int[]{0, 5});
//        motivePitchClasses.add(scale);

        chordTypes.add(ChordType.MINOR_CHR);
        chordTypes.add(ChordType.MAJOR_CHR);

        int[] setClass = chordGenerator.generatePitchClasses("2-5");
        List<Integer> pitchClasses = Arrays.stream(setClass).boxed().collect(toList());
        RowMatrix rowMatrix = new RowMatrix(setClass.length, pitchClasses);

        extractIndexes();
    }



}

