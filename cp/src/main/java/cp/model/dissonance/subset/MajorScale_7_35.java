package cp.model.dissonance.subset;

import cp.model.dissonance.Dissonance;
import cp.model.harmony.Chord;
import cp.model.harmony.CpHarmony;
import org.springframework.stereotype.Component;

@Component
public class MajorScale_7_35 implements Dissonance {

    @Override
    public double getDissonance(CpHarmony harmony) {
        int size = harmony.getChord().getPitchClassSet().size();
        switch (size){
            case 2:
                return dyadic(harmony.getChord());
            case 3:
                return triChordal(harmony.getChord());
            case 4:
                return tetrachordal(harmony.getChord());
            case 5:
                return pentaChordal(harmony.getChord());
            case 6:
                return hexaChordal(harmony.getChord());
        }
        return 0;
    }

    private double dyadic(Chord chord) {
        switch (chord.getForteName()) {
            case "2-1":
                return 0.0;
            case "2-2":
                return 0.0;
            case "2-3":
                return 1.0;
            case "2-4":
                return 1.0;
            case "2-5":
                return 1.0;
            case "2-6":
                return 0.0;
        }
        return 0;
    }

    private double triChordal(Chord chord) {
        switch (chord.getForteName()) {
            case "3-2":
                return 0.0;
            case "3-4":
                return 0.0;
            case "3-5":
                return 0.0;
            case "3-6":
                return 1.0;
            case "3-7":
                return 1.0;
            case "3-8":
                return 1.0;
            case "3-9":
                return 0.0;
            case "3-10":
                return 0.0;
            case "3-11":
                return 0.0;
        }
        return 0;
    }

    private double tetrachordal(Chord chord) {
        switch (chord.getForteName()) {
            case "4-8":
                return 0.0;
            case "4-10":
                return 0.0;
            case "4-11":
                return 0.0;
            case "4-13":
                return 0.0;
            case "4-14":
                return 0.0;
            case "4-16":
                return 0.0;
            case "4-20":
                return 0.0;
            case "4-21":
                return 0.0;
            case "4-22"://add9
                return 0.0;
            case "4-23"://sus9
                return 0.0;
            case "4-26"://m7
                return 0.0;
            case "4-27"://dom7, halfdim7
                return 0.0;
            case "4-29":
                return 0.0;
        }

        return 0;
    }

    private double pentaChordal(Chord chord) {
        switch (chord.getForteName()) {
            case "5-12":
                return 0.0;
            case "5-20":
                return 0.0;
            case "5-23":
                return 0.0;
            case "5-24":
                return 0.0;
            case "5-25":
                return 0.0;
            case "5-27":
                return 0.0;
            case "5-29":
                return 0.0;
            case "5-34":
                return 0.0;
            case "5-35":
                return 0.0;
        }
        return 0;
    }

    private double hexaChordal(Chord chord) {
        switch (chord.getForteName()) {
            case "6-25":
                return 0.0;
            case "6-26":
                return 0.0;
            case "6-32":
                return 0.0;
            case "6-33":
                return 0.0;
        }
        return 0;
    }

}


