package cp.combination;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import cp.combination.even.FourNoteEven;
import cp.combination.even.OneNoteEven;
import cp.combination.even.ThreeNoteEven;
import cp.combination.even.TwoNoteEven;
import cp.combination.uneven.ThreeNoteUneven;

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
	private ThreeNoteUneven threeNoteUneven;
	
	@Bean(name="combinationsEvenBeat")
	public Map<Integer, List<RhythmCombination>> combinationsEvenBeat() {
		Map<Integer, List<RhythmCombination>> map = new TreeMap<>();
		map.put(0, defaultCombinations());
		return map;
	}
	
	@Bean(name="combinationsUnevenBeat")
	public Map<Integer, List<RhythmCombination>> combinationsUnevenBeat() {
		Map<Integer, List<RhythmCombination>> map = new TreeMap<>();
		map.put(0, CombinationsUnevenBeat());
		map.put(1, CombinationsUnevenBeat());
		return map;
	}
	
	@Bean
	public List<RhythmCombination> defaultCombinations(){
		List<RhythmCombination> rhythmCombinations = new ArrayList<>();
		rhythmCombinations.add(oneNoteEven::pos1);
//		rhythmCombinations.add(oneNoteEven::pos2);
//		rhythmCombinations.add(oneNoteEven::pos3);
//		rhythmCombinations.add(oneNoteEven::pos4);
//		
//		rhythmCombinations.add(twoNoteEven::pos12);
		rhythmCombinations.add(twoNoteEven::pos13);
//		rhythmCombinations.add(twoNoteEven::pos14);
//		rhythmCombinations.add(twoNoteEven::pos34);
//		rhythmCombinations.add(twoNoteEven::pos23);
//		rhythmCombinations.add(twoNoteEven::pos24);
		
//		rhythmCombinations.add(threeNoteEven::pos123);
//		rhythmCombinations.add(threeNoteEven::pos134);
//		rhythmCombinations.add(threeNoteEven::pos124);
//		rhythmCombinations.add(threeNoteEven::pos234);
		
//		rhythmCombinations.add(fourNoteEven::pos1234);
		
//		rhythmCombinations.add(threeNoteUneven::pos123);
		return rhythmCombinations;
	}
	
	@Bean
	public List<RhythmCombination> CombinationsUnevenBeat(){
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
		
		rhythmCombinations.add(threeNoteUneven::pos123);
		return rhythmCombinations;
	}
}
