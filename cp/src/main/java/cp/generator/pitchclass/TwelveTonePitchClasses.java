package cp.generator.pitchclass;

import cp.composition.beat.BeatGroup;
import cp.model.melody.CpMelody;
import cp.model.note.Note;
import cp.model.note.Scale;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class TwelveTonePitchClasses {

	private int counter = 0;
	private Scale scale = Scale.VARIATIONS_FOR_ORCHESTRA_OP31;

	public List<Note> updatePitchClasses(CpMelody melody) {
        List<Note> melodyNotes = melody.getNotesNoRest();
        BeatGroup beatGroup = melody.getBeatGroup();
		Note firstNote = melodyNotes.get(0);
		firstNote.setPitchClass(getNextPitchClass());
		for (int i = 1; i < melodyNotes.size(); i++) {
			Note nextNote = melodyNotes.get(i);
			nextNote.setPitchClass(getNextPitchClass());
		}
		return melodyNotes;
	}

	private int getNextPitchClass(){
		return scale.getPitchClasses()[counter];
	}

}
