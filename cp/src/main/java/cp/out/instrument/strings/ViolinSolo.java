package cp.out.instrument.strings;

import cp.midi.GeneralMidi;
import cp.out.instrument.Instrument;
import cp.out.instrument.InstrumentGroup;


public class ViolinSolo extends Instrument {

	public ViolinSolo() {
		instrumentGroup = InstrumentGroup.STRINGS;
		order = 0;
		setLowest(55);
		setHighest(84);
		setGeneralMidi(GeneralMidi.VIOLIN);
		
		setInstrumentName("Violin");
		setInstrumentSound("strings.violin");
		setVirtualName("Violin 1 solo");
	}

}
