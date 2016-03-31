package cp.out.instrument.brass;

import cp.midi.GeneralMidi;
import cp.out.instrument.Instrument;
import cp.out.instrument.register.InstrumentRegister;

public class Trumpet extends Instrument {
	
	public Trumpet(int voice, int channel) {
		super(voice, channel);
		init();
	}
	
	public Trumpet(InstrumentRegister instrumentRegister) {
		init();
		setInstrumentRegister(instrumentRegister);
	}

	private void init() {
		setLowest(60);
		setHighest(84);
		setGeneralMidi(GeneralMidi.CELLO);
		
		setInstrumentName("Trumpet in B^b (2)");
		setInstrumentSound("brass.trumpet");
		setVirtualName("Trumpet 1");
	}

	public Trumpet() {
		init();
	}
	
	

}

