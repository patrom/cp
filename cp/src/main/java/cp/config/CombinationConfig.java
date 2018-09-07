package cp.config;

import cp.combination.RhythmCombination;
import cp.combination.even.*;
import cp.combination.uneven.*;
import cp.model.note.Note;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static cp.model.note.NoteBuilder.note;

@Configuration
public class CombinationConfig {

	@Autowired
	private OneNoteEven oneNoteEven;
	@Autowired
	private TwoNoteEven twoNoteEven;
	@Autowired
	private ThreeNoteEven threeNoteEven;
	@Autowired
	private FourNoteEven fourNoteEven;
	@Autowired
	private CombiNoteEven combiNoteEven;
	
	@Autowired
	private ThreeNoteTriplet threeNoteUneven;
	@Autowired
	private TwoNoteTriplet twoNoteUneven;
	@Autowired
	private OneNoteTriplet oneNoteUneven;
	@Autowired
	private ThreeNoteSexTuplet threeNoteSexTuplet;
	@Autowired
	private FourNoteSexTuplet fourNoteSexTuplet;
	@Autowired
	private FiveNoteSexTuplet fiveNoteSexTuplet;
	@Autowired
	private SixNoteSexTuplet sixNoteSexTuplet;
	@Autowired
	private CombiNoteUneven combiNoteUneven;
	
	@Autowired
	private Quintuplet quintuplet;//TODO investigate conversion to float???
	@Autowired
	private SeptTuplet septTuplet;

	public List<Note> rest(int beat) {
		List<Note> notes = new ArrayList<>();
		notes.add(note().pos(0).rest().len(beat).build());
		return notes;
	}

	@Bean
	public Map<Integer, List<RhythmCombination>> defaultEvenCombinations(){
		Map<Integer, List<RhythmCombination>> map = new HashMap<>();
		//rest
		List<RhythmCombination> zeroCombinations = new ArrayList<>();
		zeroCombinations.add(oneNoteEven::rest);
//		zeroCombinations.add(oneNoteEven::rest);
//		map.put(0, zeroCombinations);//TODO not possible with mutation (articulation, dynamic,...)

		List<RhythmCombination> oneCombinations = new ArrayList<>();
		oneCombinations.add(oneNoteEven::pos1);
//		oneCombinations.add(oneNoteEven::pos2);
//		oneCombinations.add(oneNoteEven::pos3);
//		oneCombinations.add(oneNoteEven::pos4);
		map.put(1, oneCombinations);

		List<RhythmCombination> twoCombinations = new ArrayList<>();
//		twoCombinations.add(twoNoteEven::pos12);
//		twoCombinations.add(twoNoteEven::pos13);
//		twoCombinations.add(twoNoteEven::pos14);
//		twoCombinations.add(twoNoteEven::pos34);
//		twoCombinations.add(twoNoteEven::pos23);
//		twoCombinations.add(twoNoteEven::pos24);

//        twoCombinations.add(twoNoteUneven::pos23);
//		twoCombinations.add(twoNoteUneven::pos12);
//		twoCombinations.add(twoNoteUneven::pos13);
//		map.put(2, twoCombinations);

        List<RhythmCombination> threeCombinations = new ArrayList<>();
		threeCombinations.add(threeNoteEven::pos123);
		threeCombinations.add(threeNoteEven::pos134);
		threeCombinations.add(threeNoteEven::pos124);
		threeCombinations.add(threeNoteEven::pos234);

        threeCombinations.add(threeNoteUneven::pos123);

//		threeCombinations.add(threeNoteSexTuplet::pos145);
//		threeCombinations.add(threeNoteSexTuplet::pos136);
//		threeCombinations.add(threeNoteSexTuplet::pos156);
//		threeCombinations.add(threeNoteSexTuplet::pos123);tex
		map.put(3, threeCombinations);

        List<RhythmCombination> fourCombinations = new ArrayList<>();
		fourCombinations.add(fourNoteEven::pos1234);

//		fourCombinations.add(fourNoteSexTuplet::pos1456);
//		fourCombinations.add(fourNoteSexTuplet::pos1234);
//        fourCombinations.add(fourNoteSexTuplet::pos1345);
//        fourCombinations.add(fourNoteSexTuplet::pos1346);
//        fourCombinations.add(fourNoteSexTuplet::pos1356);
		map.put(4, fourCombinations);

        List<RhythmCombination> fiveCombinations = new ArrayList<>();
//		fiveCombinations.add(quintuplet::pos12345);
//		fiveCombinations.add(quintuplet::pos2345);
//		map.put(5, fiveCombinations);

//		List<RhythmCombination> sevenCombinations = new ArrayList<>();
//		sevenCombinations.add(septTuplet::pos1234567);
//		sevenCombinations.add(septTuplet::pos234567);
//		map.put(7, sevenCombinations);

		return map;
	}
	
