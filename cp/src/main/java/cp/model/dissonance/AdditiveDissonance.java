package cp.model.dissonance;

import cp.model.harmony.Chord;
import cp.model.harmony.ChordType;
import cp.model.harmony.CpHarmony;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component(value="additiveDissonance")
public class AdditiveDissonance implements Dissonance {

    private static final Logger LOGGER = LoggerFactory.getLogger(AdditiveDissonance.class);

    @Override
    public double getDissonance(CpHarmony harmony) {
        if (harmony.containsMinorSecondOrb9()) {
            return 0;
        }
        int size = harmony.getChord().getPitchClassSet().size();
        switch (size){
//            case 2:
//                return dyadic(harmony.getChord());
////                return 0;
//            case 3:
//            case 4:
//            case 5:
            case 6:
                return trichordal(harmony);
        }
        return 0;
    }

    private double dyadic(Chord chord) {
        switch (chord.getChordType()) {
            case CH2_KLEINE_TERTS:
                return 0.7;
            case CH2_GROTE_TERTS:
                return 0.7;
            case CH2_GROTE_SIXT:
                return 0.7;
            case CH2_KLEINE_SIXT:
                return 0.7;
            case CH2_KWART:
                return 0.7;
            case CH2_KWINT:
                return 0.7;
        }
        return 0;
    }

    private double trichordal(CpHarmony harmony) {
        ChordType additiveChord = harmony.getAdditiveChord();
        if (additiveChord != null) {
            switch (additiveChord) {
                case ANCHOR_7:
                    return 0.8;
                case ANCHOR_10:
                    return 0.9;
                case ANCHOR_11:
                    return 0.8;
                case ANCHOR_38_MAJ:
                case ANCHOR_59_MAJ:
                case ANCHOR_49_MIN:
                case ANCHOR_58_MIN:
                case ANCHOR_68_DOM:
                case ANCHOR_35_DOM:
                case ANCHOR_26_DOM:
                case ANCHOR_29_DOM:
                case ANCHOR_46:
                case ANCHOR_25:
                case ANCHOR_28:
                case ANCHOR_87_MAJ7:
                case ANCHOR_54_MAJ7:
                case ANCHOR_36_DIM:
                case ANCHOR_39_DIM:
                case ANCHOR_69_DIM:
                    return 1.0;
            }
        }

//        final Chord chord = harmony.getChord();
//        switch (chord.getChordType()) {
//            case MAJOR_1:
//                return 0.9;
//            case MAJOR_2:
//                return 0.5;
//            case MINOR_1:
//                return 0.9;
//            case MINOR_2:
//                return 0.5;
//            case DIM:
//                return 0.9;
//            case AUGM:
//                return 0.8;
//        }
        return 0.0;
    }

}
