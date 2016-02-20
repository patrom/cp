package cp.out.instrument.strings;

import cp.midi.GeneralMidi;
import cp.out.instrument.Instrument;

public class ViolaSolo extends Instrument {

	public ViolaSolo(int voice, int channel) {
		super(voice, channel);
		setLowest(48);
		setHighest(72);
		setGeneralMidi(GeneralMidi.VIOLA);
		
		setInstrumentName("Viola");
		setInstrumentSound("strings.viola");
		setVirtualName("Viola 1 solo");
	}

}
