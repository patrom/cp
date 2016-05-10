package cp.out.instrument.percussion;

import cp.out.instrument.Instrument;
import cp.out.instrument.InstrumentGroup;
import cp.out.instrument.register.InstrumentRegister;

public class Harp extends Instrument {
	
	public Harp(int voice, int channel) {
		super(voice, channel);
		init();
	}
	
	public Harp() {
		init();
	}

	private void init() {
		instrumentGroup = InstrumentGroup.PERCUSSION;
		order = 0;
		setLowest(23);
		setHighest(103);
//		setGeneralMidi(GeneralMidi.CELLO);
		
		setInstrumentName("Harp");
		setInstrumentSound("pluck.harp");
		setVirtualName("Harp");
	}

	public Harp(InstrumentRegister instrumentRegister) {
		init();
		setInstrumentRegister(instrumentRegister);
	}
}

