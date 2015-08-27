package cp.model.dissonance;

import org.springframework.stereotype.Component;

import cp.model.harmony.Chord;

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
			return 0.99;
		case "3-11"://min/maj
			return 0.995;
		case "3-12"://aug
			return 0.99;
	}
	return 0;
	}

}
