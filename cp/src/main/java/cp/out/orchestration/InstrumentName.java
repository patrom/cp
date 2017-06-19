package cp.out.orchestration;

public enum InstrumentName {

	PICCOLO("Piccolo"), FLUTE("Flute (2)"), ALTO_FLUTE("Alto Flute"), 
	OBOE("Oboe"), COR_ANGLAIS("Cor Anglais"),
	SMALL_CLARINET("Clarinet in E^b"),CLARINET("Clarinet in B^b"), BASS_CLARINET("Bass Clarinet in B^b"),
	BASSOON("Bassoon"), CONTRABASSOON("Contrabassoon"),
	HORN("Horn in F (2)"), TRUMPET("Trumpet in B^b (2)"), TUBA("Tuba"),
	TROMBONE("Trombone (2)"), BASS_TROMBONE("Bass Trombone"),
	VIOLIN_I("Violin I"), VIOLIN_II("Violin II"), VIOLA("Viola (2)"), CELLO("Violoncello (2)"), CONTRABASS("Contrabass (2)"),
	PIANO("Piano (2)"), CELESTA("Celesta"),
	GLOCKENSPIEL("Glockenspiel (2)"),HARP("Harp"),TIMPANI("Timpani"),XYLOPHONE("Xylophone (2)"),MARIMBA("Marimba"),VIBRAPHONE("Vibraphone"),
	GUITAR("Guitar (2)"),
	VIOLIN_SOLO("Violin"), VIOLA_SOLO("Viola"),CELLO_SOLO("Violoncello"),CONTRABASS_SOLO("Contrabass"),
	SOPRANO("Soprano"), ALTO("Alto"), TENOR("Tenor"), BASS("Bass (2)");

	private final String name;

	InstrumentName(String name) {
		this.name = name;
	}
	
	public String getName() {
		return name;
	}
}
