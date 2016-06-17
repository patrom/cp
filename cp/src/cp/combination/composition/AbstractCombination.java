package cp.combination.composition;

import org.springframework.beans.factory.annotation.Autowired;

import cp.combination.Combination;
import cp.combination.even.FourNoteEven;
import cp.combination.even.OneNoteEven;
import cp.combination.even.ThreeNoteEven;
import cp.combination.even.TwoNoteEven;
import cp.combination.uneven.FiveNoteSexTuplet;
import cp.combination.uneven.FourNoteSexTuplet;
import cp.combination.uneven.OneNoteUneven;
import cp.combination.uneven.SixNoteSexTuplet;
import cp.combination.uneven.ThreeNoteSexTuplet;
import cp.combination.uneven.ThreeNoteUneven;
import cp.combination.uneven.TwoNoteUneven;

public abstract class AbstractCombination implements Combination{

	@Autowired
	protected OneNoteEven oneNoteEven;
	@Autowired
	protected TwoNoteEven twoNoteEven;
	@Autowired
	protected ThreeNoteEven threeNoteEven;
	@Autowired
	protected FourNoteEven fourNoteEven;
	
	@Autowired
	protected ThreeNoteUneven threeNoteUneven;
	@Autowired
	protected TwoNoteUneven twoNoteUneven;
	@Autowired
	protected OneNoteUneven oneNoteUneven;
	@Autowired
	protected ThreeNoteSexTuplet threeNoteSexTuplet;
	@Autowired
	protected FourNoteSexTuplet fourNoteSexTuplet;
	@Autowired
	protected FiveNoteSexTuplet fiveNoteSexTuplet;
	@Autowired
	protected SixNoteSexTuplet sixNoteSexTuplet;
}
