package cp.composition.beat;

import org.springframework.stereotype.Component;

@Component
public class BeatGroupFactory {

	public BeatGroup getBeatGroupUneven(int length) {
        return new BeatGroupThree(length);
	}

	public BeatGroup getBeatGroupEven(int length) {
		return new BeatGroupTwo(length);
	}

}
