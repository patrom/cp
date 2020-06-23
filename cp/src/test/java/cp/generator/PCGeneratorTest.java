package cp.generator;

import cp.DefaultConfig;
import cp.VariationConfig;
import cp.config.BeatGroupConfig;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = {DefaultConfig.class, VariationConfig.class, BeatGroupConfig.class})
@TestPropertySource(properties = "composition.voices=4")
class PCGeneratorTest {

    @Autowired
    private PCGenerator pcGenerator;

    @Test
    public void getPitchClasses2() {
        List<Integer> pitchClasses = pcGenerator.getPitchClasses("8-23", 4);
        pitchClasses.forEach(integer -> System.out.print(integer + ", "));
        System.out.println("----");
        pitchClasses = pcGenerator.getInversionPitchClasses("8-23", 2);
        pitchClasses.forEach(integer -> System.out.print(integer + ", "));
    }

    @Test
    public void getPitchClasses4() {
        List<Integer> pitchClasses = pcGenerator.getPitchClasses("6-32", 4);
        pitchClasses.forEach(integer -> System.out.print(integer + ", "));
        System.out.println("----");
        pitchClasses = pcGenerator.getInversionPitchClasses("6-32", 4);
        pitchClasses.forEach(integer -> System.out.print(integer + ", "));
    }

    @Test
    public void getPitchClasses() {
        List<Integer> pitchClasses = pcGenerator.getPitchClasses("3-2", 0);
        assertIterableEquals(Stream.of(0,1,3).collect(Collectors.toList()), pitchClasses);
    }

    @Test
    public void getPitchClasses3() {
        List<Integer> pitchClasses = pcGenerator.getPitchClasses("7-Z37", 0);
        pitchClasses.forEach(integer -> System.out.print(integer + ", "));
    }

    @Test
    public void getInversionPitchClasses() {
        List<Integer> pitchClasses = pcGenerator.getInversionPitchClasses("3-2", 0);
        assertIterableEquals(Stream.of(3,2,0).collect(Collectors.toList()), pitchClasses);
    }

    @Test
    public void getShuffledPitchClasses() {
        List<Integer> pitchClasses = pcGenerator.getShuffledPitchClasses("3-2", 0);
        pitchClasses.forEach(integer -> System.out.print(integer + ", "));
    }

    @Test
    public void getRepetitionPitchClasses() {
        List<Integer> pitchClasses = pcGenerator.getRandomRepetitionPitchClasses("4-21", 1, 0);
        pitchClasses.forEach(integer -> System.out.print(integer + ", "));
    }

    @Test
    public void getOrderedRepetitionPitchClasses() {
        List<Integer> pitchClasses = pcGenerator.getOrderedRepetitionPitchClasses("4-2",  2, 0);
        pitchClasses.forEach(integer -> System.out.print(integer + ", "));
    }
}