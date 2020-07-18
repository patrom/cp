package cp.generator;

import cp.combination.RhythmCombination;
import cp.composition.MelodicValue;
import cp.composition.MelodicValueMelody;
import cp.composition.MelodicValueRhythmCombination;
import cp.model.harmony.Chord;
import cp.model.melody.CpMelody;
import org.paukov.combinatorics3.Generator;
import org.springframework.stereotype.Component;

import java.util.*;
import java.util.stream.Collectors;

@Component
public class CombinationGenerator extends cp.generator.Generator {

    /**
     * Random pitchclasses voor setclass
     *
     * vb. alle pitchclasses voor setclass 3-5
     */
    public MelodicValue getShuffledPitchClasses(List<Integer> pitchClasses) {
        Collections.shuffle(pitchClasses);
        MelodicValueRhythmCombination melodicValue = new MelodicValueRhythmCombination();
        melodicValue.setPermutationsPitchClasses(Collections.singletonList(pitchClasses));
        return melodicValue;
    }

    /**
     * Alle setclasses voor forteName in super setclass, 1 per setclass
     *
     * vb. alle setclasses 3-5 in setclass 6-7
     */
    public MelodicValue getSetClassesForForteNameInSuperSetClass(List<Integer> superSetClassPitchClasses, String forteName){
        List<List<Integer>> subsets = Generator.combination(superSetClassPitchClasses)
                .simple(Integer.parseInt(forteName.substring(0,1)))
                .stream()
                .collect(Collectors.toList());
        List<List<Integer>> subsetsForteName = new ArrayList<>();
        for (List<Integer> subset : subsets) {
            Chord chord = new Chord(subset, 0);
            if(chord.getForteName().equals(forteName)){
                subsetsForteName.add(subset);
            }
        }

        if (subsetsForteName.isEmpty()) {
            throw new IllegalStateException("Geen subsets gevonden");
        }

        MelodicValueRhythmCombination melodicValue = new MelodicValueRhythmCombination();
        melodicValue.setPermutationsPitchClasses(subsetsForteName);
        return melodicValue;
    }

    /**
     * Alle setclasses (+ retrograde) voor forteName in super setclass, 2 per setclass
     *
     * vb. alle setclasses 3-5 in setclass 6-7
     */
    public MelodicValue getSetClassesAndRetrogradeForForteNameInSuperSetClass(String forteNameSuperSetClass, String forteName, Integer transpose){
        List<Integer> pitchClasses = getPitchClasses(forteNameSuperSetClass);
        List<List<Integer>> subsets = Generator.combination(pitchClasses)
                .simple(Integer.parseInt(forteName.substring(0,1)))
                .stream()
                .collect(Collectors.toList());
        List<List<Integer>> subsetsForteName = new ArrayList<>();
        for (List<Integer> subset : subsets) {
            Chord chord = new Chord(subset, 0);
            if(chord.getForteName().equals(forteName)){
                List<Integer> transposedSubset = subset.stream().map(integer -> {
                    return (integer + transpose) % 12;
                }).collect(Collectors.toList());
                subsetsForteName.add(transposedSubset);
                List<Integer> reversedList = new ArrayList<>(transposedSubset);
                Collections.reverse(reversedList);
                subsetsForteName.add(reversedList);
            }
        }

        if (subsetsForteName.isEmpty()) {
            throw new IllegalStateException("Geen subsets gevonden");
        }

        MelodicValueRhythmCombination melodicValue = new MelodicValueRhythmCombination();
        melodicValue.setPermutationsPitchClasses(subsetsForteName);
        return melodicValue;
    }

    /**
     * Bevat alle pitchclasses, volgorde blijft behouden
     *
     */
    public MelodicValue generateKcombinationOrderedWithRepetition(String forteName, Integer transpose, Integer... pitchClasses){
        List<List<Integer>> subsets = Generator.combination(pitchClasses)
                .simple(Integer.parseInt(forteName.substring(0,1)))
                .stream()
                .map(combination -> Generator.permutation(combination)
                        .simple().stream().collect(Collectors.toList())).flatMap(Collection::stream).collect(Collectors.toList());
        List<List<Integer>> subsetsForteName = new ArrayList<>();
        for (List<Integer> subset : subsets) {
            Chord chord = new Chord(subset, 0);
            if(chord.getForteName().equals(forteName)){
                List<Integer> transposedSubset = subset.stream().map(integer -> {
                    return (integer + transpose) % 12;
                }).collect(Collectors.toList());
                subsetsForteName.add(transposedSubset);
            }
        }

        if (subsetsForteName.isEmpty()) {
            throw new IllegalStateException("Geen subsets gevonden");
        }

        MelodicValueRhythmCombination melodicValue = new MelodicValueRhythmCombination();
        melodicValue.setPermutationsPitchClasses(subsetsForteName);
        return melodicValue;
    }

    /**
     * Alle permutaties van setclass voor forteName in super setclass
     *
     * vb. alle permutaties van setclass 3-5 voor setclass 6-7
     *
     * @param forteName forteName
     */
    public MelodicValue allPermutationsForSetClassInSuperSetClass(List<Integer> superSetPitchClasses, String forteName){
        List<List<Integer>> subsets = Generator.combination(superSetPitchClasses)
                .simple(Integer.parseInt(forteName.substring(0,1)))
                .stream()
                .map(combination -> Generator.permutation(combination)
                        .simple().stream().collect(Collectors.toList())).flatMap(Collection::stream).collect(Collectors.toList());
        List<List<Integer>> subsetsForteName = new ArrayList<>();
        for (List<Integer> subset : subsets) {
            Chord chord = new Chord(subset, 0);
            if(chord.getForteName().equals(forteName)){
                subsetsForteName.add(subset);
            }
        }

        if (subsetsForteName.isEmpty()) {
            throw new IllegalStateException("Geen subsets gevonden");
        }

        MelodicValueRhythmCombination melodicValue = new MelodicValueRhythmCombination();
        melodicValue.setPermutationsPitchClasses(subsetsForteName);
        return melodicValue;
    }

    /**
     * Alle permutaties van pitchClasses
     *
     * @param pitchClasses pitchClasses
     */
    public MelodicValue allPermutations(List<Integer> pitchClasses){
        List<List<Integer>> subsets = Generator.permutation(pitchClasses).simple().stream().collect(Collectors.toList());
        if (subsets.isEmpty()) {
            throw new IllegalStateException("Geen subsets gevonden");
        }
        MelodicValueRhythmCombination melodicValue = new MelodicValueRhythmCombination();
        melodicValue.setPermutationsPitchClasses(subsets);
        return melodicValue;
    }

}
