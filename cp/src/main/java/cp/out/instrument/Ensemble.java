package cp.out.instrument;

import java.util.HashMap;
import java.util.Map;


public class Ensemble {
	
   protected Map<Integer, Instrument> instruments = new HashMap<>();

	public Map<Integer, Instrument> getInstruments() {
		return instruments;
	}

	public Instrument getInstrumentForVoice(int voice) {
		return instruments.get(voice);
	}
}
