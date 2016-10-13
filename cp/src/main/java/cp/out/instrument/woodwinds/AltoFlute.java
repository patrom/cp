package cp.out.instrument.woodwinds;

import cp.out.instrument.Instrument;
import cp.out.instrument.InstrumentGroup;
import cp.out.instrument.register.InstrumentRegister;

public class AltoFlute extends Instrument{
	
	public AltoFlute(int voice, int channel) {
		super(voice, channel);
		init();
	}

	private void init() {
		instrumentGroup = InstrumentGroup.WOODWINDS;
		order = 4;
		setLowest(55);
		setHighest(91);
//		setGeneralMidi(GeneralMidi.FLUTE);
		
		setInstrumentName("Alto Flute");
		setInstrumentSound("wind.flutes.flute.alto");
		setVirtualName("Alto Flute");
	}

	public AltoFlute() {
		init();
	}
	
	public AltoFlute(InstrumentRegister instrumentRegister){
		init();
		setInstrumentRegister(instrumentRegister);
	}

}
