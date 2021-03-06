package cp.objective.harmony;

import cp.model.harmony.Chord;
import cp.model.harmony.CpHarmony;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class DissonantResolutionImpl {

	@Autowired
	private HarmonicObjective harmonicObjective;

	public boolean isDissonant(CpHarmony harmony){
		int size = harmony.getChord().getPitchClassSet().size();
		switch (size) {
			case 1:
			case 2:
				return harmonicObjective.getDissonance().getDissonance(harmony) == 0;
			case 3:
				return harmonicObjective.getDissonance().getDissonance(harmony) == 0;
//				return isTriadDissonant(chord);
//				return isSetClassDissonant(chord);
//				return true;
			case 4:
                return harmonicObjective.getDissonance().getDissonance(harmony) == 0;
//				return isSetClassDissonant(chord);
//				return isTetraDissonant(chord);
			case 5:
				return harmonicObjective.getDissonance().getDissonance(harmony) == 0;
			case 6:
				return harmonicObjective.getDissonance().getDissonance(harmony) == 0;
			default:
				break;
		}
		return false;
	}

	private boolean isPentaDissonant(Chord chord) {
        switch (chord.getForteName()) {
            case "5-27":
            case "5-31":
            case "5-34":
            case "5-35":
                return false;
        }
		return true;
	}

	private boolean isTetraDissonant(Chord chord) {
		switch (chord.getChordType()) {
			case MAJOR7:
			case MAJOR7_1:
//			case MAJOR7_2:
//			case MAJOR7_3:
			case MINOR7:
			case MINOR7_1:
//			case MINOR7_2:
//			case MINOR7_3:
			case DOM7:
			case HALFDIM7:
			case DIM7:
				return false;
			default:
				break;
		}
		return true;
	}

	private boolean isSetClassDissonant(Chord chord) {
		switch (chord.getForteName()) {
//			case "3-1":
//			case "3-3":
//			case "3-5":
			case "4-25"://dom7b5
			case "4-26"://m7
			case "4-27"://dom7, halfdim7
			case "4-28"://dim
				return false;
			default:
				break;
			}
			return true;
	}

	private boolean isTriadDissonant(Chord chord) {
		switch (chord.getChordType()) {
			case MAJOR:
			case MAJOR_1:
			case MINOR:
			case MINOR_1:
			case MINOR7_OMIT5:
			case MINOR7_OMIT5_1:
			case MAJOR7_OMIT5:
				return false;
			default:
				break;
		}
		return true;
	}

	private boolean isIntervalDissonant(Chord chord) {
		switch (chord.getChordType()) {
			case CH2_GROTE_SECONDE:
			case CH2_GROOT_SEPTIEM:
			case CH2_KLEIN_SEPTIEM:
			case CH2_KLEINE_SECONDE:
			case CH2_TRITONE:
			case CH2_KWART:
				return true;
			default:
				break;
		}
		return false;
	}
	
}
