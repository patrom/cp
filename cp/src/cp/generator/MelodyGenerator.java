package cp.generator;

import static cp.model.note.NoteBuilder.note;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import org.springframework.stereotype.Component;

import cp.composition.Composition;
import cp.composition.beat.BeatGroup;
import cp.composition.beat.BeatGroupStrategy;
import cp.composition.timesignature.TimeConfig;
import cp.generator.pitchclass.PitchClassGenerator;
import cp.model.melody.CpMelody;
import cp.model.melody.MelodyBlock;
import cp.model.note.Note;
import cp.model.note.Scale;
import cp.model.rhythm.DurationConstants;
import cp.out.instrument.Instrument;
import cp.util.RandomUtil;

@Component
public class MelodyGenerator {
	
	private Random random = new Random();
	
	private PitchClassGenerator pitchClassGenerator;

	private Composition composition;
	
	private BeatGroupStrategy beatGroupStrategy;
	
	public MelodyBlock generateMelodyBlock(final int voice, int octave){
		return generateMelodyBlock(voice, octave, composition.getTimeConfig().randomBeatGroup(), beatGroupStrategy, composition.getTimeConfig());
	}
	
	public MelodyBlock generateMelodyBlock(final int voice, int octave, BeatGroupStrategy beatGroupStrategy){
		return generateMelodyBlock(voice, octave, composition.getTimeConfig().randomBeatGroup(), beatGroupStrategy, composition.getTimeConfig());
	}
	
	public MelodyBlock generateMelodyBlock(final int voice, int octave, BeatGroupStrategy beatGroupStrategy, TimeConfig timeConfig){
		return generateMelodyBlock(voice, octave, composition.getTimeConfig().randomBeatGroup(), beatGroupStrategy, timeConfig);
	}
	
	public MelodyBlock generateMelodyBlock(final int voice, int octave, boolean randomBeats, BeatGroupStrategy beatGroupStrategy, TimeConfig timeConfig){
		int start = composition.getStart();
		int stop = composition.getEnd();
		List<BeatGroup> beatGroups = beatGroupStrategy.getBeatGroups();
		MelodyBlock melodyBlock = new MelodyBlock(octave, voice);
		melodyBlock.setTimeConfig(timeConfig);
		melodyBlock.setOffset(timeConfig.getOffset());
		BeatGroup beatGroup;
		int i = 0;
		int size = beatGroups.size();
		if (randomBeats) {
			beatGroup = RandomUtil.getRandomFromList(beatGroups);
		}else{
			beatGroup = beatGroups.get(i);
		}
		int end = start + beatGroup.getBeatLength();
		while (end <= stop) {
			CpMelody melody = generateMelody(voice, start, beatGroup);
			melodyBlock.addMelodyBlock(melody);
			if (randomBeats) {
				beatGroup = RandomUtil.getRandomFromList(beatGroups);
			} else {
				i++;
				beatGroup = beatGroups.get(i % size);
			}		
			start = end;
			end = start + beatGroup.getBeatLength();
		}
		return melodyBlock;
	}

	public CpMelody generateMelody(int voice, int start, BeatGroup beatGroup) {
		List<Note> melodyNotes;
		if (composition.getTimeConfig().randomCombination()) {
			melodyNotes = beatGroup.getNotesRandom();
		} else {
			melodyNotes = beatGroup.getNotes();
		}	
		int offset = start;
		melodyNotes.forEach(n -> {
			n.setVoice(voice);
			n.setPosition(n.getPosition() + offset);
		});
		melodyNotes = pitchClassGenerator.updatePitchClasses(melodyNotes);
		CpMelody melody = new CpMelody(melodyNotes, voice, start, start + beatGroup.getBeatLength());
		melody.setBeatGroup(beatGroup);
		return melody;
	}
	
