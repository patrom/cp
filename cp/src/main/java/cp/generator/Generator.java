package cp.generator;

import cp.combination.RhythmCombination;
import cp.model.melody.CpMelody;
import cp.model.note.Note;
import cp.model.note.NoteBuilder;
import cp.model.rhythm.DurationConstants;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public abstract class Generator {

    private int pulse = DurationConstants.EIGHT; //pulse for balanced patterns
    @Autowired
    private ChordGenerator chordGenerator;

    protected CpMelody getMelody(List<Integer> pitchClasses, RhythmCombination rhythmCombination, int duration) {
        List<Note> notes = rhythmCombination.getNotes(duration, pulse);
        int size = notes.size();
        List<Note> notesNoRest = notes.stream().filter(note -> !note.isRest()).collect(Collectors.toList());
        for (int i = 0; i < size; i++) {
            Note note = notesNoRest.get(i);
            Integer pitchClass = pitchClasses.get(i % pitchClasses.size());
            if (pitchClass == -1){
                note.setPitch(Note.REST);
            }
            note.setPitchClass(pitchClass);
        }
        Note lastNote = notes.get(notes.size() - 1);
        int length = lastNote.getPosition() + lastNote.getLength();
        return new CpMelody(notes, -1, 0, length);
    }

    protected CpMelody getMelodyPulse(List<Integer> pitchClasses, RhythmCombination rhythmCombination, int pulse) {
        List<Note> notes = rhythmCombination.getNotes(0, pulse);
        int size = notes.size();
        List<Note> notesNoRest = notes.stream().filter(note -> !note.isRest()).collect(Collectors.toList());
        for (int i = 0; i < size; i++) {
            Note note = notesNoRest.get(i);
            Integer pitchClass = pitchClasses.get(i % pitchClasses.size());
            if (pitchClass == -1){
                note.setPitch(Note.REST);
            }
            note.setPitchClass(pitchClass);
        }
        Note lastNote = notes.get(notes.size() - 1);
        int length = lastNote.getPosition() + lastNote.getLength();
        return new CpMelody(notes, -1, 0, length);
    }

    protected CpMelody getMelody(List<Integer> pitchClasses, int... durations) {
        List<Note> notes = new ArrayList<>();
        int total = 0;
        for (int i = 0; i < durations.length; i++) {
            int duration = durations[i];
            notes.add(NoteBuilder.note().pos(total).pc(pitchClasses.get(i)).len(duration).build());
            total = total + duration;
        }
        return new CpMelody(notes, -1, 0, total);
    }

    protected CpMelody getMelodyPitches(List<Integer> pitches, RhythmCombination rhythmCombination, int duration) {
        int size = pitches.size();
        List<Note> notes = rhythmCombination.getNotes(duration, pulse);
        List<Note> notesNoRest = notes.stream().filter(note -> !note.isRest()).collect(Collectors.toList());
        for (int i = 0; i < size; i++) {
            Note note = notesNoRest.get(i);
            Integer pitch = pitches.get(i % size);
            note.setPitch(pitch);
            note.setPitchClass(pitch % 12);
        }
        Note lastNote = notes.get(notes.size() - 1);
        int length = lastNote.getPosition() + lastNote.getLength();
        return new CpMelody(notes, -1, 0, length);
    }

    protected List<List<Integer>> transposePitchClasses(List<List<Integer>> pcs) {
        List<List<Integer>> allPcs = new ArrayList<>();
        for (int i = 0; i < 12; i++) {
            for (List<Integer> pc : pcs) {
                List<Integer> transposedPcs = new ArrayList<>();
                for (Integer integer : pc) {
                    transposedPcs.add((integer + i) % 12);
                }
                allPcs.add(transposedPcs);
            }
        }
        return allPcs;
    }

    public List<Integer> getPitchClasses(String fortename){
        int[] setClass = chordGenerator.generatePitchClasses(fortename);
        return Arrays.stream(setClass).boxed().collect(Collectors.toList());
    }

    public int getPulse() {
        return pulse;
    }
}
