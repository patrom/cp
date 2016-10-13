package cp.variation.nonchordtone.suspension;

import cp.model.note.Note;
import cp.util.RandomUtil;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.List;
@Component
public class FreeSuspensionChromaticUp extends FreeSuspesion {

	public FreeSuspensionChromaticUp() {
		profile = 50;
	}
	
	@Override
	public List<Note> createVariation(Note firstNote, Note secondNote) {
		if (variationPattern.getNoteLengths().contains(firstNote.getLength())) {
			double[] pattern = RandomUtil.getRandomFromDoubleArray(variationPattern.getPatterns());
			int newPitchClass = firstNote.getPitchClass() - 1;
			int newPitch = firstNote.getPitch() - 1;
			return generateAccentedNonChordNote(firstNote, newPitchClass, newPitch, pattern);
		} else {
			return Collections.singletonList(firstNote.clone());
		}
	}

}
