package cp.out.instrument.strings;

import cp.midi.GeneralMidi;
import cp.out.instrument.Instrument;
import cp.out.instrument.register.InstrumentRegister;

public class Cello extends Instrument {
	
	public Cello(int voice, int channel) {
		super(voice, channel);
		init();
	}
	
	public Cello() {
		init();
	}

	private void init() {
		setLowest(36);
		setHighest(70);
		setGeneralMidi(GeneralMidi.CELLO);
		
		setInstrumentName("Violoncello (2)");
		setInstrumentSound("strings.cello");
		setVirtualName("Celli");
		setClef("F");
	}

	public Cello(InstrumentRegister instrumentRegister) {
		setInstrumentRegister(instrumentRegister);
		init();
	}
}
