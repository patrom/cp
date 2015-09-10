package cp.evaluation;

public class FitnessObjectiveValues {

	private double harmony;
	private double melody;
	private double voiceleading;
	private double tonality;
	private double rhythm;
	
	public double getHarmony() {
		return harmony;
	}
	public void setHarmony(double harmony) {
		this.harmony = harmony;
	}
	public double getMelody() {
		return melody;
	}
	public void setMelody(double melody) {
		this.melody = melody;
	}
	public double getVoiceleading() {
		return voiceleading;
	}
	public void setVoiceleading(double voiceleading) {
		this.voiceleading = voiceleading;
	}
	@Override
	public String toString() {
		return "FitnessObjectiveValues [harmony=" + harmony + ", melody="
				+ melody + ", voiceleading=" + voiceleading + ", tonality="
				+ tonality + "]";
	}
	public double getTonality() {
		return tonality;
	}
	public void setTonality(double tonality) {
		this.tonality = tonality;
	}
	public double getRhythm() {
		return rhythm;
	}
	public void setRhythm(double rhythm) {
		this.rhythm = rhythm;
	}
	
}
