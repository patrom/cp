package cp.model.dissonance;

import cp.model.harmony.Chord;
import cp.model.harmony.CpHarmony;
import org.springframework.stereotype.Component;

/**
 * Created by prombouts on 15/03/2017.
 */
@Component(value="PolyTonality")
public class PolyTonality implements Dissonance {

    /**
     * Polytonal for combination of C and E major
     *
     * @param harmony@return
     */
    public double getDissonance(CpHarmony harmony) {
        int size = harmony.getChord().getPitchClassSet().size();
        switch (size){
            case 2:
//				return intervals(chord);
                return 0;
            case 3:
                return triads(harmony.getChord());
            case 4:
                return tetra(harmony.getChord());
//            case 5:
//                return penta(chord);
        }
        return 0;
    }

    private double tetra(Chord chord) {
        switch (chord.getChordType()) {
            case MAJOR7:
                return 1.0;
            case MAJOR7_1:
                return 1.0;
            case MAJOR7_2:
                return 0.98;
            case MAJOR7_3:
                return 0.98;
            case MINOR7:
                return 1.0;
            case MINOR7_1:
                return 1.0;
            case MINOR7_2:
                return 0.98;
            case MINOR7_3:
                return 0.98;
            case DOM7:
                return 1.0;
            case HALFDIM7:
                return 1.0;
            case DIM7:
                return 1.0;
        }
		return tetraSetclass(chord);
    }

    private double triads(Chord chord){
        switch (chord.getChordType()) {
            case MAJOR:
                return 0.99;
            case MAJOR_1:
                return 0.99;
            case MAJOR_2:
                return 0.98;
            case MINOR:
                return 0.99;
            case MINOR_1:
                return 0.99;
            case MINOR_2:
                return 0.98;
            case DIM:
                return 0.99;
            case AUGM:
                return 0.98;
            case DOM:
                return 1.0;
//            case KWARTEN:
//                return 0.98;
//            case ADD9:
//                return 0.99;
//            case MAJOR7_OMIT5:
//                return 1.0;
//            case MINOR7_OMIT5:
//                return 1.0;
//            case MINOR7_OMIT5_1:
//                return 0.99;
        }
        return 0;
    }


    public double tetraSetclass(Chord chord) {
        switch (chord.getForteName()) {
            case "4-25"://dom7b5
                return 1.0;
            case "4-26"://m7
                return 1.0;
            case "4-27"://dom7, halfdim7
                return 1.0;
            case "4-28"://dim
                return 1.0;

            //passing tones:
            case "4-12": //-> dom7b9
                return 0.99;
			case "4-17": //-> min/maj
				return 0.99;
			case "4-18": //-> min+11
				return 0.99;
			case "4-19": //-> maj7+5/min-maj7
				return 0.99;
			case "4-24": //-> dom7+5
				return 0.99;
			case "4-Z29": //-> maj+11
				return 0.99;
        }
        return 0;
    }
}
