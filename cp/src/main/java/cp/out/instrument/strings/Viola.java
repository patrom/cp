package cp.out.instrument.strings;

import cp.midi.GeneralMidi;
import cp.out.instrument.Instrument;
import cp.out.instrument.InstrumentGroup;
import cp.out.instrument.register.InstrumentRegister;
import cp.out.orchestration.InstrumentName;

public class Viola extends Instrument {
	
	public Viola(){
		init();
	}

	private void init() {
		instrumentGroup = InstrumentGroup.ORCHESTRAL_STRINGS;
		order = 1;
		setLowest(48);
		setHighest(72);
		setGeneralMidi(GeneralMidi.VIOLA);
		
		setInstrumentName(InstrumentName.VIOLA.getName());
		setInstrumentSound("strings.viola");
		setVirtualName("Violas");
		setClef("C");
	}
	
	public Viola(InstrumentRegister instrumentRegister) {
		init();
		setInstrumentRegister(instrumentRegister);
	}

}

