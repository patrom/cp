package cp.out.instrument.woodwinds;

import cp.midi.GeneralMidi;
import cp.out.instrument.Instrument;


public class Clarinet extends Instrument {

	public Clarinet(int voice, int channel) {
		super(voice, channel);
		setLowest(50);
		setHighest(84);
		setGeneralMidi(GeneralMidi.CLARINET);
	}

}
