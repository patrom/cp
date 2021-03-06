package cp.combination.rhythm;

import cp.combination.RhythmCombination;
import cp.model.note.Note;
import cp.model.note.NoteBuilder;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class RandomRhythmCombination implements RhythmCombination {

    private List<Integer> durations = new ArrayList<>();

    public RandomRhythmCombination(Integer... durations) {
        this.durations = Arrays.asList(durations);
    }

    @Override
    public List<Note> getNotes(int beatLength, int pulse) {
        Collections.shuffle(durations);
        List<Note> notes = new ArrayList<>();
        int total = 0;
        for (Integer duration : durations){
            notes.add(NoteBuilder.note().pos(total).len(duration).build());
            total = total + duration;
        }
        return notes;
    }

    public void addDuration(int duration){
        this.durations.add(duration);
    }
}

