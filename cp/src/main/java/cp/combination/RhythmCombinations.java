package cp.combination;

import cp.combination.even.FourNoteEven;
import cp.combination.even.OneNoteEven;
import cp.combination.even.ThreeNoteEven;
import cp.combination.even.TwoNoteEven;
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
    protected FiveNoteQuintuplet fiveNoteQuintuplet;
}
