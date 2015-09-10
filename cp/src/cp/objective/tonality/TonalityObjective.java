package cp.objective.tonality;

import java.util.List;

import org.springframework.stereotype.Component;

import cp.model.Motive;
import cp.model.melody.CpMelody;
import cp.model.melody.Melody;
import cp.objective.Objective;

@Component
public class TonalityObjective extends Objective {

	@Override
	public double evaluate(Motive motive) {
		return evaluateMajorMinorTonality(motive.getMelodies());
	}
	
	private double evaluateMajorMinorTonality(List<CpMelody> melodies) {
//		double major = TonalityFunctions.getMaxCorrelationTonality(melodies, TonalityFunctions.vectorMajorTemplate);
//		double minor = TonalityFunctions.getMaxCorrelationTonality(melodies, TonalityFunctions.vectorMinorTemplate);
//		if (major > minor) {
//			return major;
//		} else {
//			return minor;
//		}
		return 0;
	}

}
