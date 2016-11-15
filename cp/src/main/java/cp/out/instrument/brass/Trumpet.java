package cp.out.instrument.brass;

import cp.out.instrument.Instrument;
import cp.out.instrument.InstrumentGroup;
import cp.out.instrument.register.InstrumentRegister;
import cp.out.orchestration.InstrumentName;

public class Trumpet extends Instrument {
	
	public Trumpet(InstrumentRegister instrumentRegister) {
		init();
		setInstrumentRegister(instrumentRegister);
	}

	private void init() {
		instrumentGroup = InstrumentGroup.BRASS;
		order = 0;
		setLowest(60);
		setHighest(84);
//		setGeneralMidi(GeneralMidi.CELLO);
		
		setInstrumentName(InstrumentName.TRUMPET.toString());
		setInstrumentSound("brass.trumpet");
		setVirtualName("Trumpet 1");
	}

	public Trumpet() {
		init();
	}
	
	

}

