package cp.out.instrument.brass;

import cp.midi.GeneralMidi;
import cp.out.instrument.Instrument;


public class FrenchHorn extends Instrument {

	public FrenchHorn(int voice, int channel) {
		super(voice, channel);
		setLowest(40);
		setHighest(70);
		setGeneralMidi(GeneralMidi.FRENCH_HORN);
		
		setInstrumentName("Horn in F (2)");
		setInstrumentSound("brass.french-horn");
		setVirtualName("Horn");
	}

}
