package cp.out.instrument.strings;

import cp.midi.GeneralMidi;
import cp.out.instrument.Instrument;
import cp.out.instrument.InstrumentGroup;

public class ViolaSolo extends Instrument {

	public ViolaSolo(int voice, int channel) {
		super(voice, channel);
		instrumentGroup = InstrumentGroup.STRINGS;
		order = 1;
		setLowest(48);
		setHighest(72);
		setGeneralMidi(GeneralMidi.VIOLA);
		
		setInstrumentName("Viola");
		setInstrumentSound("strings.viola");
		setVirtualName("Viola 1 solo");
	}

}
