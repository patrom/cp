package cp.out.instrument;

import cp.midi.GeneralMidi;

public class KontaktLibViola extends Instrument {

	public KontaktLibViola(int voice, int channel) {
		super(voice, channel);
		setLowest(48);
		setHighest(72);
		setKeySwitch(true);
		setGeneralMidi(GeneralMidi.VIOLA);
		
		setInstrumentName("Viola");
		setInstrumentSound("strings.viola");
		setVirtualName("Viola 1 solo");
	}

	@Override
	public int getArticulation(Articulation articulation) {
		switch (articulation) {
		case LEGATO:
			return 24;
//		case PIZZICATO:
//			return 29;
		case STACCATO:
			return 27;
//		case SFORZANDO:
//			return 26;
//		case TREMELO:
//			return 28;
		default:
			return super.getArticulation(articulation);
		}
	}
}
