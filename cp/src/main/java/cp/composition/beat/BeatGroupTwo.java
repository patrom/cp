package cp.composition.beat;

public class BeatGroupTwo extends BeatGroup {

	public BeatGroupTwo(int length) {
		super(length);
	}

	@Override
	public int getType() {
		return 2;
	}

	@Override
	public BeatGroupTwo clone(double times) {
		return new BeatGroupTwo((int) (length * times));
	}
}
