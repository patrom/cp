package cp.combination.even;

import static cp.model.note.NoteBuilder.note;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;

import cp.model.note.BeamType;
import cp.model.note.Note;
import cp.model.rhythm.DurationConstants;

@Component
public class FourNoteEven {

	public List<Note> pos1234(int beat) {
		List<Note> notes = new ArrayList<Note>();
		int beat4 = beat/4;
		if (beat == DurationConstants.QUARTER) {
			notes.add(note().pos(0).len(beat4).beam(BeamType.BEGIN_BEGIN).build());
			notes.add(note().pos(beat4).len(beat4).beam(BeamType.CONTINUE_CONTINUE).build());
			notes.add(note().pos(2 * beat4).len(beat4).beam(BeamType.CONTINUE_CONTINUE).build());
			notes.add(note().pos(3 * beat4).len(beat4).beam(BeamType.END_END).build());
		}else if (beat == DurationConstants.HALF){
			notes.add(note().pos(0).len(beat4).beam(BeamType.BEGIN).build());
			notes.add(note().pos(beat4).len(beat4).beam(BeamType.END).build());
			notes.add(note().pos(2 * beat4).len(beat4).beam(BeamType.BEGIN).build());
			notes.add(note().pos(3 * beat4).len(beat4).beam(BeamType.END).build());
		}else{
			notes.add(note().pos(0).len(beat4).build());
			notes.add(note().pos(beat4).len(beat4).build());
			notes.add(note().pos(2 * beat4).len(beat4).build());
			notes.add(note().pos(3 * beat4).len(beat4).build());
		}
		return notes;
	}
}
