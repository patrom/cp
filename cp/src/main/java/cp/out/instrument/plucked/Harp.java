package cp.out.instrument.plucked;

import cp.midi.GeneralMidi;
import cp.out.instrument.Instrument;
import cp.out.instrument.InstrumentGroup;
import cp.out.instrument.register.InstrumentRegister;
import cp.out.orchestration.InstrumentName;

public class Harp extends Instrument {

	public Harp() {
		init();
	}

	private void init() {
		instrumentGroup = InstrumentGroup.PLUCKED;
		order = 0;
		setLowest(23);
		setHighest(103);
		setGeneralMidi(GeneralMidi.HARP);
		
		setInstrumentName(InstrumentName.HARP.getName());
		setInstrumentSound("pluck.harp");
		setVirtualName("Harp");
	}

	public Harp(InstrumentRegister instrumentRegister) {
		init();
		setInstrumentRegister(instrumentRegister);
	}
}

