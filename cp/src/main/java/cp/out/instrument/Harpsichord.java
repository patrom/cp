package cp.out.instrument;

import cp.midi.GeneralMidi;

public class Harpsichord extends Instrument {

	public Harpsichord() {
		setLowest(40);
		setHighest(108);
		setGeneralMidi(GeneralMidi.HARPSICHORD);
		
		setInstrumentName("Harpsichord");
		setInstrumentSound("keyboard.harpsichord");
	}

}

