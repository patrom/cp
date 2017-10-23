package cp.objective.harmony;

import cp.model.Motive;
import cp.model.dissonance.Dissonance;
import cp.model.harmony.Chord;
import cp.model.harmony.CpHarmony;
import cp.objective.Objective;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class HarmonicObjective extends Objective {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(HarmonicObjective.class.getName());

	private Dissonance dissonance;
	
	public void setDissonance(Dissonance dissonance) {
		this.dissonance = dissonance;
	}

	public Dissonance getDissonance() {
		return dissonance;
	}

	@Override
	public double evaluate(Motive motive) {
		List<CpHarmony> harmonies = motive.getHarmonies();
		if (harmonies.size() <= 1) {
			return 0.0;
		}
		return getHarmonyWeights(harmonies);
	}

	protected double getHarmonyWeights(List<CpHarmony> harmonies) {
		double totalHarmonyWeight = getTotalHarmonyWeight(harmonies);
		return harmonies.stream()
				.mapToDouble(h -> {
					double harmonyWeight = h.getHarmonyWeight();
					LOGGER.debug("harmonyWeight: " + harmonyWeight);
					Chord chord = h.getChord();
					LOGGER.debug("chord: " + chord);
					double dissonanceChord = dissonance.getDissonance(h);
					return dissonanceChord * (harmonyWeight / totalHarmonyWeight);
				})
				.peek(w -> LOGGER.debug("Weight: " + w))
				.sum();
	}

	protected double getTotalHarmonyWeight(List<CpHarmony> harmonies) {
		return harmonies.stream()
				.flatMap(h -> h.getNotes().stream())
				.mapToDouble(n -> n.getPositionWeight()).sum();
	}

}
