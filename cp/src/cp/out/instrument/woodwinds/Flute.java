package cp.out.instrument.woodwinds;

import cp.midi.GeneralMidi;
import cp.out.instrument.Instrument;
import cp.out.instrument.register.InstrumentRegister;


public class Flute extends Instrument {

	public Flute(int voice, int channel) {
		super(voice, channel);
		init();
	}

	private void init() {
		setLowest(60);
		setHighest(84);
		setGeneralMidi(GeneralMidi.FLUTE);
		
		setInstrumentName("Flute (2)");
		setInstrumentSound("wind.flutes.flute");
		setVirtualName("Flute 1");
	}

	public Flute() {
		init();
	}
	
	public Flute(InstrumentRegister instrumentRegister){
		setInstrumentRegister(instrumentRegister);
		init();
	}

}
