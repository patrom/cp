package cp.composition.beat;

public class BeatGroupTwo extends BeatGroup {

	public BeatGroupTwo(int length, int size) {
		super(length, size);
	}

	@Override
	public int getType() {
		return 2;
	}
}
