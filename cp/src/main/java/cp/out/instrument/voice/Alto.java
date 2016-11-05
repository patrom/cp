package cp.out.instrument.voice;

import cp.midi.GeneralMidi;
import cp.out.instrument.Instrument;
import cp.out.instrument.InstrumentGroup;
import cp.out.instrument.register.InstrumentRegister;

public class Alto extends Instrument {
	
	public Alto() {
		init();
	}

	private void init() {
		instrumentGroup = InstrumentGroup.VOICES;
		order = 2;
		setLowest(50);
		setHighest(73);
		setGeneralMidi(GeneralMidi.CHOIR);
		
//		setInstrumentName("Violoncello (2)");
//		setInstrumentSound("strings.cello");
//		setVirtualName("Celli");
	}

	public Alto(InstrumentRegister instrumentRegister) {
		init();
		setInstrumentRegister(instrumentRegister);
	}
}

