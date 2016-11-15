package cp.out.instrument.woodwinds;

import cp.out.instrument.Instrument;
import cp.out.instrument.InstrumentGroup;
import cp.out.instrument.register.InstrumentRegister;
import cp.out.orchestration.InstrumentName;

public class ContraBassoon extends Instrument{

	private void init() {
		instrumentGroup = InstrumentGroup.WOODWINDS;
		order = 9;
		setLowest(22);
		setHighest(54);
//		setGeneralMidi(GeneralMidi.BASSOON);
		
		setInstrumentName(InstrumentName.CONTRABASSOON.toString());
		setInstrumentSound("wind.reed.contrabassoon");
		setVirtualName("Contra Bassoon");
		setClef("F");
	}

	public ContraBassoon() {
		init();
	}
	
	public ContraBassoon(InstrumentRegister instrumentRegister) {
		init();
		setInstrumentRegister(instrumentRegister);
	}

}
