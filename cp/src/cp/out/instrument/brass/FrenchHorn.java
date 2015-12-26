package cp.out.instrument.brass;

import cp.midi.GeneralMidi;
import cp.out.instrument.Instrument;


public class FrenchHorn extends Instrument {

	public FrenchHorn(int voice, int channel) {
		super(voice, channel);
		setLowest(34);
		setHighest(70);
		setGeneralMidi(GeneralMidi.FRENCH_HORN);
	}

}
