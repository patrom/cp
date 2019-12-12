package cp.config.map;

import cp.combination.RhythmCombination;
import cp.combination.rhythm.RandomRhythmCombination;
import cp.combination.rhythm.RandomRhythmWithRestCombination;
import cp.model.rhythm.DurationConstants;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;

@Component
public class HarmonyMap extends CompositionMap{

    @PostConstruct
    public void init(){
        List<RhythmCombination> rhythmCombinations = new ArrayList<>();
//        rhythmCombinations.add(allRhythmCombinations.threeNoteEven::pos134);
//        rhythmCombinations.add(allRhythmCombinations.threeNoteEven::pos234);
//        rhythmCombinations.add(allRhythmCombinations.threeNoteEven::pos123);
//        rhythmCombinations.add(allRhythmCombinations.threeNoteEven::pos124);
//        rhythmCombinations.add(allRhythmCombinations.threeNoteUneven::pos123);
        RandomRhythmCombination randomRhythmCombination =
                new RandomRhythmCombination(DurationConstants.QUARTER, DurationConstants.EIGHT, DurationConstants.HALF);
        rhythmCombinations.add(randomRhythmCombination);
        List<Integer> rests = new ArrayList<>();
        rests.add(DurationConstants.EIGHT);
        RandomRhythmWithRestCombination randomRhythmWithRestCombination = new RandomRhythmWithRestCombination(rests, DurationConstants.QUARTER, DurationConstants.EIGHT, DurationConstants.HALF);
        rhythmCombinations.add(randomRhythmWithRestCombination);

//        addMelodicValue(1, rhythmCombinations, DurationConstants.WHOLE);
//        addMelodicValue(1, rhythmCombinations, DurationConstants.WHOLE * 2);

//        addMelodicValue(4, rhythmCombinations, DurationConstants.WHOLE);
//        addMelodicValue(4, rhythmCombinations, DurationConstants.WHOLE * 2);


//        melodicValues.add(melodyMapComposition.getCompositionMap(0));

//        long count = melodicValues.stream().mapToInt(melodicValue -> melodicValue.size()).sum();
//        System.out.println("size map :" + count);
    }

}

