package cp.out.instrument.brass;

import cp.midi.GeneralMidi;
import cp.out.instrument.Instrument;

public class Trumpet extends Instrument {
	
	public Trumpet(int voice, int channel) {
		super(voice, channel);
		setLowest(60);
		setHighest(84);
		setGeneralMidi(GeneralMidi.CELLO);
		
		setInstrumentName("Trumpet in B^b (2)");
		setInstrumentSound("brass.trumpet");
		setVirtualName("Trumpet 1");
	}

}

