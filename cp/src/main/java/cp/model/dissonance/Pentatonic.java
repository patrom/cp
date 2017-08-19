package cp.model.dissonance;

import cp.model.harmony.Chord;
import org.springframework.stereotype.Component;

@Component(value="Pentatonic")
public class Pentatonic implements Dissonance{

	@Override
	public double getDissonance(Chord chord) {
		switch (chord.getForteName()) {
		case "3-6"://maj add9
			return 1.0;
		case "3-7"://m7 /m11
			return 1.0;
		case "3-9":// sus9/11
			return 1.0;
		case "3-10"://dim
			return 0.9;
		case "3-11"://min/maj
			return 0.9;
		case "3-12"://aug
			return 0.9;
	}
	return 0;
	}

}
