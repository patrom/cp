package cp.out.instrument.woodwinds;

import cp.midi.GeneralMidi;
import cp.out.instrument.Instrument;
import cp.out.instrument.InstrumentGroup;
import cp.out.instrument.register.InstrumentRegister;
import cp.out.orchestration.InstrumentName;

public class Piccolo extends Instrument{

	private void init() {
		instrumentGroup = InstrumentGroup.WOODWINDS;
		order = 0;
		setLowest(74);
		setHighest(108);
		setGeneralMidi(GeneralMidi.PICCOLO);
		
		setInstrumentName(InstrumentName.PICCOLO.getName());
		setInstrumentSound("wind.flutes.flute.piccolo");
		setVirtualName("Piccolo");
	}

	public Piccolo() {
		init();
	}
	
	public Piccolo(InstrumentRegister instrumentRegister){
		init();
		setInstrumentRegister(instrumentRegister);
	}

}
