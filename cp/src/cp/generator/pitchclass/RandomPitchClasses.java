package cp.generator.pitchclass;

import java.util.List;

import org.springframework.stereotype.Component;

import cp.model.note.Note;
import cp.model.note.Scale;

@Component
public class RandomPitchClasses  {

	public List<Note> randomPitchClasses(List<Note> notes, Scale scale, int key){
		notes.forEach(n -> { 
				if (!n.isRest()) {
					n.setPitchClass((scale.pickRandomPitchClass() + key) % 12);
				}
			});
		return notes;
	}
}
