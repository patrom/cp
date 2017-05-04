package cp.composition.beat;

public abstract class BeatGroup {

	protected int length;
	protected int size;

	public BeatGroup(int length) {
		this.length = length;
	}

	public abstract BeatGroup clone(int length);

	public abstract int getType();

//	public int getLength() {
//		return length;
//	}

	public int getBeatLength() {
		return getType() * length;
	}

	public int getSize() {
		return size;
	}

	public void setSize(int size) {
		this.size = size;
	}
}
