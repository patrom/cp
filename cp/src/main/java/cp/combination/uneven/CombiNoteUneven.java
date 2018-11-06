package cp.combination.uneven;

import cp.combination.RhythmCombination;
import cp.combination.RhythmCombinations;
import cp.model.note.Note;
import cp.util.RandomUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.Collections;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Component
public class CombiNoteUneven {

    @Autowired
    public RhythmCombinations rhythmCombinations;

    private List<RhythmCombination> twoNoteCombinations1;
    private List<RhythmCombination> twoNoteCombinations2;

    private List<RhythmCombination> threeNoteCombinations1;
    private List<RhythmCombination> threeNoteCombinations2;

    private List<RhythmCombination> fourNoteCombinations1;
    private List<RhythmCombination> fourNoteCombinations2;

    @PostConstruct
    public void init() {
        RhythmCombination pos13 = rhythmCombinations.twoNoteEven::pos13;
        twoNoteCombinations1 = Stream.of(pos13, rhythmCombinations.twoNoteEven::pos23, rhythmCombinations.twoNoteEven::pos12).collect(Collectors.toList());
        twoNoteCombinations2 = Stream.of(pos13, rhythmCombinations.twoNoteUneven::pos13, rhythmCombinations.twoNoteUneven::pos12, rhythmCombinations.twoNoteEven::pos23, rhythmCombinations.twoNoteEven::pos12).collect(Collectors.toList());

        RhythmCombination pos123 = rhythmCombinations.threeNoteEven::pos123;
        threeNoteCombinations1 = Stream.of(pos123, rhythmCombinations.threeNoteUneven::pos123, rhythmCombinations.threeNoteEven::pos134, rhythmCombinations.threeNoteEven::pos124, rhythmCombinations.threeNoteEven::pos234).collect(Collectors.toList());
        threeNoteCombinations2 = Stream.of(pos123, rhythmCombinations.threeNoteUneven::pos123,rhythmCombinations.threeNoteEven::pos134, rhythmCombinations.threeNoteEven::pos124, rhythmCombinations.threeNoteEven::pos234).collect(Collectors.toList());

        RhythmCombination pos1234 = rhythmCombinations.fourNoteEven::pos1234;
        fourNoteCombinations2 = Stream.of(pos1234).collect(Collectors.toList());
    }


    public List<Note> combiHalfQuarter(RhythmCombination combi1, RhythmCombination combi2, int beat) {
        int beat3 = beat / 3;
        List<Note> notes = combi1.getNotes(beat3 * 2);
        List<Note> notes1 = combi2.getNotes(beat3);
        notes1.forEach(note -> note.setPosition(note.getPosition() + beat3 * 2));
        notes.addAll(notes1);
        return notes;
    }

    public List<Note> combiHalfQuarter(List<RhythmCombination> combinations1, List<RhythmCombination> combinations2, int beat) {
        int beat3 = beat / 3;
        List<Note> notes = RandomUtil.getRandomFromList(combinations1).getNotes(beat3 * 2);
        List<Note> notes1 = RandomUtil.getRandomFromList(combinations2).getNotes(beat3);
        notes1.forEach(note -> note.setPosition(note.getPosition() + beat3 * 2));
        notes.addAll(notes1);
        return notes;
    }

    protected List<Note> combiQuarterHalf(RhythmCombination combi1, RhythmCombination combi2, int beat) {
        int beat3 = beat / 3;
        List<Note> notes = combi1.getNotes(beat3);
        List<Note> notes1 = combi2.getNotes(beat3 * 2);
        notes1.forEach(note -> note.setPosition(note.getPosition() + beat3 * 2));
        notes.addAll(notes1);
        return notes;
    }


    public List<Note> apply(Function<Integer, List<Note>> combi1, Function<Integer, List<Note>> combi2, int beat) {
        int beat3 = beat / 3;
        List<Note> notes = combi1.apply(beat3 * 2);
        List<Note> notes1 = combi2.apply(beat3);
        notes1.forEach(note -> note.setPosition(note.getPosition() + beat3 * 2));
        notes.addAll(notes1);
        return notes;
    }

    public List<Note> pos23pos12(int beat) {
        return combiHalfQuarter(rhythmCombinations.twoNoteEven::pos23, rhythmCombinations.twoNoteEven::pos12, beat);
    }

    public List<Note> pos12pos12(int beat) {
        return combiHalfQuarter(rhythmCombinations.twoNoteEven::pos12, rhythmCombinations.twoNoteEven::pos12, beat);
    }

    public List<Note> pos13pos12(int beat) {
        return combiHalfQuarter(rhythmCombinations.twoNoteEven::pos13, rhythmCombinations.twoNoteEven::pos12, beat);
    }

    public List<Note> posXposXX(int beat) {
        return combiHalfQuarter(Collections.singletonList(rhythmCombinations.oneNoteEven::pos1), twoNoteCombinations2, beat);
    }

    public List<Note> posXposXXX(int beat) {
        return combiHalfQuarter(Collections.singletonList(rhythmCombinations.oneNoteEven::pos1),threeNoteCombinations2, beat);
    }

    public List<Note> posXXposXX(int beat) {
        return combiHalfQuarter(twoNoteCombinations1, twoNoteCombinations2, beat);
    }

    public List<Note> posXXXposXXX(int beat) {
        return combiHalfQuarter(threeNoteCombinations1, threeNoteCombinations2, beat);
    }

    public List<Note> posXXXposXX(int beat) {
        return combiHalfQuarter(threeNoteCombinations1, twoNoteCombinations2, beat);
    }

    public List<Note> posXXposXXX(int beat) {
        return combiHalfQuarter(twoNoteCombinations1, threeNoteCombinations2, beat);
    }

    public List<Note> posXXposXXXX(int beat) {
        return combiHalfQuarter(twoNoteCombinations1, fourNoteCombinations2, beat);
    }

}
