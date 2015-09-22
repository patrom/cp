package cp.objective.voiceleading;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import cp.model.Motive;
import cp.model.harmony.CpHarmony;
import cp.objective.Objective;

@Component
public class VoiceLeadingObjective extends Objective {

	private static Logger LOGGER = LoggerFactory.getLogger(VoiceLeadingObjective.class.getName());

	@Override
	public double evaluate(Motive motive) {
		List<CpHarmony> harmonies = motive.getHarmonies();
		double totalSize = 0;
		int harmoniesSize = harmonies.size() - 1;
		for(int i = 0; i < harmoniesSize; i++){
			VoiceLeadingSize minimalVoiceLeadingSize = VoiceLeading.caculateSize(((CpHarmony)harmonies.get(i)).getChord().getPitchClassMultiSet(), harmonies.get(i + 1).getChord().getPitchClassMultiSet());
			totalSize = totalSize + minimalVoiceLeadingSize.getSize();
		}
		return totalSize/harmoniesSize;
	}

}
