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
    public void getPitchClasses() {
        List<Integer> pitchClasses = pcGenerator.getPitchClasses("3-2", 2);
        assertIterableEquals(Stream.of(2,3,5).collect(Collectors.toList()), pitchClasses);
    }

    @Test
    public void getInversionPitchClasses() {
        List<Integer> pitchClasses = pcGenerator.getInversionPitchClasses("3-2", 2);
        assertIterableEquals(Stream.of(5,4,2).collect(Collectors.toList()), pitchClasses);
    }

    @Test
    public void getShuffledPitchClasses() {
        List<Integer> pitchClasses = pcGenerator.getShuffledPitchClasses("3-2", 2);
        pitchClasses.forEach(integer -> System.out.print(integer + ", "));
    }

    @Test
    public void getRepetitionPitchClasses() {
        List<Integer> pitchClasses = pcGenerator.getRandomRepetitionPitchClasses("3-2", 2, 2);
        pitchClasses.forEach(integer -> System.out.print(integer + ", "));
    }

    @Test
    public void getOrderedRepetitionPitchClasses() {
        List<Integer> pitchClasses = pcGenerator.getOrderedRepetitionPitchClasses("4-2", 0, 2);
        pitchClasses.forEach(integer -> System.out.print(integer + ", "));
    }
}