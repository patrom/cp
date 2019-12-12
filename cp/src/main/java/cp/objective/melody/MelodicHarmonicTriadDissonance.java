package cp.objective.melody;

import cp.model.harmony.Chord;

import java.util.Arrays;

public class MelodicHarmonicTriadDissonance implements MelodyHarmonicDissonance {

    private String[] triads;
    private int size;

    public MelodicHarmonicTriadDissonance(String... triads) {
        this.triads = triads;
        this.size = Integer.parseInt(triads[0].substring(0,1));
    }

    @Override
    public double getMelodicValue(Chord chord) {
        if (Arrays.asList(triads).contains(chord.getForteName())) {
            return 1.0;
        }
        return 0;
    }

    @Override
    public int getChordSize() {
        return size;
    }
}
