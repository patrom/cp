package cp.out.instrument.strings;

import cp.midi.GeneralMidi;
import cp.out.instrument.Instrument;

public class Viola extends Instrument {

	public Viola(int voice, int channel) {
		super(voice, channel);
		setLowest(48);
		setHighest(72);
		setGeneralMidi(GeneralMidi.VIOLA);
		
		setInstrumentName("Viola (2)");
		setInstrumentSound("strings.viola");
		setVirtualName("Violas");
	}

}

