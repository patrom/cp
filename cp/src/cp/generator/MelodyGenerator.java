package cp.generator;

import static cp.model.note.NoteBuilder.note;
import static java.util.stream.Collectors.toList;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import cp.combination.NoteCombination;
import cp.generator.pitchclass.PitchClassGenerator;
import cp.model.melody.CpMelody;
import cp.model.melody.MelodyBlock;
import cp.model.note.Note;
import cp.model.note.Scale;
import cp.util.RandomUtil;

@Component
public class MelodyGenerator {
	
	private Random random = new Random();
	
	@Resource(name="evenPulseDivisions")
	private List<Integer[]> evenPulseDivisions;
	
	@Resource(name="oddPulseDivisions")
	private List<Integer[]> oddPulseDivisions;
	@Autowired
	private MusicProperties musicProperties;
	@Autowired
	private NoteCombination noteCombination;
	private PitchClassGenerator pitchClassGenerator;
	
	public void setPitchClassGenerator(PitchClassGenerator pitchClassGenerator) {
		this.pitchClassGenerator = pitchClassGenerator;
	}
	
	public MelodyBlock generateMelodyBlock(final int voice, Scale scale, int start, int stop, int octave, List<Integer> beats){
		MelodyBlock melodyBlock = new MelodyBlock(octave, voice);
		int beat = RandomUtil.getRandomFromList(beats);
		int end = start + beat;
		int key = musicProperties.getKey().getInterval();
		while (end <= stop) {
			CpMelody melody = generateMelody(voice, scale, start, beat, key);
			melodyBlock.addMelodyBlock(melody);
			beat = RandomUtil.getRandomFromList(beats);
			start = end;
			end = start + beat;
		}
		return melodyBlock;
	}

	public CpMelody generateMelody(int voice, Scale scale, int start, int beat, int key) {
		List<Note> melodyNotes = noteCombination.getNotes(beat, voice);
		int offset = start;
		melodyNotes.forEach(n -> {
			n.setPosition(n.getPosition() + offset);
		});
		melodyNotes = pitchClassGenerator.updatePitchClasses(melodyNotes, scale, key);
		CpMelody melody = new CpMelody(melodyNotes, scale, voice, start, start + beat);
		melody.setBeat(beat);
		melody.setKey(key);
		return melody;
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
		note.setLength(12);
		note.setPosition(positions[positions.length - 1]);
		melodyNotes.add(note);
		return melodyNotes;
	}
	
	public CpMelody generateMelody(Scale scale, int[] beginEndPosition, int minimumNoteValue, int voice){
			int max = beginEndPosition[1]/minimumNoteValue;
			int[] positions = generateMelodyPositions(beginEndPosition, minimumNoteValue, max);
			List<Note> melodyNotes = generateMelodyNotes(positions, scale.getPitchClasses());
			return new CpMelody(melodyNotes, scale, voice);
	}
	
	public CpMelody generateMelodyTactus(Scale scale, int[] beginEndPosition, int minimumTactus, int voice, int startOctave){
		int limit = generateLimit(beginEndPosition[0], beginEndPosition[1], minimumTactus);
		int from = ((beginEndPosition[0])/minimumTactus) + 1;
		int toExlusive = (int)Math.ceil(beginEndPosition[1]/(double)minimumTactus);
		IntStream intStream = random.ints(limit,from,toExlusive);
		List<Integer> positions = intStream
				.distinct()
				.map(i -> i * minimumTactus)
				.boxed()
				.sorted()
				.collect(toList());
		List<Note> notes = new ArrayList<>();
		for (Integer position : positions) {
			Integer[] pulse = RandomUtil.getRandomFromList(evenPulseDivisions);
			notes = createNotes(position, minimumTactus , pulse, scale, voice);
		}
		//insert last position
		Note note = note().pos(beginEndPosition[1]).len(minimumTactus).pc(scale.pickRandomPitchClass()).voice(voice).build();
		notes.add(note);
		CpMelody melody = new CpMelody(notes, scale, voice);
		return melody;
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
	
	public List<Note> generatePositions(int position, int maxEditablePosition, int minimumValue, Integer[] pulses, int type){
		List<Note> notes = new ArrayList<>();
		int noteLength = maxEditablePosition/pulses.length;
		if (noteLength < minimumValue) {
			Note note = note().pos(position).len(maxEditablePosition).build();
			notes.add(note);
			return notes;
		}
		for (int i = 0; i < pulses.length; i++) {
			if (pulses[i] == 1) {
				int notePosition = position + (i * noteLength);
				if (maxEditablePosition != minimumValue) {
					if (random.nextBoolean()) {
						//recursive
						List<Note> recursiveNotes = generatePositions(notePosition, noteLength, minimumValue, getPulses(type), type);
						notes.addAll(recursiveNotes);
					}else{
						Note note = note().pos(notePosition).len(noteLength).build();
						notes.add(note);
					}
				} else {
					Note note = note().pos(notePosition).len(noteLength).build();
					notes.add(note);
				}
			}
		}
		Collections.sort(notes);
		return notes;
	}
	
//	public List<Note> generatePulses(int type, int beatValue, int minimumValue){
//		return generatePositions(0, type * beatValue, minimumValue, getPulses(type), type);
//	}
	
	public CpMelody generateMelody(int start, int minimumValue){
		int type = musicProperties.getMelodyType();
		int end = type * musicProperties.getMelodyBeatValue();
		List<Note> melodyNotes = generatePositions(start, end, minimumValue, getPulses(type), type);
		Scale scale = musicProperties.getMelodyScale();
		CpMelody melody = new CpMelody(melodyNotes, scale, start, end);
		melody.setType(type);
		return melody;
	}

	public Integer[] getPulses(int type) {
		Integer[] pulses = null;
		if (type == 2) {
			pulses = RandomUtil.getRandomFromList(evenPulseDivisions);
		}
		if (type == 3) {
			pulses = RandomUtil.getRandomFromList(oddPulseDivisions);
		}
		if (pulses == null) {
			throw new IllegalArgumentException("No pulses for type: " + type);
		}
		return pulses;
	}
	
}
