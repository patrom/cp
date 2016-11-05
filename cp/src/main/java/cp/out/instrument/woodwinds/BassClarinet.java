package cp.out.instrument.woodwinds;

import cp.out.instrument.Instrument;
import cp.out.instrument.InstrumentGroup;
import cp.out.instrument.register.InstrumentRegister;

public class BassClarinet extends Instrument{

	private void init() {
		instrumentGroup = InstrumentGroup.WOODWINDS;
		order = 7;
		setLowest(34);
		setHighest(82);
//		setGeneralMidi(GeneralMidi.CLARINET);
		
		setInstrumentName("Bass Clarinet in B^b");
		setInstrumentSound("wind.reed.clarinet.bass");
		setVirtualName("Bass Clarinet");
		setClef("F");
	}

	public BassClarinet() {
		init();
	}
	
	public BassClarinet(InstrumentRegister instrumentRegister) {
		init();
		setInstrumentRegister(instrumentRegister);
	}

}

