package cp.out.instrument.plucked;

import cp.midi.GeneralMidi;
import cp.out.instrument.Instrument;
import cp.out.instrument.InstrumentGroup;
import cp.out.instrument.register.InstrumentRegister;
import cp.out.orchestration.InstrumentName;

public class Guitar extends Instrument {

	public Guitar() {
		init();
	}
	
	public Guitar(InstrumentRegister instrumentRegister) {
		init();
		setInstrumentRegister(instrumentRegister);
	}

	private void init() {
		instrumentGroup = InstrumentGroup.PLUCKED;
		setLowest(40);
		setHighest(79);
		setGeneralMidi(GeneralMidi.NYLON_GUITAR);
		
		setInstrumentName(InstrumentName.GUITAR.toString());
		setInstrumentSound("pluck.guitar.nylon-string");
		setVirtualName("Nylon Guitar");
	}
}

