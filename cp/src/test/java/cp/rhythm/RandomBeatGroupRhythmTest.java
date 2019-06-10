package cp.rhythm;

import cp.DefaultConfig;
import cp.VariationConfig;
import cp.combination.RhythmCombination;
import cp.config.BeatGroupConfig;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = {DefaultConfig.class, VariationConfig.class, BeatGroupConfig.class})
@ExtendWith(SpringExtension.class)
public class RandomBeatGroupRhythmTest {

    private RandomBeatGroupRhythm randomBeatGroupRhythm;

    @Resource(name = "defaultEvenCombinations")
    private Map<Integer, List<RhythmCombination>> defaultEvenCombinations;

    @BeforeEach
    public void setUp() throws Exception {
        randomBeatGroupRhythm = new RandomBeatGroupRhythm(defaultEvenCombinations);
    }

    @Test
    void getRandomRhythmNotesForBeatgroupType() {
        RhythmCombinationVO randomBeatGroupRhythmVO = randomBeatGroupRhythm.getRandomRhythmNotesForBeatgroupType();
        RhythmCombination rhythmCombination = randomBeatGroupRhythmVO.getRhythmCombination();
        Assertions.assertNotNull(rhythmCombination);
        System.out.println(randomBeatGroupRhythmVO.getSize());
;    }
}