	public MelodyBlock duplicateRhythmMelodyBlock(MelodyBlock melodyBlock, Instrument instrument){
		MelodyBlock clonedMelodyBlock = melodyBlock.clone();
		clonedMelodyBlock.setInstrument(instrument);
		clonedMelodyBlock.setVoice(instrument.getVoice());
		clonedMelodyBlock.getMelodyBlocks().forEach(m -> m.setVoice(instrument.getVoice()));
		clonedMelodyBlock.getMelodyBlockNotesWithRests().forEach(n -> n.setVoice(instrument.getVoice()));
		List<Note> melodyBlockNotes = clonedMelodyBlock.getMelodyBlockNotes();
		pitchClassGenerator.updatePitchClasses(melodyBlockNotes);
		clonedMelodyBlock.dependsOn(melodyBlock.getVoice());
		clonedMelodyBlock.setRhythmMutable(false);
		clonedMelodyBlock.setRhythmDependant(true);
		return clonedMelodyBlock;
	}
	
	public int[] generateMelodyPositions(int[] harmony, int minimumLength, int maxMelodyNotes){
		int[] pos = null;
		int limit = generateLimit(harmony[0], harmony[1], minimumLength);
		int from = ((harmony[0])/minimumLength) + 1;
		int toExlusive = (int)Math.ceil(harmony[1]/(double)minimumLength);
		IntStream intStream = random.ints(limit,from,toExlusive);
		List<Integer> positions = intStream
				.distinct()
				.map(i -> i * minimumLength)
				.boxed()
				.collect(Collectors.toList());
		int max = (maxMelodyNotes > positions.size())?positions.size():maxMelodyNotes;
		positions = positions.subList(0, max);
		positions.sort(Comparator.naturalOrder());
		pos = new int[positions.size() + 2];
		pos[0] = harmony[0];
		pos[pos.length - 1] = harmony[1];
		for (int j = 1; j < pos.length - 1; j++) {
			pos[j] = positions.get(j - 1);
		}
		return pos;
	}

	protected int generateLimit(int begin, int end, int minimumLength) {
		int positionsInHarmony = ((end - begin)/minimumLength) - 1;//minus first position
		int limit = random.nextInt(positionsInHarmony + 1);
		while (limit < 2) {
			limit = random.nextInt(positionsInHarmony + 1);
		}
		return limit;
	}
	
	public List<Note> generateMelodyNotes(int[] positions, int[] scale){
		List<Note> melodyNotes = new ArrayList<>();
		for (int i = 0; i < positions.length - 1; i++) {
			int pc = RandomUtil.getRandomFromIntArray(scale);
			Note note = note().pc(pc).build();
			int start = positions[i];
			int end = positions[i + 1];
			note.setLength(end - start);
			note.setPosition(start);
			melodyNotes.add(note);
		}
		//add last note
		int pc = RandomUtil.getRandomFromIntArray(scale);
		Note note = note().pc(pc).build();
		note.setLength(DurationConstants.QUARTER);
		note.setPosition(positions[positions.length - 1]);
		melodyNotes.add(note);
		return melodyNotes;
	}
	
	protected List<Note> createNotes(int position, int minimumPulse, Integer[] pulses, Scale scale, int voice){
		List<Note> notes = new ArrayList<>();
		int noteLength = minimumPulse/pulses.length;
		for (int i = 0; i < pulses.length; i++) {
			if (pulses[i] == 1) {
				int notePosition = position + (i * noteLength);
				Note note = note().pos(notePosition).len(noteLength).pc(scale.pickRandomPitchClass()).voice(voice).build();
				notes.add(note);
			}
		}
		return notes;
	}

	public void setCompostion(Composition compostion) {
		this.composition = compostion;
	}
	
	public void setBeatGroupStrategy(BeatGroupStrategy beatGroupStrategy) {
		this.beatGroupStrategy = beatGroupStrategy;
	}
	
	public void setPitchClassGenerator(PitchClassGenerator pitchClassGenerator) {
		this.pitchClassGenerator = pitchClassGenerator;
	}
	
}
