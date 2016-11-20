package cp.out.instrument.woodwinds;

import cp.midi.GeneralMidi;
import cp.out.instrument.Instrument;
import cp.out.instrument.InstrumentGroup;
import cp.out.instrument.register.InstrumentRegister;
import cp.out.orchestration.InstrumentName;


public class Flute extends Instrument {

	private void init() {
		instrumentGroup = InstrumentGroup.WOODWINDS;
		order = 1;
		setLowest(60);
		setHighest(84);
		setGeneralMidi(GeneralMidi.FLUTE);
		
		setInstrumentName(InstrumentName.FLUTE.getName());
		setInstrumentSound("wind.flutes.flute");
		setVirtualName("Flute 1");
	}

	public Flute() {
		init();
	}
	
	public Flute(InstrumentRegister instrumentRegister){
		init();
		setInstrumentRegister(instrumentRegister);
	}

}
