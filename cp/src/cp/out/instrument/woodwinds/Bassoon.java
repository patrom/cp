package cp.out.instrument.woodwinds;

import cp.midi.GeneralMidi;
import cp.out.instrument.Instrument;
import cp.out.instrument.InstrumentGroup;
import cp.out.instrument.register.InstrumentRegister;


public class Bassoon extends Instrument {

	public Bassoon(int voice, int channel) {
		super(voice, channel);
		init();
	}

	private void init() {
		instrumentGroup = InstrumentGroup.WOODWINDS;
		order = 8;
		setLowest(34);
		setHighest(70);
		setGeneralMidi(GeneralMidi.BASSOON);
		
		setInstrumentName("Bassoon");
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
