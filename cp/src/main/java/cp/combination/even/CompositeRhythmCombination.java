package cp.combination.even;

import cp.combination.RhythmCombination;
import cp.model.note.Note;
import cp.model.rhythm.DurationConstants;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class CompositeRhythmCombination {

    private List<RhythmCombination> rhythmCombinations = new ArrayList<>();

    public List<Note> posEven(int beatLength, int pulse) {
        int beat = beatLength/rhythmCombinations.size();
        List<Note> allNotes = new ArrayList<>();
        for (int i = 0; i <  rhythmCombinations.size() ; i++) {
            RhythmCombination rhythmCombination = rhythmCombinations.get(i);
            List<Note> notes = rhythmCombination.getNotes(beat, pulse);
            for (Note note : notes) {
                note.setPosition(note.getPosition() + (beat * i));
            }
            allNotes.addAll(notes);
        }
        return allNotes;
    }

    public List<Note> posDoubleFirst(int beatLength, int pulse) {
        int halfBeat = beatLength / 2;
        int beat = halfBeat/(rhythmCombinations.size() - 1);
        List<Note> allNotes = new ArrayList<>();
        RhythmCombination rhythmCombination = rhythmCombinations.get(0);
        List<Note> notes = rhythmCombination.getNotes(halfBeat, pulse);
        allNotes.addAll(notes);
        for (int i = 1; i <  rhythmCombinations.size() ; i++) {
            rhythmCombination = rhythmCombinations.get(i);
            notes = rhythmCombination.getNotes(beat, pulse);
            for (Note note : notes) {
                note.setPosition(note.getPosition() + (beat * (i - 1)) + halfBeat);
            }
            allNotes.addAll(notes);
        }
        return allNotes;
    }

    public List<Note> posFixed(int beatLength, int pulse) {
        int firstBeat = DurationConstants.QUARTER;
        int beat = DurationConstants.EIGHT;
        List<Note> allNotes = new ArrayList<>();
        RhythmCombination rhythmCombination = rhythmCombinations.get(0);
        List<Note> notes = rhythmCombination.getNotes(firstBeat, pulse);
        allNotes.addAll(notes);
        for (int i = 1; i <  rhythmCombinations.size() ; i++) {
            rhythmCombination = rhythmCombinations.get(i);
            notes = rhythmCombination.getNotes(beat, pulse);
            for (Note note : notes) {
                note.setPosition(note.getPosition() + (beat * (i - 1)) + firstBeat);
            }
            allNotes.addAll(notes);
        }
        return allNotes;
    }

    public void addRhythmCombinations(RhythmCombination... rhythmCombination) {
        this.rhythmCombinations.addAll(Arrays.asList(rhythmCombination));
    }
}
