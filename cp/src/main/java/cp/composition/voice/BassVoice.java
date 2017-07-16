package cp.composition.voice;

import cp.combination.RhythmCombination;
import cp.model.note.Dynamic;
import cp.out.instrument.Technical;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Stream;

import static java.util.stream.Collectors.toList;

/**
 * Created by prombouts on 25/11/2016.
 */
@Component
public class BassVoice extends Voice {

    @PostConstruct
    public void init(){
        setTimeconfig();
        timeConfig = timeDouble44;

        evenRhythmCombinationsPerNoteSize = getBassVoice();
        unevenRhythmCombinationsPerNoteSize = homophonicUneven;

        dynamic = Dynamic.MP;
        dynamics = Stream.of(Dynamic.MF, Dynamic.MP).collect(toList());
        pitchClassGenerators.add(repeatingPitchClasses::updatePitchClasses);
        pitchClassGenerators.add(randomPitchClasses::randomPitchClasses);
        pitchClassGenerators.add(passingPitchClasses::updatePitchClasses);

        technical = Technical.LEGATO;
    }

    private Map<Integer, List<RhythmCombination>> getBassVoice(){
        Map<Integer, List<RhythmCombination>> map = new HashMap<>();
        //rest
//        List<RhythmCombination> zeroCombinations = new ArrayList<>();
//        zeroCombinations.add(oneNoteEven::rest);
//        map.put(0, zeroCombinations);

        List<RhythmCombination> oneCombinations = new ArrayList<>();
        oneCombinations.add(oneNoteEven::pos1);
//		oneCombinations.add(oneNoteEven::pos2);
//        oneCombinations.add(oneNoteEven::pos3);
//		oneCombinations.add(oneNoteEven::pos4);
        map.put(1, oneCombinations);

//        List<RhythmCombination> twoCombinations = new ArrayList<>();
////		twoCombinations.add(twoNoteEven::pos12);
//        twoCombinations.add(twoNoteEven::pos13);
//        twoCombinations.add(twoNoteEven::pos14);
////		twoCombinations.add(twoNoteEven::pos34);
//        //twoCombinations.add(twoNoteEven::pos23);
//        //twoCombinations.add(twoNoteEven::pos24);
//        map.put(2, twoCombinations);
//
//        List<RhythmCombination> threeCombinations = new ArrayList<>();
//        threeCombinations.add(threeNoteEven::pos123);
//        threeCombinations.add(threeNoteEven::pos134);
////		threeCombinations.add(threeNoteEven::pos124);
//        threeCombinations.add(threeNoteEven::pos234);
//        map.put(3, threeCombinations);
//
//        List<RhythmCombination> fourCombinations = new ArrayList<>();
//        fourCombinations.add(fourNoteEven::pos1234);
//        map.put(4, fourCombinations);


//		threeCombinations.add(threeNoteUneven::pos123);
////		map.put(3, threeCombinations);
//
//		twoCombinations.add(twoNoteUneven::pos23);
//		twoCombinations.add(twoNoteUneven::pos12);
//		twoCombinations.add(twoNoteUneven::pos13);
        //		map.put(2, twoCombinations);

//        List<RhythmCombination> fiveCombinations = new ArrayList<>();
//		fiveCombinations.add(fiveNoteQuintuplet::pos12345);
//		map.put(5, fiveCombinations);
        return map;
    }


}
