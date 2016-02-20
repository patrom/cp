package cp.combination.uneven;

import static cp.model.note.NoteBuilder.note;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;

import cp.model.note.BeamType;
import cp.model.note.Note;

@Component
public class TwoNoteUneven {
	
	public List<Note> pos13(int beat) {
		List<Note> notes;
		int noteLength = beat/3;
		int noteLength2 = noteLength * 2;
		switch (beat) {
		case 12:
			notes =  posWithBeam(noteLength2, noteLength);
			notes.forEach(n -> n.setTriplet(true));
			return notes;
//		case 24:
//			notes =  pos(beat/3);
//			notes.forEach(n -> n.setTriplet(true));
//			return notes;
		case 18:
			notes =  posWithBeam(noteLength2, noteLength);
			return notes;
		default:
			notes =  pos(noteLength2, noteLength);
			return notes;
		}
	}
	
	public List<Note> pos12(int beat) {
		List<Note> notes;
		int noteLength = beat/3;
		int noteLength2 = noteLength * 2;
		switch (beat) {
		case 12:
			notes =  posWithBeam(noteLength, noteLength2);
			notes.forEach(n -> n.setTriplet(true));
			return notes;
//		case 24:
//			notes =  pos(beat/3);
//			notes.forEach(n -> n.setTriplet(true));
//			return notes;
		case 18:
			notes =  posWithBeam(noteLength, noteLength2);
			return notes;
		default:
			notes =  pos(noteLength, noteLength2);
			return notes;
		}
	}
	
	public List<Note> pos23(int beat) {
		List<Note> notes;
		int noteLength = beat/3;
		int noteLength2 = noteLength * 2;
		switch (beat) {
		case 12:
			notes =  posWithBeamStartRest(noteLength, noteLength2);
			notes.forEach(n -> n.setTriplet(true));
			return notes;
//		case 24:
//			notes =  pos(beat/3);
//			notes.forEach(n -> n.setTriplet(true));
//			return notes;
		case 18:
			notes =  posWithBeamStartRest(noteLength, noteLength2);
			return notes;
		default:
			notes =  posStartRest(noteLength, noteLength2);
			return notes;
		}
	}
	
	private List<Note> posWithBeamStartRest(int firstLength, int secondLength){
		List<Note> notes = new ArrayList<Note>();
		notes.add(note().pos(0).len(firstLength).rest().build());
		notes.add(note().pos(firstLength).len(firstLength).beam(BeamType.BEGIN).build());
		notes.add(note().pos(secondLength).len(firstLength).beam(BeamType.END).build());
		return notes;
	}
	
	private List<Note> posStartRest(int firstLength, int secondLength){
		List<Note> notes = new ArrayList<Note>();
		notes.add(note().pos(0).len(firstLength).rest().build());
		notes.add(note().pos(firstLength).len(firstLength).build());
		notes.add(note().pos(secondLength).len(firstLength).build());
		return notes;
	}
	
	private List<Note> posWithBeam(int firstLength, int secondLength){
		List<Note> notes = new ArrayList<Note>();
		notes.add(note().pos(0).len(firstLength).beam(BeamType.BEGIN).build());
		notes.add(note().pos(firstLength).len(secondLength).beam(BeamType.END).build());
		return notes;
	}
	
	private List<Note> pos(int firstLength, int secondLength){
		List<Note> notes = new ArrayList<Note>();
		notes.add(note().pos(0).len(firstLength).build());
		notes.add(note().pos(firstLength).len(secondLength).build());
		return notes;
	}
	
	public static void main(String[] args) {
		TwoNoteUneven twoNoteUneven = new TwoNoteUneven();
		List<Note > notes = twoNoteUneven.pos13(36);
		notes.forEach(n -> System.out.println(n.getPosition() + ", " + n.getLength()));
		notes = twoNoteUneven.pos12(36);
		notes.forEach(n -> System.out.println(n.getPosition() + ", " + n.getLength()));
		notes = twoNoteUneven.pos23(18);
		notes.forEach(n -> System.out.println(n.getPosition() + ", " + n.getLength()));
	}
}
