package cp.model.dissonance.subset;

import cp.model.dissonance.Dissonance;
import cp.model.harmony.Chord;
import cp.model.harmony.CpHarmony;
import org.springframework.stereotype.Component;

@Component
public class SubSets_6_27 implements Dissonance {

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
                return 0.0;
            case "2-4":
                return 0.0;
            case "2-5":
                return 0.0;
            case "2-6":
                return 0.0;
        }
        return 0;
    }

    private double triChordal(Chord chord) {
        switch (chord.getForteName()) {
            case "3-2":
                return 0.0;
            case "3-3":
                return 0.0;
            case "3-5":
                return 0.0;
            case "3-7":
                return 0.0;
            case "3-8":
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
            case "4-3":
                return 0.0;
            case "4-10":
                return 0.0;
            case "4-12":
                return 0.0;
            case "4-13":
                return 0.0;
            case "4-15":
                return 0.0;
            case "4-17":
                return 0.0;
            case "4-18":
                return 0.0;
            case "4-26"://m7
                return 0.0;
            case "4-27"://dom7, halfdim7
                return 0.0;
            case "4-28"://dim
                return 0.0;
            case "4-29":
                return 0.0;
        }
        return 0;
    }

    private double pentaChordal(Chord chord) {
        switch (chord.getForteName()) {
            case "5-10":
                return 0.0;
            case "5-16":
                return 0.0;
            case "5-25":
                return 0.0;
            case "5-31":
                return 0.0;
            case "5-32":
                return 0.0;
        }
        return 0;
    }
}


