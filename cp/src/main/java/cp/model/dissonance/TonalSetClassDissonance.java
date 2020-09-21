package cp.model.dissonance;

import cp.model.harmony.Chord;
import cp.model.harmony.CpHarmony;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

/**
 * Created by prombouts on 16/11/2016.
 */
@Component(value = "tonalSetClassDissonance")
public class TonalSetClassDissonance implements Dissonance {

    private static final Logger LOGGER = LoggerFactory.getLogger(TonalSetClassDissonance.class.getName());

    @Override
    public double getDissonance(CpHarmony harmony) {
        if (harmony.containsMinorSecondOrb9()) {
           return 0;
        }
        int size = harmony.getChord().getPitchClassSet().size();
        switch (size){
            case 2:
                return 0;
//                return dyadic(harmony.getChord());
            case 3:
                return 0;
//                return trichordal(harmony.getChord());
            case 4:
                return tetrachordal(harmony.getChord());
            case 5:
                return pentaChordal(harmony.getChord());
            case 6:
                return hexaChordal(harmony.getChord());
        }
        return 0;
    }

    private double pentaChordal(Chord chord) {
        switch (chord.getForteName()) {
            case "5-23":
                return 1.0;
            case "5-27":
                return 1.0;
            case "5-28":
                return 1.0;
            case "5-29":
                return 1.0;
            case "5-32":
                return 1.0;
            case "5-34":
                return 1.0;
            case "5-35":
                return 1.0;
        }
        return 0;
    }

    private double tetrachordal(Chord chord) {
        switch (chord.getForteName()) {
//            case "4-3":
//                return 1.0;
            case "4-8":
                return 1.0;
            case "4-16":
                return 1.0;

//            case "4-14"://m add9 - major add 11
//                return 0.99;
//            case "4-20"://major7
//                return 1.0;
            case "4-22"://add9
                return 1.0;
            case "4-23"://sus9
                return 1.0;
            case "4-25"://dom7b5
                return 1.0;
            case "4-26"://m7
                return 0.99;
            case "4-27"://dom7, halfdim7
                return 1.0;
            case "4-28"://dim
                return 1.0;
        }

        return 0;
    }

    private double dyadic(Chord chord) {
        switch (chord.getForteName()) {
//            case "2-1":
//                return 0.8;
//            case "2-2":
//                return 0.9;
//            case "2-3":
//                return 0.8;
//            case "2-4":
//                return 1.0;
            case "2-5":
                return 0.9;
            case "2-6":
                return 0.9;
        }
        return 0;
    }

    private double trichordal(Chord chord) {
        switch (chord.getForteName()) {
//            case "3-1":
//                return 0.98;
//            case "3-2":
//                return 0.9;
//            case "3-3":
//                return 0.95;
//            case "3-4":
//                return 0.98;
            case "3-5":
                return 1.0;
//            case "3-6":
//                return 0.97;
//            case "3-7":
//                return 0.98;
//            case "3-8":
//                return 0.8;
            case "3-9":
                return 1.0;
//            case "3-10":
//                return 0.9;
//            case "3-11":
//                return 0.98;
//            case "3-12":
//                return 0.9;
        }
        return 0;
    }

    private double hexaChordal(Chord chord) {
        switch (chord.getForteName()) {
           case "6-Z28":
                return 1.0;
            case "6-Z49":
                return 1.0;
        }
        return 0;
    }


}
