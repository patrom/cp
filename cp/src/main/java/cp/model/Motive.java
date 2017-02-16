package cp.model;

import cp.model.harmony.CpHarmony;
import cp.model.melody.CpMelody;
import cp.model.melody.MelodyBlock;
import cp.model.note.Note;
import cp.util.RandomUtil;

import java.util.*;
import java.util.function.Predicate;
import java.util.stream.Collectors;

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

	public MelodyBlock getRandomMutableMelodyBlockExcludingVoice(int voice){
		List<MelodyBlock> mutableMelodies = melodyBlocks.stream()
				.filter(m -> m.isMutable() && m.isRhythmMutable() && m.getVoice() != voice)
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

	public Note getRandomHarmonyNoteForPosition(int position, Predicate<Note> harmonyFilter){
		Optional<CpHarmony> optional = harmonies.stream().filter(h -> h.getPosition() <= position && position < h.getEnd()).findFirst();
		CpHarmony harmony = optional.get();
		return RandomUtil.getRandomFromList(harmony.getNotes().stream().filter(harmonyFilter).collect(Collectors.toList()));
	}

	public Note getNextHarmonyNoteForPosition(int position, Predicate<Note> harmonyFilter, int counter){
		Optional<CpHarmony> optional = harmonies.stream().filter(h -> h.getPosition() <= position && position < h.getEnd()).findFirst();
		CpHarmony harmony = optional.get();
		List<Note> harmonyNotes = harmony.getNotes().stream().filter(harmonyFilter).distinct().sorted().collect(toList());
		return harmonyNotes.get(counter % harmonyNotes.size());
	}

	public List<Note> getHarmonyNotesPosition(int position, int size, Predicate<Note> harmonyFilter){
		List<Note> harmonyNotes = harmonies.stream()
				.filter(h -> h.getPosition() <= position && position < h.getEnd())
				.flatMap(h -> h.getNotes().stream())
				.filter(harmonyFilter)
				.collect(toList());
		Collections.shuffle(harmonyNotes);
		if(size > harmonyNotes.size()){
			return harmonyNotes.subList(0, harmonyNotes.size());
		}
		return harmonyNotes.subList(0, size);
	}

	public MelodyBlock getMelodyBlock(int voice){
		return melodyBlocks.stream().filter(m -> m.getVoice() == voice).findFirst().get();
	}
	
}
