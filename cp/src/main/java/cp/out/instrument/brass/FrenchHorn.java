package cp.out.instrument.brass;

import cp.midi.GeneralMidi;
import cp.out.instrument.Instrument;
import cp.out.instrument.InstrumentGroup;
import cp.out.instrument.Technical;
import cp.out.instrument.register.InstrumentRegister;
import cp.out.orchestration.InstrumentName;


public class FrenchHorn extends Instrument {

	public FrenchHorn(InstrumentRegister instrumentRegister) {
		init();
		setInstrumentRegister(instrumentRegister);
	}
	
	public FrenchHorn() {
		init();
	}

	private void init() {
		instrumentGroup = InstrumentGroup.BRASS;
		order = 1;
		setLowest(40);
		setHighest(70);
		setGeneralMidi(GeneralMidi.FRENCH_HORN);
		
		setInstrumentName(InstrumentName.HORN.getName());
		setInstrumentSound("brass.french-horn");
		setVirtualName("Horn");
	}

    public FrenchHorn(InstrumentRegister instrumentRegister, Technical technical) {
        init();
        setInstrumentRegister(instrumentRegister);
        super.technical = technical;
    }

}
