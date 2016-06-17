package cp.combination.composition.even;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;

import cp.combination.RhythmCombination;
import cp.combination.composition.AbstractCombination;

@Component(value="FixedEven")
public class FixedEven extends AbstractCombination{
	
	public List<RhythmCombination> getCombination() {
		List<RhythmCombination> rhythmCombinations = new ArrayList<>();
		// rhythmCombinations.add(oneNoteEven::pos1);
		// rhythmCombinations.add(oneNoteEven::pos2);
		// rhythmCombinations.add(oneNoteEven::pos3);
		// rhythmCombinations.add(oneNoteEven::pos4);
		//
		rhythmCombinations.add(twoNoteEven::pos12);
//		rhythmCombinations.add(twoNoteEven::pos13);
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
