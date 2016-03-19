package cp.out.instrument.woodwinds;

import cp.midi.GeneralMidi;
import cp.out.instrument.Instrument;
import cp.out.instrument.register.InstrumentRegister;


public class Clarinet extends Instrument {

	public Clarinet(int voice, int channel) {
		super(voice, channel);
		init();
	}

	private void init() {
		setLowest(50);
		setHighest(84);
		setGeneralMidi(GeneralMidi.CLARINET);
		
		setInstrumentName("Clarinet in B^b");
		setInstrumentSound("wind.reed.clarinet");
		setVirtualName("Clarinet Bb 1");
	}

	public Clarinet() {
		init();
	}
	
	public Clarinet(InstrumentRegister instrumentRegister) {
		setInstrumentRegister(instrumentRegister);
		init();
	}

}
