package cp.generator;

import static cp.model.note.NoteBuilder.note;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;

import cp.model.melody.CpMelody;
import cp.model.note.Note;
import cp.model.note.Scale;
import cp.util.RandomUtil;

@Component
public class MelodyGenerator {
	
	private Random random = new Random();
	
	@Resource(name="pulseDivisions")
	private List<Integer[]> pulseDivisions;
	
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
	
	public CpMelody generateMelodyTactus(Scale scale, int[] beginEndPosition, int minimumTactus, int voice){
		int limit = generateLimit(beginEndPosition[0], beginEndPosition[1], minimumTactus);
		int from = ((beginEndPosition[0])/minimumTactus) + 1;
		int toExlusive = (int)Math.ceil(beginEndPosition[1]/(double)minimumTactus);
		IntStream intStream = random.ints(limit,from,toExlusive);
		List<Integer> positions = intStream
				.distinct()
				.map(i -> i * minimumTactus)
				.boxed()
				.sorted()
				.collect(Collectors.toList());
		CpMelody melody = new CpMelody(scale, voice , beginEndPosition[0], beginEndPosition[1]);
		for (Integer position : positions) {
			Integer[] pulse = RandomUtil.getRandomFromList(pulseDivisions);
			melody.insert(position, minimumTactus , pulse);
		}
		melody.insert( beginEndPosition[1], minimumTactus , new Integer[]{1,0});//insert last position
		return melody;
	}
	
}
