package cp.combination.rhythm;

import cp.combination.RhythmCombination;
import cp.model.note.Note;

import java.util.ArrayList;
import java.util.List;

public class CompositeRhythmCombination implements RhythmCombination {

    private List<RhythmCombination> rhythmCombinations = new ArrayList<>();

    @Override
    public List<Note> getNotes(int beatLength, int pulse) {
        List<Note> notes = new ArrayList<>();
        int length = beatLength / rhythmCombinations.size();
        int totalLength = 0;
        for (RhythmCombination rhythmCombination : rhythmCombinations) {
            List<Note> rhythmCombinationNotes = rhythmCombination.getNotes(length, pulse);
            for (Note note : rhythmCombinationNotes) {
                note.setPosition(note.getPosition() + totalLength);
            }
            notes.addAll(rhythmCombinationNotes);
            totalLength = totalLength + length;
        }
        return notes;
    }

    public void addRhythmCombination(RhythmCombination rhythmCombination){
        this.rhythmCombinations.add(rhythmCombination);
    }
}
