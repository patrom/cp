package cp.out.instrument.percussion;

import cp.out.instrument.Instrument;
import cp.out.instrument.InstrumentGroup;
import cp.out.instrument.register.InstrumentRegister;

public class Timpani extends Instrument {
	
	public Timpani(int voice, int channel) {
		super(voice, channel);
		init();
	}
	
	public Timpani() {
		init();
	}

	private void init() {
		instrumentGroup = InstrumentGroup.PERCUSSION;
		order = 1;
		setLowest(41);
		setHighest(53);
//		setGeneralMidi(GeneralMidi.CELLO);
		
		setInstrumentName("Timpani");
		setInstrumentSound("drum.timpani");
		setVirtualName("Timpani hit");
	}

	public Timpani(InstrumentRegister instrumentRegister) {
		init();
		setInstrumentRegister(instrumentRegister);
	}
}
