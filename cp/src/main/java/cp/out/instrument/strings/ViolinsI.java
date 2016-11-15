package cp.out.instrument.strings;

import cp.midi.GeneralMidi;
import cp.out.instrument.Instrument;
import cp.out.instrument.InstrumentGroup;
import cp.out.instrument.register.InstrumentRegister;
import cp.out.orchestration.InstrumentName;

public class ViolinsI extends Instrument {

	public ViolinsI() {
		init();
	}
	
	public void init() {
		instrumentGroup = InstrumentGroup.STRINGS;
		order = 0;
		setLowest(55);
		setHighest(84);
		setGeneralMidi(GeneralMidi.VIOLIN);
		
		setInstrumentName(InstrumentName.VIOLIN_I.toString());
		setInstrumentSound("strings.violin");
		setVirtualName("Violins");
	}
	
	public ViolinsI(InstrumentRegister instrumentRegister) {
		init();
		setInstrumentRegister(instrumentRegister);
	}
	
	
}