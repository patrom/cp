package cp.composition.beat;

public class BeatGroupTwo extends BeatGroup {

	public BeatGroupTwo(int length) {
		super(length);
	}

	@Override
	public BeatGroup clone(int length) {
		return new BeatGroupTwo(length);
	}

	@Override
	public int getType() {
		return 2;
	}
}
