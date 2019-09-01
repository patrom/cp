package cp.objective.transformation;

import cp.model.harmony.Chord;
import cp.model.harmony.ChordType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Progression {

    private static final Logger LOGGER = LoggerFactory.getLogger(Progression.class);

    public static Transformation getTransformation(Chord chord, Chord nextChord) {
        ChordType chordType = chord.getChordType();
        ChordType nextChordType = nextChord.getChordType();
        if (chordType.getSize() == 3 && nextChord.getSize() == 3) {
            return triadic(chord, nextChord);
        } else  if (chordType.getSize() == 4 && nextChord.getSize() == 4) {
            return cube(chord, nextChord);
        }
        return Transformation.UNDEFINED;
    }

    private static Transformation cube(Chord chord, Chord nextChord) {
        ChordType chordType = chord.getChordType();
        ChordType nextChordType = nextChord.getChordType();
            if ((chordType == ChordType.DOM7 ||
                    chordType == ChordType.HALFDIM7 ||
                    chordType == ChordType.MINOR7 ||
                    chordType == ChordType.MINOR7_1 ||
                    chordType == ChordType.MINOR7_2 ||
                    chordType == ChordType.MINOR7_3 ||
                    chord.getForteName().equals("4-25") ||
                    chordType == ChordType.DIM7)
                    && (nextChordType == ChordType.DOM7 ||
                    nextChordType == ChordType.HALFDIM7 ||
                    nextChordType == ChordType.MINOR7 ||
                    nextChordType == ChordType.MINOR7_1 ||
                    nextChordType == ChordType.MINOR7_2 ||
                    nextChordType == ChordType.MINOR7_3 ||
                    nextChord.getForteName().equals("4-25") ||
                    nextChordType == ChordType.DIM7)) {
                int stepInterval = chord.getVoiceLeadingZone() - nextChord.getVoiceLeadingZone();
                switch (stepInterval) {
                    case 0:
                        return Transformation.ZONE_0;
                    case 1:
                        return Transformation.ZONE_1;
                    case 2:
                        return Transformation.ZONE_2;
                    case 3:
                        return Transformation.ZONE_3;
                    case 4:
                        return Transformation.ZONE_4;
                    case 5:
                        return Transformation.ZONE_5;
                    case 6:
                        return Transformation.ZONE_6;
                    case 7:
                        return Transformation.ZONE_5;
                    case 8:
                        return Transformation.ZONE_4;
                    case 9:
                        return Transformation.ZONE_3;
                    case 10:
                        return Transformation.ZONE_2;
                    case 11:
                        return Transformation.ZONE_1;
                    case -1:
                        return Transformation.ZONE_1;
                    case -2:
                        return Transformation.ZONE_2;
                    case -3:
                        return Transformation.ZONE_3;
                    case -4:
                        return Transformation.ZONE_4;
                    case -5:
                        return Transformation.ZONE_5;
                    case -6:
                        return Transformation.ZONE_6;
                    case -7:
                        return Transformation.ZONE_5;
                    case -8:
                        return Transformation.ZONE_4;
                    case -9:
                        return Transformation.ZONE_3;
                    case -10:
                        return Transformation.ZONE_2;
                    case -11:
                        return Transformation.ZONE_1;
                }

        }
        return Transformation.UNDEFINED;
    }


    private static Transformation triadic(Chord chord, Chord nextChord) {
        ChordType chordType = chord.getChordType();
        ChordType nextChordType = nextChord.getChordType();
        int root = chord.getRoot();
        int nextRoot = nextChord.getRoot();
//        LOGGER.info("root: " + root);
//        LOGGER.info("nextRoot: " + nextRoot);
        if (root > -1 && nextRoot > -1) {
//            LOGGER.info("step");
            int stepInterval = root - nextRoot;
            if ((chordType == ChordType.MAJOR ||
                    chordType == ChordType.MAJOR_1 ||
                    chordType == ChordType.MAJOR_2)
                    && (nextChordType == ChordType.MAJOR ||
                    nextChordType == ChordType.MAJOR_1 ||
                    nextChordType == ChordType.MAJOR_2)) {
                    switch (stepInterval) {
                        case 1:
                            break;
                        case 2:
                            break;
                        case 3:
                            return Transformation.RP_SM;
                        case 4:
                            return Transformation.PL_SM;
                        case 5:
                            return Transformation.SUB;
                        case 6:
                            return Transformation.T6_SUBV;
                        case 7:
                            return Transformation.DOM;
                        case 8:
                            return Transformation.LP_M;
                        case 9:
                            return Transformation.PR_M;
                        case 10:
                            break;
                        case 11:
                            break;
                        case -1:
                            break;
                        case -2:
                            break;
                        case -3:
                            return Transformation.PR_M;
                        case -4:
                            return Transformation.LP_M;
                        case -5:
                            return Transformation.DOM;
                        case -6:
                            return Transformation.T6_SUBV;
                        case -7:
                            return Transformation.SUB;
                        case -8:
                            return Transformation.PL_SM;
                        case -9:
                            return Transformation.RP_SM;
                        case -10:
                            break;
                        case -11:
                            break;
                    }
            } else if ((chordType == ChordType.MAJOR ||
                    chordType == ChordType.MAJOR_1 ||
                    chordType == ChordType.MAJOR_2)
                    && (nextChordType == ChordType.MINOR ||
                    nextChordType == ChordType.MINOR_1 ||
                    nextChordType == ChordType.MINOR_2)) {
                switch (stepInterval) {
                    case 0:
                        return Transformation.P;
                    case 1:
                        break;
                    case 2:
                        break;
                    case 3:
                        return Transformation.R;
                    case 4:
                        return Transformation.H;
                    case 5:
                        break;
                    case 6:
                        break;
                    case 7:
                        return Transformation.N;
                    case 8:
                        return Transformation.L;
                    case 9:
                        return Transformation.PRP;
                    case 10:
                        break;
                    case 11:
                        return Transformation.S;
                    case -1:
                        return Transformation.S;
                    case -2:
                        break;
                    case -3:
                        return Transformation.PRP;
                    case -4:
                        return Transformation.L;
                    case -5:
                        return Transformation.N;
                    case -6:
                        break;
                    case -7:
                        break;
                    case -8:
                        return Transformation.H;
                    case -9:
                        return Transformation.R;
                    case -10:
                        break;
                    case -11:
                        break;
                }
            } else if ((chordType == ChordType.MINOR ||
                    chordType == ChordType.MINOR_1 ||
                    chordType == ChordType.MINOR_2)
                    && (nextChordType == ChordType.MINOR ||
                    nextChordType == ChordType.MINOR_1 ||
                    nextChordType == ChordType.MINOR_2)) {
                switch (stepInterval) {
                    case 0:
                        break;
                    case 1:
                        break;
                    case 2:
                        break;
                    case 3:
                        return Transformation.PR_m;
                    case 4:
                        return Transformation.LP_m;
                    case 5:
                        return Transformation.SUB;
                    case 6:
                        break;
                    case 7:
                        return Transformation.DOM;
                    case 8:
                        return Transformation.PL_sm;
                    case 9:
                        return Transformation.RP_sm;
                    case 10:
                        break;
                    case 11:
                        break;
                    case -1:
                        break;
                    case -2:
                        break;
                    case -3:
                        return Transformation.RP_sm;
                    case -4:
                        return Transformation.PL_sm;
                    case -5:
                        return Transformation.DOM;
                    case -6:
                        break;
                    case -7:
                        return Transformation.SUB;
                    case -8:
                        return Transformation.LP_m;
                    case -9:
                        return Transformation.PR_m;
                    case -10:
                        break;
                    case -11:
                        break;
                }
            } else if ((chordType == ChordType.MINOR ||
                    chordType == ChordType.MINOR_1 ||
                    chordType == ChordType.MINOR_2)
                    && (nextChordType == ChordType.MAJOR ||
                    nextChordType == ChordType.MAJOR_1 ||
                    nextChordType == ChordType.MAJOR_2)) {
                switch (stepInterval) {
                    case 0:
                        return Transformation.P;
                    case 1:
                        return Transformation.S;
                    case 2:
                        break;
                    case 3:
                        return Transformation.PRP;
                    case 4:
                        return Transformation.L;
                    case 5:
                        return Transformation.N;
                    case 6:
                        break;
                    case 7:
                        break;
                    case 8:
                        return Transformation.H;
                    case 9:
                        return Transformation.R;
                    case 10:
                        break;
                    case 11:
                        break;
                    case -1:
                        break;
                    case -2:
                        break;
                    case -3:
                        return Transformation.R;
                    case -4:
                        return Transformation.H;
                    case -5:
                        break;
                    case -6:
                        break;
                    case -7:
                        return Transformation.N;
                    case -8:
                        return Transformation.L;
                    case -9:
                        return Transformation.PRP;
                    case -10:
                        break;
                    case -11:
                        return Transformation.S;
                }
            }
        }
        return Transformation.UNDEFINED;
    }
}
