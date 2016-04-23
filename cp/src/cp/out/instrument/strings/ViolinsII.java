package cp.out.instrument.strings;

import cp.midi.GeneralMidi;
import cp.out.instrument.Instrument;
import cp.out.instrument.InstrumentGroup;
import cp.out.instrument.register.InstrumentRegister;

public class ViolinsII extends Instrument {

	public ViolinsII(int voice, int channel) {
		super(voice, channel);
		init();
	}

	private void init() {
		instrumentGroup = InstrumentGroup.STRINGS;
		order = 0;
		setLowest(55);
		setHighest(84);
		setGeneralMidi(GeneralMidi.VIOLIN);
		
		setInstrumentName("Violin II");
		setInstrumentSound("strings.violin");
		setVirtualName("Violins");
	}
	
	public ViolinsII(){
		init();
	}
	
	public ViolinsII(InstrumentRegister instrumentRegister) {
		init();
		setInstrumentRegister(instrumentRegister);
	}
}
