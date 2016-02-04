package cp.generator.pitchclass;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import cp.model.TimeLine;
import cp.model.note.Note;
import cp.model.note.Scale;

@Component
public class RandomPitchClasses  {

	@Autowired
	private TimeLine timeLine;
	
	public List<Note> randomPitchClasses(List<Note> notes, Scale scale){
		notes.forEach(n -> { 
				if (!n.isRest()) {
					int key = timeLine.getKeyAtPosition(n.getPosition()).getInterval();
					n.setPitchClass((scale.pickRandomPitchClass() + key) % 12);
				}
			});
		return notes;
	}
}
