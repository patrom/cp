package cp.composition;

import cp.combination.RhythmCombination;
import cp.combination.RhythmCombinations;
import cp.combination.rhythm.CompositeRhythmCombination;
import cp.generator.PitchClassGenerator;
import cp.generator.SingleMelodyGenerator;
import cp.generator.SingleRhythmGenerator;
import cp.out.print.Keys;
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
        initRhythm1();
        initRhythm2();
        initRhythm3();
        initRhythm4();
        initRhythm5();
        initRhythm6();
    }

    public List<RhythmCombination> getrhythmMap(int key) {
        return rhythmCombinationsMap.get(key);
    }

    private void initRhythm1() {
        List<RhythmCombination> rhythmCombinations = new ArrayList<>();
        rhythmCombinations.add(allRhythms.oneNoteEven::rest);
        rhythmCombinations.add(allRhythms.oneNoteEven::pos1);
        rhythmCombinations.add(allRhythms.oneNoteEven::pos2);
        rhythmCombinations.add(allRhythms.oneNoteEven::pos3);
        rhythmCombinations.add(allRhythms.oneNoteEven::pos4);
        rhythmCombinationsMap.put(1, rhythmCombinations);
    }

    private void initRhythm2() {
        List<RhythmCombination> twoCombinations = new ArrayList<>();
//		twoCombinations.add(twoNoteEven::pos12);
        twoCombinations.add(allRhythms.twoNoteEven::pos13);
//		twoCombinations.add(twoNoteEven::pos14);
//		twoCombinations.add(twoNoteEven::pos34);
//		twoCombinations.add(twoNoteEven::pos23);
//		twoCombinations.add(twoNoteEven::pos24);

//        twoCombinations.add(twoNoteUneven::pos23);
//		twoCombinations.add(twoNoteUneven::pos12);
//		twoCombinations.add(twoNoteUneven::pos13);
        rhythmCombinationsMap.put(2, twoCombinations);
    }

    private void initRhythm3() {
        List<RhythmCombination> threeCombinations = new ArrayList<>();
//		threeCombinations.add(threeNoteEven::pos123);
//		threeCombinations.add(threeNoteEven::pos134);
//		threeCombinations.add(threeNoteEven::pos124);
        threeCombinations.add(allRhythms.threeNoteEven::pos234);

//        threeCombinations.add(threeNoteUneven::pos123);

//		threeCombinations.add(threeNoteSexTuplet::pos145);
//		threeCombinations.add(threeNoteSexTuplet::pos136);
//		threeCombinations.add(threeNoteSexTuplet::pos156);
//		threeCombinations.add(threeNoteSexTuplet::pos123);
        rhythmCombinationsMap.put(3, threeCombinations);
    }

    private void initRhythm4() {
        List<RhythmCombination> fourCombinations = new ArrayList<>();
        fourCombinations.add(allRhythms.fourNoteEven::pos1234);

//		fourCombinations.add(fourNoteSexTuplet::pos1456);
//		fourCombinations.add(fourNoteSexTuplet::pos1234);
//        fourCombinations.add(fourNoteSexTuplet::pos1345);
//        fourCombinations.add(fourNoteSexTuplet::pos1346);
//        fourCombinations.add(fourNoteSexTuplet::pos1356);

        rhythmCombinationsMap.put(4, fourCombinations);
    }

    private void initRhythm5() {
        List<RhythmCombination> fiveCombinations = new ArrayList<>();
        CompositeRhythmCombination compositeRhythmCombination = new CompositeRhythmCombination();
        compositeRhythmCombination.addRhythmCombination(allRhythms.threeNoteSexTuplet::pos136);
        compositeRhythmCombination.addRhythmCombination(allRhythms.twoNoteEven::pos12);
        fiveCombinations.add(compositeRhythmCombination);
//		fiveCombinations.add(quintuplet::pos12345);
//		fiveCombinations.add(quintuplet::pos2345);
        rhythmCombinationsMap.put(5, fiveCombinations);
    }

    private void initRhythm6() {
		List<RhythmCombination> rhythmCombinations = new ArrayList<>();
        rhythmCombinationsMap.put(6, rhythmCombinations);
    }
}
