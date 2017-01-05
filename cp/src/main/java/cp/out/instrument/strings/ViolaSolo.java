package cp.out.instrument.strings;

import cp.midi.GeneralMidi;
import cp.out.instrument.Instrument;
import cp.out.instrument.InstrumentGroup;
import cp.out.instrument.register.InstrumentRegister;
import cp.out.orchestration.InstrumentName;

public class ViolaSolo extends Instrument {

	public ViolaSolo() {
		init();
	}

	private void init() {
		instrumentGroup = InstrumentGroup.STRINGS;
		order = 1;
		setLowest(48);
		setHighest(72);
		setGeneralMidi(GeneralMidi.VIOLA);

		setInstrumentName(InstrumentName.VIOLA_SOLO.getName());
		setInstrumentSound("strings.viola");
		setVirtualName("Viola 1 solo");
	}

	public ViolaSolo(InstrumentRegister instrumentRegister) {
		init();
		setInstrumentRegister(instrumentRegister);
	}


}
