package cp.config.map;

import cp.combination.RhythmCombination;
import cp.combination.rhythm.CompositeRhythmCombination;
import cp.combination.rhythm.RandomRhythmCombination;
import cp.combination.rhythm.RandomRhythmWithRestCombination;
import cp.composition.ContourType;
import cp.composition.MelodicValue;
import cp.composition.MelodicValueMelody;
import cp.composition.MelodicValueRhythmCombination;
import cp.model.note.Scale;
import cp.model.rhythm.DurationConstants;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.*;

@Component
public class MelodyMap extends CompositionMap{

//    @PostConstruct
    public void init(){
        List<RhythmCombination> rhythmCombinations = new ArrayList<>();
        rhythmCombinations.add(allRhythmCombinations.threeNoteEven::pos134);
        rhythmCombinations.add(allRhythmCombinations.threeNoteEven::pos234);
        rhythmCombinations.add(allRhythmCombinations.threeNoteEven::pos123);
        rhythmCombinations.add(allRhythmCombinations.threeNoteEven::pos124);
//        rhythmCombinations.add(allRhythmCombinations.threeNoteUneven::pos123);
//        rhythmCombinations.add(allRhythmCombinations.threeNoteSexTuplet::pos156);
//        rhythmCombinations.add(allRhythmCombinations.threeNoteSexTuplet::pos145);

//        rhythmCombinations.add(allRhythmCombinations.balancedPattern::pos5);

//        DurationRhythmCombination durationRhythmCombination =
//        new DurationRhythmCombination(DurationConstants.QUARTER, DurationConstants.SIXTEENTH, DurationConstants.SIXTEENTH);
//        rhythmCombinations.add(durationRhythmCombination);
//        List<RhythmCombination> rhythmCombinations4 = new ArrayList<>();
//        RandomRhythmCombination randomRhythmCombination =
//                new RandomRhythmCombination(DurationConstants.THREE_QUARTERS, DurationConstants.QUARTER, DurationConstants.QUARTER, DurationConstants.THREE_QUARTERS);
//        rhythmCombinations4.add(randomRhythmCombination);
//        List<Integer> rests = new ArrayList<>();
//        rests.add(DurationConstants.QUARTER);
//        RandomRhythmWithRestCombination randomRhythmWithRestCombination = new RandomRhythmWithRestCombination(rests, DurationConstants.QUARTER, DurationConstants.EIGHT, DurationConstants.EIGHT, DurationConstants.HALF);
//        rhythmCombinations4.add(randomRhythmWithRestCombination);
//        rhythmCombinations4.add(allRhythmCombinations.fourNoteSexTuplet::pos1346);
//        rhythmCombinations4.add(allRhythmCombinations.fourNoteSexTuplet::pos1356);

        addMelodicValue(2, rhythmCombinations, DurationConstants.HALF);
//        addMelodicValue(3, rhythmCombinations, DurationConstants.HALF);
//        addMelodicValue(3, rhythmCombinations4, DurationConstants.HALF);

//        addMelodicValue(4, rhythmCombinations, DurationConstants.HALF);
//        addMelodicValue(5, rhythmCombinations4, DurationConstants.HALF);
//        addMelodicValue(6, rhythmCombinations4, DurationConstants.HALF);

//        melodicValues.add(melodyMapComposition.getCompositionMap(0));
//        melodicValues.add(melodyMapComposition.getCompositionMap(1));
//        melodicValues.add(melodyMapComposition.getCompositionMap(2));

//        long count = melodicValues.stream().mapToInt(melodicValue -> melodicValue.size()).sum();
//        System.out.println("size map :" + count);
    }

//    @PostConstruct
    public void initTonal() {
//        melodicValues.add(melodyMapComposition.getCompositionMap(0));
//        melodicValues.add(melodyMapComposition.getCompositionMap(1));
//        melodicValues.add(melodyMapComposition.getCompositionMap(2));
//        melodicValues.add(melodyMapComposition.getCompositionMap(3));
//        melodicValues.add(melodyMapComposition.getCompositionMap(4));
    }

    @PostConstruct
    public void initRhythm() {
        List<RhythmCombination> rhythmCombinations = new ArrayList<>();
//        rhythmCombinations.add(allRhythmCombinations.threeNoteEven::pos123);

        CompositeRhythmCombination compositeRhythmCombination = new CompositeRhythmCombination();
        compositeRhythmCombination.addRhythmCombination(allRhythmCombinations.threeNoteEven::pos134);
        compositeRhythmCombination.addRhythmCombination(allRhythmCombinations.fourNoteEven::pos1234);
        rhythmCombinations.add(compositeRhythmCombination);
        addMelodicValue(0, rhythmCombinations, DurationConstants.WHOLE);
        addMelodicValue(1, rhythmCombinations, DurationConstants.WHOLE);
//        addMelodicValue(1, rhythmCombinations, DurationConstants.HALF);

//        melodicValues.add(singleRhythmGenerator.generateOstinato(rhythmCombinations, DurationConstants.THREE_EIGHTS, ContourType.ASC, 1,7));


//        List<RhythmCombination> rhythmCombinations = new ArrayList<>();
//        rhythmCombinations.add(allRhythmCombinations.balancedPattern::pos6N30);

//        rhythmCombinations.add(allRhythmCombinations.threeNoteEven::pos234);
//        rhythmCombinations.add(allRhythmCombinations.threeNoteEven::pos134);
//        addMelodicValue(2, rhythmCombinations, DurationConstants.HALF);
//        addMelodicValue(2, rhythmCombinations, DurationConstants.WHOLE);
//        melodicValues.add(melodyMapComposition.getCompositionMap(3));
//        melodicValues.add(melodyMapComposition.getCompositionMap(0));
//        melodicValues.add(melodyMapComposition.getCompositionMap(1));
//        melodicValues.add(melodyMapComposition.getCompositionMap(3));
//        melodicValues.add(melodyMapComposition.getCompositionMap(4));
//        melodicValues.add(melodyMapComposition.getCompositionMap(3));
//        melodicValues.add(melodyMapComposition.getCompositionMap(4));
//        exhaustiveMelodicValues = new ArrayList<>(melodicValues);
    }


}
