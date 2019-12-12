package cp.combination.even;

import cp.combination.RhythmCombination;
import cp.combination.RhythmCombinations;
import cp.model.note.Note;
import cp.model.rhythm.DurationConstants;
import cp.util.RandomUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Component
public class CombiNoteEven {

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

    private List<Note> combi(RhythmCombination combi1, RhythmCombination comvi2, int beat, int pulse) {
        List<Note> notes = combi1.getNotes(beat, pulse);
        List<Note> notes1 = comvi2.getNotes(beat, pulse);
        notes1.forEach(note -> note.setPosition(note.getPosition() + beat));
        notes.addAll(notes1);
        return notes;
    }

    public List<Note> pos23pos12(int beat, int pulse) {
        return combi(rhythmCombinations.twoNoteEven::pos23, rhythmCombinations.twoNoteEven::pos12, 30 * 2, pulse);
    }

    public List<Note> quintupletpos2345pos1(int beat, int pulse) {
        int beat2 = beat / 4;
        List<Note> notes = rhythmCombinations.quintuplet.pos2345(beat2, pulse);
        List<Note> notes1 = rhythmCombinations.oneNoteEven.pos1(beat2 * 3, pulse);
        notes1.forEach(note -> note.setPosition(note.getPosition() + beat2));
        notes.addAll(notes1);
        return notes;
    }

    public List<Note> quintupletpos12345pos1(int beat, int pulse) {
        int beat2 = beat / 4;
        List<Note> notes = rhythmCombinations.quintuplet.pos12345(beat2, pulse);
        List<Note> notes1 = rhythmCombinations.oneNoteEven.pos1(beat2 * 3, pulse);
        notes1.forEach(note -> note.setPosition(note.getPosition() + beat2));
        notes.addAll(notes1);
        return notes;
    }

    public List<Note> pos1quintupletpos12345(int beat, int pulse) {
        int beat2 = beat / 4;
        List<Note> notes = rhythmCombinations.oneNoteEven.pos1(beat2 * 3, pulse);
        List<Note> notes1 = rhythmCombinations.quintuplet.pos12345(beat2, pulse);
        notes1.forEach(note -> note.setPosition(note.getPosition() + beat2));
        notes.addAll(notes1);
        return notes;
    }

    public List<Note> septTupletpos234567pos1(int beat, int pulse) {
        int beat2 = beat / 4;
        List<Note> notes = rhythmCombinations.septTuplet.pos234567(beat2, pulse);
        List<Note> notes1 = rhythmCombinations.oneNoteEven.pos1(beat2, pulse);
        notes1.forEach(note -> note.setPosition(note.getPosition() + beat2));
        notes.addAll(notes1);
        return notes;
    }

    public List<Note> septTupletpos1234567pos1(int beat, int pulse) {
        int beat2 = beat / 4;
        List<Note> notes = rhythmCombinations.septTuplet.pos1234567(beat2, pulse);
        List<Note> notes1 = rhythmCombinations.oneNoteEven.pos1(beat2 * 3, pulse);
        notes1.forEach(note -> note.setPosition(note.getPosition() + beat2));
        notes.addAll(notes1);
        return notes;
    }

    public List<Note> pos1eptTupletpos1234567(int beat, int pulse) {
        int beat2 = beat / 4;
        List<Note> notes = rhythmCombinations.oneNoteEven.pos1(beat2 * 3, pulse);
        List<Note> notes1 = rhythmCombinations.septTuplet.pos1234567(beat2, pulse);
        notes1.forEach(note -> note.setPosition(note.getPosition() + beat2));
        notes.addAll(notes1);
        return notes;
    }

    public List<Note> combiHalf(RhythmCombination combi1, RhythmCombination combi2, int beat, int pulse) {
        int beat2 = beat / 2;
        List<Note> notes = combi1.getNotes(beat2, pulse);
        List<Note> notes1 = combi2.getNotes(beat2, pulse);
        notes1.forEach(note -> note.setPosition(note.getPosition() + beat2));
        notes.addAll(notes1);
        return notes;
    }

    public List<Note> custom(int beat, int pulse) {
        int beat4 = beat / 4;
        List<Note> notes = rhythmCombinations.twoNoteEven.pos13(beat4, pulse);
        List<Note> notes1 = rhythmCombinations.oneNoteEven.pos1(beat / 2, pulse);
        List<Note> notes2 = rhythmCombinations.twoNoteEven.pos13(beat4, pulse);
        notes1.forEach(note -> note.setPosition(note.getPosition() + beat4));
        notes.addAll(notes1);
        notes2.forEach(note -> note.setPosition(note.getPosition() + beat4 * 3));
        notes.addAll(notes2);
        return notes;
    }

    public List<Note> customRandom(int beat, int pulse) {
        int beat4 = beat / 4;
        List<Note> notes = RandomUtil.getRandomFromList(twoNoteCombinations1).getNotes(beat4, pulse);
        List<Note> notes1 = rhythmCombinations.oneNoteEven.pos1(beat / 2, pulse);
        List<Note> notes2 = RandomUtil.getRandomFromList(twoNoteCombinations1).getNotes(beat4, pulse);
        notes1.forEach(note -> note.setPosition(note.getPosition() + beat4));
        notes.addAll(notes1);
        notes2.forEach(note -> note.setPosition(note.getPosition() + beat4 * 3));
        notes.addAll(notes2);
        return notes;
    }

    public static void main(String[] args) {
        CombiNoteEven combiNoteEven = new CombiNoteEven();
        List<Note > notes = combiNoteEven.pos23pos12(DurationConstants.QUARTER, DurationConstants.EIGHT);
        notes.forEach(n -> System.out.println(n.getPosition() + ", " + n.getLength() + ", " + n.isRest()));
    }
}
