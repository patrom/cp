package cp.model.dissonance;

import cp.model.harmony.CpHarmony;

@FunctionalInterface
public interface Dissonance {

	double getDissonance(CpHarmony harmony);
	
}
