package cp.midi;

public enum MidiDevicePlayer {

	KONTAKT;
	
	private String name;
	
	MidiDevicePlayer(){
		String OS = System.getProperty("os.name").toLowerCase();
		if (OS.contains("win")) {
			this.name = "LoopBe Internal MIDI";
//			this.name = "Vienna Instruments MIDI";
		} else if(OS.contains("mac")){
			this.name = "Kontakt 5 Virtual Input";
		}
	}

	public String getName() {
		return name;
	}
	
}
