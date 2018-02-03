package cp.model.twelve;

import java.util.Objects;

public class TwelveToneSplit implements Comparable<TwelveToneSplit>{

    private Integer voice;
    private boolean split;
    private int splitVoice;

    public TwelveToneSplit(int voice, boolean split, int splitVoice) {
        this.voice = voice;
        this.split = split;
        this.splitVoice = splitVoice;
    }

    public boolean isSplit() {
        return split;
    }

    public void setSplit(boolean split) {
        this.split = split;
    }

    public int getSplitVoice() {
        return splitVoice;
    }

    public void setSplitVoice(int splitVoice) {
        this.splitVoice = splitVoice;
    }

    public int getVoice() {
        return voice;
    }

    public void setVoice(int voice) {
        this.voice = voice;
    }

    @Override
    public int compareTo(TwelveToneSplit twelveToneSplit) {
        return this.voice.compareTo(twelveToneSplit.getVoice());
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof TwelveToneSplit)) return false;
        TwelveToneSplit that = (TwelveToneSplit) o;
        return Objects.equals(getVoice(), that.getVoice());
    }

    @Override
    public int hashCode() {

        return Objects.hash(getVoice());
    }
}
