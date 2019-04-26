package cp.model.setclass;

import cp.generator.ChordGenerator;
import cp.model.harmony.Chord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

@Component
public class SubSetCalculator {

    @Autowired
    private ChordGenerator chordGenerator;

    public void calculateSubSets(String forteName) {
        int[] pitchClasses = chordGenerator.generatePitchClasses(forteName);
        List<Integer> setClass = Arrays.stream(pitchClasses).boxed().collect(Collectors.toList());
        List<List<Integer>> powerset = powerset(setClass);
        Map<String, List<String>> subSetPerCardinality = powerset.stream().filter(subSetClass -> subSetClass.size() != 1
                && subSetClass.size() != setClass.size()
                && !subSetClass.isEmpty())
                .map(integers -> new Chord(integers, integers.get(0)))
                .map(chord -> chord.getForteName())
                .collect(Collectors.groupingBy(forte -> forte.substring(0, 1)));
        for (List<String> subSet : subSetPerCardinality.values()) {
            Map<String, Long> collect = subSet.stream().collect(Collectors.groupingBy(Function.identity(), Collectors.counting()));
            collect.entrySet().stream().sorted(Collections.reverseOrder(Map.Entry.comparingByValue())).forEach(stringLongEntry -> System.out.println(stringLongEntry.getKey() + ", " + stringLongEntry.getValue()));
        }
        Map<String, Long> sortedSubSets = subSetPerCardinality.values().stream().flatMap(strings -> strings.stream())
                .collect(Collectors.groupingBy(Function.identity(), Collectors.counting()));
        subSetPerCardinality.entrySet().forEach(stringLongEntry -> System.out.println(stringLongEntry.getKey() + ", " + stringLongEntry.getValue()));
    }

    public List<Chord> getSubSets(String forteNameSuperSet, String... forteNameSubSet) {
        int[] pitchClasses = chordGenerator.generatePitchClasses(forteNameSuperSet);
        List<Integer> setClass = Arrays.stream(pitchClasses).boxed().collect(Collectors.toList());
        List<List<Integer>> powerset = powerset(setClass);
        return powerset.stream().filter(subSetClass ->
                subSetClass.size() != 1
                        && subSetClass.size() != setClass.size()
                        && !subSetClass.isEmpty())
                .map(integers -> new Chord(integers, integers.get(0)))
                .filter(chord -> Arrays.stream(forteNameSubSet).anyMatch(chord.getForteName()::equals))
                .collect(Collectors.toList());
    }

    private <T> List<List<T>> powerset(Collection<T> list) {
        List<List<T>> ps = new ArrayList<>();
        ps.add(new ArrayList<>());   // add the empty set

        // for every item in the original list
        for (T item : list) {
            List<List<T>> newPs = new ArrayList<>();

            for (List<T> subset : ps) {
                // copy all of the current powerset's subsets
                newPs.add(subset);

                // plus the subsets appended with the current item
                List<T> newSubset = new ArrayList<>(subset);
                newSubset.add(item);
                newPs.add(newSubset);
            }

            // powerset is now powerset of list.subList(0, list.indexOf(item)+1)
            ps = newPs;
        }
        return ps;
    }
}
