package cp.model.note;

public enum Dynamic {
 
	PPP(20), PP(39), P(61), MP(71), MF(84), F(98), FF(113), FFF(125), SFZ(40), FP(40);
	
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
