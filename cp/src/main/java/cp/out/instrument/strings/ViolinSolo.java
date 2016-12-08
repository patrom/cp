package cp.out.instrument.strings;

import cp.midi.GeneralMidi;
import cp.out.instrument.Instrument;
import cp.out.instrument.InstrumentGroup;
import cp.out.instrument.keyswitch.VSLStringsKeySwitch;
import cp.out.orchestration.InstrumentName;


public class ViolinSolo extends Instrument {

	public ViolinSolo() {
		instrumentGroup = InstrumentGroup.STRINGS;
		order = 0;
		setLowest(55);
		setHighest(84);
		setGeneralMidi(GeneralMidi.VIOLIN);
		
		setInstrumentName(InstrumentName.VIOLA_SOLO.getName());
		setInstrumentSound("strings.violin");
		setVirtualName("Violin 1 solo");
		keySwitch = new VSLStringsKeySwitch();
	}

}
