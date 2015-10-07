package cp.out.instrument;

import cp.midi.GeneralMidi;


public class KontaktLibViolin extends Instrument {

	public KontaktLibViolin(int voice, int channel) {
		super(voice, channel);
		setLowest(55);
		setHighest(84);
		setKeySwitch(true);
		setGeneralMidi(GeneralMidi.VIOLIN);
		
		setInstrumentName("Violin");
		setInstrumentSound("strings.violin");
		setVirtualName("Violin 1 solo");
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
