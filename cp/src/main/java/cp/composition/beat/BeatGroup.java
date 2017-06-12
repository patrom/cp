package cp.composition.beat;

public abstract class BeatGroup {

	protected int length;

    public BeatGroup(int length) {
        this.length = length;
    }

    public abstract int getType();

    public int getBeatLength() {
		return getType() * length;
	}

}
