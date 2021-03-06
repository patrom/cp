package cp.objective.melody;

import cp.model.harmony.Chord;
import org.springframework.stereotype.Component;

@Component
public class MelodyHarmoniceTetraChordalDissonance implements MelodyHarmonicDissonance{

    public double getMelodicValue(Chord chord) {
        switch (chord.getForteName()) {
//            case "4-4":
//                return 0.98;
//            case "4-5":
//                return 0.98;
//            case "4-6":
//                return 0.98;
//            case "4-7":
//                return 0.98;
//            case "4-8":
//                return 0.98;
//            case "4-9":
//                return 0.98;
            case "4-22":
               return 1.0;
            case "4-23":
                return 1.0;
        }
        return 0;
    }

    @Override
    public int getChordSize() {
        return 4;
    }

    @Override
    public int getStartingOverlap() {
        return getChordSize();
    }
}
