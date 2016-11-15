package cp.out.instrument.brass;

import cp.out.instrument.Instrument;
import cp.out.instrument.InstrumentGroup;
import cp.out.instrument.register.InstrumentRegister;
import cp.out.orchestration.InstrumentName;

public class Trombone extends Instrument {

	public Trombone(InstrumentRegister instrumentRegister) {
		init();
		setInstrumentRegister(instrumentRegister);
	}

	private void init() {
		instrumentGroup = InstrumentGroup.BRASS;
		order = 2;
		setLowest(40);
		setHighest(71);
//		setGeneralMidi(GeneralMidi.t);
		
		setInstrumentName(InstrumentName.TROMBONE.toString());
		setInstrumentSound("brass.trombone.tenor");
		setVirtualName("Trombone");
	}

	public Trombone() {
		init();
	}
	
	
}


