package cp.objective.harmony;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import cp.model.Motive;
import cp.model.dissonance.Dissonance;
import cp.model.harmony.Chord;
import cp.model.harmony.CpHarmony;
import cp.objective.Objective;

@Component
public class HarmonicObjective extends Objective {
	
	private static Logger LOGGER = LoggerFactory.getLogger(HarmonicObjective.class.getName());

	@Autowired
	@Qualifier(value = "IntervalDissonance")
	private Dissonance dissonance;

	@Override
	public double evaluate(Motive motive) {
		List<CpHarmony> harmonies = motive.getHarmonies();
		if (harmonies.isEmpty()) {
			return 0.0;
		}
		return getHarmonyWeights(harmonies);
	}

	protected double getHarmonyWeights(List<CpHarmony> harmonies) {
		double totalHarmonyWeight = getTotalHarmonyWeight(harmonies);
		return harmonies.stream()
				.mapToDouble(h -> {
					double harmonyWeight = h.getHarmonyWeight();
//					LOGGER.info("harmonyWeight: " + harmonyWeight);
					Chord chord = h.getChord();
//					LOGGER.info("chord: " + chord);
					double dissonanceChord = dissonance.getDissonance(chord);
					double chordWeight = dissonanceChord* (harmonyWeight / totalHarmonyWeight);
					return chordWeight;
				})
//				.peek(w -> LOGGER.info("Weight: " + w))
				.average().getAsDouble();
	}

	protected double getTotalHarmonyWeight(List<CpHarmony> harmonies) {
		return harmonies.stream()
				.flatMap(h -> h.getNotes().stream())
				.mapToDouble(n -> n.getPositionWeight()).sum();
	}

}
