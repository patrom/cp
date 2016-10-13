package cp.out.instrument.voice;

import cp.midi.GeneralMidi;
import cp.out.instrument.Instrument;
import cp.out.instrument.InstrumentGroup;
import cp.out.instrument.register.InstrumentRegister;

public class Soprano extends Instrument {

	public Soprano(int voice, int channel) {
		super(voice, channel);
		init();
	}
	
	public Soprano() {
		init();
	}

	private void init() {
		instrumentGroup = InstrumentGroup.VOICES;
		setLowest(60);
		setHighest(79);
		setGeneralMidi(GeneralMidi.CHOIR);
		
//		setInstrumentName("Violoncello (2)");
//		setInstrumentSound("strings.cello");
//		setVirtualName("Celli");
	}

	public Soprano(InstrumentRegister instrumentRegister) {
		init();
		setInstrumentRegister(instrumentRegister);
	}
}

