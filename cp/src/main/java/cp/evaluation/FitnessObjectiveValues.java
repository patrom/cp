package cp.evaluation;

public class FitnessObjectiveValues {

	private double harmony;
	private double melody;
	private double melodicHarmonic;
	private double voiceleading;
	private double tonality;
	private double rhythm;
	private double meter;
	private double resolution;
	private double register;
	
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

	public double getRegister() {
		return register;
	}

	public void setRegister(double register) {
		this.register = register;
	}

	public double getMeter() {
		return meter;
	}
	public void setMeter(double meter) {
		this.meter = meter;
	}
	public double getResolution() {
		return resolution;
	}
	public void setResolution(double resolution) {
		this.resolution = resolution;
	}

	public double getMelodicHarmonic() {
		return melodicHarmonic;
	}

	public void setMelodicHarmonic(double melodicHarmonic) {
		this.melodicHarmonic = melodicHarmonic;
	}

	@Override
	public String toString() {
		return "FitnessObjectiveValues [harmony=" + harmony + ", melody="
				+ melody + ", voiceleading=" + voiceleading + ", tonality="
				+ tonality + ", rhythm=" + rhythm + ", meter=" + meter  + ", mh=" + melodicHarmonic
				+ ", resolution=" + resolution + ",  register=" + register + "]";
	}
	
}
