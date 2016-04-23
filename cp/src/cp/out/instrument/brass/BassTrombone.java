package cp.out.instrument.brass;

import cp.midi.GeneralMidi;
import cp.out.instrument.Instrument;
import cp.out.instrument.InstrumentGroup;
import cp.out.instrument.register.InstrumentRegister;

public class BassTrombone extends Instrument{

	public BassTrombone(int voice, int channel) {
		super(voice, channel);
		init();
	}

	public BassTrombone(InstrumentRegister instrumentRegister) {
		init();
		setInstrumentRegister(instrumentRegister);
	}

	private void init() {
		instrumentGroup = InstrumentGroup.BRASS;
		order = 3;
		setLowest(34);
		setHighest(65);
//		setGeneralMidi(GeneralMidi.CELLO);
		
		setInstrumentName("Bass Trombone");
		setInstrumentSound("brass.trombone.bass");
		setVirtualName("Bass Trombone");
		setClef("F");
	}

	public BassTrombone() {
		init();
	}
	
}


