package cp.model.dissonance;

import cp.model.harmony.CpHarmony;
import org.springframework.stereotype.Component;

@Component(value="AllTriads")
public class AllTriads implements Dissonance {

	@Override
	public double getDissonance(CpHarmony harmony) {
		switch (harmony.getChord().getChordType()) {
			case MAJOR:
				return 1.0;
			case MAJOR_1:
				return 0.98;
			case MAJOR_2:
				return 0.97;
			case MINOR:
				return 1.0;
			case MINOR_1:
				return 0.98;
			case MINOR_2:
				return 0.97;
			case DIM:
				return 0.99;
			case AUGM:
				return 1.0;
			case DOM:
				return 1.0;
			case KWARTEN:
				return 1.0;
			case ADD9:
				return 1.0;
			case MAJOR7_OMIT5:
				return 1.0;
			case MINOR7_OMIT5:
				return 1.0;
			case MINOR7_OMIT5_1:
				return 0.99;
			default:
				break;
		}
		return 0.0;
	}
}