	@Bean
	public Map<Integer, List<RhythmCombination>> defaultUnevenCombinations(){
		Map<Integer, List<RhythmCombination>> map = new HashMap<>();
		List<RhythmCombination> zeroCombinations = new ArrayList<>();
//		zeroCombinations.add(oneNoteEven::rest);
//		map.put(0, zeroCombinations);//TODO not possible with mutation (articulation, dynamic,...)

//        List<RhythmCombination> oneCombinations = new ArrayList<>();
//        oneCombinations.add(oneNoteEven::pos1);
////		oneCombinations.add(oneNoteEven::pos2);
////		oneCombinations.add(oneNoteEven::pos3);
////		oneCombinations.add(oneNoteEven::pos4);
//		map.put(1, oneCombinations);
//
//        List<RhythmCombination> twoCombinations = new ArrayList<>();
//		twoCombinations.add(twoNoteEven::pos12);
//		twoCombinations.add(twoNoteEven::pos13);
//		twoCombinations.add(twoNoteEven::pos14);
//		twoCombinations.add(twoNoteEven::pos34);
//		twoCombinations.add(twoNoteEven::pos23);
//		twoCombinations.add(twoNoteEven::pos24);
//        map.put(2, twoCombinations);

//        List<RhythmCombination> threeCombinations = new ArrayList<>();
//		threeCombinations.add(threeNoteEven::pos123);
//		threeCombinations.add(threeNoteEven::pos134);
//		threeCombinations.add(threeNoteEven::pos124);
//		threeCombinations.add(threeNoteEven::pos234);
//        map.put(3, threeCombinations);

//        List<RhythmCombination> fourCombinations = new ArrayList<>();
//		fourCombinations.add(fourNoteEven::pos1234);
//        map.put(4, fourCombinations);
		
		//3 divisions
        List<RhythmCombination> oneUnevenCombinations = new ArrayList<>();
//		oneUnevenCombinations.add(oneNoteUneven::pos1);
		oneUnevenCombinations.add(oneNoteUneven::pos2);
		oneUnevenCombinations.add(oneNoteUneven::pos3);
//        map.put(1, oneUnevenCombinations);

        List<RhythmCombination> twoUnevenombinations = new ArrayList<>();
		twoUnevenombinations.add(twoNoteUneven::pos13);
        twoUnevenombinations.add(twoNoteUneven::pos12);
		twoUnevenombinations.add(twoNoteUneven::pos23);
		map.put(2, twoUnevenombinations);

        List<RhythmCombination> threeUnevenCombinations = new ArrayList<>();
		threeUnevenCombinations.add(threeNoteUneven::pos123);

		threeUnevenCombinations.add(threeNoteSexTuplet::pos145);
		threeUnevenCombinations.add(threeNoteSexTuplet::pos136);
		threeUnevenCombinations.add(threeNoteSexTuplet::pos156);
//		threeUnevenCombinations.add(threeNoteSexTuplet::pos123);
        threeUnevenCombinations.add(combiNoteUneven::posXposXX);
        map.put(3, threeUnevenCombinations);

        List<RhythmCombination> fourUnevenCombinations = new ArrayList<>();
//		fourUnevenCombinations.add(fourNoteSexTuplet::pos1456);
//		fourUnevenCombinations.add(fourNoteSexTuplet::pos1345);
//		fourUnevenCombinations.add(fourNoteSexTuplet::pos1346);
//		fourUnevenCombinations.add(fourNoteSexTuplet::pos1356);

//        fourUnevenCombinations.add(combiNoteUneven::pos23pos12);
//		fourUnevenCombinations.add(combiNoteUneven::pos13pos12);
//		fourUnevenCombinations.add(combiNoteUneven::pos12pos12);
		fourUnevenCombinations.add(combiNoteUneven::posXXposXX);
		fourUnevenCombinations.add(combiNoteUneven::posXposXXX);
        map.put(4, fourUnevenCombinations);

        List<RhythmCombination> fiveUnevenCombinations = new ArrayList<>();
//		fiveUnevenCombinations.add(fiveNoteSexTuplet::pos13456);
        fiveUnevenCombinations.add(combiNoteUneven::posXXposXXX);
        fiveUnevenCombinations.add(combiNoteUneven::posXXXposXX);
        map.put(5, fiveUnevenCombinations);
//
        List<RhythmCombination> sixUnevenCombinations = new ArrayList<>();
//		sixUnevenCombinations.add(sixNoteSexTuplet::pos123456);
        sixUnevenCombinations.add(combiNoteUneven::posXXXposXXX);
        sixUnevenCombinations.add(combiNoteUneven::posXXposXXXX);
//        map.put(6, sixUnevenCombinations);
		return map;
	}
	
