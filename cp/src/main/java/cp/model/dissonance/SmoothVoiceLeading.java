package cp.model.dissonance;

import cp.model.harmony.Chord;
import org.springframework.stereotype.Component;
@Component(value="SmoothVoiceLeading")
public class SmoothVoiceLeading implements Dissonance {

	@Override
	public double getDissonance(Chord chord) {
		switch (chord.getForteName()) {
			case "3-6":
				return 1.0;
			case "3-1":
				return 1.0;
		}
		return 0;
	}


}
