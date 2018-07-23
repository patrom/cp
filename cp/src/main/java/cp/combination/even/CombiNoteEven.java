package cp.combination.even;

import cp.combination.RhythmCombination;
import cp.combination.RhythmCombinations;
import cp.model.note.Note;
import cp.model.rhythm.DurationConstants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class CombiNoteEven {

    @Autowired
    public RhythmCombinations rhythmCombinations;

    private List<Note> combi(RhythmCombination combi1, RhythmCombination comvi2, int beat) {
        List<Note> notes = combi1.getNotes(beat);
        List<Note> notes1 = comvi2.getNotes(beat);
        notes1.forEach(note -> note.setPosition(note.getPosition() + beat));
        notes.addAll(notes1);
        return notes;
    }

    public List<Note> pos23pos12(int beat) {
        return combi(rhythmCombinations.twoNoteEven::pos23, rhythmCombinations.twoNoteEven::pos12, beat/2);
    }

    public List<Note> quintupletpos2345pos1(int beat) {
        List<Note> notes = rhythmCombinations.quintuplet.pos2345(beat);
        List<Note> notes1 = rhythmCombinations.oneNoteEven.pos1(beat);
        notes1.forEach(note -> note.setPosition(note.getPosition() + beat));
        notes.addAll(notes1);
        return notes;
    }

    public List<Note> quintupletpos12345pos1(int beat) {
        List<Note> notes = rhythmCombinations.quintuplet.pos12345(beat);
        List<Note> notes1 = rhythmCombinations.oneNoteEven.pos1(beat);
        notes1.forEach(note -> note.setPosition(note.getPosition() + beat));
        notes.addAll(notes1);
        return notes;
    }

    public List<Note> septTupletpos234567pos1(int beat) {
        List<Note> notes = rhythmCombinations.septTuplet.pos234567(beat);
        List<Note> notes1 = rhythmCombinations.oneNoteEven.pos1(beat);
        notes1.forEach(note -> note.setPosition(note.getPosition() + beat));
        notes.addAll(notes1);
        return notes;
    }

    public List<Note> septTupletpos1234567pos1(int beat) {
        List<Note> notes = rhythmCombinations.septTuplet.pos1234567(beat);
        List<Note> notes1 = rhythmCombinations.oneNoteEven.pos1(beat);
        notes1.forEach(note -> note.setPosition(note.getPosition() + beat));
        notes.addAll(notes1);
        return notes;
    }

    public static void main(String[] args) {
        CombiNoteEven combiNoteEven = new CombiNoteEven();
        List<Note > notes = combiNoteEven.pos23pos12(DurationConstants.QUARTER);
        notes.forEach(n -> System.out.println(n.getPosition() + ", " + n.getLength() + ", " + n.isRest()));
    }
}
