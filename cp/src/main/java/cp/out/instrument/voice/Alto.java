package cp.out.instrument.voice;

import cp.midi.GeneralMidi;
import cp.out.instrument.Instrument;
import cp.out.instrument.InstrumentGroup;
import cp.out.instrument.register.InstrumentRegister;
import cp.out.orchestration.InstrumentName;

public class Alto extends Instrument {
	
	public Alto() {
		init();
	}

	private void init() {
		instrumentGroup = InstrumentGroup.VOICES;
		order = 2;
		setLowest(53);
		setHighest(73);
		setGeneralMidi(GeneralMidi.CHOIR);
		
		setInstrumentName(InstrumentName.ALTO.getName());
//		setInstrumentSound("strings.cello");
		setVirtualName("Alto");
	}

	public Alto(InstrumentRegister instrumentRegister) {
		init();
		setInstrumentRegister(instrumentRegister);
	}
}

