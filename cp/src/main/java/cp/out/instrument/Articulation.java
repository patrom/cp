package cp.out.instrument;

public enum Articulation {
	
	LEGATO("legato"),
	MARCATO("accent"),
	SFORZANDO("sforzando"),
	PORTATO("portato"),

	STACCATO("staccato"),
	STACCATISSIMO("staccatissimo"),
	TENUTO("tenuto"),
	MARTELATO(""),
	TREMELO(""),
	FORTEPIANO(""),

	PONTICELLO_STACCATO(""),
	PONTICELLO_SUSTAIN(""),
	PONTICELL_TREMELO(""),

	SUSTAINVIBRATO(""),
	REPEATLEGATO(""),
	PORTAMENTO(""),
	DETACHE(""),
	HARMONIC(""),
	SPICCATO("spiccato"),
	PIZZICATO("pizzicato");

	
	private final String musicXmlLabel;
	
	Articulation(String musicXmlLabel){
		this.musicXmlLabel = musicXmlLabel;
	}
	
	public String getMusicXmlLabel() {
		return musicXmlLabel;
	}

}
