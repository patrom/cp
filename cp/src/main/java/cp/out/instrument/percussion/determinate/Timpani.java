package cp.out.instrument.percussion.determinate;

import cp.midi.GeneralMidi;
import cp.out.instrument.Instrument;
import cp.out.instrument.InstrumentGroup;
import cp.out.instrument.register.InstrumentRegister;
import cp.out.orchestration.InstrumentName;

public class Timpani extends Instrument {

	public Timpani() {
		init();
	}

	private void init() {
		instrumentGroup = InstrumentGroup.PERCUSSION;
		order = 1;
		setLowest(41);
		setHighest(53);
		setGeneralMidi(GeneralMidi.TIMPANI);
		
		setInstrumentName(InstrumentName.TIMPANI.getName());
		setInstrumentSound("drum.timpani");
		setVirtualName("Timpani hit");
	}

	public Timpani(InstrumentRegister instrumentRegister) {
		init();
		setInstrumentRegister(instrumentRegister);
	}
}
