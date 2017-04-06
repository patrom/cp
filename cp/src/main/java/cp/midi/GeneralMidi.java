package cp.midi;

public enum GeneralMidi {

	PIANO(1),
	VIOLIN(41), VIOLA(42), CELLO(43), CONTRABASS(44),
	FLUTE(74), CLARINET(72), OBOE(69), BASSOON(71), ENGLISH_HORN(70), PICCOLO(73),
	FRENCH_HORN(61),
	CHOIR(53),
	HARPSICHORD(7),
	NYLON_GUITAR(25),
	TIMPANI(48),
	TRUMPET(57), TROMBONE(58), TUBA(59);
	
	private final int event;
	
	GeneralMidi(int event){
		this.event = event;
	}

	public int getEvent() {
		return event;
	}
}
