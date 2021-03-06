package cp.variation;

import cp.model.note.Note;
import cp.model.note.Scale;
import cp.model.rhythm.DurationConstants;
import cp.variation.nonchordtone.Variation;
import cp.variation.pattern.VariationPattern;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static cp.model.note.NoteBuilder.note;

public abstract class AbstractVariationTest {
	
	protected Variation variation;
	protected VariationPattern variationPattern;
	protected double[][] pattern;

	protected void setVariation() {
		variation.setScales(Collections.singletonList(Scale.MAJOR_SCALE));
		variationPattern.setPatterns(pattern);
		List<Integer> allowedLengths = new ArrayList<>();
		allowedLengths.add(DurationConstants.QUARTER);
		variationPattern.setNoteLengths(allowedLengths);
		variationPattern.setSecondNoteLengths(allowedLengths);
		variation.setVariationPattern(variationPattern);
	}
	
	protected List<Note> testNotAllowedLength(){
		Note firstNote = note().pc(4).pitch(64).pos(0).len(3).octave(5).build();
		Note secondNote = note().pc(0).pitch(60).pos(3).len(9).octave(5).build();
		return variation.createVariation(firstNote, secondNote);
	}
}
