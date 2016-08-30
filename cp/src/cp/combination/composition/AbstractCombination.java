package cp.combination.composition;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;

import cp.combination.Combination;
import cp.combination.even.FourNoteEven;
import cp.combination.even.OneNoteEven;
import cp.combination.even.ThreeNoteEven;
import cp.combination.even.TwoNoteEven;
import cp.combination.uneven.FiveNoteSexTuplet;
import cp.combination.uneven.FourNoteSexTuplet;
import cp.combination.uneven.OneNoteTriplet;
import cp.combination.uneven.SixNoteSexTuplet;
import cp.combination.uneven.ThreeNoteSexTuplet;
import cp.combination.uneven.ThreeNoteTriplet;
import cp.combination.uneven.TwoNoteTriplet;


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
