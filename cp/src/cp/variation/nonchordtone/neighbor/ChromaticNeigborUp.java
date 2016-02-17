package cp.variation.nonchordtone.neighbor;

import java.util.Collections;
import java.util.List;

import org.springframework.stereotype.Component;

import cp.model.note.Note;
import cp.util.RandomUtil;

@Component
public class ChromaticNeigborUp extends Neighbor{
	
	public ChromaticNeigborUp() {
		profile = 10;
	}

	@Override
	public List<Note> createVariation(Note note, Note secondNote) {
		if (variationPattern.getNoteLengths().contains(note.getLength())) {
			double[] pattern = RandomUtil.getRandomFromDoubleArray(variationPattern.getPatterns());
			int pitchClass = note.getPitchClass() + 1;
			int pitch = note.getPitch() + 1;
			return generateNeighborNote(note, pitchClass, pitch, pattern);
		} else {
			return Collections.singletonList(note.clone());
		}
	}
}
