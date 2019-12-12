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
public class RhythmMap extends CompositionMap{

    @PostConstruct
    public void initRhythm() {


//        List<RhythmCombination> rhythmCombinations = new ArrayList<>();
//        rhythmCombinations.add(allRhythmCombinations.balancedPattern::pos5N30);

//        melodicValues.add(melodyMapComposition.getCompositionMap(2));
//        addMelodicValue(2, rhythmCombinations, 0);
//        melodicValues.add(melodyMapComposition.getCompositionMap(4));
        melodicValues.add(melodyMapComposition.getCompositionMap(1));
        melodicValues.add(melodyMapComposition.getCompositionMap(0));
//        melodicValues.add(melodyMapComposition.getCompositionMap(3));
//        melodicValues.add(melodyMapComposition.getCompositionMap(4));
//        melodicValues.add(melodyMapComposition.getCompositionMap(3));
//        melodicValues.add(melodyMapComposition.getCompositionMap(4));
//        exhaustiveMelodicValues = new ArrayList<>(melodicValues);
    }


}

