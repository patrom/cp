package cp.model.dissonance;

import cp.model.harmony.Chord;
import cp.model.harmony.CpHarmony;
import org.springframework.stereotype.Component;

@Component(value = "symmetryDissonance")
public class SymmetryDissonance implements Dissonance{

    @Override
    public double getDissonance(CpHarmony harmony) {
        int size = harmony.getChord().getPitchClassSet().size();
        switch (size){
            case 2:
                return 0;
            case 3:
                return trichordal(harmony.getChord());
            case 4:
                return tetrachordal(harmony.getChord());
//            case 5:
//                return pentaChordal(chord);
        }
        return 0;
    }

    private double pentaChordal(Chord chord) {
        switch (chord.getForteName()) {
//            case "5-27":
//                return 0.99;
//            case "5-31":
//                return 0.99;
//            case "5-34":
//                return 0.99;
//            case "5-35":
//                return 0.99;
        }
        return 0;
    }

    private double tetrachordal(Chord chord) {
        switch (chord.getForteName()) {
            case "4-1": //X
                return 0.9;
            case "4-3":
                return 0.9;
            case "4-7":
                return 0.9;
            case "4-8":
                return 0.9;
            case "4-9":
                return 1.0;//Z
            case "4-10":
                return 1.0;//dorian
            case "4-17"://min-maj
                return 1.0;
            case "4-20"://maj7
                return 1.0;
            case "4-21"://Y
                return 1.0;
            case "4-23"://sus9
                return 1.0;
            case "4-26"://m7
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
            case "3-1"://maj add9 X
                return 0.9;
            case "3-6"://maj add9 Y
                return 1.0;
            case "3-9":// sus9/11
                return 1.0;
            case "3-10"://dim
                return 1.0;
            case "3-12"://aug
                return 1.0;
        }
        return 0;
    }
}

