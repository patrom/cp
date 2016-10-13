package cp.variation.nonchordtone.passing;

import cp.model.note.Note;
import cp.model.note.Scale;
import cp.util.RandomUtil;
import cp.util.Util;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.List;

@Component(value="PassingUp")
public class PassingUp extends Passing {

	@Override
	public List<Note> createVariation(Note note, Note secondNote) {
		if (variationPattern.getNoteLengths().contains(note.getLength())) {
			double[] pattern = RandomUtil.getRandomFromDoubleArray(variationPattern.getPatterns());
			Scale scale = RandomUtil.getRandomFromList(scales);
			int newPitchClass = scale.pickNextPitchFromScale(note.getPitchClass());
			int ic = Util.intervalClass(newPitchClass - note.getPitchClass());
			int newPitch = note.getPitch() + ic;
			return generateNonChordNote(note, newPitchClass, newPitch, pattern);
		} else {
			return Collections.singletonList(note.clone());
		}
	}

}
