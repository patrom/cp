package cp.generator.rhythm;

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
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = {DefaultConfig.class, VariationConfig.class, BeatGroupConfig.class})
@TestPropertySource(properties = "composition.voices=4")
class SchillingerTest {

    @Autowired
    private Schillinger schillinger;

    @Test
    void uniformBinarySynchronization() {
        List<Integer> integers = schillinger.uniformBinarySynchronization(4);
        integers.forEach(integer -> System.out.println(integer + ", "));
    }

    @Test
    void nonUniformBinarySynchronization() {
        List<Integer> integers = schillinger.nonUniformBinarySynchronization(7,4);
        integers.forEach(integer -> System.out.print(integer + ", "));
    }

    @Test
    void fractioning() {
        Set<Integer> integers = schillinger.fractioning(6,5);
        integers.forEach(integer -> System.out.print(integer + ", "));
    }
}