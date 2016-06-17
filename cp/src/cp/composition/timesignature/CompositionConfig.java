package cp.composition.timesignature;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;

import cp.combination.Combination;
import cp.generator.MusicProperties;

public abstract class CompositionConfig {

	protected List<Integer> beats = new ArrayList<>();
	protected List<Integer> beatsDoubleLength = new ArrayList<>();
	protected List<Integer> beatsAll = new ArrayList<>();
	
	@Value("${composition.numerator:4}")
	protected int numerator;
	@Value("${composition.denominator:4}")
	protected int denominator;
	
	@Autowired
	protected MusicProperties musicProperties;
	
	public abstract boolean randomCombinations();//fixed rhythm patterns
	
	public abstract boolean randomBeats();//composite time signatures
	
	public abstract Combination getFixed();
	
	@PostConstruct
	public void init() {
		musicProperties.setNumerator(numerator);
		musicProperties.setDenominator(denominator);
	}
	
	public List<Integer> getAllBeats() {
		return beatsAll;
	}
	
}
