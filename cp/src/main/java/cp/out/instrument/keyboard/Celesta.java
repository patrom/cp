package cp.out.instrument.keyboard;

import cp.out.instrument.Instrument;
import cp.out.instrument.InstrumentGroup;
import cp.out.instrument.register.InstrumentRegister;

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
		
		setInstrumentName("Celesta");
		setInstrumentSound("keyboard.celesta");
		setVirtualName("Celesta");
	}

	public Celesta(InstrumentRegister instrumentRegister) {
		init();
		setInstrumentRegister(instrumentRegister);
	}
}
