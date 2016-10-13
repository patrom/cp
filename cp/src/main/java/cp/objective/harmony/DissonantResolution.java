package cp.objective.harmony;

import cp.model.harmony.Chord;

@FunctionalInterface
public interface DissonantResolution {

	boolean isDissonant(Chord chord);
}
