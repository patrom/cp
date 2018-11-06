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
		setLowest(48);
		setHighest(68);
		setGeneralMidi(GeneralMidi.CHOIR);
		
		setInstrumentName(InstrumentName.TENOR.getName());
//		setInstrumentSound("strings.cello");
		setVirtualName("Tenor");
	}

	public Tenor(InstrumentRegister instrumentRegister) {
		init();
		setInstrumentRegister(instrumentRegister);
	}
}

