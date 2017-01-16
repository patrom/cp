package cp.generator.pitchclass;

import cp.model.TimeLine;
import cp.model.note.Note;
import cp.model.note.Scale;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

import static java.util.stream.Collectors.toList;

@Component
public class TwelveTonePitchClasses {

	@Autowired
	private TimeLine timeLine;

	private int counter = 0;
	private Scale scale = Scale.VARIATIONS_FOR_ORCHESTRA_OP31;

	public List<Note> updatePitchClasses(List<Note> notes) {
		List<Note> melodyNotes = notes.stream().filter(n -> !n.isRest()).collect(toList());
		Note firstNote = melodyNotes.get(0);
		firstNote.setPitchClass(getNextPitchClass());
		for (int i = 1; i < melodyNotes.size(); i++) {
			Note nextNote = melodyNotes.get(i);
			nextNote.setPitchClass(getNextPitchClass());
		}
		return notes;
	}

	private int getNextPitchClass(){
		return scale.getPitchClasses()[counter];
	}

}
