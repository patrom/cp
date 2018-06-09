package cp.objective.melody;

import cp.model.harmony.Chord;

import java.util.Arrays;

public class MelodicHarmonicTriadDissonance implements MelodyHarmonicDissonance {

    private String[] triads;

    public MelodicHarmonicTriadDissonance(String... triads) {
        this.triads = triads;
    }

    @Override
    public double getMelodicValue(Chord chord) {
        String forteName = chord.getForteName();

        if (Arrays.asList(triads).contains(chord.getForteName())) {
            return 1.0;
        }
        return 0;
    }

    @Override
    public int getChordSize() {
        return 3;
    }
}
