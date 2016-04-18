package cp.out.instrument.strings;

import cp.midi.GeneralMidi;
import cp.out.instrument.Instrument;
import cp.out.instrument.InstrumentGroup;

public class ViolinsII extends Instrument {

	public ViolinsII(int voice, int channel) {
		super(voice, channel);
		instrumentGroup = InstrumentGroup.STRINGS;
		order = 0;
		setLowest(55);
		setHighest(84);
		setGeneralMidi(GeneralMidi.VIOLIN);
		
		setInstrumentName("Violin II");
		setInstrumentSound("strings.violin");
		setVirtualName("Violins");
	}
}
