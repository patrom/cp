package cp.out.orchestration;

public enum InstrumentName {

	PICCOLO("Piccolo"), FLUTE("Flute 1"), FLUTE_2("Flute 2"), ALTO_FLUTE("Alto Flute"),
	OBOE("Oboe 1"), OBOE_2("Oboe 2"), COR_ANGLAIS("Cor Anglais"),
	SMALL_CLARINET("Clarinet in Eb"), CLARINET("Clarinet (B Flat) 1"), CLARINET_2("Clarinet (B Flat) 2"), BASS_CLARINET("Bass Clarinet"),
	BASSOON("Bassoon 1"), BASSOON_2("Bassoon 12"), CONTRABASSOON("Contrabassoon"),

	HORN("French Horn (F) 1"), HORN_2("French Horn (F) 2"),
    TRUMPET("Trumpet 1"), TRUMPET_2("Trumpet 2"), TUBA("Tuba"),
	TROMBONE("Trombone 1"), TROMBONE_2("Trombone 2"), BASS_TROMBONE("Bass Trombone"),

	VIOLIN_I("Violin I"), VIOLIN_II("Violin II"), VIOLA("Viola"), CELLO("Violoncello"), CONTRABASS("Double Bass"),

	PIANO("Piano (2)"), CELESTA("Celesta"),

	GLOCKENSPIEL("Glockenspiel"),HARP("Harp 1"),TIMPANI("Timpani"),XYLOPHONE("Xylophone"),MARIMBA("Marimba"),VIBRAPHONE("Vibraphone"),
    CROTALES("Crotales"), TUBALAR_BELLS("Tubalar Bells"),
	GUITAR("Guitar (2)"),

	VIOLIN_SOLO("Violin Solo"), VIOLA_SOLO("Viola Solo"),CELLO_SOLO("Violoncello Solo"),CONTRABASS_SOLO("Contrabass Solo"),

	SOPRANO("Soprano"), ALTO("Alto"), TENOR("Tenor"), BASS("Bass (2)");

	private final String name;

	InstrumentName(String name) {
		this.name = name;
	}
	
	public String getName() {
		return name;
	}
}
