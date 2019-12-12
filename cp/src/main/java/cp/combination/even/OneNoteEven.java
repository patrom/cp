package cp.combination.even;

import cp.model.note.Note;
import cp.model.rhythm.DurationConstants;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

import static cp.model.note.NoteBuilder.note;

@Component
public class OneNoteEven{
	
	public List<Note> pos1(int beat, int pulse) {
		List<Note> notes = new ArrayList<>();
		notes.add(note().pos(0).len(beat).build());
		return notes;
	}
	
	public List<Note> pos2(int beat, int pulse) {
		return posRest(30 * 4, beat);
	}
	
	public List<Note> pos3(int beat, int pulse) {
		return posRest(30 * 2, beat);
	}
	
	public List<Note> pos4(int beat, int pulse) {
		return posRest(3*beat/4, beat);
	}
	
	private List<Note> posRest(int noteLength, int length){
		List<Note> notes = new ArrayList<>();
		notes.add(note().pos(0).rest().len(noteLength).build());
		notes.add(note().pos(noteLength).len(length - noteLength).build());
		return notes;
	}
	
	public static void main(String[] args) {
		OneNoteEven oneNoteEven = new OneNoteEven();
		List<Note > notes = oneNoteEven.pos2(24, DurationConstants.QUARTER);
		notes.forEach(n -> System.out.println(n.getPosition() + ", " + n.getLength()));
	}

	public List<Note> rest(int beat, int pulse) {
		List<Note> notes = new ArrayList<>();
		notes.add(note().pos(0).rest().len(beat).build());
		return notes;
	}

}
