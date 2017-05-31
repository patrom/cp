package cp.composition.beat;

public abstract class BeatGroup {

	protected int length;
	protected int size;

    public BeatGroup(int length, int size) {
        this.length = length;
        this.size = size;
    }

    public abstract int getType();

    public int getBeatLength() {
		return getType() * length;
	}

	public int getSize() {
		return size;
	}

}
