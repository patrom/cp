package cp.model.dissonance;

import org.springframework.stereotype.Component;

import cp.model.harmony.Chord;
import cp.model.note.Interval;

@Component(value="IntervalDissonance")
public class IntervalDissonance implements Dissonance{

	@Override
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
			return 0.7;
		case CH2_KWART:
			return 0.7;
		case CH2_KWINT:
			return 0.9;
		
		case CH2_GROTE_SECONDE:
			return 0.4;
		case CH2_GROOT_SEPTIEM:
			return 0.5;
		case CH2_KLEIN_SEPTIEM:
			return 0.6;
		case CH2_KLEINE_SECONDE:
			return 0.3;
		case CH2_TRITONE:
			return 0.6;
	}
	return 0.0;
}

}

