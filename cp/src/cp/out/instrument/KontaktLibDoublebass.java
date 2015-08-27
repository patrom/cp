package cp.out.instrument;

import cp.midi.GeneralMidi;


public class KontaktLibDoublebass extends Instrument {

	public KontaktLibDoublebass(int voice, int channel) {
		super(voice, channel);
		setLowest(38);
		setKeySwitch(false);
		setGeneralMidi(GeneralMidi.CONTRABASS);
		
		setInstrumentName("Contrabass");
		setInstrumentSound("strings.Contrabass");
		setVirtualName("Bass 1 solo");
		setClef("F");
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
