package cp.combination;

import cp.combination.balance.BalancedPattern;
import cp.combination.even.*;
import cp.combination.uneven.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class RhythmCombinations {

    @Autowired
    public OneNoteEven oneNoteEven;
    @Autowired
    public TwoNoteEven twoNoteEven;
    @Autowired
    public ThreeNoteEven threeNoteEven;
    @Autowired
    public FourNoteEven fourNoteEven;

    @Autowired
    public ThreeNoteTriplet threeNoteUneven;
    @Autowired
    public TwoNoteTriplet twoNoteUneven;
    @Autowired
    public OneNoteTriplet oneNoteUneven;
    @Autowired
    public ThreeNoteSexTuplet threeNoteSexTuplet;
    @Autowired
    public FourNoteSexTuplet fourNoteSexTuplet;
    @Autowired
    public FiveNoteSexTuplet fiveNoteSexTuplet;
    @Autowired
    public SixNoteSexTuplet sixNoteSexTuplet;

    @Autowired
    public Quintuplet quintuplet;

    @Autowired
    public SeptTuplet septTuplet;


    @Autowired
    public CombiNoteEven combiNoteEven;
    @Autowired
    public BalancedPattern balancedPattern;
    @Autowired
    public RandomCombination randomCombination;

}
