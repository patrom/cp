package cp.variation.nonchordtone.passing;

import cp.model.note.Note;
import cp.util.RandomUtil;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.List;

@Component(value="ChromaticPassingDown")
public class ChromaticPassingDown extends Passing {
	
	public ChromaticPassingDown() {
		profile = 30;
	}

	@Override
	public List<Note> createVariation(Note note, Note secoNote) {
		if (variationPattern.getNoteLengths().contains(note.getLength())) {
			double[] pattern = RandomUtil.getRandomFromDoubleArray(variationPattern.getPatterns());
			int newPitchClass = note.getPitchClass() - 1;
			int newPitch = note.getPitch() - 1;
			return generateNonChordNote(note, newPitchClass, newPitch, pattern);
		} else {
			return Collections.singletonList(note.clone());
		}
	}

}
