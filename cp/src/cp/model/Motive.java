package cp.model;

import static java.util.stream.Collectors.groupingBy;
import static java.util.stream.Collectors.summingDouble;
import static java.util.stream.Collectors.toList;

import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import cp.model.harmony.CpHarmony;
import cp.model.melody.MelodyBlock;
import cp.model.note.Note;
import cp.util.RandomUtil;

public class Motive implements Cloneable {

	private List<CpHarmony> harmonies;
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
	
	public  Map<Integer, Double> extractRhythmProfile(){
		List<Note> mergedMelodyNotes = melodyBlocks.stream().flatMap(m -> m.getMelodyBlockNotes().stream()).collect(toList());
		return  mergedMelodyNotes.stream().collect(groupingBy(Note::getPosition, TreeMap::new, summingDouble(Note::getPositionWeight)));
	}
	
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
	
}
