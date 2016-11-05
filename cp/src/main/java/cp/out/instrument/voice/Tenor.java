package cp.out.instrument.voice;

import cp.midi.GeneralMidi;
import cp.out.instrument.Instrument;
import cp.out.instrument.InstrumentGroup;
import cp.out.instrument.register.InstrumentRegister;

public class Tenor extends Instrument {

	public Tenor() {
		init();
	}

	private void init() {
		instrumentGroup = InstrumentGroup.VOICES;
		setLowest(49);
		setHighest(68);
		setGeneralMidi(GeneralMidi.CHOIR);
		
//		setInstrumentName("Violoncello (2)");
//		setInstrumentSound("strings.cello");
//		setVirtualName("Celli");
	}

	public Tenor(InstrumentRegister instrumentRegister) {
		init();
		setInstrumentRegister(instrumentRegister);
	}
}

