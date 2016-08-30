package cp.model.dissonance;

import org.springframework.stereotype.Component;

import cp.model.harmony.Chord;

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
	public double getDissonance(Chord chord) {
		switch (chord.getForteName()) {
			
			case "2-1":
				return 1.0;
			case "2-2":
				return 0.9;
			case "2-3":
				return 1.0;
			case "2-4":
				return 1.0;
			case "2-5":
				return 0.9;
		
		
		
			case "3-1":
				return 1.0;
			case "3-2":
				return 0.9;
			case "3-3":
				return 1.0;//Webern op. 24
			case "3-4":
				return 0.9;
			case "3-5":
				return 1.0;
			case "3-6":
				return 0.9;
			case "3-7":
				return 0.9;
			case "3-8":
				return 0.9;
			case "3-9":
				return 0.9;
			case "3-10":
				return 0.9;
			case "3-11":
				return 0.99;
			case "3-12":
				return 0.9;
		}
		return 0;
	}

}
