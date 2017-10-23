package cp.model.dissonance;

import cp.model.harmony.Chord;
import cp.model.harmony.CpHarmony;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component(value="TonalDissonance")
public class TonalDissonance implements Dissonance {

	private static final Logger LOGGER = LoggerFactory.getLogger(TonalDissonance.class.getName());

	@Override
	public double getDissonance(CpHarmony harmony) {
		int size = harmony.getChord().getPitchClassSet().size();
		switch (size){
			case 2:
//				return dyadic(harmony.getChord());
                return 0;
			case 3:
				return trichordal(harmony.getChord());
//                return 0;
			case 4:
				return tetrachordal(harmony.getChord());
//                return 0;
			case 5:
				return pentaChordal(harmony);
			case 6:
//                LOGGER.info("6 chord");
				return 0;
		}
		return 0;
	}

	private double pentaChordal(CpHarmony harmony) {
		switch (harmony.getChord().getForteName()) {
			case "5-27"://major9
				switch (harmony.getLowestChord().getChordType()){
					case MAJOR:
					case MAJOR7_OMIT5:
//						LOGGER.info("major9");
						return 1.0;
				}
				return 0.8;
			case "5-28"://b9 #11
				switch (harmony.getLowestChord().getChordType()){
					case MAJOR:
					case MAJOR7_OMIT5:
//						LOGGER.info("b9 #11");
						return 1.0;
				}
				return 0.8;
			case "5-31": //dim
				return 0.9;
			case "5-34": //b9
				switch (harmony.getLowestChord().getChordType()){
					case MAJOR:
					case DOM:
//						LOGGER.info("b9");
						return 1.0;
				}
				return 0.8;
			case "5-35"://min 11 / 6/9
				switch (harmony.getLowestChord().getChordType()){
					case MAJOR:
					case MINOR:
					case MINOR7_OMIT5:
//						LOGGER.info("min 11 / 6/9");
						return 1.0;
				}
				return 0.8;
		}
		return 0;
	}

	private double tetrachordal(Chord chord) {
		switch (chord.getChordType()) {
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

		return 0;
	}

	private double dyadic(Chord chord) {
		switch (chord.getChordType()) {
			case CH2_KLEINE_TERTS:
				return 0.9;
			case CH2_GROTE_TERTS:
				return 0.9;
			case CH2_GROTE_SIXT:
				return 0.9;
			case CH2_KLEINE_SIXT:
				return 0.9;
			case CH2_KWART:
				return 0.9;
			case CH2_KWINT:
				return 0.9;
		}
		return 0;
	}

	private double trichordal(Chord chord) {
		switch (chord.getChordType()) {
			case MAJOR:
				return 0.9;
			case MAJOR_1:
				return 0.9;
			case MAJOR_2:
				return 0.9;
			case MINOR:
				return 0.9;
			case MINOR_1:
				return 0.9;
			case MINOR_2:
				return 0.9;
			case DIM:
				return 0.9;
			case AUGM:
				return 0.9;
			case DOM:
				return 0.9;
		}
		return 0.0;
	}

}
