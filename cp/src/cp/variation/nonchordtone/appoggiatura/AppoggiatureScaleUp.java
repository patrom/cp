package cp.variation.nonchordtone.appoggiatura;

import java.util.Collections;
import java.util.List;

import org.springframework.stereotype.Component;

import cp.model.note.Note;
import cp.model.note.Scale;
import cp.util.RandomUtil;
import cp.util.Util;

@Component
public class AppoggiatureScaleUp extends Appoggiature {

	@Override
	public List<Note> createVariation(Note firstNote, Note secondNote) {
		if (variationPattern.getNoteLengths().contains(firstNote.getLength())) {
			secondNoteChanged = true;
			double[] pattern = RandomUtil.getRandomFromDoubleArray(variationPattern.getPatterns());
			Scale scale = RandomUtil.getRandomFromList(scales);
			int newPitchClass = scale.pickNextPitchFromScale(secondNote.getPitchClass());
			int ic = Util.intervalClass(newPitchClass - secondNote.getPitchClass());
			int newPitch = secondNote.getPitch() - ic;
			List<Note> notes = generateAccentedNonChordNote(secondNote, newPitchClass, newPitch, pattern);
			notes.add(0, firstNote);
			return notes;
		} else {
			return Collections.singletonList(firstNote.clone());
		}
	}
}
