package cp.out.instrument.brass;

import cp.midi.GeneralMidi;
import cp.out.instrument.Instrument;
import cp.out.instrument.InstrumentGroup;
import cp.out.instrument.Technical;
import cp.out.instrument.register.InstrumentRegister;
import cp.out.orchestration.InstrumentName;

public class Trombone extends Instrument {

	public Trombone(InstrumentRegister instrumentRegister) {
		init();
		setInstrumentRegister(instrumentRegister);
	}

	private void init() {
		instrumentGroup = InstrumentGroup.BRASS;
		order = 2;
		setLowest(40);
		setHighest(71);
		setGeneralMidi(GeneralMidi.TROMBONE);
		
		setInstrumentName(InstrumentName.TROMBONE.getName());
		setInstrumentSound("brass.trombone.tenor");
		setVirtualName("Trombone");
	}

	public Trombone() {
		init();
	}

    public Trombone(InstrumentRegister instrumentRegister, Technical technical) {
        init();
        setInstrumentRegister(instrumentRegister);
        super.technical = technical;
    }
}


