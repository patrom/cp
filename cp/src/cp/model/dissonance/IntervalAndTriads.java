package cp.model.dissonance;

import org.springframework.stereotype.Component;

import cp.model.harmony.Chord;

@Component
public class IntervalAndTriads {
	
	public double getDissonance(Chord chord) {
		int size = chord.getPitchClassSet().size();
		if (size < 3) {
			return intervals(chord);
		} else {
			return triads(chord);
		}
	}
	
	private double triads(Chord chord){
		switch (chord.getChordType()) {
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
				return 0.97;
			case DOM:
				return 1.0;
			case KWARTEN:
				return 0.98;
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

	private double intervals(Chord chord) {
		switch (chord.getChordType()) {
			case CH2_GROTE_TERTS:
				return 1.0;
			case CH2_KLEINE_TERTS:
				return 1.0;
			case CH2_GROTE_SIXT:
				return 1.0;
			case CH2_KLEINE_SIXT:
				return 1.0;
				
			case CH1://octaaf
				return 0.6;
			case CH2_KWART:
				return 0.7;
			case CH2_KWINT:
				return 0.8;
			
			case CH2_GROTE_SECONDE:
				return 0.4;
			case CH2_GROOT_SEPTIEM:
				return 0.6;
			case CH2_KLEIN_SEPTIEM:
				return 0.7;
			case CH2_KLEINE_SECONDE:
				return 0.3;
			case CH2_TRITONE:
				return 0.7;
		}
		return 0;
	}

}


