package cp.model.note;

public enum BeamType {

	BEGIN("begin", "", false), CONTINUE("continue", "", false), END("end", "",false), 
	BEGIN_BEGIN("begin", "begin", true), CONTINUE_CONTINUE("continue", "continue", true), END_END("end", "end", true),
	CONTINUE_BEGIN("continue", "begin", true), CONTINUE_END("continue", "end", true) ;
	
	private String label;
	private boolean doubleBeam;
	private String secondLabel;
	
	private BeamType(String label, String secondLabel, boolean doubleBeam){
		this.label = label;
		this.doubleBeam = doubleBeam;
		this.secondLabel = secondLabel;
	}
	
	public String getLabel() {
		return label;
	}
	
	public String getSecondLabel() {
		return secondLabel;
	}

	public boolean isDoubleBeam() {
		return doubleBeam;
	}
	
}
