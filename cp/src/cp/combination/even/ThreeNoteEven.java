package cp.combination.even;

import static cp.model.note.NoteBuilder.note;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;

import cp.model.note.BeamType;
import cp.model.note.Note;
import cp.model.rhythm.DurationConstants;

@Component
public class ThreeNoteEven {

	public List<Note> pos123(int beat) {
		List<Note> notes = new ArrayList<Note>();
		int beat2 = beat/2;
		int beat4 = beat/4;
		if (beat == DurationConstants.QUARTER) {
			notes.add(note().pos(0).len(beat4).beam(BeamType.BEGIN_BEGIN).build());
			notes.add(note().pos(beat4).len(beat4).beam(BeamType.CONTINUE_END).build());
			notes.add(note().pos(beat2).len(beat2).beam(BeamType.END).build());
		}else if (beat == DurationConstants.HALF){
			notes.add(note().pos(0).len(beat4).beam(BeamType.BEGIN).build());
			notes.add(note().pos(beat4).len(beat4).beam(BeamType.END).build());
			notes.add(note().pos(beat2).len(beat2).build());
		}else{
			notes.add(note().pos(0).len(beat4).build());
			notes.add(note().pos(beat4).len(beat4).build());
			notes.add(note().pos(beat2).len(beat2).build());
		}
		return notes;
	}
	
	public List<Note> pos134(int beat) {
		List<Note> notes = new ArrayList<Note>();
		int beat2 = beat/2;
		int beat4 = beat/4;
		if (beat == DurationConstants.QUARTER) {
			notes.add(note().pos(0).len(beat2).beam(BeamType.BEGIN).build());
			notes.add(note().pos(beat2).len(beat4).beam(BeamType.CONTINUE_BEGIN).build());
			notes.add(note().pos(beat2 + beat4).len(beat4).beam(BeamType.END_END).build());
		}else if (beat == DurationConstants.HALF){
			notes.add(note().pos(0).len(beat2).build());
			notes.add(note().pos(beat2).len(beat4).beam(BeamType.BEGIN).build());
			notes.add(note().pos(beat2 + beat4).len(beat4).beam(BeamType.END).build());
		}else{
			notes.add(note().pos(0).len(beat2).build());
			notes.add(note().pos(beat2).len(beat4).build());
			notes.add(note().pos(beat2 + beat4).len(beat4).build());
		}
		return notes;
	}
	
	public List<Note> pos234(int beat) {
		List<Note> notes = new ArrayList<Note>();
		int beat4 = beat/4;
		if (beat == DurationConstants.QUARTER) {
			notes.add(note().pos(0).rest().len(beat4).build());
			notes.add(note().pos(beat4).len(beat4).beam(BeamType.BEGIN_BEGIN).build());
			notes.add(note().pos(2 * beat4).len(beat4).beam(BeamType.CONTINUE_CONTINUE).build());
			notes.add(note().pos(3 * beat4).len(beat4).beam(BeamType.END_END).build());
		}else if (beat == DurationConstants.HALF){
			notes.add(note().pos(0).rest().len(beat4).build());
			notes.add(note().pos(beat4).len(beat4).build());
			notes.add(note().pos(2 * beat4).len(beat4).beam(BeamType.BEGIN).build());
			notes.add(note().pos(3 * beat4).len(beat4).beam(BeamType.END).build());
		}else{
			notes.add(note().pos(0).rest().len(beat4).build());
			notes.add(note().pos(beat4).len(beat4).build());
			notes.add(note().pos(2 * beat4).len(beat4).build());
			notes.add(note().pos(3 * beat4).len(beat4).build());
		}
		return notes;
	}
	
	public List<Note> pos124(int beat) {
		List<Note> notes = new ArrayList<Note>();
		int beat2 = beat/2;
		int beat4 = beat/4;
		if (beat == DurationConstants.QUARTER) {
			notes.add(note().pos(0).len(beat4).beam(BeamType.BEGIN).build());
			notes.add(note().pos(beat4).len(beat2).beam(BeamType.CONTINUE).build());
			notes.add(note().pos(beat2 + beat4).len(beat4).beam(BeamType.END).build());
		} else {
			notes.add(note().pos(0).len(beat4).build());
			notes.add(note().pos(beat4).len(beat2).build());
			notes.add(note().pos(beat2 + beat4).len(beat4).build());
		}
		return notes;
	}
	
	
	public static void main(String[] args) {
		ThreeNoteEven threeNoteEven = new ThreeNoteEven();
		List<Note > notes = threeNoteEven.pos123(DurationConstants.HALF);
		notes.forEach(n -> System.out.println(n.getPosition() + ", " + n.getLength()));
		
		notes = threeNoteEven.pos124(DurationConstants.HALF);
		notes.forEach(n -> System.out.println(n.getPosition() + ", " + n.getLength()));
		
		notes = threeNoteEven.pos134(DurationConstants.HALF);
		notes.forEach(n -> System.out.println(n.getPosition() + ", " + n.getLength()));
		
		notes = threeNoteEven.pos234(DurationConstants.HALF);
		notes.forEach(n -> System.out.println(n.getPosition() + ", " + n.getLength()));
	}

}
