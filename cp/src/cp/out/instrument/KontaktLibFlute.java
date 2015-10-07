package cp.out.instrument;

import cp.midi.GeneralMidi;


public class KontaktLibFlute extends Instrument {

	public KontaktLibFlute(int voice, int channel) {
		super(voice, channel);
		setLowest(60);
		setHighest(84);
		setKeySwitch(true);
		setGeneralMidi(GeneralMidi.FLUTE);
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
