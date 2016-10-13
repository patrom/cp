package cp.model.dissonance;

import cp.model.harmony.Chord;

@FunctionalInterface
public interface Dissonance {

	double getDissonance(Chord chord);
	
}
