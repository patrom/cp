package cp.out.instrument.voice;

import cp.midi.GeneralMidi;
import cp.out.instrument.Instrument;

public class Tenor extends Instrument {

	public Tenor(int voice, int channel) {
		super(voice, channel);
		setLowest(49);
		setHighest(68);
		setGeneralMidi(GeneralMidi.CHOIR);
	}

}
