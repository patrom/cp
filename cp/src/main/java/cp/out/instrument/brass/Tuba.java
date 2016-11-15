package cp.out.instrument.brass;

import cp.out.instrument.Instrument;
import cp.out.instrument.InstrumentGroup;
import cp.out.instrument.register.InstrumentRegister;
import cp.out.orchestration.InstrumentName;

public class Tuba extends Instrument {

	public Tuba(InstrumentRegister instrumentRegister) {
		init();
		setInstrumentRegister(instrumentRegister);
	}
	
	public Tuba() {
		init();
	}

	private void init() {
		instrumentGroup = InstrumentGroup.BRASS;
		order = 4;
		setLowest(30);
		setHighest(53);
//		setGeneralMidi(GeneralMidi.CELLO);
		
		setInstrumentName(InstrumentName.TUBA.toString());
		setInstrumentSound("brass.tuba");
		setVirtualName("Tuba Bb");
		setClef("F");
	}
}


