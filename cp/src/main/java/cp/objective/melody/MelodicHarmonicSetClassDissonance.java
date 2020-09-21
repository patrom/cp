package cp.objective.melody;

import cp.model.harmony.Chord;

import java.util.Arrays;

public class MelodicHarmonicSetClassDissonance implements MelodyHarmonicDissonance {

    private String[] setClasses;
    private int size;
    private int startingOverlap;

    public MelodicHarmonicSetClassDissonance(int windowSize, int startingOverlap, String... setClasses) {
        this.setClasses = setClasses;
        this.size = windowSize;
        this.startingOverlap = startingOverlap;
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

    @Override
    public int getStartingOverlap() {
        return startingOverlap;
    }
}
