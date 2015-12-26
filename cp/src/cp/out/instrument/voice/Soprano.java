package cp.out.instrument.voice;

import cp.midi.GeneralMidi;
import cp.out.instrument.Instrument;

public class Soprano extends Instrument {

	public Soprano(int voice, int channel) {
		super(voice, channel);
		setLowest(60);
		setHighest(79);
		setGeneralMidi(GeneralMidi.CHOIR);
	}
}
