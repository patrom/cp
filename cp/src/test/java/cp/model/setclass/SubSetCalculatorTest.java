package cp.model.setclass;

import cp.DefaultConfig;
import cp.model.harmony.Chord;
import cp.model.note.Scale;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = DefaultConfig.class)
@TestPropertySource(properties = "composition.voices=4")
public class SubSetCalculatorTest {

    @Autowired
    private SubSetCalculator subSetCalculator;

    @Test
    public void calculateSubSets() {
        subSetCalculator.calculateSubSets("6-27");
    }

    @Test
    public void getSubSets() {
        int[] pitchClasses = Scale.SET_6_27.getPitchClasses();
        Map<Integer, List<Chord>> map = new HashMap<>();
        for (int pitchClass : pitchClasses) {
            List<Chord> subSets = subSetCalculator.getSubSets("6-27", "3-3");
            List<Chord> chords = subSets.stream().filter(chord -> chord.contains(pitchClass)).collect(Collectors.toList());
            map.put(pitchClass, chords);
        }
        for (Map.Entry<Integer, List<Chord>> entry : map.entrySet()) {
            System.out.println(entry.getKey());
            for (Chord subSet : entry.getValue()) {
                System.out.print(subSet.getForteName());
                System.out.print(subSet.getChordType());
                System.out.print(subSet.getPitchClassSet());
            }
            System.out.println();
        }
    }
}