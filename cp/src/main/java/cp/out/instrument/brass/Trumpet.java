package cp.out.instrument.brass;

import cp.midi.GeneralMidi;
import cp.out.instrument.Instrument;
import cp.out.instrument.InstrumentGroup;
import cp.out.instrument.Technical;
import cp.out.instrument.register.InstrumentRegister;
import cp.out.orchestration.InstrumentName;

public class Trumpet extends Instrument {
	
	public Trumpet(InstrumentRegister instrumentRegister) {
		init();
		setInstrumentRegister(instrumentRegister);
	}

    public Trumpet(InstrumentRegister instrumentRegister, Technical technical) {
        init();
        setInstrumentRegister(instrumentRegister);
        super.technical = technical;
    }

	private void init() {
		instrumentGroup = InstrumentGroup.BRASS;
		order = 0;
		setLowest(60);
		setHighest(84);
		setGeneralMidi(GeneralMidi.TRUMPET);
		
		setInstrumentName(InstrumentName.TRUMPET.getName());
		setInstrumentSound("brass.trumpet");
		setVirtualName("Trumpet 1");
	}

	public Trumpet() {
		init();
	}
	
	

}

