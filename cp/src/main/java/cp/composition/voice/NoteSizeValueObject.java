package cp.composition.voice;

import cp.combination.RhythmCombination;

/**
 * Created by prombouts on 12/06/2017.
 */
public class NoteSizeValueObject {

    private Integer key;
    private  RhythmCombination rhythmCombination;

    public NoteSizeValueObject(Integer key, RhythmCombination rhythmCombination) {
        this.key = key;
        this.rhythmCombination = rhythmCombination;
    }

    public Integer getKey() {
        return key;
    }

    public RhythmCombination getRhythmCombination() {
        return rhythmCombination;
    }

}
