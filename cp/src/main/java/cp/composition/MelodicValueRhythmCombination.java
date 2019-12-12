package cp.composition;

import cp.combination.RhythmCombination;
import cp.model.melody.CpMelody;
import cp.model.note.Note;
import cp.util.RandomUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class MelodicValueRhythmCombination implements MelodicValue{

    private List<List<Integer>> permutationsPitchClasses = new ArrayList<>();
    private List<RhythmCombination> rhythmCombinations = new ArrayList<>();
    private int duration;
    private int pulse;

    public MelodicValueRhythmCombination() {
    }

    public MelodicValueRhythmCombination(MelodicValueRhythmCombination melodicValue) {
        this.permutationsPitchClasses = melodicValue.getPermutationsPitchClasses();
        this.rhythmCombinations = melodicValue.getRhythmCombinations();
        this.duration = melodicValue.getDuration();
    }

    public MelodicValueRhythmCombination(List<List<Integer>> permutationsPitchClasses, List<RhythmCombination> rhythmCombinations, int duration) {
        this.permutationsPitchClasses = permutationsPitchClasses;
        this.rhythmCombinations = rhythmCombinations;
        this.duration = duration;
    }

    public MelodicValue clone() {
        return new MelodicValueRhythmCombination(this);
    }

    public CpMelody pickRandomMelody(){
        List<Integer> permutation = RandomUtil.getRandomFromList(permutationsPitchClasses);
        RhythmCombination rhythmCombination = RandomUtil.getRandomFromList(rhythmCombinations);
        return getMelody(permutation, rhythmCombination, duration);
    }

    public CpMelody pickExhaustiveMelody(){
        List<List<Integer>> allPermutations = new ArrayList<>(permutationsPitchClasses);
        if (allPermutations.isEmpty()) {
            allPermutations.addAll(permutationsPitchClasses);
        }
        List<Integer> permutation = RandomUtil.getRandomFromList(allPermutations);
        allPermutations.remove(permutation);

        List<RhythmCombination> allRhythmCombinations = new ArrayList<>(rhythmCombinations);
        if (allRhythmCombinations.isEmpty()) {
            allRhythmCombinations.addAll(rhythmCombinations);
        }
        RhythmCombination rhythmCombination = RandomUtil.getRandomFromList(allRhythmCombinations);
        allRhythmCombinations.remove(rhythmCombination);

        return getMelody(permutation, rhythmCombination, duration);
    }

    protected CpMelody getMelody(List<Integer> pitchClasses, RhythmCombination rhythmCombination, int duration) {
        int size = pitchClasses.size();
        List<Note> notes = rhythmCombination.getNotes(duration, pulse);
        List<Note> notesNoRest = notes.stream().filter(note -> !note.isRest()).collect(Collectors.toList());
        for (int i = 0; i < size; i++) {
            Note note = notesNoRest.get(i);
            note.setPitchClass(pitchClasses.get(i));
        }
        int length = notes.stream().mapToInt(value -> value.getLength()).sum();
        return new CpMelody(notes, -1, 0, length);
    }

    public void setPermutationsPitchClasses(List<List<Integer>> permutationsPitchClasses) {
        this.permutationsPitchClasses = permutationsPitchClasses;
    }

    public void setRhythmCombinations(List<RhythmCombination> rhythmCombinations) {
        this.rhythmCombinations = rhythmCombinations;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    public List<List<Integer>> getPermutationsPitchClasses() {
        return permutationsPitchClasses;
    }

    public List<RhythmCombination> getRhythmCombinations() {
        return rhythmCombinations;
    }

    public int getDuration() {
        return duration;
    }

    public void setPulse(int pulse) {
        this.pulse = pulse;
    }
}

