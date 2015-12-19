package cp.model.dissonance;

import org.springframework.stereotype.Component;

import cp.model.harmony.Chord;

@Component
public class IntervalDissonance {

	public double getDissonance(Chord chord) {
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
	return 0.0;
}

}

