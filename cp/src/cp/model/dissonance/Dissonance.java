package cp.model.dissonance;

import cp.model.harmony.Chord;

@FunctionalInterface
public interface Dissonance {

	public double getDissonance(Chord chord);
	
}
