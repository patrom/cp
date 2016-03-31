package cp.out.instrument.strings;

import cp.midi.GeneralMidi;
import cp.out.instrument.Instrument;
import cp.out.instrument.register.InstrumentRegister;

public class Doublebass extends Instrument {

	public Doublebass(int voice, int channel) {
		super(voice, channel);
		init();
	}

	private void init() {
		setLowest(38);
		setHighest(67);
		setGeneralMidi(GeneralMidi.CONTRABASS);
		
		setInstrumentName("Contrabass (2)");
		setInstrumentSound("strings.Contrabass");
		setVirtualName("Basses");
		setClef("F");
	}

	public Doublebass() {
		init();
	}
	
	public Doublebass(InstrumentRegister instrumentRegister) {
		init();
		setInstrumentRegister(instrumentRegister);
	}
	

}
