package cp.out.instrument.voice;

import cp.midi.GeneralMidi;
import cp.out.instrument.Instrument;
import cp.out.instrument.InstrumentGroup;
import cp.out.instrument.register.InstrumentRegister;

public class Bass extends Instrument {

	public Bass() {
		init();
	}

	private void init() {
		instrumentGroup = InstrumentGroup.VOICES;
		setLowest(37);
		setHighest(58);
		setGeneralMidi(GeneralMidi.CHOIR);
		
//		setInstrumentName("Violoncello (2)");
//		setInstrumentSound("strings.cello");
//		setVirtualName("Celli");
	}

	public Bass(InstrumentRegister instrumentRegister) {
		init();
		setInstrumentRegister(instrumentRegister);
	}
}

