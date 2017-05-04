package cp.composition.beat;

public class BeatGroupThree extends BeatGroup {
	

	public BeatGroupThree(int length) {
		super(length);
	}

	@Override
	public BeatGroup clone(int length) {
		return new BeatGroupThree(length);
	}

	@Override
	public int getType() {
		return 3;
	}

}
