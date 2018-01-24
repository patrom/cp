package cp.config;

import cp.combination.RhythmCombination;
import cp.model.note.Scale;

public class ScaleConfig {

    private int start;
    private int end;
    private int beat;
    private RhythmCombination[] rhythmCombinations;
    private int repeat;
    private int length;
    private Scale scale;

    public ScaleConfig(int beat, int repeat, Scale scale, RhythmCombination... rhythmCombinations) {
        this.beat = beat;
        this.rhythmCombinations = rhythmCombinations;
        this.repeat = repeat;
        this.scale = scale;
    }

    public int getStart() {
        return start;
    }

    public void setStart(int start) {
        this.start = start;
    }

    public int getEnd() {
        return end;
    }

    public void setEnd(int end) {
        this.end = end;
    }

    public int getBeat() {
        return beat;
    }

    public void setBeat(int beat) {
        this.beat = beat;
    }

    public RhythmCombination[] getRhythmCombinations() {
        return rhythmCombinations;
    }

    public void setRhythmCombinations(RhythmCombination[] rhythmCombinations) {
        this.rhythmCombinations = rhythmCombinations;
    }

    public int getRepeat() {
        return repeat;
    }

    public void setRepeat(int repeat) {
        this.repeat = repeat;
    }

    public int getLength() {
        return length;
    }

    public void setLength(int length) {
        this.length = length;
    }

    public Scale getScale() {
        return scale;
    }

    public void setScale(Scale scale) {
        this.scale = scale;
    }
}
