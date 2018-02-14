package cp.config;

import cp.combination.RhythmCombination;
import cp.model.twelve.BuilderType;

import java.util.ArrayList;
import java.util.List;

public class ScaleConfig {

    private int start;
    private int end;
    private List<Integer> beats = new ArrayList<>();
    private RhythmCombination[] rhythmCombinations;
    private int repeat;
    private int length;
    private int[] pitchClasses;
    private BuilderType builderType;

    private List<Integer> splitVoices = new ArrayList<>();

    public ScaleConfig(List<Integer> durationBeats, int repeat, int[] pitchClasses, BuilderType builderType, RhythmCombination... rhythmCombinations) {
        for (int i = 0; i < repeat; i++) {
            beats.add(durationBeats.get(i % durationBeats.size()));
        }
        this.rhythmCombinations = rhythmCombinations;
        this.repeat = repeat;
        this.pitchClasses = pitchClasses;
        this.builderType = builderType;
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

    public int[] getPitchClasses() {
        return pitchClasses;
    }

    public List<Integer> getBeats() {
        return beats;
    }

    public void setBeats(List<Integer> beats) {
        this.beats = beats;
    }

    public List<Integer> getSplitVoices() {
        return splitVoices;
    }

    public void setSplitVoices(List<Integer> splitVoices) {
        this.splitVoices = splitVoices;
    }

    public BuilderType getBuilderType() {
        return builderType;
    }
}
