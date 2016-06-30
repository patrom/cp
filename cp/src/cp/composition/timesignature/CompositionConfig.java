package cp.composition.timesignature;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;

import cp.composition.beat.BeatGroup;
import cp.composition.beat.BeatGroupFactory;
import cp.generator.MusicProperties;

public abstract class CompositionConfig {

	protected List<BeatGroup> beats = new ArrayList<>();
	protected List<BeatGroup> beatsDoubleLength = new ArrayList<>();
	protected List<BeatGroup> beatsAll = new ArrayList<>();
	
	@Value("${composition.numerator:4}")
	protected int numerator;
	@Value("${composition.denominator:4}")
	protected int denominator;
	
	protected int offset;
	
	@Autowired
	protected MusicProperties musicProperties;
	
	@Autowired
	protected BeatGroupFactory beatGroupFactory;
	
	protected Map<Integer, List<BeatGroup>> beatGroupsPerVoice = new TreeMap<>();
	
	public abstract boolean randomBeatGroup();//composite time signatures
	
	public abstract boolean randomCombination();//fixed rhythm patterns
	
	@PostConstruct
	public void init() {
		musicProperties.setNumerator(numerator);
		musicProperties.setDenominator(denominator);
	}
	
	public List<BeatGroup> getAllBeats() {
		return beatsAll;
	}
	
	public List<BeatGroup> getBeatGroup(int voice){
		return beatGroupsPerVoice.getOrDefault(voice, beatsAll);
	}
	
	public void setBeatGroups(int voice, List<BeatGroup> beatGroups){
		beatGroupsPerVoice.put(voice, beatGroups);
	}
	
	public List<BeatGroup> getBeats() {
		return beats;
	}
	
	public List<BeatGroup> getBeatsDoubleLength() {
		return beatsDoubleLength;
	}
	
	public int getOffset() {
		return offset;
	}
	
	public abstract List<BeatGroup> getFixedBeatGroup();
	
}
