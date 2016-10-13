package cp.variation.nonchordtone.suspension;

import cp.model.note.Note;
import cp.model.note.Scale;
import cp.util.RandomUtil;
import cp.util.Util;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.List;

@Component
public class FreeSuspensionScaleDown extends FreeSuspesion {

	@Override
	public List<Note> createVariation(Note firstNote, Note secondNote) {
		if (variationPattern.getNoteLengths().contains(firstNote.getLength())) {
			double[] pattern = RandomUtil.getRandomFromDoubleArray(variationPattern.getPatterns());
			Scale scale = RandomUtil.getRandomFromList(scales);
			int newPitchClass = scale.pickNextPitchFromScale(firstNote.getPitchClass());
			int ic = Util.intervalClass(newPitchClass - firstNote.getPitchClass());
			int newPitch = firstNote.getPitch() + ic;
			return generateAccentedNonChordNote(firstNote, newPitchClass, newPitch, pattern);
		} else {
			return Collections.singletonList(firstNote.clone());
		}
	}
	
}
