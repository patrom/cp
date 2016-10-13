package cp.model.note;

public enum Dynamic {
 
	PPP(16), PP(33), P(49), MP(64), MF(80), F(96), FF(112), FFF(126), SFZ(80);
	
	private final int level;
	
	Dynamic(int level){
		this.level = level;
	}
	
	public int getLevel() {
		return level;
	}
}
