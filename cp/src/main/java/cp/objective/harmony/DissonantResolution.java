package cp.objective.harmony;

import cp.model.harmony.Chord;

@FunctionalInterface
public interface DissonantResolution {

	public boolean isDissonant(Chord chord);
}
