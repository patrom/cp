package cp.objective.harmony;

import cp.model.harmony.CpHarmony;

@FunctionalInterface
public interface DissonantResolution {

	boolean isDissonant(CpHarmony harmony);
}
