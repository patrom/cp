package cp.out.instrument.strings;

import cp.midi.GeneralMidi;
import cp.out.instrument.Instrument;
import cp.out.instrument.InstrumentGroup;
import cp.out.orchestration.InstrumentName;


public class DoublebassSolo extends Instrument {

	public DoublebassSolo() {
		instrumentGroup = InstrumentGroup.STRINGS;
		order = 3;
		setLowest(38);
		setHighest(67);
		setGeneralMidi(GeneralMidi.CONTRABASS);
		
		setInstrumentName(InstrumentName.CONTRABASS_SOLO.toString());
		setInstrumentSound("strings.Contrabass");
		setVirtualName("Bass 1 solo");
		setClef("F");
	}

}
