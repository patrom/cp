package cp.combination.rhythm;

import cp.combination.RhythmCombination;
import cp.model.note.Note;
import cp.model.note.NoteBuilder;
import cp.model.rhythm.DurationConstants;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class RandomRhythmWithRestCombination implements RhythmCombination {

    private List<Integer> durations;
    private List<Integer> rests;

    public RandomRhythmWithRestCombination(List<Integer> rests, Integer... durations) {
        this.durations = Arrays.asList(durations);
        this.rests = rests;
    }

    @Override
    public List<Note> getNotes(int beatLength, int pulse) {
        List<Note> notes = new ArrayList<>();
        for (Integer rest : rests) {
            notes.add(NoteBuilder.note().rest().len(rest).build());
        }
        for (Integer duration : durations){
            notes.add(NoteBuilder.note().len(duration).build());
        }
        Collections.shuffle(notes);
        int size = notes.size() - 1;
        for (int i = 0; i < size; i++) {
            Note note = notes.get(i);
            Note nextNote = notes.get(i + 1);
            nextNote.setPosition(note.getPosition() + note.getLength());
        }
        return notes;
    }

    public void addDuration(int duration){
        this.durations.add(duration);
    }
}

