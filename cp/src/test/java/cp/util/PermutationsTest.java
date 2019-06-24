package cp.util;

import cp.DefaultConfig;
import cp.VariationConfig;
import cp.config.BeatGroupConfig;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Arrays;
import java.util.List;
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
    }
}
