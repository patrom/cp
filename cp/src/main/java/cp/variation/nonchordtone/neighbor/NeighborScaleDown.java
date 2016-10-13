package cp.variation.nonchordtone.neighbor;

import cp.model.note.Note;
import cp.model.note.Scale;
import cp.util.RandomUtil;
import cp.util.Util;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.List;

@Component
public class NeighborScaleDown extends Neighbor {
	
	@Override
	public List<Note> createVariation(Note note, Note secondNote) {
		if (variationPattern.getNoteLengths().contains(note.getLength())) {
			Scale scale = RandomUtil.getRandomFromList(scales);
			int pitchClass = scale.pickPreviousPitchFromScale(note.getPitchClass());
			double[] pattern = RandomUtil.getRandomFromDoubleArray(variationPattern.getPatterns());
			int ic = Util.intervalClass(pitchClass - note.getPitchClass());
			int pitch = note.getPitch() - ic;
			return generateNeighborNote(note, pitchClass, pitch, pattern);
		} else {
			return Collections.singletonList(note.clone());
		}
	}
}
