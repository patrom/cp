package cp.out.instrument.woodwinds;

import cp.midi.GeneralMidi;
import cp.out.instrument.Instrument;


public class Bassoon extends Instrument {

	public Bassoon(int voice, int channel) {
		super(voice, channel);
		setLowest(34);
		setHighest(70);
		setGeneralMidi(GeneralMidi.BASSOON);
		
		setInstrumentName("Bassoon");
		setInstrumentSound("wind.reed.bassoon");
		setVirtualName("Bassoon 1");
		setClef("F");
	}

}
