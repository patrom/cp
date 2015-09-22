package cp.out.instrument;

public enum Articulation {
	
	LEGATO("legato"),
	MARCATO("marcato"),
	PIZZICATO("pizzicato"),
	PORTATO("portato"),
	SPICCATO("spiccato"),
	STACCATO("staccato"),
	STACCATISSIMO("staccatissimo"),
	TENUTO("tenuto"),
	MARTELATO("martelato"),
	TREMELO(""),
	SFORZANDO("");
	
	private String musicXmlLabel;
	
	private Articulation(String musicXmlLabel){
		this.musicXmlLabel = musicXmlLabel;
	}
	
	public String getMusicXmlLabel() {
		return musicXmlLabel;
	}

}
