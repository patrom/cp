package cp.out.instrument.woodwinds;

import cp.midi.GeneralMidi;
import cp.out.instrument.Instrument;
import cp.out.instrument.InstrumentGroup;
import cp.out.instrument.register.InstrumentRegister;
import cp.out.orchestration.InstrumentName;

public class CorAnglais extends Instrument{

	private void init() {
		instrumentGroup = InstrumentGroup.WOODWINDS;
		order = 5;
		setLowest(52);
		setHighest(84);
		setGeneralMidi(GeneralMidi.ENGLISH_HORN);
		
		setInstrumentName(InstrumentName.COR_ANGLAIS.getName());
		setInstrumentSound("wind.reed.english-horn");
		setVirtualName("English Horn");
	}

	public CorAnglais() {
		init();
	}
	
	public CorAnglais(InstrumentRegister instrumentRegister){
		init();
		setInstrumentRegister(instrumentRegister);
	}

}

