package cp.out.instrument.woodwinds;

import cp.out.instrument.Instrument;
import cp.out.instrument.InstrumentGroup;
import cp.out.instrument.register.InstrumentRegister;
import cp.out.orchestration.InstrumentName;

public class ClarinetEFlat extends Instrument {

	private void init() {
		instrumentGroup = InstrumentGroup.WOODWINDS;
		order = 4;
		setLowest(55);
		setHighest(95);
//		setGeneralMidi(GeneralMidi.CLARINET);
		
		setInstrumentName(InstrumentName.SMALL_CLARINET.getName());
		setInstrumentSound("wind.reed.clarinet");
		setVirtualName("Clarinet Bb 1");
	}

	public ClarinetEFlat() {
		init();
	}
	
	public ClarinetEFlat(InstrumentRegister instrumentRegister) {
		init();
		setInstrumentRegister(instrumentRegister);
	}

}