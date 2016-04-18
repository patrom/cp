package cp.out.instrument.woodwinds;

import cp.midi.GeneralMidi;
import cp.out.instrument.Instrument;
import cp.out.instrument.InstrumentGroup;
import cp.out.instrument.register.InstrumentRegister;

public class CorAnglais extends Instrument{

	public CorAnglais(int voice, int channel) {
		super(voice, channel);
		init();
	}

	private void init() {
		instrumentGroup = InstrumentGroup.WOODWINDS;
		order = 5;
		setLowest(52);
		setHighest(84);
//		setGeneralMidi(GeneralMidi.FLUTE);
		
		setInstrumentName("Cor Anglais");
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

