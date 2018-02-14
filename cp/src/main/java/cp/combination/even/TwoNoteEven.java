package cp.combination.even;

import cp.model.note.BeamType;
import cp.model.note.Note;
import cp.model.rhythm.DurationConstants;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

import static cp.model.note.NoteBuilder.note;

@Component
public class TwoNoteEven {

	public List<Note> pos12(int beat) {
		if (beat == DurationConstants.QUARTER) {
			return posWithBeam(beat/4, beat);
		} else {
			return pos(beat/4, beat);
		}
	}
	
	public List<Note> pos13(int beat) {
		if (beat == DurationConstants.QUARTER) {
			return posWithBeam(beat/2, beat);
		} else {
			return pos(beat/2, beat);
		}
	}
	
	public List<Note> pos14(int beat) {
		if (beat == DurationConstants.QUARTER) {
			return posWithBeam(3*beat/4, beat);
		} else {
			return pos(3*beat/4, beat);
		}
	}
	
	public List<Note> pos34(int beat) {
		List<Note> notes = new ArrayList<>();
		int beat2 = beat/2;
		int beat4 = beat/4;
		if (beat == DurationConstants.QUARTER) {
			notes.add(note().pos(0).rest().len(beat2).build());
			notes.add(note().pos(beat2).len(beat4).beam(BeamType.BEGIN_BEGIN).build());
			notes.add(note().pos(beat2 + beat4).len(beat4).beam(BeamType.END_END).build());
		}else if (beat == DurationConstants.HALF){
			notes.add(note().pos(0).rest().len(beat2).build());
			notes.add(note().pos(beat2).len(beat4).beam(BeamType.BEGIN).build());
			notes.add(note().pos(beat2 + beat4).len(beat4).beam(BeamType.END).build());
		}else{
			notes.add(note().pos(0).rest().len(beat2).build());
			notes.add(note().pos(beat2).len(beat4).build());
			notes.add(note().pos(beat2 + beat4).len(beat4).build());
		}
		return notes;
	}
	
	public List<Note> pos23(int beat) {
		List<Note> notes = new ArrayList<>();
		int beat2 = beat/2;
		int beat4 = beat/4;
		notes.add(note().pos(0).rest().len(beat4).build());
		notes.add(note().pos(beat4).len(beat4).build());
		notes.add(note().pos(beat2).len(beat2).build());
		return notes;
	}
	
	public List<Note> pos24(int beat) {
		List<Note> notes = new ArrayList<>();
		int beat2 = beat/2;
		int beat4 = beat/4;
		if (beat == DurationConstants.QUARTER) {
			notes.add(note().pos(0).rest().len(beat4).build());
			notes.add(note().pos(beat4).len(beat2).beam(BeamType.BEGIN).build());
			notes.add(note().pos(beat2 + beat4).len(beat4).beam(BeamType.END).build());
		}else{
			notes.add(note().pos(0).rest().len(beat4).build());
			notes.add(note().pos(beat4).len(beat2).build());
			notes.add(note().pos(beat2 + beat4).len(beat4).build());
		}
		return notes;
	}
	
	private List<Note> pos(int noteLength, int length){
		List<Note> notes = new ArrayList<>();
		notes.add(note().pos(0).len(noteLength).build());
		notes.add(note().pos(noteLength).len(length - noteLength).build());
		return notes;
	}
	
	private List<Note> posWithBeam(int noteLength, int length){
		List<Note> notes = new ArrayList<>();
		notes.add(note().pos(0).len(noteLength).beam(BeamType.BEGIN).build());
		notes.add(note().pos(noteLength).len(length - noteLength).beam(BeamType.END).build());
		return notes;
	}
	
	public static void main(String[] args) {
		TwoNoteEven twoNoteEven = new TwoNoteEven();
		List<Note > notes = twoNoteEven.pos13(DurationConstants.QUARTER);
		notes.forEach(n -> System.out.println(n.getPosition() + ", " + n.getLength() + ", " + n.isRest()));
	}

}
