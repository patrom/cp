package cp.combination.uneven;

import static cp.model.note.NoteBuilder.note;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;

import cp.model.note.BeamType;
import cp.model.note.Note;

@Component
public class ThreeNoteSexTuplet {

	public List<Note> pos145(int beat) {
		List<Note> notes;
		switch (beat) {
//		case 12:
//			notes =  posWithBeam(beat/3);
//			notes.forEach(n -> n.setTriplet(true));
//			return notes;
//		case 24:
//			notes =  pos(beat/3);
//			notes.forEach(n -> n.setTriplet(true));
//			return notes;
		case 18:
			notes =  posWithBeam(beat/6);
			return notes;
		default:
			notes =  pos(beat/6);
			return notes;
		}
	}
	
	private List<Note> posWithBeam(int noteLength){
		List<Note> notes = new ArrayList<Note>();
		int length3 = noteLength * 3;
		int length2 = noteLength * 2;
		notes.add(note().pos(0).len(length3).beam(BeamType.BEGIN).build());
		notes.add(note().pos(length3).len(noteLength).beam(BeamType.CONTINUE).build());
		notes.add(note().pos(length3 + noteLength).len(length2).beam(BeamType.END).build());
		return notes;
	}
	
	private List<Note> pos(int noteLength){
		List<Note> notes = new ArrayList<Note>();
		int length3 = noteLength * 3;
		int length2 = noteLength * 2;
		notes.add(note().pos(0).len(length3).build());
		notes.add(note().pos(length3).len(noteLength).build());
		notes.add(note().pos(length3 + noteLength).len(length2).build());
		return notes;
	}
	
	public static void main(String[] args) {
		ThreeNoteSexTuplet threeNoteSexTuplet = new ThreeNoteSexTuplet();
		List<Note > notes = threeNoteSexTuplet.pos145(18);
		notes.forEach(n -> System.out.println(n.getPosition() + ", " + n.getLength()));
		
		notes = threeNoteSexTuplet.pos145(36);
		notes.forEach(n -> System.out.println(n.getPosition() + ", " + n.getLength()));
	}
}

