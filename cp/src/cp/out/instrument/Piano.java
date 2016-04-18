package cp.out.instrument;

import cp.midi.GeneralMidi;
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
		instrumentGroup = InstrumentGroup.PERCUSSION;
		setLowest(40);
		setHighest(108);
		setGeneralMidi(GeneralMidi.PIANO);
		
		setInstrumentName("Piano (2)");
		setInstrumentSound("keyboard.piano.grand");
		setVirtualName("Concert Grand Piano");
	}
}
