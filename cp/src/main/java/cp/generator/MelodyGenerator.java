package cp.generator;

import cp.composition.Composition;
import cp.composition.beat.BeatGroup;
import cp.composition.beat.BeatGroupStrategy;
import cp.composition.timesignature.TimeConfig;
import cp.generator.pitchclass.PitchClassGenerator;
import cp.model.TimeLine;
import cp.model.melody.CpMelody;
import cp.model.melody.MelodyBlock;
import cp.model.note.Note;
import cp.model.note.Scale;
import cp.model.rhythm.DurationConstants;
import cp.out.instrument.Instrument;
import cp.util.RandomUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static cp.model.note.NoteBuilder.note;

@Component
public class MelodyGenerator {
	
	private final Random random = new Random();

	@Autowired
	private TimeLine timeLine;
	
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

	public MelodyBlock generateDependantMelodyBlock(final int voice, int octave, MelodyBlock dependingMelodyBlock){
		int start = composition.getStart();
		int stop = composition.getEnd();
		MelodyBlock melodyBlock = new MelodyBlock(octave, voice);
		melodyBlock.setTimeConfig(composition.getTimeConfig());

		int end = start;
		while (end < stop) {
			CpMelody clonedMelody = getCpMelody(voice, start, dependingMelodyBlock);
			melodyBlock.addMelodyBlock(clonedMelody);
			start = clonedMelody.getEnd();
			end = start;
		}
		return melodyBlock;
	}

	public MelodyBlock generateRandomNoteBlock(final int voice, int octave){
		MelodyBlock melodyBlock = new MelodyBlock(voice, octave);
		List<Note> notes = new ArrayList<>();
		int quarterSize = composition.getEnd() / DurationConstants.QUARTER;
		for (int i = 0; i < quarterSize; i++) {
			int position = i * DurationConstants.QUARTER;
			Scale scale = timeLine.getTimeLineKeyAtPosition(position, voice).getScale();
			notes.add(note().pc(scale.pickRandomPitchClass()).ocatve(octave).voice(voice).pos(position).len(DurationConstants.QUARTER).build());
		}
		CpMelody oneNoteMelody = new CpMelody(notes, voice, composition.getStart(), composition.getEnd());
		melodyBlock.setMelodyBlocks(Collections.singletonList(oneNoteMelody));
		melodyBlock.setTimeConfig(composition.getTimeConfig());
		return melodyBlock;
	}

	public MelodyBlock generateEmptyBlock(final Instrument instrument){
		MelodyBlock melodyBlock = new MelodyBlock(instrument.pickRandomOctaveFromRange(), instrument.getVoice());
		melodyBlock.setTimeConfig(composition.getTimeConfig());
		melodyBlock.setVoice(instrument.getVoice());
		melodyBlock.setInstrument(instrument);
		return melodyBlock;
	}

	private CpMelody getCpMelody(int voice, int start,  MelodyBlock dependingMelodyBlock) {
		CpMelody randomMelody = RandomUtil.getRandomFromList(dependingMelodyBlock.getMelodyBlocks());
		CpMelody clonedMelody = randomMelody.clone(dependingMelodyBlock.getVoice());

		if(RandomUtil.toggleSelection()){
			int steps = RandomUtil.getRandomNumberInRange(0, 7);
			clonedMelody.transposePitchClasses(steps, 0 , timeLine);
		}else{
			int steps = RandomUtil.getRandomNumberInRange(1, 7);
			clonedMelody.inversePitchClasses(steps, 0 , timeLine);
		}
//		clonedMelody.I();

		//TODO pass operator?
		clonedMelody.getNotes().forEach(n -> {
            n.setPosition(n.getPosition() + start - randomMelody.getStart());
        });
		clonedMelody.setStart(start);
		clonedMelody.setEnd(start + randomMelody.getBeatGroupLength());
		return clonedMelody;
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
		melodyNotes.forEach(n -> {
			n.setVoice(voice);
			n.setPosition(n.getPosition() + start);
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
		int[] pos;
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
