package cp.out.instrument.brass;

import cp.midi.GeneralMidi;
import cp.out.instrument.Instrument;

public class Tuba extends Instrument {
	
	public Tuba(int voice, int channel) {
		super(voice, channel);
		setLowest(30);
		setHighest(53);
		setGeneralMidi(GeneralMidi.CELLO);
		
		setInstrumentName("Tuba");
		setInstrumentSound("brass.tuba");
		setVirtualName("Tuba Bb");
	}

}


