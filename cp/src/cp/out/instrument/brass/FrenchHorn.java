package cp.out.instrument.brass;

import cp.midi.GeneralMidi;
import cp.out.instrument.Instrument;
import cp.out.instrument.InstrumentGroup;
import cp.out.instrument.register.InstrumentRegister;


public class FrenchHorn extends Instrument {

	public FrenchHorn(int voice, int channel) {
		super(voice, channel);
		init();
	}
	
	public FrenchHorn(InstrumentRegister instrumentRegister) {
		init();
		setInstrumentRegister(instrumentRegister);
	}
	
	public FrenchHorn() {
		init();
	}

	private void init() {
		instrumentGroup = InstrumentGroup.BRASS;
		order = 0;
		setLowest(40);
		setHighest(70);
		setGeneralMidi(GeneralMidi.FRENCH_HORN);
		
		setInstrumentName("Horn in F (2)");
		setInstrumentSound("brass.french-horn");
		setVirtualName("Horn");
	}

}
