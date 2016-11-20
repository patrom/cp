package cp.out.instrument.keyboard;

import cp.out.instrument.Instrument;
import cp.out.instrument.InstrumentGroup;
import cp.out.instrument.register.InstrumentRegister;
import cp.out.orchestration.InstrumentName;

public class Celesta extends Instrument {

	public Celesta() {
		init();
	}

	private void init() {
		instrumentGroup = InstrumentGroup.KEYBOARD;
		order = 0;
		setLowest(60);
		setHighest(108);
//		setGeneralMidi(GeneralMidi.CELLO);
		
		setInstrumentName(InstrumentName.CELESTA.getName());
		setInstrumentSound("keyboard.celesta");
		setVirtualName("Celesta");
	}

	public Celesta(InstrumentRegister instrumentRegister) {
		init();
		setInstrumentRegister(instrumentRegister);
	}
}
