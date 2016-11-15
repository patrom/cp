package cp.out.instrument.woodwinds;

import cp.midi.GeneralMidi;
import cp.out.instrument.Instrument;
import cp.out.instrument.InstrumentGroup;
import cp.out.instrument.register.InstrumentRegister;
import cp.out.orchestration.InstrumentName;


public class Bassoon extends Instrument {

	private void init() {
		instrumentGroup = InstrumentGroup.WOODWINDS;
		order = 8;
		setLowest(34);
		setHighest(70);
		setGeneralMidi(GeneralMidi.BASSOON);
		
		setInstrumentName(InstrumentName.BASSOON.toString());
		setInstrumentSound("wind.reed.bassoon");
		setVirtualName("Bassoon 1");
		setClef("F");
	}

	public Bassoon() {
		init();
	}
	
	public Bassoon(InstrumentRegister instrumentRegister) {
		init();
		setInstrumentRegister(instrumentRegister);
	}

}
