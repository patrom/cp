package cp.generator;

import cp.combination.RhythmCombination;
import cp.composition.Composition;
import cp.generator.pitchclass.combination.PitchClassCombination;
import cp.model.melody.CpMelody;
import cp.model.note.Note;
import cp.model.note.NoteBuilder;
import cp.model.note.Scale;
import cp.out.print.Keys;
import cp.out.print.note.Key;
import cp.util.Permutations;
import cp.util.RandomUtil;
import org.paukov.combinatorics3.Generator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static cp.model.note.NoteBuilder.note;

@Component
public class SingleMelodyGenerator {

    @Autowired
    private Composition composition;
    @Autowired
    private Keys keys;

    public List<CpMelody> generateSingleNoteScale(Scale scale, int duration, Key key){
        List<CpMelody> melodies = new ArrayList<>();
        for (int pitchClass : scale.getPitchClasses()) {
            int pitchClassForKey = getPitchClassForKey(pitchClass, key);
            melodies.add(generateSingleNote(pitchClassForKey, duration));
        }
        return melodies;
    }

    public List<CpMelody> generateSingleNoteScale(Scale scale, int duration){
        List<CpMelody> melodies = new ArrayList<>();
        for (int pitchClass : scale.getPitchClasses()) {
            melodies.add(generateSingleNote(pitchClass, duration));
        }
        return melodies;
    }

    public CpMelody generateSingleNote(int pitchClass, int duration){
        List<Note> notes = new ArrayList<>();
        notes.add(note().pos(0).pc(pitchClass).len(duration).build());
        return new CpMelody(notes, -1, 0, duration);
    }

//    public List<CpMelody> generatePermutations(Scale scale, Key key, int subsetSize, List<RhythmCombination> rhythmCombinations, int duration){
//        if (rhythmCombinations.stream().anyMatch(rhythmCombination ->
//                rhythmCombination.getNotes(duration, 0).stream().filter(note -> !note.isRest()).collect(Collectors.toList()).size() != subsetSize)){
//            throw new IllegalArgumentException("The rhythmcombinations do not match the subset size.");
//        }
//        List<CpMelody> allPermutationMelodies = new ArrayList<>();
//        List<Integer> pitchClassesInKey = scale.getPitchClassesInKey(key);
//        List<List<Integer>> subsets = Permutations.getSubsets(pitchClassesInKey, subsetSize);
//        for (List<Integer> subset : subsets) {
//            Stream<Stream<Integer>> permutationsSubset = Permutations.of(subset);
//            Stream<List<CpMelody>> listStream = permutationsSubset.map(integerStream ->
//                    getMelody(integerStream.collect(Collectors.toList()), rhythmCombinations, duration));
//            List<CpMelody> allPermutationMelodiesSubset = listStream.flatMap(cpMelodies -> cpMelodies.stream()).collect(Collectors.toList());
//            allPermutationMelodies.addAll(allPermutationMelodiesSubset);
//        }
//        return allPermutationMelodies;
//    }

    public List<CpMelody> generatePermutationsWithRepetition(Scale scale, Key key, int subsetSize, List<RhythmCombination> rhythmCombinations, int duration){
        if (rhythmCombinations.stream().anyMatch(rhythmCombination ->
                rhythmCombination.getNotes(duration, 0).stream().filter(note -> !note.isRest()).count() < subsetSize)){
            throw new IllegalArgumentException("The rhythmcombinations are smaller than the subset size.");
        }
        List<CpMelody> allPermutationMelodies = new ArrayList<>();
        List<Integer> pitchClassesInKey = scale.getPitchClassesInKey(key);
        List<List<Integer>> subsets = Permutations.getSubsets(pitchClassesInKey, subsetSize);

//        List<List<Integer>> pitchClassesList = Generator.combination(pitchClassesInKey)
//                .simple(subsetSize)
//                .stream()
//                .map(combination -> Generator.permutation(combination)
//                        .simple().stream().collect(Collectors.toList())).flatMap(lists -> lists.stream()).collect(Collectors.toList());

        for (List<Integer> subset : subsets) {
            Stream<Stream<Integer>> permutationsSubset = Permutations.of(subset);
            Stream<List<CpMelody>> listStream = permutationsSubset.map(integerStream ->
                    getMelodyWithRepetition(integerStream.collect(Collectors.toList()), rhythmCombinations, duration));
            List<CpMelody> allPermutationMelodiesSubset = listStream.flatMap(cpMelodies -> cpMelodies.stream()).collect(Collectors.toList());
            allPermutationMelodies.addAll(allPermutationMelodiesSubset);
        }
        return allPermutationMelodies;
    }

