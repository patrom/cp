package cp.model.dissonance.subset;

import cp.model.dissonance.Dissonance;
import cp.model.harmony.Chord;
import cp.model.harmony.CpHarmony;

public class SubSetDissonance implements Dissonance {

    @Override
    public double getDissonance(CpHarmony harmony) {
        int size = harmony.getChord().getPitchClassSet().size();
        switch (size){
            case 2:
                return dyadic(harmony.getChord());
//                return 0;
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
            case "3-1":
                return 0.0;
            case "3-2":
                return 0.0;
            case "3-3":
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
                return 0.0;
            case "3-11":
                return 0.0;
            case "3-12":
                return 0.0;
        }
        return 0;
    }

    private double tetrachordal(Chord chord) {
        switch (chord.getForteName()) {
            case "4-1":
                return 0.0;
            case "4-2":
                return 0.0;
            case "4-3":
                return 0.0;
            case "4-4":
                return 0.0;
            case "4-5":
                return 0.0;
            case "4-6":
                return 0.0;
            case "4-7":
                return 0.0;
            case "4-8":
                return 0.0;
            case "4-9":
                return 0.0;
            case "4-10":
                return 0.0;
            case "4-11":
                return 0.0;
            case "4-12":
                return 0.0;
            case "4-13":
                return 0.0;
            case "4-14":
                return 0.0;
            case "4-15":
                return 0.0;
            case "4-16":
                return 0.0;
            case "4-17":
                return 0.0;
            case "4-18":
                return 0.0;
            case "4-19":
                return 0.0;
            case "4-20":
                return 0.0;
            case "4-21":
                return 0.0;
            case "4-22"://add9
                return 0.0;
            case "4-23"://sus9
                return 0.0;
            case "4-24":
                return 0.0;
            case "4-25"://dom7b5
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
            case "5-1":
                return 0.0;
            case "5-2":
                return 0.0;
            case "5-3":
                return 0.0;
            case "5-4":
                return 0.0;
            case "5-5":
                return 0.0;
            case "5-6":
                return 0.0;
            case "5-7":
                return 0.0;
            case "5-8":
                return 0.0;
            case "5-9":
                return 0.0;
            case "5-10":
                return 0.0;
            case "5-11":
                return 0.0;
            case "5-12":
                return 0.0;
            case "5-13":
                return 0.0;
            case "5-14":
                return 0.0;
            case "5-15":
                return 0.0;
            case "5-16":
                return 0.0;
            case "5-17":
                return 0.0;
            case "5-18":
                return 0.0;
            case "5-19":
                return 0.0;
            case "5-20":
                return 0.0;
            case "5-21":
                return 0.0;
            case "5-22":
                return 0.0;
            case "5-23":
                return 0.0;
            case "5-24":
                return 0.0;
            case "5-25":
                return 0.0;
            case "5-26":
                return 0.0;
            case "5-27":
                return 0.0;
            case "5-28":
                return 0.0;
            case "5-29":
                return 0.0;
            case "5-30":
                return 0.0;
            case "5-31":
                return 0.0;
            case "5-32":
                return 0.0;
            case "5-33":
                return 0.0;
            case "5-34":
                return 0.0;
            case "5-35":
                return 0.0;
            case "5-36":
                return 0.0;
            case "5-37":
                return 0.0;
            case "5-38":
                return 0.0;
        }
        return 0;
    }

    private double hexaChordal(Chord chord) {
        switch (chord.getForteName()) {
            case "6-1":
                return 0.0;
            case "6-2":
                return 0.0;
            case "6-3":
                return 0.0;
            case "6-4":
                return 0.0;
            case "6-5":
                return 0.0;
            case "6-6":
                return 0.0;
            case "6-7":
                return 0.0;
            case "6-8":
                return 0.0;
            case "6-9":
                return 0.0;
            case "6-10":
                return 0.0;
            case "6-11":
                return 0.0;
            case "6-12":
                return 0.0;
            case "6-13":
                return 0.0;
            case "6-14":
                return 0.0;
            case "6-15":
                return 0.0;
            case "6-16":
                return 0.0;
            case "6-17":
                return 0.0;
            case "6-18":
                return 0.0;
            case "6-19":
                return 0.0;
            case "6-20":
                return 0.0;
            case "6-21":
                return 0.0;
            case "6-22":
                return 0.0;
            case "6-23":
                return 0.0;
            case "6-24":
                return 0.0;
            case "6-25":
                return 0.0;
            case "6-26":
                return 0.0;
            case "6-27":
                return 0.0;
            case "6-28":
                return 0.0;
            case "6-29":
                return 0.0;
            case "6-30":
                return 0.0;
            case "6-31":
                return 0.0;
            case "6-32":
                return 0.0;
            case "6-33":
                return 0.0;
            case "6-34":
                return 0.0;
            case "6-35":
                return 0.0;
            case "6-36":
                return 0.0;
            case "6-37":
                return 0.0;
            case "6-38":
                return 0.0;
            case "6-39":
                return 0.0;
            case "6-41":
                return 0.0;
            case "6-42":
                return 0.0;
            case "6-43":
                return 0.0;
            case "6-44":
                return 0.0;
            case "6-45":
                return 0.0;
            case "6-46":
                return 0.0;
            case "6-47":
                return 0.0;
            case "6-48":
                return 0.0;
            case "6-49":
                return 0.0;
            case "6-50":
                return 0.0;
        }
        return 0;
    }

}

