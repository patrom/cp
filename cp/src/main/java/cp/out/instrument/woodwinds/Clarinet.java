package cp.out.instrument.woodwinds;

import cp.midi.GeneralMidi;
import cp.out.instrument.Instrument;
import cp.out.instrument.InstrumentGroup;
import cp.out.instrument.register.InstrumentRegister;
import cp.out.orchestration.InstrumentName;


public class Clarinet extends Instrument {

	private void init() {
		instrumentGroup = InstrumentGroup.WOODWINDS;
		order = 6;
		setLowest(50);
		setHighest(84);
		setGeneralMidi(GeneralMidi.CLARINET);
		
		setInstrumentName(InstrumentName.CLARINET.toString());
		setInstrumentSound("wind.reed.clarinet");
		setVirtualName("Clarinet Bb 1");
	}

	public Clarinet() {
		init();
	}
	
	public Clarinet(InstrumentRegister instrumentRegister) {
		init();
		setInstrumentRegister(instrumentRegister);
	}

}
