package cp.rhythm;

import cp.combination.RhythmCombination;

public class RhythmCombinationVO {

    private int size;
    private RhythmCombination rhythmCombination;

    public RhythmCombinationVO(int size, RhythmCombination rhythmCombination) {
        this.size = size;
        this.rhythmCombination = rhythmCombination;
    }

    public int getSize() {
        return size;
    }

    public RhythmCombination getRhythmCombination() {
        return rhythmCombination;
    }
}
