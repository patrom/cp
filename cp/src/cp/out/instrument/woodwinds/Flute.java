package cp.out.instrument.woodwinds;

import cp.midi.GeneralMidi;
import cp.out.instrument.Instrument;


public class Flute extends Instrument {

	public Flute(int voice, int channel) {
		super(voice, channel);
		setLowest(60);
		setHighest(84);
		setGeneralMidi(GeneralMidi.FLUTE);
	}

}
