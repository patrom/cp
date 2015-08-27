package cp.generator;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import cp.model.note.Note;
import cp.objective.melody.MelodicObjective;
import cp.objective.rhythm.RhythmObjective;
import cp.util.RandomUtil;

@Component
public class MelodyGenerator {
	
	@Autowired
	private RhythmObjective rhythmObjective;
	@Autowired
	private MelodicObjective melodicObjective;

	private Random random = new Random();
	@Autowired
	private MusicProperties musicProperties;
	
	public int[] generateHarmonyPositions(int minimumLength, int maxHarmonies, int bars){
		int limit = (12/minimumLength) * musicProperties.getNumerator() * bars;
		IntStream intStream = random.ints(limit, 0, limit);
		List<Integer> positions = intStream
				.distinct()
				.map(i -> i * minimumLength)
				.boxed()
				.collect(Collectors.toList());
		int max = (maxHarmonies > positions.size())?positions.size():maxHarmonies;
		positions = positions.subList(0, max + 1);
		positions.sort(Comparator.naturalOrder());
		int[] pos = new int[positions.size()];
		for (int j = 0; j < pos.length; j++) {
			pos[j] = positions.get(j);
		}
		return pos;
	}
	
	public int[][] generateMelodies(int[] harmonyPositions, int minimumLength){
		int[][] melodyPositions = new int[harmonyPositions.length - 1][];
		for (int i = 0; i < harmonyPositions.length - 1; i++) {
			int[] melPosition = new int[2];
			melPosition[0] = 0;
			melPosition[1] = harmonyPositions[i + 1] - harmonyPositions[i];
			melodyPositions[i] = melPosition;
		}
		return melodyPositions;
	}
	
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
	
	public List<Note> generateMelodyChordNotes(int[] positions, List<Note> chordNotes){
		List<Note> melodyChordNotes = new ArrayList<>();
		for (int i = 0; i < positions.length - 1; i++) {
			Note note = RandomUtil.getRandomFromList(chordNotes);
			Note noteCopy = note.copy();
			int start = positions[i];
			int end = positions[i + 1];
			noteCopy.setLength(end - start);
			noteCopy.setPosition(start);
			melodyChordNotes.add(noteCopy);
		}
		return melodyChordNotes;
	}
	
	public List<Note> generateMelody(List<Note> scaleNotes, int[] beginEndPosition, int minimumNoteValue){
		for (int i = 0; i < 10000; i++) {
			int max = beginEndPosition[1]/minimumNoteValue;
			int[] positions = generateMelodyPositions(beginEndPosition, minimumNoteValue, max);
			List<Note> melodyNotes = generateMelodyChordNotes(positions, scaleNotes);
			double profile = rhythmObjective.getProfileAverage(melodyNotes, 3.0);
			if (profile >= 0.7 && melodyNotes.size() > 1) {
				double melodicValue = melodicObjective.evaluateMelody(melodyNotes, 1);
				if (melodicValue > 0.7) {
					return melodyNotes;
				}
			}
		}
		throw new IllegalArgumentException("No melody generated");
	}
	
}
