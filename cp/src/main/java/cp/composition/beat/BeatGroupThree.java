package cp.composition.beat;

public class BeatGroupThree extends BeatGroup {

	public BeatGroupThree(int length) {
		super(length);
	}

	@Override
	public int getType() {
		return 3;
	}

	@Override
	public BeatGroupThree clone(double times) {
		return new BeatGroupThree((int) (length * times));
	}

}
