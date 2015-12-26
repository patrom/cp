package cp.out.instrument.strings;

import cp.midi.GeneralMidi;
import cp.out.instrument.Instrument;


public class Doublebass extends Instrument {

	public Doublebass(int voice, int channel) {
		super(voice, channel);
		setLowest(38);
		setGeneralMidi(GeneralMidi.CONTRABASS);
		
		setInstrumentName("Contrabass");
		setInstrumentSound("strings.Contrabass");
		setVirtualName("Bass 1 solo");
		setClef("F");
	}

}
