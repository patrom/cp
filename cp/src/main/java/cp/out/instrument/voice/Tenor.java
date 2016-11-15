package cp.out.instrument.voice;

import cp.midi.GeneralMidi;
import cp.out.instrument.Instrument;
import cp.out.instrument.InstrumentGroup;
import cp.out.instrument.register.InstrumentRegister;
import cp.out.orchestration.InstrumentName;

public class Tenor extends Instrument {

	public Tenor() {
		init();
	}

	private void init() {
		instrumentGroup = InstrumentGroup.VOICES;
		setLowest(49);
		setHighest(68);
		setGeneralMidi(GeneralMidi.CHOIR);
		
		setInstrumentName(InstrumentName.TENOR.toString());
//		setInstrumentSound("strings.cello");
//		setVirtualName("Celli");
	}

	public Tenor(InstrumentRegister instrumentRegister) {
		init();
		setInstrumentRegister(instrumentRegister);
	}
}

