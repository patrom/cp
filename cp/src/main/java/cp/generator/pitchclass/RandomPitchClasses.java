package cp.generator.pitchclass;

import cp.model.TimeLine;
import cp.model.TimeLineKey;
import cp.model.note.Note;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class RandomPitchClasses  {

	@Autowired
	private TimeLine timeLine;
	
	public List<Note> randomPitchClasses(List<Note> notes){
		notes.forEach(n -> { 
				if (!n.isRest()) {
					TimeLineKey timeLineKey = timeLine.getTimeLineKeyAtPosition(n.getPosition(), n.getVoice());
					n.setPitchClass((timeLineKey.getScale().pickRandomPitchClass() + timeLineKey.getKey().getInterval()) % 12);
				}
			});
		return notes;
	}
}
