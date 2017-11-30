package cp.model.humanize;

public class Humanization {

    private int timing;
    private int intonation;
    private int attack;
    private int dynamicPitch;
    private int duration;
    private int velocity;

    private int lengthNote;
    private int interval;
    private boolean longNote;

    public int getTiming() {
        return timing;
    }

    public void setTiming(int timing) {
        this.timing = timing;
    }

    public int getIntonation() {
        return intonation;
    }

    public void setIntonation(int intonation) {
        this.intonation = intonation;
    }

    public int getAttack() {
        return attack;
    }

    public void setAttack(int attack) {
        this.attack = attack;
    }

    public int getDynamicPitch() {
        return dynamicPitch;
    }

    public void setDynamicPitch(int dynamicPitch) {
        this.dynamicPitch = dynamicPitch;
    }

    public int getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    public int getVelocity() {
        return velocity;
    }

    public void setVelocity(int velocity) {
        this.velocity = velocity;
    }

    public int getLengthNote() {
        return lengthNote;
    }

    public void setLengthNote(int lengthNote) {
        this.lengthNote = lengthNote;
    }

    public int getInterval() {
        return interval;
    }

    public void setInterval(int interval) {
        this.interval = interval;
    }

    public boolean isLongNote() {
        return longNote;
    }

    public void setLongNote(boolean longNote) {
        this.longNote = longNote;
    }
}
