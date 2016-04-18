package cp.out.instrument.strings;

import cp.midi.GeneralMidi;
import cp.out.instrument.Instrument;
import cp.out.instrument.InstrumentGroup;


public class CelloSolo extends Instrument {
	
	public CelloSolo(int voice, int channel) {
		super(voice, channel);
		instrumentGroup = InstrumentGroup.STRINGS;
		order = 2;
		setLowest(36);
		setHighest(70);
		setGeneralMidi(GeneralMidi.CELLO);
		
		setInstrumentName("Violoncello");
		setInstrumentSound("strings.cello");
		setVirtualName("Cello 1 solo");
		setClef("F");
	}

}
