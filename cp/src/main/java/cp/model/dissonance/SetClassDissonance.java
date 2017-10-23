package cp.model.dissonance;

import cp.model.harmony.CpHarmony;
import org.springframework.stereotype.Component;

@Component(value = "SetClassDissonance")
public class SetClassDissonance implements Dissonance {

	// @Override
	// public double getDissonance(Chord chord) {
	// switch (chord.getForteName()) {
	// case "3-7":
	// return 1.0;
	// case "3-8":
	// return 1.0;
	// case "3-10":
	// return 1.0;
	// case "3-11":
	// return 1.0;
	// case "3-12":
	// return 1.0;
	// }
	// return 0;
	// }

	@Override
	public double getDissonance(CpHarmony harmony) {
		switch (harmony.getChord().getForteName()) {
			
//			case "2-1":
//				return 0.99;
//			case "2-2":
//				return 0.9;
//			case "2-3":
//				return 0.99;
//			case "2-4":
//				return 0.99;
//			case "2-5":
//				return 0.9;

			case "4-4":
				return 0.98;
			case "4-5":
				return 0.98;
			case "4-6":
				return 0.98;
			case "4-7":
				return 0.98;
			case "4-8":
				return 0.98;
			case "4-9":
				return 0.98;
		
		
//			case "3-1":
//				return 1.0;
//			case "3-2":
//				return 0.9;
//			case "3-3":
//				return 1.0;//Webern op. 24
//			case "3-4":
//				return 0.9;
//			case "3-5":
//				return 1.0;
//			case "3-6":
//				return 0.9;
//			case "3-7":
//				return 0.9;
//			case "3-8":
//				return 0.9;
//			case "3-9":
//				return 0.9;
			case "3-10":
				return 1.0;
			case "3-11":
				return 1.0;
//			case "3-12":
//				return 0.9;
		}
		return 0;
	}

}
