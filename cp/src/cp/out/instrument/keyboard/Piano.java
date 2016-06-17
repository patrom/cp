package cp.out.instrument.keyboard;

import cp.midi.GeneralMidi;
import cp.out.instrument.Instrument;
import cp.out.instrument.InstrumentGroup;
import cp.out.instrument.register.InstrumentRegister;

public class Piano extends Instrument {

	public Piano(int voice, int channel) {
		super(voice, channel);
		init();
	}
	
	public Piano() {
		init();
	}
	
	public Piano(InstrumentRegister instrumentRegister) {
		init();
		setInstrumentRegister(instrumentRegister);
	}

	private void init() {
		instrumentGroup = InstrumentGroup.KEYBOARD;
		order = 0;
		setLowest(21);
		setHighest(108);
		setGeneralMidi(GeneralMidi.PIANO);
		
		setInstrumentName("Piano (2)");
		setInstrumentSound("keyboard.piano.grand");
		setVirtualName("Concert Grand Piano");
	}
}