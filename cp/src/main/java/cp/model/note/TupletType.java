package cp.model.note;

public enum TupletType {

	START("start"), STOP("stop"); 
	
	private String label;
	
	private TupletType(String label){
		this.label = label;
	}
	
	public String getLabel() {
		return label;
	}
	
}
