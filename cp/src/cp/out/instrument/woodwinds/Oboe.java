package cp.out.instrument.woodwinds;

import cp.midi.GeneralMidi;
import cp.out.instrument.Instrument;


public class Oboe extends Instrument {

	public Oboe(int voice, int channel) {
		super(voice, channel);
		setLowest(58);
		setHighest(84);
		setGeneralMidi(GeneralMidi.OBOE);
	}

}
