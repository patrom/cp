package cp.out.instrument;

import cp.midi.GeneralMidi;


public class KontaktLibBassoon extends Instrument {

	public KontaktLibBassoon(int voice, int channel) {
		super(voice, channel);
		setLowest(34);
		setHighest(70);
		setKeySwitch(true);
		setGeneralMidi(GeneralMidi.BASSOON);
	}

	@Override
	public int getArticulation(Articulation performance) {
		switch (performance) {
		case LEGATO:
			return 24;
		case MARCATO:
			return 24;
		case PIZZICATO:
			return 29;
		case STACCATO:
			return 27;
		case PORTATO:
			return 24;
		default:
			return super.getArticulation(performance);
		}
	}
}
