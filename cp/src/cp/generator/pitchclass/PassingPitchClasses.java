package cp.generator.pitchclass;

import static java.util.stream.Collectors.toList;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Component;

import cp.model.note.Note;
import cp.model.note.Scale;
import cp.util.RandomUtil;

@Component
public class PassingPitchClasses{

	public List<Note> updatePitchClasses(List<Note> notes, Scale scale, int key) {
		List<Note> melodyNotes = notes.stream().filter(n -> !n.isRest()).collect(toList());
		int tempPC = scale.pickRandomPitchClass();
		melodyNotes.get(0).setPitchClass((tempPC + key) % 12);
		for (int i = 1; i < melodyNotes.size(); i++) {
			Note nextNote = melodyNotes.get(i);
			int pitchClass;
			if (RandomUtil.toggleSelection()) {
				pitchClass = scale.pickNextPitchFromScale(tempPC);
			} else {
				pitchClass = scale.pickPreviousPitchFromScale(tempPC);
			}
			tempPC = pitchClass;
			nextNote.setPitchClass((pitchClass + key) % 12);
		}
		return notes;
	}

}
