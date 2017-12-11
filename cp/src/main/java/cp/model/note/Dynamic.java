package cp.model.note;

public enum Dynamic {

	PPP(15),PP(30), P(50), MP(60), MF(75), F(90), FF(105),FFF(119),

	SFZ(40), FP(40);
	
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
