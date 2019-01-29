package cp.objective.melody.subset;

import cp.model.harmony.Chord;
import cp.objective.melody.MelodyHarmonicDissonance;
import org.springframework.stereotype.Component;

@Component
public class MelodyMajorScale_7_35 implements MelodyHarmonicDissonance {

    @Override
    public int getChordSize() {
        return 3;
    }

    @Override
    public double getMelodicValue(Chord chord) {
        int size = chord.getPitchClassSet().size();
        switch (size) {
            case 2:
                return dyadic(chord);
            case 3:
                return triChordal(chord);
            case 4:
                return tetrachordal(chord);
            case 5:
                return pentaChordal(chord);
            case 6:
                return hexaChordal(chord);
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
                return 1.0;
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
                return 0.0;
            case "3-7":
                return 0.0;
            case "3-8":
                return 0.0;
            case "3-9":
                return 0.0;
            case "3-10":
                return 1.0;
            case "3-11":
                return 1.0;
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



