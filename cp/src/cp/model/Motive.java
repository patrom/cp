package cp.model;

import static java.util.stream.Collectors.toList;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import cp.model.harmony.CpHarmony;
import cp.model.melody.CpMelody;
import cp.model.melody.MelodyBlock;
import cp.out.instrument.Instrument;
import cp.util.RandomUtil;

public class Motive implements Cloneable {

	private List<CpHarmony> harmonies;
	private List<CpMelody> melodies;
	private List<Instrument> instruments;
	private List<MelodyBlock> melodyBlocks;
	
//	public Motive(List<CpMelody> melodies){
//		this.melodies = melodies;
//	}
	
	public Motive(List<MelodyBlock> melodyBlocks){
		this.melodyBlocks = melodyBlocks;
	}

	protected Motive(Motive motive) {
		// TODO clone implementation
//		this.melodies = motive.getMelodies().stream().map(m -> m.clone()).collect(toList());
		this.melodyBlocks = motive.getMelodyBlocks().stream().map(m -> m.clone()).collect(toList());
	}

	public List<CpHarmony> getHarmonies() {
		return harmonies;
	}
	
	public void setHarmonies(List<CpHarmony> harmonies) {
		this.harmonies = harmonies;
	}
	
//	public List<CpMelody> getMelodies() {
//		return melodies;
//	}
	
	public List<MelodyBlock> getMelodyBlocks() {
		return melodyBlocks;
	}
	
	@Override
	public Motive clone() {
		return new Motive(this);
	}
	
	public MelodyBlock getRandomMutableMelody(){
		List<MelodyBlock> mutableMelodies = melodyBlocks.stream().filter(m -> m.isMutable()).collect(toList());
		return mutableMelodies.get(RandomUtil.random(mutableMelodies.size()));
	}
	
	public MelodyBlock getRandomRhythmMutableMelody(){
		List<MelodyBlock> mutableMelodies = melodyBlocks.stream().filter(m -> m.isRhythmMutable()).collect(toList());
		return mutableMelodies.get(RandomUtil.random(mutableMelodies.size()));
	}
	
}
