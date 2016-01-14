package cp.objective.harmony;

import java.util.List;

import org.springframework.stereotype.Component;

import cp.model.Motive;
import cp.model.harmony.CpHarmony;
import cp.objective.Objective;

@Component
public class HarmonicResolutionObjective extends Objective {

	@Override
	public double evaluate(Motive motive) {
		List<CpHarmony> harmonies = motive.getHarmonies();
		if (harmonies.isEmpty()) {
			return 0.0;
		}
		return getResolutionValue(harmonies);
	}

	protected double getResolutionValue(List<CpHarmony> harmonies) {
		double total = 0;
		int size = harmonies.size() - 1;
		for (int i = 0; i < size; i++) {
			CpHarmony harmony = harmonies.get(i);
			CpHarmony nextHarmony = harmonies.get(i + 1);
			if (harmony.isDissonant() && nextHarmony.isDissonant()) {
				total = total + 0;
			}else{
				total = total + 1;
			}
		}
		return total/size;
	}

}