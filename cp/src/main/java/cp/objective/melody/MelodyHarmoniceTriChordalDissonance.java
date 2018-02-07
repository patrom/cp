package cp.objective.melody;

import cp.model.harmony.Chord;
import org.springframework.stereotype.Component;

@Component
public class MelodyHarmoniceTriChordalDissonance implements MelodyHarmonicDissonance{

    public double getMelodicValue(Chord chord) {
        switch (chord.getForteName()) {

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
//            case "3-10":
//                return 1.0;
            case "3-11":
                return 1.0;
//			case "3-12":
//				return 0.9;
        }
        return 0;
    }

    @Override
    public int getChordSize() {
        return 3;
    }
}
