package cp.out.instrument.percussion;

import cp.out.instrument.Instrument;
import cp.out.instrument.InstrumentGroup;
import cp.out.instrument.register.InstrumentRegister;

public class Xylophone  extends Instrument {
	
	public Xylophone(int voice, int channel) {
		super(voice, channel);
		init();
	}
	
	public Xylophone() {
		init();
	}

	private void init() {
		instrumentGroup = InstrumentGroup.PERCUSSION;
		order = 0;
		setLowest(65);
		setHighest(108);
//		setGeneralMidi(GeneralMidi.CELLO);
		
		setInstrumentName("Xylophone (2)");
		setInstrumentSound("pitched-percussion.xylophone");
		setVirtualName("Xylophone");
	}

	public Xylophone(InstrumentRegister instrumentRegister) {
		init();
		setInstrumentRegister(instrumentRegister);
	}
}

