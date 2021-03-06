package cp.out.instrument.voice;

import cp.midi.GeneralMidi;
import cp.out.instrument.Instrument;
import cp.out.instrument.InstrumentGroup;
import cp.out.instrument.register.InstrumentRegister;
import cp.out.orchestration.InstrumentName;

public class Bass extends Instrument {

	public Bass() {
		init();
	}

	private void init() {
		instrumentGroup = InstrumentGroup.VOICES;
		setLowest(40);
		setHighest(58);
		setGeneralMidi(GeneralMidi.CHOIR);
		
		setInstrumentName(InstrumentName.BASS.getName());
//		setInstrumentSound("strings.cello");
		setVirtualName("Bass");
	}

	public Bass(InstrumentRegister instrumentRegister) {
		init();
		setInstrumentRegister(instrumentRegister);
	}
}

