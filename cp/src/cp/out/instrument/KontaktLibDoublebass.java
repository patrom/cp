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
