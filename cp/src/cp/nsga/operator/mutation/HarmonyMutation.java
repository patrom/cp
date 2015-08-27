package cp.nsga.operator.mutation;

import java.util.List;

import cp.model.harmony.Harmony;
import cp.util.RandomUtil;

public class HarmonyMutation {
	
	private boolean isOuterBoundaryIncluded = true;

	public Harmony randomHarmony(List<Harmony> harmonies) {
		int harmonyIndex = 0;
		if (isOuterBoundaryIncluded) {
			harmonyIndex = RandomUtil.randomInt(0, harmonies.size());
		} else {
			harmonyIndex = RandomUtil.randomInt(1, harmonies.size() - 1);
		}
		return harmonies.get(harmonyIndex);
	}

	public void setOuterBoundaryIncluded(boolean isOuterBoundaryIncluded) {
		this.isOuterBoundaryIncluded = isOuterBoundaryIncluded;
	}
	
}
