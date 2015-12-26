package cp.out.instrument.keyswitch;

import cp.out.instrument.Articulation;

public class KontactStringsKeySwitch implements KeySwitch{

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
				break;
		}
		throw new IllegalArgumentException("Unknown articulation: " + articulation.getMusicXmlLabel());
	}
}
