package cp.variation.nonchordtone.passing;

import cp.model.note.Scale;
import cp.variation.nonchordtone.Variation;

public abstract class Passing extends Variation {
	
	public Passing() {
		scales.add(Scale.OCTATCONIC_HALF);
//		scales.add(new Scale(Scale.LYDIAN_SCALE));
	}
	
}
