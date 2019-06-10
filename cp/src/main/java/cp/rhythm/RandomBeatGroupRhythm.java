package cp.rhythm;

import cp.combination.RhythmCombination;
import cp.util.RandomUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.Map;
import java.util.Random;

public class RandomBeatGroupRhythm implements BeatGroupRhythm {

    protected static final Logger LOGGER = LoggerFactory.getLogger(RandomBeatGroupRhythm.class);

    private Map<Integer, List<RhythmCombination>> combinations;

    public RandomBeatGroupRhythm(Map<Integer, List<RhythmCombination>> combinations) {
        this.combinations = combinations;
    }

    @Override
    public RhythmCombinationVO getRandomRhythmNotesForBeatgroupType() {
        Object[] keys = combinations.keySet().toArray();
        Integer size = (Integer) keys[new Random().nextInt(keys.length)];
        List<RhythmCombination> rhythmCombinations = combinations.get(size);
        RhythmCombination rhythmCombination = RandomUtil.getRandomFromList(rhythmCombinations);
        return new RhythmCombinationVO(size, rhythmCombination);
    }

    @Override
    public RhythmCombinationVO getRandomRhythmNotesForBeatgroupType(Integer size) {
        List<RhythmCombination> rhythmCombinations = this.combinations.get(size);
        if(rhythmCombinations == null){
            LOGGER.info("No (provided) combination found for size: " + size);
            return null;
        }
        RhythmCombination rhythmCombination = RandomUtil.getRandomFromList(rhythmCombinations);
        return new RhythmCombinationVO(size, rhythmCombination);
    }
}
