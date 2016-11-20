package cp.out.instrument.strings;

import cp.midi.GeneralMidi;
import cp.out.instrument.Instrument;
import cp.out.instrument.InstrumentGroup;
import cp.out.orchestration.InstrumentName;


public class CelloSolo extends Instrument {
	
	public CelloSolo() {
		instrumentGroup = InstrumentGroup.STRINGS;
		order = 2;
		setLowest(36);
		setHighest(70);
		setGeneralMidi(GeneralMidi.CELLO);

		setInstrumentName(InstrumentName.CELLO_SOLO.getName());
		setInstrumentSound("strings.cello");
		setVirtualName("Cello 1 solo");
		setClef("F");
	}

}
