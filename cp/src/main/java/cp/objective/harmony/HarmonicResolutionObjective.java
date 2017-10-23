package cp.objective.harmony;

import cp.model.Motive;
import cp.model.harmony.CpHarmony;
import cp.objective.Objective;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class HarmonicResolutionObjective extends Objective {
	
	private DissonantResolution dissonantResolution;
	
	public void setDissonantResolution(DissonantResolution dissonantResolution) {
		this.dissonantResolution = dissonantResolution;
	}

	@Override
	public double evaluate(Motive motive) {
		List<CpHarmony> harmonies = motive.getHarmonies();
//		if (harmonies.size() <= 1) {
//			return 1.0;
//		}
		return getResolutionValue(harmonies);
	}

	protected double getResolutionValue(List<CpHarmony> harmonies) {
		double total = 0;
		int size = harmonies.size() - 1;
		for (int i = 0; i < size; i++) {
			CpHarmony harmony = harmonies.get(i);
			CpHarmony nextHarmony = harmonies.get(i + 1);
			if ((dissonantResolution.isDissonant(harmony) && dissonantResolution.isDissonant(nextHarmony))
					|| (!dissonantResolution.isDissonant(harmony) && !dissonantResolution.isDissonant(nextHarmony))) {
				total = total + 1;
			}
		}
		return total/size;
	}

}
