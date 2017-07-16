package cp.out.instrument.strings;

import cp.midi.GeneralMidi;
import cp.out.instrument.Instrument;
import cp.out.instrument.InstrumentGroup;
import cp.out.instrument.register.InstrumentRegister;
import cp.out.orchestration.InstrumentName;

public class DoubleBass extends Instrument {

	private void init() {
		instrumentGroup = InstrumentGroup.ORCHESTRAL_STRINGS;
		order = 3;
		setLowest(38);
		setHighest(67);
		setGeneralMidi(GeneralMidi.CONTRABASS);
		
		setInstrumentName(InstrumentName.BASS.getName());
		setInstrumentSound("strings.Contrabass");
		setVirtualName("Basses");
		setClef("F");
	}

	public DoubleBass() {
		init();
	}
	
	public DoubleBass(InstrumentRegister instrumentRegister) {
		init();
		setInstrumentRegister(instrumentRegister);
	}
	

}
