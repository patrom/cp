package cp.rhythm;

import cp.combination.RhythmCombination;
import cp.util.RandomUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.*;

public class CompleteBeatGroupRhythm implements BeatGroupRhythm {

    protected static final Logger LOGGER = LoggerFactory.getLogger(CompleteBeatGroupRhythm.class);

    private Map<Integer, List<RhythmCombination>> combinations;
    private Map<Integer, List<RhythmCombination>> combinationsCopy;
    private Set<Integer> keys;

    public CompleteBeatGroupRhythm(Map<Integer, List<RhythmCombination>> combinations) {
        this.combinations = combinations;
        this.combinationsCopy = deepClone(combinations);
        keys = new HashSet<>(combinations.keySet());
    }

    @Override
    public RhythmCombinationVO getRandomRhythmNotesForBeatgroupType() {
        if (keys.isEmpty()) {
            keys = new HashSet<>(combinations.keySet());
        }
        Object[] keyObjects = keys.toArray();
        Integer size = (Integer) keyObjects[new Random().nextInt(keyObjects.length)];
        List<RhythmCombination> rhythmCombinations = combinationsCopy.get(size);
        if (rhythmCombinations.size() == 1) {
            keys.remove(size);
        }
        RhythmCombination rhythmCombination = RandomUtil.getRandomFromList(rhythmCombinations);
        rhythmCombinations.remove(rhythmCombination);
        if (rhythmCombinations.isEmpty()) {
            List<RhythmCombination> rhythmCombinationList = new ArrayList<>(this.combinations.get(size));
            combinationsCopy.put(size, rhythmCombinationList);
        }
        return new RhythmCombinationVO(size, rhythmCombination);
    }

    @Override
    public RhythmCombinationVO getRandomRhythmNotesForBeatgroupType(Integer size) {
        if (!keys.contains(size)) {
            keys = new HashSet<>(combinations.keySet());
        }
        List<RhythmCombination> rhythmCombinations = this.combinationsCopy.get(size);
        if(rhythmCombinations == null){
            LOGGER.info("No (provided) combination found for size: " + size);
            return null;
        }
        RhythmCombination rhythmCombination = RandomUtil.getRandomFromList(rhythmCombinations);
        rhythmCombinations.remove(rhythmCombination);
        if (rhythmCombinations.isEmpty()) {
            List<RhythmCombination> rhythmCombinationList = new ArrayList<>(this.combinations.get(size));
            combinationsCopy.put(size, rhythmCombinationList);
        }
        return new RhythmCombinationVO(size, rhythmCombination);
    }


    private Map<Integer, List<RhythmCombination>> deepClone(Map<Integer, List<RhythmCombination>> original) {
        HashMap<Integer, List<RhythmCombination>> copy = new HashMap<Integer, List<RhythmCombination>>();
        for (Map.Entry<Integer, List<RhythmCombination>> entry : original.entrySet()) {
            copy.put(entry.getKey(), new ArrayList<RhythmCombination>(entry.getValue()));
        }
        return copy;
    }
}
