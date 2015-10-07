package cp.out.instrument;

import cp.midi.GeneralMidi;


public class KontaktLibOboe extends Instrument {

	public KontaktLibOboe(int voice, int channel) {
		super(voice, channel);
		setLowest(58);
		setHighest(84);
		setKeySwitch(true);
		setGeneralMidi(GeneralMidi.OBOE);
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