    public List<CpMelody> generateTranspositionsPitchClassesForSteps(int[] steps, List<RhythmCombination> rhythmCombinations, int duration , Integer... pitchClasses ){
        Integer[] pcs = pitchClasses;
        List<Integer> transpositions = new ArrayList<>();
        List<CpMelody> transpositionMelodies = new ArrayList<>();
        for (int step : steps) {
            transpositions.clear();
            for (Integer pc : pcs) {
                transpositions.add((pc + step) % 12);
            }
            for (RhythmCombination rhythmCombination : rhythmCombinations) {
                transpositionMelodies.add(getMelody(transpositions, rhythmCombination, duration));
            }
        }
        return transpositionMelodies;
    }

    public List<CpMelody> generateTranspositionsAndInversionsForScale(Scale scale, List<RhythmCombination> rhythmCombinations, int duration , int... pitchClasses ){
        List<List<Integer>> allPitchClasses = new ArrayList<>();
        int length = scale.getPitchClasses().length;
        int[] indexes = scale.getIndexes(pitchClasses);
        int firstIndex = indexes[0];
        int[] indexesInverted = new int[indexes.length];
        indexesInverted[0] = firstIndex;
        for (int i = 1; i < indexes.length; i++) {
            int index = indexes[i];
            int invertedIndex = -(index - firstIndex);
            int i1 = (firstIndex + invertedIndex + length) % length;
            indexesInverted[i] = i1;
        }

        for (int i = 0; i < length; i++) {
            List<Integer> pitchClassesForIndexes = scale.getPitchClasses(indexes, 0, keys.C);
            allPitchClasses.add(pitchClassesForIndexes);
            for (int j = 0; j < indexes.length; j++) {
                indexes[j] = indexes[j] + 1 % length;
            }
        }

        for (int i = 0; i < length; i++) {
            List<Integer> pitchClassesForIndexesInverted = scale.getPitchClasses(indexesInverted, 0, keys.C);
            allPitchClasses.add(pitchClassesForIndexesInverted);
            for (int j = 0; j < indexesInverted.length; j++) {
                indexesInverted[j] = indexesInverted[j] + 1 % length;
            }
        }

        List<CpMelody> transpositionMelodies = new ArrayList<>();
        for (List<Integer> allPitchClass : allPitchClasses) {
            for (RhythmCombination rhythmCombination : rhythmCombinations) {
                transpositionMelodies.add(getMelody(allPitchClass, rhythmCombination, duration));
            }
        }

        return transpositionMelodies;
    }

    public List<CpMelody> generateTranspositionsForPitchesClasses(Scale scale, List<RhythmCombination> rhythmCombinations, int duration , int... pitchClasses ){
        List<List<Integer>> allPitchClasses = new ArrayList<>();
        int length = scale.getPitchClasses().length;
        int[] indexes = scale.getIndexes(pitchClasses);
        for (int i = 0; i < length; i++) {
            List<Integer> pitchClassesForIndexes = scale.getPitchClasses(indexes, 0, keys.C);
            allPitchClasses.add(pitchClassesForIndexes);
            for (int j = 0; j < indexes.length; j++) {
                indexes[j] = indexes[j] + 1 % length;
            }
        }
        List<CpMelody> transpositionMelodies = new ArrayList<>();
        for (List<Integer> allPitchClass : allPitchClasses) {
            for (RhythmCombination rhythmCombination : rhythmCombinations) {
                transpositionMelodies.add(getMelody(allPitchClass, rhythmCombination, duration));
            }
        }
        return transpositionMelodies;
    }

