package cp.generator;

import static cp.model.note.NoteBuilder.note;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import org.springframework.stereotype.Component;

import cp.model.melody.CpMelody;
import cp.model.note.Note;
import cp.model.note.Scale;
import cp.util.RandomUtil;

@Component
public class MelodyGenerator {
	
	private Random random = new Random();
	
	public int[] generateMelodyPositions(int[] harmony, int minimumLength, int maxMelodyNotes){
		int[] pos = null;
		int positionsInHarmony = ((harmony[1] - harmony[0])/minimumLength) - 1;//minus first position
		int limit = random.nextInt(positionsInHarmony + 1);
		if (limit > 0) {
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
		return harmony;
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
		return melodyNotes;
	}
	
	public CpMelody generateMelody(Scale scale, int[] beginEndPosition, int minimumNoteValue, int voice){
			int max = beginEndPosition[1]/minimumNoteValue;
			int[] positions = generateMelodyPositions(beginEndPosition, minimumNoteValue, max);
			List<Note> melodyNotes = generateMelodyNotes(positions, scale.getScale());
			return new CpMelody(melodyNotes, scale, voice);
	}
	
}
