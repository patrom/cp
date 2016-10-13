package cp.model.dissonance;

import cp.model.harmony.Chord;
import org.springframework.stereotype.Component;

@Component(value="TriadDissonance")
public class TriadDissonance implements Dissonance{

	@Override
	public double getDissonance(Chord chord) {
		switch (chord.getForteName()) {
		case "3-6":
			return 1.0;
		case "3-9":
			return 1.0;
			
		case "4-14":
			return 1.0;
		case "4-19":
			return 1.0;
		case "4-20":
			return 1.0;
		case "4-22":
			return 1.0;
		case "4-26":
			return 1.0;
			
		case "5-27":
			return 1.0;
		case "5-35":
			return 1.0;
			
		case "6-20":
			return 0.99;
		case "6-32":
			return 0.99;
	}
	return 0;
	}

}
