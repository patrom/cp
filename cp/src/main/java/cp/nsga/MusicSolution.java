package cp.nsga;

import jmetal.core.Problem;
import jmetal.core.Solution;

import java.util.Objects;

public class MusicSolution extends Solution {
	
	private double harmony;
	private double melody;
	private double voiceLeading;
	private double rhythm;
	private double tonality;
	private double meter;
	private double resolution;
	private double register;
	private double melodicHarmonic;
	private double transformation;
	
	private double constraintLowestInterval;
	private double constraintRhythm;
	private double constraintRepetition;
	
	public MusicSolution(Problem problem) throws ClassNotFoundException {
		super(problem);
	}
	
	public MusicSolution(Solution solution) {
		super(solution);
	}

	public double getConstraintLowestInterval() {
		return constraintLowestInterval;
	}

	public void setConstraintLowestInterval(double constraintLowestInterval) {
		this.constraintLowestInterval = constraintLowestInterval;
	}

	public double getConstraintRhythm() {
		return constraintRhythm;
	}

	public void setConstraintRhythm(double constraintRhythm) {
		this.constraintRhythm = constraintRhythm;
	}

	public double getConstraintRepetition() {
		return constraintRepetition;
	}

	public void setConstraintRepetition(double constraintRepetition) {
		this.constraintRepetition = constraintRepetition;
	}

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
	public double getVoiceLeading() {
		return voiceLeading;
	}
	public void setVoiceLeading(double voiceLeading) {
		this.voiceLeading = voiceLeading;
	}
	public double getRhythm() {
		return rhythm;
	}
	public void setRhythm(double rhythm) {
		this.rhythm = rhythm;
	}
	public double getTonality() {
		return tonality;
	}
	public void setTonality(double tonality) {
		this.tonality = tonality;
	}

	public double getRegister() {
		return register;
	}

	public void setRegister(double register) {
		this.register = register;
	}

	public void setMelodicHarmonic(double melodicHarmonic) {
		this.melodicHarmonic = melodicHarmonic;
	}

	public double getMelodicHarmonic() {
		return melodicHarmonic;
	}

    public double getTransformation() {
        return transformation;
    }

    public void setTransformation(double transformation) {
        this.transformation = transformation;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        MusicSolution that = (MusicSolution) o;
        return Double.compare(that.harmony, harmony) == 0 &&
                Double.compare(that.melody, melody) == 0 &&
                Double.compare(that.voiceLeading, voiceLeading) == 0 &&
                Double.compare(that.rhythm, rhythm) == 0 &&
                Double.compare(that.tonality, tonality) == 0 &&
                Double.compare(that.meter, meter) == 0 &&
                Double.compare(that.resolution, resolution) == 0 &&
                Double.compare(that.register, register) == 0 &&
                Double.compare(that.melodicHarmonic, melodicHarmonic) == 0 &&
                Double.compare(that.transformation, transformation) == 0;
    }

    @Override
    public int hashCode() {
        return Objects.hash(harmony, melody, voiceLeading, rhythm, tonality, meter, resolution, register, melodicHarmonic, transformation);
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

	@Override
	public String toString() {
		return "MusicSolution [harmony=" + harmony + ", melody=" + melody
				+ ", voiceLeading=" + voiceLeading + ", rhythm=" + rhythm
				+ ", tonality=" + tonality + ", meter=" + meter + ", mh=" + melodicHarmonic + ", tr=" + transformation
				+ ", resolution=" + resolution + ", register=" +register + "]";
	}
}
