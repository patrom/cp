package cp.model.dissonance;

import cp.model.harmony.Chord;
import org.springframework.stereotype.Component;

@Component(value="FourCubeTrio")
public class FourCubeTrio implements Dissonance {

	@Override
	public double getDissonance(Chord chord) {
		switch (chord.getForteName()) {
			case "4-25"://dom7b5
				return 1.0;
			case "4-26"://m7
				return 1.0;
			case "4-27"://dom7, halfdim7
				return 1.0;
			case "4-28"://dim
				return 1.0;
			
			case "3-11":
				return 0.95;
			case "3-12"://aug
				return 1.0;
		}
		return 0;
	}
}
