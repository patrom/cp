package cp.combination;

import cp.combination.even.FourNoteEven;
import cp.combination.even.OneNoteEven;
import cp.combination.even.ThreeNoteEven;
import cp.combination.even.TwoNoteEven;
import cp.combination.uneven.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.ArrayList;
import java.util.List;

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
	private FiveNoteQuintuplet fiveNoteQuintuplet;//TODO investigate conversion to float???
	

	@Bean
	public List<RhythmCombination> defaultEvenCombinations(){
		List<RhythmCombination> rhythmCombinations = new ArrayList<>();
		rhythmCombinations.add(oneNoteEven::pos1);
//		rhythmCombinations.add(oneNoteEven::pos2);
//		rhythmCombinations.add(oneNoteEven::pos3);
		rhythmCombinations.add(oneNoteEven::pos4);
		
		rhythmCombinations.add(twoNoteEven::pos12);
		rhythmCombinations.add(twoNoteEven::pos13);
//		rhythmCombinations.add(twoNoteEven::pos14);
		rhythmCombinations.add(twoNoteEven::pos34);
//		rhythmCombinations.add(twoNoteEven::pos23);
//		rhythmCombinations.add(twoNoteEven::pos24);
		
		rhythmCombinations.add(threeNoteEven::pos123);
		rhythmCombinations.add(threeNoteEven::pos134);
		rhythmCombinations.add(threeNoteEven::pos124);
		rhythmCombinations.add(threeNoteEven::pos234);

		rhythmCombinations.add(fourNoteEven::pos1234);

		rhythmCombinations.add(threeNoteUneven::pos123);
		rhythmCombinations.add(twoNoteUneven::pos23);
		rhythmCombinations.add(twoNoteUneven::pos12);
		rhythmCombinations.add(twoNoteUneven::pos13);

		rhythmCombinations.add(fiveNoteQuintuplet::pos12345);
		return rhythmCombinations;
	}
	
	@Bean
	public List<RhythmCombination> defaultUnevenCombinations(){
		List<RhythmCombination> rhythmCombinations = new ArrayList<>();
//		rhythmCombinations.add(oneNoteEven::pos1);
//		rhythmCombinations.add(oneNoteEven::pos2);
//		rhythmCombinations.add(oneNoteEven::pos3);
//		rhythmCombinations.add(oneNoteEven::pos4);
//		
//		rhythmCombinations.add(twoNoteEven::pos12);
//		rhythmCombinations.add(twoNoteEven::pos13);
//		rhythmCombinations.add(twoNoteEven::pos14);
//		rhythmCombinations.add(twoNoteEven::pos34);
//		rhythmCombinations.add(twoNoteEven::pos23);
//		rhythmCombinations.add(twoNoteEven::pos24);
		
//		rhythmCombinations.add(threeNoteEven::pos123);
//		rhythmCombinations.add(threeNoteEven::pos134);
//		rhythmCombinations.add(threeNoteEven::pos124);
//		rhythmCombinations.add(threeNoteEven::pos234);
		
//		rhythmCombinations.add(fourNoteEven::pos1234);
		
		//3 divisions
		rhythmCombinations.add(oneNoteUneven::pos1);
//		rhythmCombinations.add(oneNoteUneven::pos2);
//		rhythmCombinations.add(oneNoteUneven::pos3);
		
		rhythmCombinations.add(twoNoteUneven::pos13);
		rhythmCombinations.add(twoNoteUneven::pos12);
		rhythmCombinations.add(twoNoteUneven::pos23);
		
		rhythmCombinations.add(threeNoteUneven::pos123);

		rhythmCombinations.add(threeNoteSexTuplet::pos145);
//		rhythmCombinations.add(threeNoteSexTuplet::pos136);
		rhythmCombinations.add(threeNoteSexTuplet::pos156);
//		
		rhythmCombinations.add(fourNoteSexTuplet::pos1456);
//		rhythmCombinations.add(fourNoteSexTuplet::pos1346);
//		rhythmCombinations.add(fourNoteSexTuplet::pos1356);
		
//		rhythmCombinations.add(fiveNoteSexTuplet::pos13456);
//		
//		rhythmCombinations.add(sixNoteSexTuplet::pos123456);
		return rhythmCombinations;
	}
	
	@Bean
	public List<RhythmCombination> unevenBeat0(){
		List<RhythmCombination> rhythmCombinations = new ArrayList<>();
//		rhythmCombinations.add(oneNoteEven::pos1);
//		rhythmCombinations.add(oneNoteEven::pos2);
//		rhythmCombinations.add(oneNoteEven::pos3);
//		rhythmCombinations.add(oneNoteEven::pos4);
//		
//		rhythmCombinations.add(twoNoteEven::pos12);
//		rhythmCombinations.add(twoNoteEven::pos13);
//		rhythmCombinations.add(twoNoteEven::pos14);
//		rhythmCombinations.add(twoNoteEven::pos34);
//		rhythmCombinations.add(twoNoteEven::pos23);
//		rhythmCombinations.add(twoNoteEven::pos24);
		
//		rhythmCombinations.add(threeNoteEven::pos123);
//		rhythmCombinations.add(threeNoteEven::pos134);
//		rhythmCombinations.add(threeNoteEven::pos124);
//		rhythmCombinations.add(threeNoteEven::pos234);
		
//		rhythmCombinations.add(fourNoteEven::pos1234);
		
		//3 divisions
		rhythmCombinations.add(oneNoteUneven::pos1);
//		rhythmCombinations.add(oneNoteUneven::pos2);
//		rhythmCombinations.add(oneNoteUneven::pos3);
		
		rhythmCombinations.add(twoNoteUneven::pos13);
//		rhythmCombinations.add(twoNoteUneven::pos12);
//		rhythmCombinations.add(twoNoteUneven::pos23);
		
		rhythmCombinations.add(threeNoteUneven::pos123);

//		rhythmCombinations.add(threeNoteSexTuplet::pos145);
//		rhythmCombinations.add(threeNoteSexTuplet::pos136);
//		rhythmCombinations.add(threeNoteSexTuplet::pos156);
//		
//		rhythmCombinations.add(fourNoteSexTuplet::pos1456);
//		rhythmCombinations.add(fourNoteSexTuplet::pos1346);
//		rhythmCombinations.add(fourNoteSexTuplet::pos1356);
//		
//		rhythmCombinations.add(fiveNoteSexTuplet::pos13456);
//		
//		rhythmCombinations.add(sixNoteSexTuplet::pos123456);
		return rhythmCombinations;
	}
	
	@Bean
	public List<RhythmCombination> longCombination(){
		List<RhythmCombination> rhythmCombinations = new ArrayList<>();
		rhythmCombinations.add(oneNoteEven::pos1);
		return rhythmCombinations;
	}
	
	@Bean
	public List<RhythmCombination> fixedUneven() {
		List<RhythmCombination> rhythmCombinations = new ArrayList<>();
		rhythmCombinations.add(oneNoteEven::pos1);
		// rhythmCombinations.add(oneNoteEven::pos2);
		// rhythmCombinations.add(oneNoteEven::pos3);
		// rhythmCombinations.add(oneNoteEven::pos4);
		//
		// rhythmCombinations.add(twoNoteEven::pos12);
		// rhythmCombinations.add(twoNoteEven::pos13);
		// rhythmCombinations.add(twoNoteEven::pos14);
		// rhythmCombinations.add(twoNoteEven::pos34);
		// rhythmCombinations.add(twoNoteEven::pos23);
		// rhythmCombinations.add(twoNoteEven::pos24);

		// rhythmCombinations.add(threeNoteEven::pos123);
		// rhythmCombinations.add(threeNoteEven::pos134);
		// rhythmCombinations.add(threeNoteEven::pos124);
		// rhythmCombinations.add(threeNoteEven::pos234);

		// rhythmCombinations.add(fourNoteEven::pos1234);
		//

		// rhythmCombinations.add(twoNoteUneven::pos12);
		rhythmCombinations.add(twoNoteUneven::pos13);
		// rhythmCombinations.add(twoNoteUneven::pos23);
		// rhythmCombinations.add(threeNoteUneven::pos123);
		return rhythmCombinations;
	}
	
	@Bean
	public List<RhythmCombination> fixedEven() {
		List<RhythmCombination> rhythmCombinations = new ArrayList<>();
		// rhythmCombinations.add(oneNoteEven::pos1);
		// rhythmCombinations.add(oneNoteEven::pos2);
		// rhythmCombinations.add(oneNoteEven::pos3);
		// rhythmCombinations.add(oneNoteEven::pos4);
		//
//		rhythmCombinations.add(twoNoteEven::pos12);
		rhythmCombinations.add(twoNoteEven::pos13);
		// rhythmCombinations.add(twoNoteEven::pos14);
		// rhythmCombinations.add(twoNoteEven::pos34);
		// rhythmCombinations.add(twoNoteEven::pos23);
		// rhythmCombinations.add(twoNoteEven::pos24);

		// rhythmCombinations.add(threeNoteEven::pos123);
		// rhythmCombinations.add(threeNoteEven::pos134);
		// rhythmCombinations.add(threeNoteEven::pos124);
		// rhythmCombinations.add(threeNoteEven::pos234);

		// rhythmCombinations.add(fourNoteEven::pos1234);
		//
		// rhythmCombinations.add(threeNoteUneven::pos123);
		return rhythmCombinations;
	}
}
