package cp.util;

import cp.DefaultConfig;
import cp.VariationConfig;
import cp.config.BeatGroupConfig;
import cp.model.note.Scale;
import jm.audio.io.SampleOut;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.paukov.combinatorics3.Generator;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.security.cert.CollectionCertStoreParameters;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.LongStream;

@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = {DefaultConfig.class, VariationConfig.class, BeatGroupConfig.class})
@ExtendWith(SpringExtension.class)
public class PermutationsTest {

    @Test
    public void testFactorial(){
        List<String> items = Arrays.asList("A", "B", "C");
        long permutations = Permutations.factorial(items.size());
        System.out.println(items + " can be combined in " + permutations + " different ways:");
        LongStream.range(0, permutations).forEachOrdered(i -> {
            System.out.println(i + ": " + Permutations.permutation(i, items));
        });

        Permutations.of(0,4,7)
                .map(s -> s.collect(Collectors.toList()))
                .forEachOrdered(System.out::println);

        System.out.println();
        List<Integer> integers = new ArrayList<>();
        integers.add(0);
        integers.add(4);
        integers.add(7);
        Permutations.of(integers)
                .map(s -> s.collect(Collectors.toList()))
                .forEachOrdered(System.out::println);
    }

    @Test
    public void testSubsetSize() {
        int arr[] = Scale.MAJOR_SCALE.getPitchClasses();
        int r = 3;
        Permutations.printCombination(arr, r);
    }

    @Test
    public void testSubsetSize2() {
        List<Integer> superSet = Scale.MAJOR_SCALE.getPitchClassesAsList();
        List<List<Integer>> subsets = Permutations.getSubsets(superSet, 6);
        for (List<Integer> subset : subsets) {
            subset.forEach(integer -> System.out.print(integer + " "));
            System.out.println();
        }
    }

    @Test
    public void testPermutation(){
        List<Integer> superSet = Scale.MAJOR_SCALE.getPitchClassesAsList();
        List<List<Integer>> pitchClassesList = Generator.combination(superSet)
                .simple(3)
                .stream()
                .map(combination -> Generator.permutation(combination)
                        .simple().stream().collect(Collectors.toList())).flatMap(lists -> lists.stream()).collect(Collectors.toList());
        System.out.println(pitchClassesList.size());
        for (List<Integer> subset : pitchClassesList) {
            subset.forEach(integer -> System.out.print(integer + " "));
            System.out.println();
        }
    }
}
