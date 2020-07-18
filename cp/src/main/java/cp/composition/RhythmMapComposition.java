package cp.composition;

import cp.combination.RhythmCombination;
import cp.combination.RhythmCombinations;
import cp.combination.rhythm.CompositeFiveFourRhythmCombination;
import cp.model.rhythm.DurationConstants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component(value = "rhythmMapComposition")
@ConditionalOnProperty(name = "mapComposition", havingValue = "true")
public class RhythmMapComposition {

    protected Map<Integer, List<RhythmCombination>> rhythmCombinationsMap = new HashMap<>();

    @Autowired
    private RhythmCombinations allRhythms;

    @PostConstruct
    public void initRhythm() {
        initRhythmEight();
        initRhythmQuarter();
        initRhythmThreeEights();
        initRhythmHalf();
        initRhythmThreeQuarters();
        initRhythmWhole();
    }

    public List<RhythmCombination> getRhythmMap(int key) {
        return rhythmCombinationsMap.get(key);
    }

    private void initRhythmEight() {
        List<RhythmCombination> rhythmCombinations = new ArrayList<>();
        rhythmCombinations.add(allRhythms.oneNoteEven::pos1);
        rhythmCombinations.add(allRhythms.oneNoteEven::pos2);
        rhythmCombinationsMap.put(DurationConstants.EIGHT, rhythmCombinations);
    }

    private void initRhythmQuarter() {
        List<RhythmCombination> twoCombinations = new ArrayList<>();
//		twoCombinations.add(twoNoteEven::pos12);
        twoCombinations.add(allRhythms.twoNoteEven::pos13);
		twoCombinations.add(allRhythms.twoNoteEven::pos14);
//		twoCombinations.add(twoNoteEven::pos34);
//		twoCombinations.add(twoNoteEven::pos23);
//		twoCombinations.add(twoNoteEven::pos24);

//        twoCombinations.add(twoNoteUneven::pos23);
//		twoCombinations.add(twoNoteUneven::pos12);
//		twoCombinations.add(twoNoteUneven::pos13);
        rhythmCombinationsMap.put(DurationConstants.QUARTER, twoCombinations);
    }

    private void initRhythmThreeEights() {
        List<RhythmCombination> threeCombinations = new ArrayList<>();
		threeCombinations.add(allRhythms.twoNoteUneven::pos23);

//        threeCombinations.add(allRhythms.threeNoteUneven::pos123);

//		threeCombinations.add(allRhythms.threeNoteSexTuplet::pos145);
//		threeCombinations.add(allRhythms.threeNoteSexTuplet::pos136);
//		threeCombinations.add(allRhythms.threeNoteSexTuplet::pos156);
//		threeCombinations.add(allRhythms.threeNoteSexTuplet::pos123);
        rhythmCombinationsMap.put(DurationConstants.THREE_EIGHTS, threeCombinations);
    }

    private void initRhythmHalf() {
        List<RhythmCombination> fourCombinations = new ArrayList<>();
//        fourCombinations.add(allRhythms.fourNoteEven::pos1234);
        fourCombinations.add(allRhythms.twoNoteEven::pos13);
        fourCombinations.add(allRhythms.twoNoteEven::pos14);
//		fourCombinations.add(allRhythms.fourNoteSexTuplet::pos1456);
//		fourCombinations.add(allRhythms.fourNoteSexTuplet::pos1234);
//        fourCombinations.add(allRhythms.fourNoteSexTuplet::pos1345);
//        fourCombinations.add(allRhythms.fourNoteSexTuplet::pos1346);
//        fourCombinations.add(allRhythms.fourNoteSexTuplet::pos1356);

        rhythmCombinationsMap.put(DurationConstants.HALF, fourCombinations);
    }

    private void initRhythmThreeQuarters() {
        List<RhythmCombination> rhythmCombinations = new ArrayList<>();
        CompositeFiveFourRhythmCombination compositeRhythmCombination = new CompositeFiveFourRhythmCombination ();
        compositeRhythmCombination.addRhythmCombinationThree(allRhythms.threeNoteSexTuplet::pos145);
        compositeRhythmCombination.addRhythmCombinationThree(allRhythms.threeNoteSexTuplet::pos123);
        compositeRhythmCombination.addRhythmCombinationThree(allRhythms.threeNoteSexTuplet::pos136);
//        compositeRhythmCombination.addRhythmCombinationThree(allRhythms.fourNoteSexTuplet::pos1456);
        compositeRhythmCombination.addRhythmCombinationTwo(allRhythms.twoNoteEven::pos14);
        compositeRhythmCombination.addRhythmCombinationTwo(allRhythms.twoNoteEven::pos13);
        compositeRhythmCombination.addRhythmCombinationTwo(allRhythms.twoNoteEven::pos34);
//        compositeRhythmCombination.addRhythmCombinationTwo(allRhythms.threeNoteEven::pos123);
//        compositeRhythmCombination.addRhythmCombinationTwo(allRhythms.threeNoteEven::pos134);
//       fiveCombinations.add(compositeRhythmCombination);
//		fiveCombinations.add(quintuplet::pos12345);
//		fiveCombinations.add(quintuplet::pos2345);

        rhythmCombinations.add(allRhythms.twoNoteUneven::pos23);
        rhythmCombinations.add(allRhythms.twoNoteUneven::pos13);
        rhythmCombinationsMap.put(DurationConstants.THREE_QUARTERS, rhythmCombinations);
    }

    private void initRhythmWhole() {
		List<RhythmCombination> rhythmCombinations = new ArrayList<>();
        rhythmCombinations.add(allRhythms.twoNoteEven::pos13);
        rhythmCombinations.add(allRhythms.twoNoteEven::pos14);
        rhythmCombinationsMap.put(DurationConstants.WHOLE, rhythmCombinations);
    }
}
