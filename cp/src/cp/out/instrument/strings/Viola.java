package cp.out.instrument.strings;

import cp.midi.GeneralMidi;
import cp.out.instrument.Instrument;
import cp.out.instrument.register.InstrumentRegister;

public class Viola extends Instrument {
	
	public Viola(){
		init();
	}

	public Viola(int voice, int channel) {
		super(voice, channel);
		init();
	}

	private void init() {
		setLowest(48);
		setHighest(72);
		setGeneralMidi(GeneralMidi.VIOLA);
		
		setInstrumentName("Viola (2)");
		setInstrumentSound("strings.viola");
		setVirtualName("Violas");
	}
	
	public Viola(InstrumentRegister instrumentRegister) {
		setInstrumentRegister(instrumentRegister);
		init();
	}

}

