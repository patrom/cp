package cp.variation.nonchordtone.anticipation;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.springframework.stereotype.Component;

import cp.model.note.Note;
import cp.util.RandomUtil;
import cp.variation.nonchordtone.Variation;

@Component
public class Anticipation extends Variation {
	
	public Anticipation() {
		profile = 10;
		excludedVoices.add(0);
	}

	@Override
	public List<Note> createVariation(Note firstNote, Note secondNote) {
		if (variationPattern.getNoteLengths().contains(firstNote.getLength())) {
			double[] pattern = RandomUtil.getRandomFromDoubleArray(variationPattern.getPatterns());
			return generateNonChordNote(firstNote, secondNote.getPitchClass(), secondNote.getPitch(), pattern);
		} 
		return Collections.singletonList(firstNote.copy());
	}

}
