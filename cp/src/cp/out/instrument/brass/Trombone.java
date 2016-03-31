package cp.out.instrument.brass;

import cp.midi.GeneralMidi;
import cp.out.instrument.Instrument;
import cp.out.instrument.register.InstrumentRegister;

public class Trombone extends Instrument {
	
	public Trombone(int voice, int channel) {
		super(voice, channel);
		init();
	}

	public Trombone(InstrumentRegister instrumentRegister) {
		init();
		setInstrumentRegister(instrumentRegister);
	}

	private void init() {
		setLowest(40);
		setHighest(71);
		setGeneralMidi(GeneralMidi.CELLO);
		
		setInstrumentName("Trombone (2)");
		setInstrumentSound("brass.trombone.tenor");
		setVirtualName("Trombone");
	}

	public Trombone() {
		init();
	}
	
	
}