	@Bean
	public Map<Integer, List<RhythmCombination>> homophonicEven(){
        Map<Integer, List<RhythmCombination>> map = new HashMap<>();
        //rest
        List<RhythmCombination> zeroCombinations = new ArrayList<>();
		zeroCombinations.add(oneNoteEven::rest);
//		map.put(0, zeroCombinations);//TODO not possible with mutation (articulation, dynamic,...)

        List<RhythmCombination> oneCombinations = new ArrayList<>();
//        oneCombinations.add(oneNoteEven::pos1);
//		oneCombinations.add(oneNoteEven::pos2);
//		oneCombinations.add(oneNoteEven::pos3);
//		oneCombinations.add(oneNoteEven::pos4);
//        map.put(1, oneCombinations);

        List<RhythmCombination> twoCombinations = new ArrayList<>();
//		twoCombinations.add(twoNoteEven::pos12);
        twoCombinations.add(twoNoteEven::pos13);
		twoCombinations.add(twoNoteEven::pos14);
//		twoCombinations.add(twoNoteEven::pos34);
//		twoCombinations.add(twoNoteEven::pos23);
//		twoCombinations.add(twoNoteEven::pos24);

		//		twoTripletCombinations.add(twoNoteUneven::pos23);
//		twoTripletCombinations.add(twoNoteUneven::pos12);
//		twoTripletCombinations.add(twoNoteUneven::pos13);
        map.put(2, twoCombinations);

        List<RhythmCombination> threeCombinations = new ArrayList<>();
//		threeCombinations.add(threeNoteEven::pos123);
		threeCombinations.add(threeNoteEven::pos134);
//		threeCombinations.add(threeNoteEven::pos124);
//		threeCombinations.add(threeNoteEven::pos234);

		threeCombinations.add(threeNoteUneven::pos123);
		map.put(3, threeCombinations);

        List<RhythmCombination> fourCombinations = new ArrayList<>();
		fourCombinations.add(fourNoteEven::pos1234);
//		map.put(4, fourCombinations);


//        List<RhythmCombination> fiveCombinations = new ArrayList<>();
//		fiveCombinations.add(quintuplet::pos12345);
//		map.put(5, fiveCombinations);
        return map;
    }

