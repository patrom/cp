package cp.model.rhythm;

public enum Duration {

	S3(2),S(3),E3(4),E(6),Q3(8),ES(9),Q(12),
	QS3(14),QS(15),H3_QE3(16),QE(18),QQ3(20),QES(21),H(24),
	HQ(36),W(48),
	
	R_S3(2,true),R_S(3,true),R_E3(4,true),R_E(6,true),R_Q3(8,true),R_ES(9,true),R_Q(12,true),
	R_QS3(14,true),R_QS(15,true),R_H3_QE3(16,true),R_QE(18,true),R_QQ3(20,true),R_QES(21,true),R_H(24,true),
	R_HQ(36,true),R_W(48,true);
	
	private int duration;
	private boolean rest;
	
	private Duration(int duration){
		this.duration = duration;
		this.rest = false;
	}
	
	private Duration(int duration, boolean rest){
		this.duration = duration;
		this.rest = rest;
	}
	
	public int getDuration() {
		return duration;
	}

	public boolean isRest() {
		return rest;
	}
	
}
