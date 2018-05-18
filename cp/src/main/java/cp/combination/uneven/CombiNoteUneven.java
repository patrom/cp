package cp.combination.uneven;

import cp.combination.RhythmCombination;
import cp.combination.RhythmCombinations;
import cp.model.note.Note;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class CombiNoteUneven {

    @Autowired
    public RhythmCombinations rhythmCombinations;

    public List<Note> combi(RhythmCombination combi1, RhythmCombination comvi2, int beat) {
        List<Note> notes = combi1.getNotes(beat);
        List<Note> notes1 = comvi2.getNotes(beat);
        notes1.forEach(note -> note.setPosition(note.getPosition() + beat));
        notes.addAll(notes1);
        return notes;
    }

    public List<Note> pos23pos12(int beat) {
        List<Note> notes = rhythmCombinations.twoNoteUneven.pos23(beat);
        List<Note> notes1 = rhythmCombinations.twoNoteUneven.pos12(beat);
        notes1.forEach(note -> note.setPosition(note.getPosition() + beat));
        notes.addAll(notes1);
        return notes;
    }

}
