package cp.out.orchestration.quality;

import cp.out.instrument.register.ViolaPleasant;
import cp.out.instrument.register.ViolinSoloPleasant;

public class Pleasant extends OrchestralQuality{

	public Pleasant() {
		color = "green";
		quality = "pleasant";
		type = "basic";
		instrumentRegisters.add(new ViolinSoloPleasant());
		instrumentRegisters.add(new ViolaPleasant());
	}

}
