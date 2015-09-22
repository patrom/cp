package cp.out.instrument;

import cp.midi.GeneralMidi;


public class KontaktLibCello extends Instrument {

	public KontaktLibCello(int voice, int channel) {
		super(voice, channel);
		setLowest(36);
		setHighest(70);
		setKeySwitch(true);
		setGeneralMidi(GeneralMidi.CELLO);
		
		setInstrumentName("Violoncello");
		setInstrumentSound("strings.cello");
		setVirtualName("Cello 1 solo");
		setClef("F");
	}

	@Override
	public int getArticulation(Articulation articulation) {
		switch (articulation) {
		case LEGATO:
			return 24;
		case PIZZICATO:
			return 29;
		case STACCATO:
			return 27;
		case SFORZANDO:
			return 26;
		case TREMELO:
			return 28;
		default:
			return super.getArticulation(articulation);
		}
	}
}
