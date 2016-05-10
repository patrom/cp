package cp.out.instrument.percussion;

import cp.midi.GeneralMidi;
import cp.out.instrument.Instrument;
import cp.out.instrument.InstrumentGroup;
import cp.out.instrument.register.InstrumentRegister;

public class Glockenspiel extends Instrument {
	
	public Glockenspiel(int voice, int channel) {
		super(voice, channel);
		init();
	}
	
	public Glockenspiel() {
		init();
	}

	private void init() {
		instrumentGroup = InstrumentGroup.PERCUSSION;
		order = 0;
		setLowest(77);
		setHighest(108);
//		setGeneralMidi(GeneralMidi.CELLO);
		
		setInstrumentName("Glockenspiel (2)");
		setInstrumentSound("pitched-percussion.glockenspiel");
		setVirtualName("Glockenspiel");
	}

	public Glockenspiel(InstrumentRegister instrumentRegister) {
		init();
		setInstrumentRegister(instrumentRegister);
	}
}
