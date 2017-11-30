package cp.model.note;

public enum Dynamic {
 
	 PP(20), P(40), MP(60), MF(80), F(98), FF(112), SFZ(40), FP(40);
	
	private final int level;
	
	Dynamic(int level){
		this.level = level;
	}
	
	public int getLevel() {
		return level;
	}

	public static Dynamic  getDynamic(String label){
		return Dynamic.valueOf(label.toUpperCase());
	}
}
