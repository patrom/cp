package cp.out.instrument.woodwinds;

import cp.midi.GeneralMidi;
import cp.out.instrument.Instrument;
import cp.out.instrument.register.InstrumentRegister;


public class Oboe extends Instrument {

	public Oboe(int voice, int channel) {
		super(voice, channel);
		init();
	}

	public Oboe() {
		init();
	}

	private void init() {
		setLowest(58);
		setHighest(84);
		setGeneralMidi(GeneralMidi.OBOE);
		
		setInstrumentName("Oboe");
		setInstrumentSound("wind.reed.oboe");
		setVirtualName("Oboe 1");
	}
	
	public Oboe(InstrumentRegister instrumentRegister) {
		init();
		setInstrumentRegister(instrumentRegister);
	}

}
