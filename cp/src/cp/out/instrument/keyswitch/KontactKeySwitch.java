package cp.out.instrument.keyswitch;

import cp.out.instrument.Articulation;

public class KontactKeySwitch implements KeySwitch{

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
				break;
		}
		throw new IllegalArgumentException("Unknown articulation: " + articulation.getMusicXmlLabel());
	}
	
}
