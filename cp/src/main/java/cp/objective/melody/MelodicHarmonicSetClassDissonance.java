package cp.objective.melody;

import cp.model.harmony.Chord;

import java.util.Arrays;

public class MelodicHarmonicSetClassDissonance implements MelodyHarmonicDissonance {

    private String[] setClasses;
    private int size;

    public MelodicHarmonicSetClassDissonance(int windowSize, String... setClasses) {
        this.setClasses = setClasses;
        this.size = windowSize;
    }

    @Override
    public double getMelodicValue(Chord chord) {
        if (Arrays.asList(setClasses).contains(chord.getForteName())) {
            return 1.0;
        }
        return 0;
    }

    @Override
    public int getChordSize() {
        return size;
    }
}
