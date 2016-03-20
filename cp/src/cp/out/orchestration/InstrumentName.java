package cp.out.orchestration;

public enum InstrumentName {

	FLUTE("Flute (2)"), OBOE("Oboe"), CLARINET("Clarinet in B^b"), BASSOON("Bassoon"), HORN("Horn in F (2)"), TRUMPET("Trumpet in B^b (2)"),
	VIOLIN_I("Violin I"), VIOLIN_II("Violin II"), VIOLA("Viola (2)"), CELLO("Violoncello (2)"), BASS("Contrabass (2)");

	private String name;

	private InstrumentName(String name) {
		this.name = name;
	}
	
	public String getName() {
		return name;
	}
}
