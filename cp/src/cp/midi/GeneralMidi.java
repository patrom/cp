package cp.midi;

public enum GeneralMidi {

	PIANO(2), 
	VIOLIN(40), VIOLA(41), CELLO(42), CONTRABASS(43),
	FLUTE(73), CLARINET(71), OBOE(68), BASSOON(70),
	FRENCH_HORN(60),
	CHOIR(52),
	HARPSICHORD(7);
	
	private int event;
	
	private GeneralMidi(int event){
		this.event = event;
	}

	public int getEvent() {
		return event;
	}
}
