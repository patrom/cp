package cp.out.instrument;

public enum MidiDevice {

	KONTAKT;
	
	private String name;
	
	MidiDevice(){
		String OS = System.getProperty("os.name").toLowerCase();
		if (OS.indexOf("win") >= 0) {
			this.name = "LoopBe Internal MIDI";
		} else if(OS.indexOf("mac") >= 0){
			this.name = "Kontakt 5 Virtual Input";
		}
	}

	public String getName() {
		return name;
	}
	
}
