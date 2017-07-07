package cp.model.dissonance;

import cp.model.harmony.Chord;
import org.springframework.stereotype.Component;

/**
 * Created by prombouts on 16/11/2016.
 */
@Component(value = "tonalSetClassDissonance")
public class TonalSetClassDissonance implements Dissonance {
    @Override
    public double getDissonance(Chord chord) {
        int size = chord.getPitchClassSet().size();
        switch (size){
            case 2:
//                return dyadic(chord);
                return 0;
            case 3:
                return trichordal(chord);
            case 4:
                return tetrachordal(chord);
            case 5:
                return pentaChordal(chord);
        }
        return 0;
    }

    private double pentaChordal(Chord chord) {
        switch (chord.getForteName()) {
            case "5-27":
                return 0.99;
            case "5-31":
                return 0.99;
            case "5-34":
                return 0.99;
            case "5-35":
                return 0.99;
        }
        return 0;
    }

    private double tetrachordal(Chord chord) {
        switch (chord.getForteName()) {
            case "4-22"://add9
                return 1.0;
            case "4-23"://sus9
                return 1.0;
            case "4-25"://dom7b5
                return 1.0;
            case "4-26"://m7
                return 1.0;
            case "4-27"://dom7, halfdim7
                return 1.0;
            case "4-28"://dim
                return 1.0;
        }

        return 0;
    }

    private double dyadic(Chord chord) {
        switch (chord.getForteName()) {
            case "2-1":
                return 0.55;
            case "2-2":
                return 0.55;
            case "2-3":
                return 0.65;
            case "2-4":
                return 0.6;
            case "2-5":
                return 0.6;
            case "2-6":
                return 0.6;
        }
        return 0;
    }

    private double trichordal(Chord chord) {
        switch (chord.getForteName()) {
            case "3-1":
                return 0.80;
            case "3-2":
                return 0.88;
            case "3-3":
                return 0.98;
            case "3-4":
                return 0.99;
            case "3-5":
                return 0.88;
            case "3-6":
                return 0.99;
            case "3-7":
                return 0.99;
            case "3-8":
                return 0.99;
            case "3-9":
                return 0.99;
            case "3-10":
                return 1.0;
            case "3-11":
                return 1.0;
            case "3-12":
                return 0.99;
        }
        return 0;
    }




}
