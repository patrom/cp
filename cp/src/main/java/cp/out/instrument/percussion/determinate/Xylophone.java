package cp.out.instrument.percussion.determinate;

import cp.midi.GeneralMidi;
import cp.out.instrument.Instrument;
import cp.out.instrument.InstrumentGroup;
import cp.out.instrument.register.InstrumentRegister;
import cp.out.orchestration.InstrumentName;

public class Xylophone  extends Instrument {
	
	public Xylophone() {
		init();
	}

	private void init() {
		instrumentGroup = InstrumentGroup.MALLETS;
		order = 0;
		setLowest(65);
		setHighest(108);
		setGeneralMidi(GeneralMidi.XYLOPHONE);
		
		setInstrumentName(InstrumentName.XYLOPHONE.getName());
		setInstrumentSound("pitched-percussion.xylophone");
		setVirtualName("Xylophone");
	}

	public Xylophone(InstrumentRegister instrumentRegister) {
		init();
		setInstrumentRegister(instrumentRegister);
	}
}

