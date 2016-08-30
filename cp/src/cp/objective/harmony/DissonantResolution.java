package cp.objective.harmony;

import cp.model.harmony.Chord;
import cp.model.harmony.CpHarmony;

@FunctionalInterface
public interface DissonantResolution {

	public boolean isDissonant(Chord chord);
}
