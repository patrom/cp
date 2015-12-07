package cp.generator;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.TreeMap;

import org.springframework.stereotype.Component;

import cp.model.note.Scale;
import cp.out.instrument.Instrument;

@Component
public class MusicProperties {
	
	private int harmonyBeatDivider = 12;
	private int tempo = 100;
	private Map<Integer, Double> rhythmWeightValues = new TreeMap<>(); //Must match length of harmonies based on division by minimumLength.
	private int minimumLength = 6;
	private Integer[] octaveLowestPitchClassRange = {0};
	private boolean outerBoundaryIncluded = true;
	private double[] filterLevels = {0.5};
	private List<Instrument> instruments;
	private int minimumRhythmFilterLevel = 1;
	private int[] distance = {2,3,4,5,6,8,9,10,12,14,15,16,18,20,21,22,24,26,27,28,30,32};//atomic beat = 12
	private int melodyType = 2; //or 3
	private int melodyBeatValue = 12; // for musicXML
	
	//tonality
	private Scale scale = Scale.MAJOR_SCALE;
	private Scale melodyScale = Scale.MAJOR_SCALE;
	
	//harmony
	private double harmonyConsDissValue = 0.9;
	private int allowChordsOfPitchesOrHigher = 3;
	
	//melody
	private double melodyConsDissValue = 0.2;//hoe lager, hoe cons - stapsgewijs
	
	//voice leading
	private double voiceLeadingConsDissValue;
	private String voiceLeadingStrategy;

	//score
	private int numerator = 3;
	private int denominator = 4;
	private int keySignature = 0;//1 = 1 kruis / -1 = 1 bemol
	
	//MOGA
	private int populationSize;
	private int maxEvaluations;
	private double crossoverProbability;
	private double mutationProbability;
	
	private int[][] harmonies;
	
	public Instrument findInstrument(int voice){
		Optional<Instrument> instrument = instruments.stream().filter(instr -> (instr.getVoice()) == voice).findFirst();
		if (instrument.isPresent()) {
			return instrument.get();
		}else{
			throw new IllegalArgumentException("Instrument for voice " + voice + " is missing!");
		}
	}
	
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
	
	public static Instrument getInstrument(int voice, int low, int high) {
		Instrument range = new Instrument();
		range.setVoice(voice);
		range.setLowest(low);
		range.setHighest(high);
		return range;
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
	
	public int getMinimumLength() {
		return minimumLength;
	}
	
	public void setMinimumLength(int minimumLength) {
		this.minimumLength = minimumLength;
	}
	
	public void setScale(Scale scale) {
		this.scale = scale;
	}
	
	public Integer[] getOctaveLowestPitchClassRange() {
		return octaveLowestPitchClassRange;
	}
	
	public void setOctaveHighestPitchClassRange(
			Integer[] octaveHighestPitchClassRange) {
		this.octaveLowestPitchClassRange = octaveHighestPitchClassRange;
	}
	
	public Scale getMelodyScale() {
		return melodyScale;
	}
	
	public void setMelodyScale(Scale melodyScale) {
		this.melodyScale = melodyScale;
	}
	
	public boolean isOuterBoundaryIncluded() {
		return outerBoundaryIncluded;
	}
	
	public void setOuterBoundaryIncluded(boolean outerBoundaryIncluded) {
		this.outerBoundaryIncluded = outerBoundaryIncluded;
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

	public Map<Integer, Double> getRhythmWeightValues() {
		return rhythmWeightValues;
	}

	public void setRhythmWeightValues(Map<Integer, Double> rhythmWeightValues) {
		this.rhythmWeightValues = rhythmWeightValues;
	}

	public int[][] getHarmonies() {
		return harmonies;
	}

	public void setHarmonies(int[][] harmonies) {
		this.harmonies = harmonies;
	}

	public List<Instrument> getInstruments() {
		return instruments;
	}

	public void setInstruments(List<Instrument> instruments) {
		this.instruments = instruments;
	}

	public int getMinimumRhythmFilterLevel() {
		return minimumRhythmFilterLevel;
	}

	public void setMinimumRhythmFilterLevel(int minimumRhythmFilterLevel) {
		this.minimumRhythmFilterLevel = minimumRhythmFilterLevel;
	}

	public int[] getDistance() {
		return distance;
	}

	public void setDistance(int[] distance) {
		this.distance = distance;
	}

	public int getMelodyType() {
		return melodyType;
	}

	public void setMelodyType(int melodyType) {
		this.melodyType = melodyType;
	}

	public int getMelodyBeatValue() {
		return melodyBeatValue;
	}

	public void setMelodyBeatValue(int melodyBeatValue) {
		this.melodyBeatValue = melodyBeatValue;
	}
	
}
