package cp.config.map;

import cp.DefaultConfig;
import cp.VariationConfig;
import cp.config.BeatGroupConfig;
import cp.model.melody.CpMelody;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = {DefaultConfig.class, VariationConfig.class, BeatGroupConfig.class})
@TestPropertySource(properties = "composition.voices=4")
class MelodyMapTest {

    @Autowired
    private MelodyMap melodyMap;

    @BeforeEach
    void setUp() {
//        melodyMap = new MelodyMap();
//        melodyMap.setMelodyMapComposition();
    }

    @Test
    void initTonal() {
        CpMelody melody = melodyMap.getExhaustiveMelody(0);
        melody.getNotes().forEach(pc -> System.out.println(pc + ", "));
    }
}