	@Bean
	public Map<Integer, List<RhythmCombination>> homophonicUneven(){
        Map<Integer, List<RhythmCombination>> map = new HashMap<>();
//		List<RhythmCombination> zeroCombinations = new ArrayList<>();
//		zeroCombinations.add(oneNoteEven::rest);
//		map.put(0, zeroCombinations);//TODO not possible with mutation (articulation, dynamic,...)

        List<RhythmCombination> oneCombinations = new ArrayList<>();
//        oneCombinations.add(oneNoteEven::pos1);
//		oneCombinations.add(oneNoteEven::pos2);
//		oneCombinations.add(oneNoteEven::pos3);
//		oneCombinations.add(oneNoteEven::pos4);
//        map.put(1, oneCombinations);
//
//        List<RhythmCombination> twoCombinations = new ArrayList<>();
//		twoCombinations.add(twoNoteEven::pos12);
//		twoCombinations.add(twoNoteEven::pos13);
//		twoCombinations.add(twoNoteEven::pos14);
//		twoCombinations.add(twoNoteEven::pos34);
//		twoCombinations.add(twoNoteEven::pos23);
//		twoCombinations.add(twoNoteEven::pos24);
//        map.put(2, twoCombinations);

//        List<RhythmCombination> threeCombinations = new ArrayList<>();
//		threeCombinations.add(threeNoteEven::pos123);
//		threeCombinations.add(threeNoteEven::pos134);
//		threeCombinations.add(threeNoteEven::pos124);
//		threeCombinations.add(threeNoteEven::pos234);
//        map.put(3, threeCombinations);


        List<RhythmCombination> fourCombinations = new ArrayList<>();
//		fourCombinations.add(combiNoteUneven::pos23pos12);
//		fourCombinations.add(combiNoteUneven::pos13pos12);
//		fourCombinations.add(combiNoteUneven::pos12pos12);
//		fourCombinations.add(combiNoteUneven::pos23pos12);
//		fourCombinations.add(fourNoteEven::pos1234);
//        map.put(4, fourCombinations);

        //3 divisions
//        List<RhythmCombination> oneUnevenCombinations = new ArrayList<>();
//		oneUnevenCombinations.add(oneNoteUneven::pos1);
//		oneUnevenCombinations.add(oneNoteUneven::pos2);
//		oneUnevenCombinations.add(oneNoteUneven::pos3);
//        map.put(1, oneUnevenCombinations);

        List<RhythmCombination> twoUnevenombinations = new ArrayList<>();
//		twoUnevenombinations.add(twoNoteUneven::pos13);
//        twoUnevenombinations.add(twoNoteUneven::pos12);
		twoUnevenombinations.add(twoNoteUneven::pos23);
        map.put(2, twoUnevenombinations);

        List<RhythmCombination> threeUnevenCombinations = new ArrayList<>();
		threeUnevenCombinations.add(threeNoteUneven::pos123);

		threeUnevenCombinations.add(threeNoteSexTuplet::pos145);
//		threeUnevenCombinations.add(threeNoteSexTuplet::pos136);
//		threeUnevenCombinations.add(threeNoteSexTuplet::pos156);
        map.put(3, threeUnevenCombinations);
////
//        List<RhythmCombination> fourUnevenCombinations = new ArrayList<>();
//		fourUnevenCombinations.add(fourNoteSexTuplet::pos1456);
//		fourUnevenCombinations.add(fourNoteSexTuplet::pos1345);
//		fourUnevenCombinations.add(fourNoteSexTuplet::pos1356);
//        map.put(4, fourUnevenCombinations);

//        List<RhythmCombination> fiveUnevenCombinations = new ArrayList<>();
//		fiveUnevenCombinations.add(fiveNoteSexTuplet::pos13456);
//        map.put(5, fiveUnevenCombinations);
//
//        List<RhythmCombination> sixUnevenCombinations = new ArrayList<>();
//		sixUnevenCombinations.add(sixNoteSexTuplet::pos123456);
//        map.put(6, sixUnevenCombinations);


        return map;
    }
	
	@Bean
	public Map<Integer, List<RhythmCombination>> fixedUneven() {
		return getUnevenBeatGroups();
	}
	
	@Bean
	public Map<Integer, List<RhythmCombination>> fixedEven() {
		return getEvenBeatGroups();
	}

	private Map<Integer, List<RhythmCombination>> getEvenBeatGroups(){
		Map<Integer, List<RhythmCombination>> map = new HashMap<>();
		List<RhythmCombination> beatGroups = new ArrayList<>();
		beatGroups.add(fourNoteEven::pos1234);
		beatGroups.add(combiNoteEven::pos23pos12);
		map.put(2, beatGroups);
		return map;
	}

	private Map<Integer, List<RhythmCombination>> getUnevenBeatGroups(){
		Map<Integer, List<RhythmCombination>> map = new HashMap<>();
		List<RhythmCombination> beatGroups = new ArrayList<>();
		beatGroups.add(fourNoteSexTuplet::pos1456);
		beatGroups.add(fourNoteSexTuplet::pos1356);
		map.put(4, beatGroups);
		return map;
	}

}
