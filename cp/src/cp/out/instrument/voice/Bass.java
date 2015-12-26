package cp.out.instrument.voice;

import cp.midi.GeneralMidi;
import cp.out.instrument.Instrument;

public class Bass extends Instrument {

	public Bass(int voice, int channel) {
		super(voice, channel);
		setLowest(37);
		setHighest(58);
		setGeneralMidi(GeneralMidi.CHOIR);
	}
}
