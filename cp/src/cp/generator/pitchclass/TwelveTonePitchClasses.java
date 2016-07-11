package cp.generator.pitchclass;

import static java.util.stream.Collectors.toList;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import cp.model.TimeLine;
import cp.model.TimeLineKey;
import cp.model.note.Note;
import cp.util.RandomUtil;

@Component
public class TwelveTonePitchClasses {

	@Autowired
	private TimeLine timeLine;

	public List<Note> updatePitchClasses(List<Note> notes) {
		List<Note> melodyNotes = notes.stream().filter(n -> !n.isRest()).collect(toList());
		Note firstNote = melodyNotes.get(0);
		TimeLineKey timeLineKey = timeLine.getTimeLineKeyAtPosition(firstNote.getPosition(), firstNote.getVoice());
		int tempPC = timeLineKey.getScale().getPitchClasses()[0];
		
		firstNote.setPitchClass(tempPC);
		for (int i = 1; i < melodyNotes.size(); i++) {
			Note nextNote = melodyNotes.get(i);
			timeLineKey = timeLine.getTimeLineKeyAtPosition(nextNote.getPosition(), nextNote.getVoice());
			int pitchClass = timeLineKey.getScale().pickNextPitchFromScale(tempPC);			
			tempPC = pitchClass;
			nextNote.setPitchClass(pitchClass);
		}
		return notes;
	}

}
