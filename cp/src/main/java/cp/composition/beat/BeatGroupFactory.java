package cp.composition.beat;

import org.springframework.stereotype.Component;

@Component
public class BeatGroupFactory {

	public BeatGroup getBeatGroupUneven(int length, int size) {
        return new BeatGroupThree(length, size);
	}

	public BeatGroup getBeatGroupEven(int length, int size) {
		return new BeatGroupTwo(length, size);
	}

}
