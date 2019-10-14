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

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static cp.model.note.NoteBuilder.note;

@Component
public class SingleMelodyGenerator {

    @Autowired
    private Composition composition;
    @Autowired
    private Keys keys;

    public CpMelody generateRest(int duration){
        List<CpMelody> melodies = new ArrayList<>();
        Note rest = note().rest().len(duration).build();
        return new CpMelody(Collections.singletonList(rest), -1, 0, duration);
    }

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

    /**
     * All transposities van de pitchclasses voor opgegeven stappen (pitchclasses)
     */
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

    /**
     * All transposities van de pitchclasses in de scale
     * (De pitchclasses moeten voorkomen in de scale)
     */
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

    public List<List<Integer>> generateTranspositionsForPitchesClasses(Scale scale, int... pitchClasses ){
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
        return allPitchClasses;
    }

    /**
     * Alle inversies van de pitchclasses in de scale
     * (De pitchclasses moeten voorkomen in de scale)
     */

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

    private RhythmCombination getRhythmCombination(int... durations) {
        List<Note> notes = new ArrayList<>();
        int total = 0;
        for (int duration : durations) {
            notes.add(NoteBuilder.note().pos(total).len(duration).build());
            total = total + duration;
        }
         return (beatLength, pulse) -> notes;
    }

    public int getPitchClassForKey(int pitchClass, Key key) {
        return (pitchClass + key.getInterval()) % 12;
    }

    /**
     * Alle combinaties zonder volgorde, gebruik Collecions.shuffle() om alle mogelijke combinaties te verkrijgen.
     */
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

    /**
     * Alle permutaties van de pitchclasses
     *
     * vb: pitchclasses 4,5,0
     * 4, 5, 0,
     * 4, 0, 5,
     * 0, 4, 5,
     * 0, 5, 4,
     * 5, 0, 4,
     * 5, 4, 0
     *
     * @param rhythmCombinations ritmes voor de permutaties
     * @param duration lengte van de ritmes
     */
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

    public List<List<Integer>> generatePermutationsOrderedNoRepetition(List<Integer> pitchClasses){
        List<CpMelody> melodies = new ArrayList<>();
        return Generator.permutation(pitchClasses)
                .simple()
                .stream()
                .collect(Collectors.toList());
    }

    /**
     * Alle permutaties van pitchclasses met opgegeven grootte
     *
     * vb. alle permutaties met grootte 3 van major scale
     *
     * @param rhythmCombinations de ritmes van de permutaties
     * @param size de grootte van de permutaties
     * @param duration de lengte van de permutaties
     */
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

    /**
     * Alle combinaties met opgeven grootte met herhalingen
     *
     * vb size 3 in C major scale
     * 0, 0, 0,
     * 0, 0, 2,
     * 0, 2, 0,
     * 2, 0, 0,
     * ...
     * 2, 2, 0,
     * 0, 2, 4,
     * 0, 4, 2,
     * 4, 0, 2,
     * ...
     * @param rhythmCombinations de ritmes van de combinaties
     * @param size de grootte
     * @param duration
     * @return
     */
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

    /**
     * Bevat alle pitchclasses met eventueel herhalingen en volgorde blijft behouden
     *
     * vb: pitchclasses 4,0,11 met grootte 4
     * 4, 4, 0, 11,
     * 4, 0, 0, 11,
     * 4, 0, 11, 11
     *
     * @param rhythmCombinations
     * @param size
     * @param duration
     * @return
     */
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
