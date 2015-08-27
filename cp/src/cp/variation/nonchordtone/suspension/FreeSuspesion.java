package cp.variation.nonchordtone.suspension;

import cp.model.note.Scale;
import cp.variation.nonchordtone.Variation;

public abstract class FreeSuspesion extends Variation {
	
	public FreeSuspesion() {
		scales.add(new Scale(Scale.MIXOLYDIAN_SCALE));
		scales.add(new Scale(Scale.LYDIAN_SCALE));
		profile = 70;
		excludedVoices.add(0);
		excludedVoices.add(1);
	}

}
