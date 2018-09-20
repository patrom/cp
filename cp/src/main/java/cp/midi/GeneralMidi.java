package cp.midi;

public enum GeneralMidi {

	PIANO(0),
	VIOLIN(40), VIOLA(41), CELLO(42), CONTRABASS(43),
	FLUTE(73), CLARINET(71), OBOE(68), BASSOON(70), ENGLISH_HORN(69), PICCOLO(72),
	FRENCH_HORN(60),
	CHOIR(52),
	HARPSICHORD(6),
	CELESTA(8),MARIMBA(12),VIBRAPHONE(11), XYLOPHONE(13), GLOCKEN(9),HARP(46),
	NYLON_GUITAR(24),
	TIMPANI(47),
	TRUMPET(56), TROMBONE(57), TUBA(58);
	
	private final int event;
	
	GeneralMidi(int event){
		this.event = event;
	}

	public int getEvent() {
		return event;
	}
}
