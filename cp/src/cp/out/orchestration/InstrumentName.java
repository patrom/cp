package cp.out.orchestration;

public enum InstrumentName {

	PICCOLO("Piccolo"), FLUTE("Flute (2)"), ALTO_FLUTE("Alto Flute"), 
	OBOE("Oboe"), COR_ANGLAIS("Cor Anglais"),
	SMALL_CLARINET("Clarinet in E^b"),CLARINET("Clarinet in B^b"), BASS_CLARINET("Bass Clarinet in B^b"),
	BASSOON("Bassoon"), CONTRABASSOON("Contrabassoon"),
	HORN("Horn in F (2)"), TRUMPET("Trumpet in B^b (2)"),
	TROMBONE("Trombone (2)"), BASS_TROMBONE("Bass Trombone"),
	VIOLIN_I("Violin I"), VIOLIN_II("Violin II"), VIOLA("Viola (2)"), CELLO("Violoncello (2)"), BASS("Contrabass (2)"),
	PIANO("Piano (2)");

	private String name;

	private InstrumentName(String name) {
		this.name = name;
	}
	
	public String getName() {
		return name;
	}
}
