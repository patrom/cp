package cp.out.instrument.strings;

import cp.midi.GeneralMidi;
import cp.out.instrument.Instrument;
import cp.out.instrument.InstrumentGroup;
import cp.out.instrument.register.InstrumentRegister;
import cp.out.orchestration.InstrumentName;

public class Cello extends Instrument {
	
	public Cello() {
		init();
	}

	private void init() {
		instrumentGroup = InstrumentGroup.ORCHESTRAL_STRINGS;
		order = 2;
		setLowest(36);
		setHighest(70);
		setGeneralMidi(GeneralMidi.CELLO);
		
		setInstrumentName(InstrumentName.CELLO.getName());
		setInstrumentSound("strings.cello");
		setVirtualName("Celli");
		setClef("F");
	}

	public Cello(InstrumentRegister instrumentRegister) {
		init();
		setInstrumentRegister(instrumentRegister);
	}
}
