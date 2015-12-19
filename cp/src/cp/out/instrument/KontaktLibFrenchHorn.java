package cp.out.instrument;

import cp.midi.GeneralMidi;


public class KontaktLibFrenchHorn extends Instrument {

	public KontaktLibFrenchHorn(int voice, int channel) {
		super(voice, channel);
		setLowest(34);
		setHighest(70);
		setKeySwitch(false);
		setGeneralMidi(GeneralMidi.FRENCH_HORN);
	}

	@Override
	public int getArticulation(Articulation articulation) {
		switch (articulation) {
		case LEGATO:
			return 24;
		case MARCATO:
			return 24;
		case STACCATO:
			return 27;
//		case PORTATO:
//			return 24;
		default:
			return super.getArticulation(articulation);
		}
	}
}
