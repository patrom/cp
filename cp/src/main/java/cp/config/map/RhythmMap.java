package cp.config.map;

import cp.combination.RhythmCombination;
import cp.combination.rhythm.RandomRhythmCombination;
import cp.combination.rhythm.RandomRhythmWithRestCombination;
import cp.composition.ContourType;
import cp.composition.MelodicValue;
import cp.composition.MelodicValueMelody;
import cp.model.rhythm.DurationConstants;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;

@Component
public class RhythmMap extends CompositionMap{

    @PostConstruct
    public void initRhythm() {


        List<RhythmCombination> rhythmCombinations = new ArrayList<>();
//        rhythmCombinations.add(allRhythmCombinations.balancedPattern::pos6N30);
//        rhythmCombinations.add(allRhythmCombinations.threeNoteEven::pos134);
//            addMelodicValue(5, 2, DurationConstants.QUARTER);
//        MelodicValue melodicValue = melodyMapComposition.getCompositionMap(2);
//        addMelodicValue(2,rhythmCombinations, DurationConstants.QUARTER);
//        melodicValues.add(melodicValue);
//        melodicValues.add(melodyMapComposition.getCompositionMap(3));
//        melodicValues.add(singleRhythmGenerator.generateBalancedPattern(allRhythmCombinations.balancedPattern::pos3N30,
//                DurationConstants.SIXTEENTH, "3-1"));
//        addMelodicValueAscendingContour(0, rhythmCombinations, 0);
//        melodicValues.add(melodyMapComposition.getCompositionMap(4));
//        MelodicValue melodicValue = melodyMapComposition.getCompositionMap(3);
//        melodicValue.setContourType(ContourType.ASC);
//        melodicValues.add(melodicValue);
//        melodicValues.add(melodyMapComposition.getCompositionMap(0));
//        melodicValues.add(melodyMapComposition.getCompositionMap(3));
//        melodicValues.add(melodyMapComposition.getCompositionMap(4));
//        melodicValues.add(melodyMapComposition.getCompositionMap(3));
//        melodicValues.add(melodyMapComposition.getCompositionMap(4));
//        exhaustiveMelodicValues = new ArrayList<>(melodicValues);
    }


}

