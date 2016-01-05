package cp.combination.uneven;

import static cp.model.note.NoteBuilder.note;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;

import cp.model.note.Note;

@Component
public class OneNoteUneven {

	public List<Note> pos1(int beat) {
		List<Note> notes = new ArrayList<Note>();
		notes.add(note().pos(0).len(beat).build());
		return notes;
	}
	
	public List<Note> pos2(int beat) {
		return posRest(beat/3, beat);
	}
	
	public List<Note> pos3(int beat) {
		return posRest((beat/3) * 2, beat);
	}
	
	private List<Note> posRest(int noteLength, int length){
		List<Note> notes = new ArrayList<Note>();
		notes.add(note().pos(0).rest().len(noteLength).build());
		notes.add(note().pos(noteLength).len(length - noteLength).build());
		return notes;
	}
	
	public static void main(String[] args) {
		OneNoteUneven oneNoteUneven = new OneNoteUneven();
		List<Note > notes = oneNoteUneven.pos1(18);
		notes.forEach(n -> System.out.println(n.getPosition() + ", " + n.getLength()));
		
		notes = oneNoteUneven.pos1(36);
		notes.forEach(n -> System.out.println(n.getPosition() + ", " + n.getLength()));
		
		notes = oneNoteUneven.pos2(36);
		notes.forEach(n -> System.out.println(n.getPosition() + ", " + n.getLength()));
		
		notes = oneNoteUneven.pos3(36);
		notes.forEach(n -> System.out.println(n.getPosition() + ", " + n.getLength()));
	}

}
