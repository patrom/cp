package cp.out.instrument.voice;

import cp.midi.GeneralMidi;
import cp.out.instrument.Instrument;

public class Alto extends Instrument {

	public Alto(int voice, int channel) {
		super(voice, channel);
		setLowest(50);
		setHighest(73);
		setGeneralMidi(GeneralMidi.CHOIR);
	}
}
