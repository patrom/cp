package cp.variation.nonchordtone.passing;

import cp.model.note.Note;
import cp.util.RandomUtil;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.List;

@Component(value="ChromaticPassingUp")
public class ChromaticPassingUp extends Passing {

	public ChromaticPassingUp() {
		profile = 30;
	}
	
	@Override
	public List<Note> createVariation(Note note, Note secondNote) {
		if (variationPattern.getNoteLengths().contains(note.getLength())) {
			double[] pattern = RandomUtil.getRandomFromDoubleArray(variationPattern.getPatterns());
			int newPitchClass = note.getPitchClass() + 1;
			int newPitch = note.getPitch() + 1;
			return generateNonChordNote(note, newPitchClass, newPitch, pattern);
		} else {
			return Collections.singletonList(note.clone());
		}
	}

	

}
