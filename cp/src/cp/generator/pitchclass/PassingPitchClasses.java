package cp.generator.pitchclass;

import static java.util.stream.Collectors.toList;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import cp.model.TimeLine;
import cp.model.note.Note;
import cp.model.note.Scale;
import cp.util.RandomUtil;

@Component
public class PassingPitchClasses{
	
	@Autowired
	private TimeLine timeLine;

	public List<Note> updatePitchClasses(List<Note> notes, Scale scale) {
		
		List<Note> melodyNotes = notes.stream().filter(n -> !n.isRest()).collect(toList());
		int tempPC = scale.pickRandomPitchClass();
		Note firstNote = melodyNotes.get(0);
		int key = timeLine.getKeyAtPosition(firstNote.getPosition()).getInterval();
		firstNote.setPitchClass((tempPC + key) % 12);
		for (int i = 1; i < melodyNotes.size(); i++) {
			Note nextNote = melodyNotes.get(i);
			int pitchClass;
			if (RandomUtil.toggleSelection()) {
				pitchClass = scale.pickNextPitchFromScale(tempPC);
			} else {
				pitchClass = scale.pickPreviousPitchFromScale(tempPC);
			}
			tempPC = pitchClass;
			key = timeLine.getKeyAtPosition(nextNote.getPosition()).getInterval();
			nextNote.setPitchClass((pitchClass + key) % 12);
		}
		return notes;
	}

}
