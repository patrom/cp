package cp.combination.uneven;

import static cp.model.note.NoteBuilder.note;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;

import cp.model.note.BeamType;
import cp.model.note.Note;

@Component
public class ThreeNoteUneven {

	public List<Note> pos123(int beat) {
		List<Note> notes;
		switch (beat) {
		case 12:
			notes =  posWithBeam(beat/3);
			notes.forEach(n -> n.setTriplet(true));
			return notes;
		case 24:
			notes =  pos(beat/3);
			notes.forEach(n -> n.setTriplet(true));
			return notes;
		case 18:
			notes =  posWithBeam(beat/3);
			return notes;
		default:
			notes =  pos(beat/3);
			return notes;
		}
	}
	
	private List<Note> posWithBeam(int noteLength){
		List<Note> notes = new ArrayList<Note>();
		notes.add(note().pos(0).len(noteLength).beam(BeamType.BEGIN).build());
		notes.add(note().pos(noteLength).len(noteLength).beam(BeamType.CONTINUE).build());
		notes.add(note().pos(2 * noteLength).len(noteLength).beam(BeamType.END).build());
		return notes;
	}
	
	private List<Note> pos(int noteLength){
		List<Note> notes = new ArrayList<Note>();
		notes.add(note().pos(0).len(noteLength).build());
		notes.add(note().pos(noteLength).len(noteLength).build());
		notes.add(note().pos(2 * noteLength).len(noteLength).build());
		return notes;
	}
	
	public static void main(String[] args) {
		ThreeNoteUneven threeNoteUneven = new ThreeNoteUneven();
		List<Note > notes = threeNoteUneven.pos123(12);
		notes.forEach(n -> System.out.println(n.getPosition() + ", " + n.getLength()));
	}
}
