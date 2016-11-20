package cp.out.instrument.strings;

import cp.midi.GeneralMidi;
import cp.out.instrument.Instrument;
import cp.out.instrument.InstrumentGroup;
import cp.out.orchestration.InstrumentName;

public class ViolaSolo extends Instrument {

	public ViolaSolo() {
		instrumentGroup = InstrumentGroup.STRINGS;
		order = 1;
		setLowest(48);
		setHighest(72);
		setGeneralMidi(GeneralMidi.VIOLA);
		
		setInstrumentName(InstrumentName.VIOLA_SOLO.getName());
		setInstrumentSound("strings.viola");
		setVirtualName("Viola 1 solo");
	}

}
