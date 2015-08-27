package cp.out.instrument;

import cp.midi.GeneralMidi;

public class KontaktLibPiano extends Instrument {

	public KontaktLibPiano(int voice, int channel) {
		super(voice, channel);
		setLowest(40);
		setHighest(108);
		setGeneralMidi(GeneralMidi.PIANO);
		
		setInstrumentName("Piano (2)");
		setInstrumentSound("keyboard.piano.grand");
		setVirtualName("Concert Grand Piano");
	}
}
