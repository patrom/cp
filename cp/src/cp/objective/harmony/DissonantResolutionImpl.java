package cp.objective.harmony;

import org.springframework.stereotype.Component;

import cp.model.harmony.Chord;

@Component
public class DissonantResolutionImpl {

	public boolean isDissonant(Chord chord){
		int size = chord.getPitchClassSet().size();
		switch (size) {
			case 2:
				return isIntervalDissonant(chord);
			case 3:
//				return isTriadDissonant(chord);
				return isSetClassDissonant(chord);
			default:
				break;
		}

		return false;
	}

	private boolean isSetClassDissonant(Chord chord) {
		switch (chord.getForteName()) {
//			case "3-1":
			case "3-3":
//			case "3-5":
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
