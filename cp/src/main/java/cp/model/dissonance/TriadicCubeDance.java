package cp.model.dissonance;

import cp.model.harmony.CpHarmony;
import org.springframework.stereotype.Component;

@Component(value="TriadicCubeDance")
public class TriadicCubeDance implements Dissonance {

	@Override
	public double getDissonance(CpHarmony harmony) {
		switch (harmony.getChord().getChordType()) {
			case MAJOR:
				return 1.0;
			case MINOR:
				return 1.0;
			case AUGM:
				return 1.0;
		}
		return 0.0;
	}

}
