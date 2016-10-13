package cp.model.dissonance;

import cp.model.harmony.Chord;
import org.springframework.stereotype.Component;

@Component(value="TonalDissonance")
public class TonalDissonance implements Dissonance {

	@Override
	public double getDissonance(Chord chord) {
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
				return 0.95;
			case DOM:
				return 1.0;
			case CH0:
				return 0.0;
			case CH1:
				return 0.0;
//			case CH2:
//				return 0.0;
			case CH3:
				return 0.0;
			case CH4:
				return 0.0;
			case CH5:
				return 0.0;
			case MAJOR7:
				return 0.99;
			case MAJOR7_1:
				return 0.98;	
			case MAJOR7_2:
				return 0.97;	
			case MAJOR7_3:
				return 0.95;	
			case MINOR7:
				return 0.99;
			case MINOR7_1:
				return 0.98;
			case MINOR7_2:
				return 0.97;
			case MINOR7_3:
				return 0.97;
			case DOM7:
				return 1.0;	
			case HALFDIM7:
				return 0.98;	
			case DIM7:
				return 0.98;	
		}
		return 0.0;
	}

}
