package cp.objective.tonality;

import cp.model.Motive;
import cp.model.melody.MelodyBlock;
import cp.objective.Objective;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class TonalityObjective extends Objective {

	@Override
	public double evaluate(Motive motive) {
		return evaluateMajorMinorTonality(motive.getMelodyBlocks());
	}
	
	private double evaluateMajorMinorTonality(List<MelodyBlock> melodyBlocks) {
		double major = TonalityFunctions.getMaxCorrelationTonality(melodyBlocks, TonalityFunctions.vectorMajorTemplate);
		double minor = TonalityFunctions.getMaxCorrelationTonality(melodyBlocks, TonalityFunctions.vectorMinorTemplate);
		if (major > minor) {
			return major;
		} else {
			return minor;
		}
	}

}
