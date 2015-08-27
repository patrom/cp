package cp.out.instrument;

import cp.midi.GeneralMidi;


public class KontaktLibClarinet extends Instrument {

	public KontaktLibClarinet(int voice, int channel) {
		super(voice, channel);
		setLowest(50);
		setHighest(84);
		setKeySwitch(true);
		setGeneralMidi(GeneralMidi.CLARINET);
	}

	@Override
	public int getPerformanceValue(Articulation performance) {
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
			return super.getPerformanceValue(performance);
		}
	}
}
