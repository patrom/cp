package cp.variation.nonchordtone.escape;

import cp.model.note.Note;
import cp.model.note.Scale;
import cp.util.RandomUtil;
import cp.util.Util;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.List;

@Component
public class EscapeScaleDown extends Escape {

	@Override
	public List<Note> createVariation(Note note, Note secondNote) {
		if (variationPattern.getNoteLengths().contains(note.getLength())) {
			double[] pattern = RandomUtil.getRandomFromDoubleArray(variationPattern.getPatterns());
			Scale scale = RandomUtil.getRandomFromList(scales);
			int newPitchClass = scale.pickPreviousPitchFromScale(note.getPitchClass());
			int ic = Util.intervalClass(newPitchClass - note.getPitchClass());
			int newPitch = note.getPitch() - ic;
			return generateNonChordNote(note, newPitchClass, newPitch, pattern);
		} else {
			return Collections.singletonList(note.clone());
		}
	}
}