    public List<CpMelody> generateInversionsForPitchClasses(Scale scale, List<RhythmCombination> rhythmCombinations, int duration , int... pitchClasses ){
        List<List<Integer>> allPitchClasses = new ArrayList<>();
        int length = scale.getPitchClasses().length;
        int[] indexes = scale.getIndexes(pitchClasses);
        int firstIndex = indexes[0];
        int[] indexesInverted = new int[indexes.length];
        indexesInverted[0] = firstIndex;
        for (int i = 1; i < indexes.length; i++) {
            int index = indexes[i];
            int invertedIndex = -(index - firstIndex);
            int i1 = (firstIndex + invertedIndex + length) % length;
            indexesInverted[i] = i1;
        }
        for (int i = 0; i < length; i++) {
            List<Integer> pitchClassesForIndexesInverted = scale.getPitchClasses(indexesInverted, 0, keys.C);
            allPitchClasses.add(pitchClassesForIndexesInverted);
            for (int j = 0; j < indexesInverted.length; j++) {
                indexesInverted[j] = indexesInverted[j] + 1 % length;
            }
        }
        List<CpMelody> transpositionMelodies = new ArrayList<>();
        for (List<Integer> allPitchClass : allPitchClasses) {
            for (RhythmCombination rhythmCombination : rhythmCombinations) {
                transpositionMelodies.add(getMelody(allPitchClass, rhythmCombination, duration));
            }
        }
        return transpositionMelodies;
    }


    public List<CpMelody> generateTranspositionsAndInversionsWithRhythm(List<Integer> pitchClasses,  int... durations ){
        List<Integer> transpositions = new ArrayList<>();
        List<CpMelody> transpositionMelodies = new ArrayList<>();
        for (int i = 0; i < 12; i++) {
            transpositions.clear();
            for (Integer pc : pitchClasses) {
                transpositions.add((pc + i) % 12);
            }
            transpositionMelodies.add(getMelody(transpositions, durations));
        }
        List<CpMelody> inversionMelodies = new ArrayList<>();
        for (CpMelody cpMelody : transpositionMelodies) {
            CpMelody inversionMelody = cpMelody.clone().I();
            inversionMelodies.add(inversionMelody);
        }
        transpositionMelodies.addAll(inversionMelodies);
        return transpositionMelodies;
    }

    protected CpMelody getMelody(List<Integer> pitchClasses, RhythmCombination rhythmCombination, int duration) {
        int size = pitchClasses.size();
        List<Note> notes = rhythmCombination.getNotes(duration, 0);
        List<Note> notesNoRest = notes.stream().filter(note -> !note.isRest()).collect(Collectors.toList());
        for (int i = 0; i < size; i++) {
            Note note = notesNoRest.get(i);
            note.setPitchClass(pitchClasses.get(i));
        }
        return new CpMelody(notes, -1, 0, duration);
    }

    protected CpMelody getMelody(List<Integer> pitchClasses, int... durations) {
//        int size = pitchClasses.size();
//        while (size < durations.length){
//            int randomIndex = RandomUtil.getRandomIndex(pitchClasses);
//            pitchClasses.add(randomIndex, pitchClasses.get(randomIndex));
//            size = size + 1;
//        }
        List<Note> notes = new ArrayList<>();
        int total = 0;
        for (int i = 0; i < durations.length; i++) {
            int duration = durations[i];
            notes.add(NoteBuilder.note().pos(total).pc(pitchClasses.get(i)).len(duration).build());
            total = total + duration;
        }
        return new CpMelody(notes, -1, 0, total);
    }

    private RhythmCombination getRhythmCombination(int... durations) {
        List<Note> notes = new ArrayList<>();
        int total = 0;
        for (int duration : durations) {
            notes.add(NoteBuilder.note().pos(total).len(duration).build());
            total = total + duration;
        }
         return (beatLength, pulse) -> notes;
    }

