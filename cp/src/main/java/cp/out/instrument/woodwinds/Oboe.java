package cp.out.instrument.woodwinds;

import cp.midi.GeneralMidi;
import cp.out.instrument.Instrument;
import cp.out.instrument.InstrumentGroup;
import cp.out.instrument.register.InstrumentRegister;
import cp.out.orchestration.InstrumentName;


public class Oboe extends Instrument {

	public Oboe() {
		init();
	}

	private void init() {
		instrumentGroup = InstrumentGroup.WOODWINDS;
		order = 3;
		setLowest(58);
		setHighest(84);
		setGeneralMidi(GeneralMidi.OBOE);
		
		setInstrumentName(InstrumentName.OBOE.toString());
		setInstrumentSound("wind.reed.oboe");
		setVirtualName("Oboe 1");
	}
	
	public Oboe(InstrumentRegister instrumentRegister) {
		init();
		setInstrumentRegister(instrumentRegister);
	}

}
