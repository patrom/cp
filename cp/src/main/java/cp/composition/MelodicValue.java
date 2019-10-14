package cp.composition;

import cp.combination.RhythmCombination;
import cp.model.melody.CpMelody;
import cp.model.note.Note;
import cp.util.RandomUtil;
import org.paukov.combinatorics3.Generator;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class MelodicValue {

    private  List<CpMelody> melodies = new ArrayList<>();

    private List<List<Integer>> permutationsPitchClasses = new ArrayList<>();
    private List<RhythmCombination> rhythmCombinations = new ArrayList<>();
    private int duration;

    public MelodicValue() {
    }

    public MelodicValue(List<CpMelody> melodies) {
        this.melodies = melodies;
    }

    public MelodicValue(List<List<Integer>> permutationsPitchClasses, List<RhythmCombination> rhythmCombinations, int duration) {
        this.permutationsPitchClasses = permutationsPitchClasses;
        this.rhythmCombinations = rhythmCombinations;
        this.duration = duration;
    }

    public CpMelody pickRandomMelody(){
       if(melodies.isEmpty()){
           List<Integer> permutation = RandomUtil.getRandomFromList(permutationsPitchClasses);
           RhythmCombination rhythmCombination = RandomUtil.getRandomFromList(rhythmCombinations);
           return getMelody(permutation, rhythmCombination, duration);
       } else {
          return RandomUtil.getRandomFromList(melodies);
       }
    }

    protected CpMelody getMelody(List<Integer> pitchClasses, RhythmCombination rhythmCombination, int duration) {
        int size = pitchClasses.size();
        List<Note> notes = rhythmCombination.getNotes(duration, 0);
        List<Note> notesNoRest = notes.stream().filter(note -> !note.isRest()).collect(Collectors.toList());
        for (int i = 0; i < size; i++) {
            Note note = notesNoRest.get(i);
            note.setPitchClass(pitchClasses.get(i));
        }
        int length = notes.stream().mapToInt(value -> value.getLength()).sum();
        return new CpMelody(notes, -1, 0, length);
    }

    public void setMelodies(List<CpMelody> melodies) {
        this.melodies = melodies;
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
}
