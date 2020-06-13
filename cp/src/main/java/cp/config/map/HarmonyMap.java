package cp.config.map;

import cp.combination.RhythmCombination;
import cp.combination.rhythm.RandomRhythmCombination;
import cp.combination.rhythm.RandomRhythmWithRestCombination;
import cp.model.rhythm.DurationConstants;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Component
public class HarmonyMap extends CompositionMap{

//    @PostConstruct
    public void init(){
        List<RhythmCombination> rhythmCombinations = new ArrayList<>();


//        rhythmCombinations.add(allRhythmCombinations.threeNoteEven::pos134);
//        rhythmCombinations.add(allRhythmCombinations.threeNoteEven::pos234);
//        rhythmCombinations.add(allRhythmCombinations.threeNoteEven::pos123);
//        rhythmCombinations.add(allRhythmCombinations.threeNoteEven::pos124);
//        rhythmCombinations.add(allRhythmCombinations.threeNoteUneven::pos123);
//        RandomRhythmCombination randomRhythmCombination =
//                new RandomRhythmCombination(DurationConstants.QUARTER, DurationConstants.EIGHT, DurationConstants.HALF);
//        rhythmCombinations.add(randomRhythmCombination);
//        List<Integer> rests = new ArrayList<>();
//        rests.add(DurationConstants.EIGHT);
//        RandomRhythmWithRestCombination randomRhythmWithRestCombination = new RandomRhythmWithRestCombination(rests, DurationConstants.QUARTER, DurationConstants.EIGHT, DurationConstants.HALF);
//        rhythmCombinations.add(randomRhythmWithRestCombination);

//        addMelodicValue(1, rhythmCombinations, DurationConstants.WHOLE);
//        addMelodicValue(1, rhythmCombinations, DurationConstants.WHOLE * 2);

//        addMelodicValue(4, rhythmCombinations, DurationConstants.WHOLE);
//        addMelodicValue(4, rhythmCombinations, DurationConstants.WHOLE * 2);


//        melodicValues.add(melodyMapComposition.getCompositionMap(0));

//        long count = melodicValues.stream().mapToInt(melodicValue -> melodicValue.size()).sum();
//        System.out.println("size map :" + count);
    }

    @PostConstruct
    public void initRhythm() {

//        melodicValues.add(singleRhythmGenerator.generateBalancedPattern(allRhythmCombinations.balancedPattern::pos5N30,
//                DurationConstants.SIXTEENTH, "4-3"));

//        List<RhythmCombination> rhythmCombinations = new ArrayList<>();
//        rhythmCombinations.add(allRhythmCombinations.threeNoteEven::pos123);
//        melodicValues.add(melodyMapComposition.getCompositionMap(4));

        addMelodicValue(0, 3, DurationConstants.THREE_QUARTERS);
        addMelodicValue(0, 3, DurationConstants.HALF * 3);
        addMelodicValue(1, 3, DurationConstants.THREE_QUARTERS);
        addMelodicValue(1, 3, DurationConstants.HALF * 3);
//        addMelodicValue(1, 2, DurationConstants.HALF);
//        addMelodicValue(5, 3, DurationConstants.WHOLE);
//        addMelodicValue(6, 3, DurationConstants.WHOLE);
//        addMelodicValue(5, 3, DurationConstants.WHOLE * 2);
//        addMelodicValue(6, 3, DurationConstants.WHOLE * 2);
//        melodicValues.add(melodyMapComposition.getCompositionMap(2));
//        melodicValues.add(melodyMapComposition.getCompositionMap(2));
//        addMelodicValue(1, rhythmCombinations, DurationConstants.HALF);

//        List<RhythmCombination> rhythmCombinations = new ArrayList<>();
//        rhythmCombinations.add(allRhythmCombinations.balancedPattern::pos5N30);
//        melodicValues.add(melodyMapComposition.getCompositionMap(2));
//        melodicValues.add(melodyMapComposition.getCompositionMap(2));
//        addMelodicValue(2, rhythmCombinations, 0);

//        addMelodicValueAscendingContour(2, rhythmCombinations, 0);
//        melodicValues.add(melodyMapComposition.getCompositionMap(4));
//        melodicValues.add(melodyMapComposition.getCompositionMap(0));
//        melodicValues.add(melodyMapComposition.getCompositionMap(1));
//        melodicValues.add(melodyMapComposition.getCompositionMap(3));
//        melodicValues.add(melodyMapComposition.getCompositionMap(4));
//        melodicValues.add(melodyMapComposition.getCompositionMap(3));
//        melodicValues.add(melodyMapComposition.getCompositionMap(4));
//        exhaustiveMelodicValues = new ArrayList<>(melodicValues);
    }

}

