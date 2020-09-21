package cp.objective.melody;

import cp.model.harmony.Chord;

public interface MelodyHarmonicDissonance {

    double getMelodicValue(Chord chord);

    int getChordSize();

    int getStartingOverlap();
}
