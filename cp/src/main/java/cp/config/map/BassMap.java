package cp.config.map;

import cp.combination.RhythmCombination;
import cp.combination.rhythm.RandomRhythmCombination;
import cp.combination.rhythm.RandomRhythmWithRestCombination;
import cp.model.melody.CpMelody;
import cp.model.note.Scale;
import cp.model.rhythm.DurationConstants;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;

@Component
public class BassMap extends CompositionMap{

    @PostConstruct
    public void init(){
        List<RhythmCombination> rhythmCombinations4 = new ArrayList<>();
        RandomRhythmCombination randomRhythmCombination =
                new RandomRhythmCombination(DurationConstants.THREE_QUARTERS, DurationConstants.QUARTER, DurationConstants.QUARTER, DurationConstants.THREE_QUARTERS);
        rhythmCombinations4.add(randomRhythmCombination);
        List<Integer> rests = new ArrayList<>();
        rests.add(DurationConstants.EIGHT);
        RandomRhythmCombination randomRhythmWithRestCombination = new RandomRhythmCombination(DurationConstants.QUARTER, DurationConstants.HALF, DurationConstants.QUARTER, DurationConstants.HALF);
        rhythmCombinations4.add(randomRhythmWithRestCombination);
//        rhythmCombinations4.add(allRhythmCombinations.fourNoteSexTuplet::pos1346);
//        rhythmCombinations4.add(allRhythmCombinations.fourNoteSexTuplet::pos1356);

//        addMelodicValue(1, rhythmCombinations, DurationConstants.HALF);
//        addMelodicValue(3, rhythmCombinations4, DurationConstants.HALF);

//        long count = melodicValues.stream().mapToInt(melodicValue -> melodicValue.size()).sum();
//        System.out.println("size map :" + count);
    }


}

