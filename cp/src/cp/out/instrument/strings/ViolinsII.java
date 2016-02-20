package cp.out.instrument.strings;

import cp.midi.GeneralMidi;
import cp.out.instrument.Instrument;

public class ViolinsII extends Instrument {

	public ViolinsII(int voice, int channel) {
		super(voice, channel);
		setLowest(55);
		setHighest(84);
		setGeneralMidi(GeneralMidi.VIOLIN);
		
		setInstrumentName("Violin II");
		setInstrumentSound("strings.violin");
		setVirtualName("Violins");
	}
}
