package cp.out.instrument.percussion;

import cp.out.instrument.Instrument;
import cp.out.instrument.InstrumentGroup;
import cp.out.instrument.register.InstrumentRegister;
import cp.out.orchestration.InstrumentName;

public class Harp extends Instrument {

	public Harp() {
		init();
	}

	private void init() {
		instrumentGroup = InstrumentGroup.PERCUSSION;
		order = 0;
		setLowest(23);
		setHighest(103);
//		setGeneralMidi(GeneralMidi.CELLO);
		
		setInstrumentName(InstrumentName.HARP.getName());
		setInstrumentSound("pluck.harp");
		setVirtualName("Harp");
	}

	public Harp(InstrumentRegister instrumentRegister) {
		init();
		setInstrumentRegister(instrumentRegister);
	}
}

