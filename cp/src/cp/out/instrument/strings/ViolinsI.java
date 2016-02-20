package cp.out.instrument.strings;

import cp.midi.GeneralMidi;
import cp.out.instrument.Instrument;

public class ViolinsI extends Instrument {

	public ViolinsI(int voice, int channel) {
		super(voice, channel);
		setLowest(55);
		setHighest(84);
		setGeneralMidi(GeneralMidi.VIOLIN);
		
		setInstrumentName("Violin I");
		setInstrumentSound("strings.violin");
		setVirtualName("Violins");
	}

}