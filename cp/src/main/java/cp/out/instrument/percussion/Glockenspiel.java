package cp.out.instrument.percussion;

import cp.midi.GeneralMidi;
import cp.out.instrument.Instrument;
import cp.out.instrument.InstrumentGroup;
import cp.out.instrument.register.InstrumentRegister;
import cp.out.orchestration.InstrumentName;

public class Glockenspiel extends Instrument {

	public Glockenspiel() {
		init();
	}

	private void init() {
		instrumentGroup = InstrumentGroup.PERCUSSION;
		order = 0;
		setLowest(77);
		setHighest(108);
		setGeneralMidi(GeneralMidi.GLOCKEN);
		
		setInstrumentName(InstrumentName.GLOCKENSPIEL.getName());
		setInstrumentSound("pitched-percussion.glockenspiel");
		setVirtualName("Glockenspiel");
	}

	public Glockenspiel(InstrumentRegister instrumentRegister) {
		init();
		setInstrumentRegister(instrumentRegister);
	}
}
