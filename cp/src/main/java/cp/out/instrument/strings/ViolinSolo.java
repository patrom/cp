package cp.out.instrument.strings;

import cp.midi.GeneralMidi;
import cp.out.instrument.Instrument;
import cp.out.instrument.InstrumentGroup;
import cp.out.instrument.register.InstrumentRegister;
import cp.out.orchestration.InstrumentName;


public class ViolinSolo extends Instrument {

	public ViolinSolo() {
		init();
	}

	private void init() {
		instrumentGroup = InstrumentGroup.STRINGS;
		order = 0;
		setLowest(55);
		setHighest(84);
		setGeneralMidi(GeneralMidi.VIOLIN);

		setInstrumentName(InstrumentName.VIOLIN_SOLO.getName());
		setInstrumentSound("strings.violin");
		setVirtualName("Violin 1 solo");
	}

	public ViolinSolo(InstrumentRegister instrumentRegister) {
		init();
		setInstrumentRegister(instrumentRegister);
	}

}
