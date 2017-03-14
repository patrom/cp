package cp.generator;

import cp.model.note.Scale;
import cp.model.rhythm.DurationConstants;
import cp.out.print.note.Key;
import org.springframework.stereotype.Component;

@Component
public class MusicProperties {
	
	private int harmonyBeatDivider = DurationConstants.QUARTER;
	private int tempo = 100;
	private double[] filterLevels = {0.5};
//	private int minimumRhythmFilterLevel = 12; //levels pitch, crest/keel, ...
//	private int[] distance = {2,3,4,5,6,8,9,10,12,14,15,16,18,20,21,22,24,26,27,28,30,32};//minimumRhythmicValue = 12 - default
	private int[] distance = {3,6,9,12,15,18,20,21,22,24,26,27,28,30,32};//minimumRhythmicValue = 12 -  3/4
//	private int[] distance = {2,4,8,10,12,14,16,18,20,21,22,24,26,27,28,30,32};//minimumRhythmicValue = 12 - 4/4
	private Key key;
	private int outputCountRun = 10;
	
	//tonality
	private Scale scale = Scale.MAJOR_SCALE;
	private Scale melodyScale = Scale.MAJOR_SCALE;
	
	//harmony
	private double harmonyConsDissValue = 0.10;
	private int allowChordsOfPitchesOrHigher = 3;
	
	//melody
	private double melodyConsDissValue = 0.3;//hoe lager, hoe cons - stapsgewijs
	
	//voice leading
	private double voiceLeadingConsDissValue;
	private String voiceLeadingStrategy;

	//score
	private int numerator = 4;
	private int denominator = 4;
	private int keySignature = 0;//1 = 1 kruis / -1 = 1 bemol
	
	//MOGA
	private int populationSize;
	private int maxEvaluations;
	private double crossoverProbability;
	private double mutationProbability;
	
	private int[][] harmonies;
	
	public String getVoiceLeadingStrategy() {
		return voiceLeadingStrategy;
	}
	public void setVoiceLeadingStrategy(String voiceLeadingStrategy) {
		this.voiceLeadingStrategy = voiceLeadingStrategy;
	}
	public double getHarmonyConsDissValue() {
		return harmonyConsDissValue;
	}
	public void setHarmonyConsDissValue(double harmonyConsDissValue) {
		this.harmonyConsDissValue = harmonyConsDissValue;
	}
	public int getAllowChordsOfPitchesOrHigher() {
		return allowChordsOfPitchesOrHigher;
	}
	public void setAllowChordsOfPitchesOrHigher(int allowChordsOfPitchesOrHigher) {
		this.allowChordsOfPitchesOrHigher = allowChordsOfPitchesOrHigher;
	}
	public double getMelodyConsDissValue() {
		return melodyConsDissValue;
	}
	public void setMelodyConsDissValue(double melodyConsDissValue) {
		this.melodyConsDissValue = melodyConsDissValue;
	}
	public double getVoiceLeadingConsDissValue() {
		return voiceLeadingConsDissValue;
	}
	public void setVoiceLeadingConsDissValue(double voiceLeadingConsDissValue) {
		this.voiceLeadingConsDissValue = voiceLeadingConsDissValue;
	}
	public Scale getScale() {
		return scale;
	}
	public int getNumerator() {
		return numerator;
	}
	public void setNumerator(int numerator) {
		this.numerator = numerator;
	}
	public int getPopulationSize() {
		return populationSize;
	}
	public void setPopulationSize(int populationSize) {
		this.populationSize = populationSize;
	}
	public int getMaxEvaluations() {
		return maxEvaluations;
	}
	public void setMaxEvaluations(int maxEvaluations) {
		this.maxEvaluations = maxEvaluations;
	}
	public double getCrossoverProbability() {
		return crossoverProbability;
	}
	public void setCrossoverProbability(double crossoverProbability) {
		this.crossoverProbability = crossoverProbability;
	}
	public double getMutationProbability() {
		return mutationProbability;
	}
	public void setMutationProbability(double mutationProbability) {
		this.mutationProbability = mutationProbability;
	}
	
	public int getHarmonyBeatDivider() {
		return harmonyBeatDivider;
	}
	
	public void setHarmonyBeatDivider(int harmonyBeatDivider) {
		this.harmonyBeatDivider = harmonyBeatDivider;
	}
	
	public int getTempo() {
		return tempo;
	}
	
	public void setTempo(int tempo) {
		this.tempo = tempo;
	}
	
	public void setScale(Scale scale) {
		this.scale = scale;
	}
	
	public Scale getMelodyScale() {
		return melodyScale;
	}
	
	public void setMelodyScale(Scale melodyScale) {
		this.melodyScale = melodyScale;
	}
	
	public double[] getFilterLevels() {
		return filterLevels;
	}
	
	public void setFilterLevels(double[] filterLevels) {
		this.filterLevels = filterLevels;
	}
	
	public int getDenominator() {
		return denominator;
	}
	
	public void setDenominator(int denominator) {
		this.denominator = denominator;
	}
	
	public int getKeySignature() {
		return keySignature;
	}
	
	public void setKeySignature(int keySignature) {
		this.keySignature = keySignature;
	}

	public int[][] getHarmonies() {
		return harmonies;
	}

	public void setHarmonies(int[][] harmonies) {
		this.harmonies = harmonies;
	}

	public int[] getDistance() {
		return distance;
	}

	public void setDistance(int[] distance) {
		this.distance = distance;
	}

	public Key getKey() {
		return key;
	}

	public void setKey(Key key) {
		this.key = key;
	}

	public int getOutputCountRun() {
		return outputCountRun;
	}

	public void setOutputCountRun(int outputCountRun) {
		this.outputCountRun = outputCountRun;
	}
	
}
