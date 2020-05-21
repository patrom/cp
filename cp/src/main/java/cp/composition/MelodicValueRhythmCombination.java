package cp.composition;

import cp.combination.RhythmCombination;
import cp.model.melody.CpMelody;
import cp.model.note.Note;
import cp.util.RandomUtil;
import org.paukov.combinatorics3.Generator;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.toList;

public class MelodicValueRhythmCombination implements MelodicValue{

    private List<List<Integer>> permutationsPitchClasses = new ArrayList<>();
    private List<RhythmCombination> rhythmCombinations = new ArrayList<>();
    private ContourType contourType;
    private int duration;
    private int pulse;
    private int melodicNumber;

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
        CpMelody melody = RandomUtil.getRandomFromList(getMelodyKcombination(permutation, rhythmCombination, duration));
        if (ContourType.ASC == contourType){
            melody.updateContourAscending();
        }
        if (ContourType.DESC == contourType){
            melody.updateContourDescending();
        }
        return melody;
    }

    @Override
    public CpMelody pickRandomMelodyWithMultipleNotes() {
        return pickRandomMelody();
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

        return RandomUtil.getRandomFromList(getMelodyKcombination(permutation, rhythmCombination, duration));
    }

//    protected CpMelody getMelody(List<Integer> pitchClasses, RhythmCombination rhythmCombination, int duration) {
//        int size = pitchClasses.size();
//        List<Note> notes = rhythmCombination.getNotes(duration, pulse);
//        List<Note> notesNoRest = notes.stream().filter(note -> !note.isRest()).collect(toList());
//        for (int i = 0; i < notesNoRest.size(); i++) {
//            Note note = notesNoRest.get(i);
//            Integer pitchClass = pitchClasses.get(i);
//            if (pitchClass == -1){
//                note.setPitch(Note.REST);
//            }
//            note.setPitchClass(pitchClass);
//        }
//        int length = notes.stream().mapToInt(value -> value.getLength()).sum();
//        return new CpMelody(notes, -1, 0, length);
//    }

    protected List<CpMelody> getMelodyKcombination(List<Integer> pitchClasses, RhythmCombination rhythmCombination, int duration) {
        List<CpMelody> melodies = new ArrayList<>();
        int size = pitchClasses.size();
        List<Note> notes = rhythmCombination.getNotes(duration, pulse);
        List<Note> notesNoRest = notes.stream().filter(note -> !note.isRest()).collect(toList());
        int notesNoRestSize = notesNoRest.size();
        if (size == notesNoRestSize) {
            CpMelody melody = getMelodyForPitchClasses(notes, notesNoRest, pitchClasses);
            melodies.add(melody);
        } else if (notesNoRestSize < size) { //subsets
            int iterationSize = size - notesNoRestSize + 1;
            for (int i = 0; i < iterationSize; i++) {
                List<Integer> subList = pitchClasses.subList(i, notesNoRest.size() + i);
                notes = rhythmCombination.getNotes(duration, pulse);//clone notes
                notesNoRest = notes.stream().filter(note -> !note.isRest()).collect(toList());
                CpMelody melody = getMelodyForPitchClasses(notes, notesNoRest, subList);
                melodies.add(melody);
            }
        } else if (notesNoRestSize > size) {
            List<List<Integer>> subsets = Generator.combination(pitchClasses)
                    .multi(notesNoRestSize).stream()
                    .filter(integers -> integers.containsAll(pitchClasses))
                    .collect(toList());
            for (List<Integer> subset : subsets) {
                notes = rhythmCombination.getNotes(duration, pulse);
                notesNoRest = notes.stream().filter(note -> !note.isRest()).collect(toList());
                if(notesNoRest.size() != subset.size() ){
                    System.out.println();
                }
                CpMelody melody = getMelodyForPitchClasses(notes, notesNoRest, subset);
                melodies.add(melody);
            }
        }
        return melodies;
    }

    private CpMelody getMelodyForPitchClasses(List<Note> notes, List<Note> notesNoRest, List<Integer> pitchClasses){
        for (int i = 0; i < notesNoRest.size(); i++) {
            Note note = notesNoRest.get(i);
            Integer pitchClass = pitchClasses.get(i);
            if (pitchClass == -1){
                note.setPitch(Note.REST);
            }
            note.setPitchClass(pitchClass);
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

    public void setContourType(ContourType contourType) {
        this.contourType = contourType;
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

    @Override
    public int getMelodicNumber() {
        return melodicNumber;
    }

    @Override
    public void setMelodicNumber(int melodicNumber) {
        this.melodicNumber = melodicNumber;
    }
}