    protected List<CpMelody> getMelodyWithRepetition(List<Integer> pitchClasses, List<RhythmCombination> rhythmCombinations, int duration) {
        int size = pitchClasses.size();
        List<CpMelody> melodies = new ArrayList<>();
        for (RhythmCombination rhythmCombination : rhythmCombinations) {
            List<Note> notes = rhythmCombination.getNotes(duration, 0);
            List<Note> notesNoRest = notes.stream().filter(note -> !note.isRest()).collect(Collectors.toList());
            while (size < notesNoRest.size()){
                int randomIndex = RandomUtil.getRandomIndex(pitchClasses);
                pitchClasses.add(randomIndex, pitchClasses.get(randomIndex));
                size = size + 1;
            }
            for (int i = 0; i < size; i++) {
                Note note = notesNoRest.get(i);
                note.setPitchClass(pitchClasses.get(i));
            }
            CpMelody cpMelody = new CpMelody(notes, -1, 0, duration);
            melodies.add(cpMelody);
        }
        return melodies;
    }

    public int getPitchClassForKey(int pitchClass, Key key) {
        return (pitchClass + key.getInterval()) % 12;
    }

    public List<CpMelody> generateCombinationNoOrderNoRepetition(List<Integer> pitchClasses, List<RhythmCombination> rhythmCombinations, int size, int duration){
        List<CpMelody> melodies = new ArrayList<>();
        List<List<Integer>> subsets = Generator.combination(pitchClasses)
                .simple(size)
                .stream()
                .collect(Collectors.toList());
        for (List<Integer> subset : subsets) {
            for (RhythmCombination rhythmCombination : rhythmCombinations) {
                melodies.add(getMelody(subset, rhythmCombination, duration));
            }
        }
        return melodies;
    }

    public List<CpMelody> generatePermutationsOrderedNoRepetition(List<Integer> pitchClasses, List<RhythmCombination> rhythmCombinations, int duration){
        List<CpMelody> melodies = new ArrayList<>();
        List<List<Integer>> subsets = Generator.permutation(pitchClasses)
                .simple()
                .stream()
                .collect(Collectors.toList());
        for (List<Integer> subset : subsets) {
            for (RhythmCombination rhythmCombination : rhythmCombinations) {
                melodies.add(getMelody(subset, rhythmCombination, duration));
            }
        }
        return melodies;
    }

    public List<CpMelody> generateKpermutationOrderedNoRepetition(List<Integer> pitchClasses, List<RhythmCombination> rhythmCombinations, int size, int duration){
        List<CpMelody> melodies = new ArrayList<>();
        List<List<Integer>> subsets = Generator.combination(pitchClasses)
                .simple(size)
                .stream()
                .map(combination -> Generator.permutation(combination)
                        .simple().stream().collect(Collectors.toList())).flatMap(Collection::stream).collect(Collectors.toList());
        for (List<Integer> subset : subsets) {
            for (RhythmCombination rhythmCombination : rhythmCombinations) {
                melodies.add(getMelody(subset, rhythmCombination, duration));
            }
        }
        return melodies;
    }

    public List<CpMelody> generateKpermutationOrderedWithRepetition(List<Integer> pitchClasses, List<RhythmCombination> rhythmCombinations, int size, int duration){
        List<CpMelody> melodies = new ArrayList<>();
        List<List<Integer>> subsets = Generator.combination(pitchClasses)
                .multi(size)
                .stream()
                .map(combination -> Generator.permutation(combination)
                        .simple().stream().collect(Collectors.toList())).flatMap(Collection::stream).collect(Collectors.toList());
        for (List<Integer> subset : subsets) {
            for (RhythmCombination rhythmCombination : rhythmCombinations) {
                melodies.add(getMelody(subset, rhythmCombination, duration));
            }
        }
        return melodies;
    }

    public List<CpMelody> generateKcombinationOrderedWithRepetition(List<Integer> pitchClasses, List<RhythmCombination> rhythmCombinations, int size, int duration){
        List<CpMelody> melodies = new ArrayList<>();
        List<List<Integer>> subsets = Generator.combination(pitchClasses)
                .multi(size)
                .stream()
                .filter(integers -> integers.containsAll(pitchClasses))
                .collect(Collectors.toList());
        for (List<Integer> subset : subsets) {
            for (RhythmCombination rhythmCombination : rhythmCombinations) {
                melodies.add(getMelody(subset, rhythmCombination, duration));
            }
        }
        return melodies;
    }

}
