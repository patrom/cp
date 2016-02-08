package cp.generator.pitchclass;

import static java.util.stream.Collectors.toList;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import cp.model.TimeLine;
import cp.model.TimeLineKey;
import cp.model.note.Note;
import cp.model.note.Scale;
import cp.util.RandomUtil;

@Component
public class PassingPitchClasses{
	
	@Autowired
	private TimeLine timeLine;

	public List<Note> updatePitchClasses(List<Note> notes) {
		
		List<Note> melodyNotes = notes.stream().filter(n -> !n.isRest()).collect(toList());
		Note firstNote = melodyNotes.get(0);
		TimeLineKey timeLineKey = timeLine.getTimeLineKeyAtPosition(firstNote.getPosition());
		int tempPC = timeLineKey.getScale().pickRandomPitchClass();
		
		firstNote.setPitchClass((tempPC + timeLineKey.getKey().getInterval()) % 12);
		for (int i = 1; i < melodyNotes.size(); i++) {
			Note nextNote = melodyNotes.get(i);
			timeLineKey = timeLine.getTimeLineKeyAtPosition(nextNote.getPosition());
			int pitchClass;
			if (RandomUtil.toggleSelection()) {
				pitchClass = timeLineKey.getScale().pickNextPitchFromScale(tempPC);
			} else {
				pitchClass = timeLineKey.getScale().pickPreviousPitchFromScale(tempPC);
			}
			tempPC = pitchClass;
			nextNote.setPitchClass((pitchClass + timeLineKey.getKey().getInterval()) % 12);
		}
		return notes;
	}

}
