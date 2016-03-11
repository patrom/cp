package cp.out.orchestration.quality;

import cp.out.instrument.Instrument;
import cp.out.instrument.register.ViolaPleasant;
import cp.out.instrument.register.ViolinSoloPleasant;

public class Pleasant extends OrchestralQuality{

	public Pleasant() {
		color = "green";
		quality = "pleasant";
		type = "basic";
		Instrument violin = new Instrument();
		violin.setInstrumentRegister(new ViolinSoloPleasant());
		instruments.add(violin);
//		instruments.add(new ViolaPleasant());
	}

}
