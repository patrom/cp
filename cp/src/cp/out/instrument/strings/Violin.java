package cp.out.instrument.strings;

import cp.midi.GeneralMidi;
import cp.out.instrument.Instrument;


public class Violin extends Instrument {

	public Violin(int voice, int channel) {
		super(voice, channel);
		setLowest(55);
		setHighest(84);
		setGeneralMidi(GeneralMidi.VIOLIN);
		
		setInstrumentName("Violin");
		setInstrumentSound("strings.violin");
		setVirtualName("Violin 1 solo");
	}

}
