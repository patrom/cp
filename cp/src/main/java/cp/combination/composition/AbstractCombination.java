package cp.combination.composition;

import cp.combination.even.FourNoteEven;
import cp.combination.even.OneNoteEven;
import cp.combination.even.ThreeNoteEven;
import cp.combination.even.TwoNoteEven;
import cp.combination.uneven.*;
import org.springframework.beans.factory.annotation.Autowired;


public abstract class AbstractCombination{

	@Autowired
	protected OneNoteEven oneNoteEven;
	@Autowired
	protected TwoNoteEven twoNoteEven;
	@Autowired
	protected ThreeNoteEven threeNoteEven;
	@Autowired
	protected FourNoteEven fourNoteEven;
	
	@Autowired
	protected ThreeNoteTriplet threeNoteUneven;
	@Autowired
	protected TwoNoteTriplet twoNoteUneven;
	@Autowired
	protected OneNoteTriplet oneNoteUneven;
	@Autowired
	protected ThreeNoteSexTuplet threeNoteSexTuplet;
	@Autowired
	protected FourNoteSexTuplet fourNoteSexTuplet;
	@Autowired
	protected FiveNoteSexTuplet fiveNoteSexTuplet;
	@Autowired
	protected SixNoteSexTuplet sixNoteSexTuplet;
}
