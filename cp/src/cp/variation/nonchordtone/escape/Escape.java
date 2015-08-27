package cp.variation.nonchordtone.escape;

import cp.variation.nonchordtone.Variation;

public abstract class Escape extends Variation {

	public Escape() {
		excludedVoices.add(0);
		excludedVoices.add(1);
	}

}
