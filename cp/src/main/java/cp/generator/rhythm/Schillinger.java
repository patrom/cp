package cp.generator.rhythm;

import org.springframework.stereotype.Component;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Component
public class Schillinger {

    public List<Integer> uniformBinarySynchronization(int majorGenerator){
        return IntStream.range(0, majorGenerator).boxed().collect(Collectors.toList());
    }

    public List<Integer> nonUniformBinarySynchronization(int majorGenerator, int minorGenerator){
        int timeUnits = majorGenerator * minorGenerator;
        IntStream majorGenerations = IntStream.iterate(0, i -> i + majorGenerator).limit(minorGenerator);
        IntStream minorGenerations = IntStream.iterate(0, i -> i + minorGenerator).limit(majorGenerator);
        return IntStream.concat(majorGenerations, minorGenerations).distinct().sorted().boxed().collect(Collectors.toList());
    }

    public Set<Integer> fractioning(int majorGenerator, int minorGenerator){
        int timeUnits = majorGenerator * majorGenerator;
        IntStream majorGenerations = IntStream.iterate(0, i -> i + majorGenerator).limit(majorGenerator);
        List<Integer> generations = majorGenerations.boxed().collect(Collectors.toList());
        Set<Integer> interferences = new TreeSet<>(generations);
        int size = majorGenerator - minorGenerator + 1;
        for (int i = 0; i < size; i++) {
            List<Integer> minorGenerations = IntStream.iterate(generations.get(i), j -> j + minorGenerator).limit(majorGenerator).boxed().collect(Collectors.toList());
            interferences.addAll(minorGenerations);
        }
        return interferences;
    }
}
