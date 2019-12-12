package cp.rhythm;

import cp.DefaultConfig;
import cp.VariationConfig;
import cp.combination.RhythmCombination;
import cp.config.BeatGroupConfig;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.RepeatedTest;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = {DefaultConfig.class, VariationConfig.class, BeatGroupConfig.class})
@TestPropertySource(properties = "composition.voices=4")
class CompleteBeatGroupRhythmTest {

    @Autowired
    public BeatGroupRhythm testCompleteBeatGroupRhythm;

    @RepeatedTest(value = 10)
    void getRandomRhythmNotesForBeatgroupType() {
        RhythmCombinationVO randomBeatGroupRhythmVO = testCompleteBeatGroupRhythm.getRandomRhythmNotesForBeatgroupType();
        RhythmCombination rhythmCombination = randomBeatGroupRhythmVO.getRhythmCombination();
        Assertions.assertNotNull(rhythmCombination);
        System.out.println(randomBeatGroupRhythmVO.getSize());
    }

    @RepeatedTest(value = 10)
    void getRandomRhythmNotesForBeatgroupType1() {
            RhythmCombinationVO randomBeatGroupRhythmVO = testCompleteBeatGroupRhythm.getRandomRhythmNotesForBeatgroupType(2);
            RhythmCombination rhythmCombination = randomBeatGroupRhythmVO.getRhythmCombination();
            Assertions.assertNotNull(rhythmCombination);
            System.out.println(randomBeatGroupRhythmVO.getSize());
    }

    @RepeatedTest(value = 10)
    void getRandomRhythmNotesForBeatgroupTypeAll() {
        RhythmCombinationVO randomBeatGroupRhythmVO = testCompleteBeatGroupRhythm.getRandomRhythmNotesForBeatgroupType();
        RhythmCombination rhythmCombination = randomBeatGroupRhythmVO.getRhythmCombination();
        Assertions.assertNotNull(rhythmCombination);
        System.out.println(randomBeatGroupRhythmVO.getSize());

        randomBeatGroupRhythmVO = testCompleteBeatGroupRhythm.getRandomRhythmNotesForBeatgroupType(2);
        rhythmCombination = randomBeatGroupRhythmVO.getRhythmCombination();
        Assertions.assertNotNull(rhythmCombination);
        System.out.println(randomBeatGroupRhythmVO.getSize());
    }
}