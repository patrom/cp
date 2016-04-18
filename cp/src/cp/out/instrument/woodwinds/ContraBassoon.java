package cp.out.instrument.woodwinds;

import cp.midi.GeneralMidi;
import cp.out.instrument.Instrument;
import cp.out.instrument.InstrumentGroup;
import cp.out.instrument.register.InstrumentRegister;

public class ContraBassoon extends Instrument{
	
	public ContraBassoon(int voice, int channel) {
		super(voice, channel);
		init();
	}

	private void init() {
		instrumentGroup = InstrumentGroup.WOODWINDS;
		order = 9;
		setLowest(22);
		setHighest(54);
//		setGeneralMidi(GeneralMidi.BASSOON);
		
		setInstrumentName("Contrabassoon");
		setInstrumentSound("wind.reed.contrabassoon");
		setVirtualName("Contra Bassoon");
		setClef("F");
	}

	public ContraBassoon() {
		init();
	}
	
	public ContraBassoon(InstrumentRegister instrumentRegister) {
		init();
		setInstrumentRegister(instrumentRegister);
	}

}
