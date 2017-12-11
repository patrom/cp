package cp.out.instrument;

public enum Articulation {

	//VSL
	PORTAMENTO(""),

	//On note markings
	//musicXml articulations:
	MARCATO("accent"),
	STRONG_ACCENT("strong-accent"),
	STACCATO("staccato"),
	TENUTO("tenuto"),
	DETACHED_LEGATO("detached-legato"),//a tenuto line and staccato dot
	STACCATISSIMO("staccatissimo"),
	SPICCATO("spiccato"),
	SCOOP("scoop"),
	PLOP("plop"),
	DOIT("doit"),
	FALLOFF("falloff");


//	LEGATO("legato"),
//
////	SFORZANDO("sforzando"),
////	FORTEPIANO(""),
//
//	PORTATO("portato"),
//
//	MARTELATO(""),
//	TREMELO(""),
//
//
//	PONTICELLO_STACCATO(""),
//	PONTICELLO_SUSTAIN(""),
//	PONTICELL_TREMELO(""),
//
//	SUSTAIN_VIBRATO(""),
//	SUSTAIN_NO_VIBRATO(""),
//	FLUTTER(""),
//	REPEATLEGATO(""),
//	PORTAMENTO(""),
//	DETACHE("detaché"),
//	HARMONIC(""),
//
//	PIZZICATO("pizzicato");

	
	private final String musicXmlLabel;
	
	Articulation(String musicXmlLabel){
		this.musicXmlLabel = musicXmlLabel;
	}
	
	public String getMusicXmlLabel() {
		return musicXmlLabel;
	}

	public static Articulation getArticulation(String label){
		for(Articulation articulation : values()) {
			if(articulation.musicXmlLabel.equals(label)) return articulation;
		}
		throw new IllegalArgumentException("Articulation not found for: " + label);
    }

}
