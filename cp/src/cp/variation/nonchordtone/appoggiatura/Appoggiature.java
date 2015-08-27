package cp.variation.nonchordtone.appoggiatura;

import cp.variation.nonchordtone.Variation;

public abstract class Appoggiature extends Variation {

	public Appoggiature() {
		excludedVoices.add(0);
		excludedVoices.add(1);
	}
}
