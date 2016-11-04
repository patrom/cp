package cp.model;

import cp.model.harmony.CpHarmony;
import cp.model.melody.CpMelody;
import cp.model.melody.MelodyBlock;
import cp.model.note.Note;
import cp.util.RandomUtil;

import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import static java.util.stream.Collectors.*;

public class Motive implements Cloneable {

	private List<CpHarmony> harmonies;
	private final List<MelodyBlock> melodyBlocks;
	
	public Motive(List<MelodyBlock> melodyBlocks){
		this.melodyBlocks = melodyBlocks;
	}

	protected Motive(Motive motive) {
		// TODO clone implementation
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
		List<MelodyBlock> mutableMelodies = melodyBlocks.stream()
				.filter(m -> m.isMutable() && m.isRhythmMutable())
				.collect(toList());
		return RandomUtil.getRandomFromList(mutableMelodies);
	}

	public CpMelody getRandomMelodyWithBeatGroupLength(int length){
		List<CpMelody> melodies =  melodyBlocks.stream()
				.flatMap(block -> block.getMelodyBlocks().stream())
				.filter(melody -> melody.getBeatGroup().getBeatLength() == length).collect(toList());
		return RandomUtil.getRandomFromList(melodies);
	}

	public MelodyBlock getRandomMelodyForVoice(int voice){
		List<MelodyBlock> mutableMelodies = melodyBlocks.stream()
				.filter(m -> m.getVoice() == voice)
				.collect(toList());
		return RandomUtil.getRandomFromList(mutableMelodies);
	}
	
}
