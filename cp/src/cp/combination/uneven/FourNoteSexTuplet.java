package cp.combination.uneven;

import static cp.model.note.NoteBuilder.note;

import java.util.ArrayList;
import java.util.List;

import cp.model.note.BeamType;
import cp.model.note.Note;

public class FourNoteSexTuplet {

	public List<Note> pos1456(int beat) {
		List<Note> notes;
		int noteLength = beat/6;
		int length3 = noteLength * 3;
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
			notes =  posWithBeam(length3, noteLength, noteLength, noteLength);
			return notes;
		default:
			notes =  pos(length3, noteLength, noteLength, noteLength);
			return notes;
		}
	}
	
	public List<Note> pos1346(int beat) {
		List<Note> notes;
		int noteLength = beat/6;
		int length2 = noteLength * 2;
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
			notes =  posWithBeam(length2, noteLength, noteLength, length2);
			return notes;
		default:
			notes =  pos(length2, noteLength, noteLength, length2);
			return notes;
		}
	}
	
	public List<Note> pos1356(int beat) {
		List<Note> notes;
		int noteLength = beat/6;
		int length2 = noteLength * 2;
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
			notes =  posWithBeam(length2, length2, noteLength, noteLength);
			return notes;
		default:
			notes =  pos(length2, length2, noteLength, noteLength);
			return notes;
		}
	}
	
	
	private List<Note> posWithBeam(int first, int second, int third, int fourth){
		List<Note> notes = new ArrayList<Note>();
		notes.add(note().pos(0).len(first).beam(BeamType.BEGIN).build());
		notes.add(note().pos(first).len(second).beam(BeamType.CONTINUE).build());
		notes.add(note().pos(first + second).len(third).beam(BeamType.CONTINUE).build());
		notes.add(note().pos(first + second + third).len(fourth).beam(BeamType.END).build());
		return notes;
	}
	
	private List<Note> pos(int first, int second, int third, int fourth){
		List<Note> notes = new ArrayList<Note>();
		notes.add(note().pos(0).len(first).build());
		notes.add(note().pos(first).len(second).build());
		notes.add(note().pos(first + second).len(third).build());
		notes.add(note().pos(first + second + third).len(fourth).build());
		return notes;
	}
	
	public static void main(String[] args) {
		FourNoteSexTuplet fourNoteSexTuplet = new FourNoteSexTuplet();
		List<Note > notes = fourNoteSexTuplet.pos1456(18);
		notes.forEach(n -> System.out.println(n.getPosition() + ", " + n.getLength()));
		
		notes = fourNoteSexTuplet.pos1346(36);
		notes.forEach(n -> System.out.println(n.getPosition() + ", " + n.getLength()));
		
		notes = fourNoteSexTuplet.pos1356(36);
		notes.forEach(n -> System.out.println(n.getPosition() + ", " + n.getLength()));
	}
}


