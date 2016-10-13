package cp.out.instrument.strings;

import cp.midi.GeneralMidi;
import cp.out.instrument.Instrument;
import cp.out.instrument.InstrumentGroup;


public class DoublebassSolo extends Instrument {

	public DoublebassSolo(int voice, int channel) {
		super(voice, channel);
		instrumentGroup = InstrumentGroup.STRINGS;
		order = 3;
		setLowest(38);
		setHighest(67);
		setGeneralMidi(GeneralMidi.CONTRABASS);
		
		setInstrumentName("Contrabass");
		setInstrumentSound("strings.Contrabass");
		setVirtualName("Bass 1 solo");
		setClef("F");
	}

}
