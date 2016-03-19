package cp.out.instrument.strings;

import cp.midi.GeneralMidi;
import cp.out.instrument.Instrument;
import cp.out.instrument.register.InstrumentRegister;

public class ViolinsI extends Instrument {

	public ViolinsI(int voice, int channel) {
		super(voice, channel);
		init();
	}

	public ViolinsI() {
		init();
	}
	
	public void init() {
		setLowest(55);
		setHighest(84);
		setGeneralMidi(GeneralMidi.VIOLIN);
		
		setInstrumentName("Violin I");
		setInstrumentSound("strings.violin");
		setVirtualName("Violins");
	}
	
	public ViolinsI(InstrumentRegister instrumentRegister) {
		setInstrumentRegister(instrumentRegister);
		init();
	}
	
	
}