package cp.combination.uneven;

import static cp.model.note.NoteBuilder.note;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;

import cp.model.note.Note;
import cp.model.note.TupletType;

@Component
public class OneNoteTriplet {

	public List<Note> pos1(int beat) {
		List<Note> notes = new ArrayList<Note>();
		notes.add(note().pos(0).len(beat).build());
		return notes;
	}
	
	public List<Note> pos2(int beat) {
		List<Note> notes;
		int noteLength = beat/3;
		int noteLength2 = noteLength * 2;
		switch (beat) {
			case 12:
				notes =  posRestTuplet(noteLength, noteLength2);
				notes.forEach(n -> n.setTriplet(true));
				return notes;
	//		case 24:
	//			notes =  pos(beat/3);
	//			notes.forEach(n -> n.setTriplet(true));
	//			return notes;
			case 18:
				notes =  posRest(noteLength, noteLength2);
				return notes;
			default:
				notes =  posRest(noteLength, noteLength2);
				return notes;
		}
	}
	
	public List<Note> pos3(int beat) {
		List<Note> notes;
		int noteLength = beat/3;
		int noteLength2 = noteLength * 2;
		switch (beat) {
			case 12:
				notes =  posRestTuplet(noteLength2, noteLength);
				notes.forEach(n -> n.setTriplet(true));
				return notes;
	//		case 24:
	//			notes =  pos(beat/3);
	//			notes.forEach(n -> n.setTriplet(true));
	//			return notes;
			case 18:
				notes =  posRest(noteLength2, noteLength);
				return notes;
			default:
				notes =  posRest(noteLength2, noteLength);
				return notes;
		}
	}
	
	private List<Note> posRest(int firstLength, int secondLength){
		List<Note> notes = new ArrayList<Note>();
		notes.add(note().pos(0).rest().len(firstLength).build());
		notes.add(note().pos(firstLength).len(secondLength).build());
		return notes;
	}
	
	private List<Note> posRestTuplet(int firstLength, int secondLength){
		List<Note> notes = new ArrayList<Note>();
		notes.add(note().pos(0).rest().len(firstLength).tuplet(TupletType.START).build());
		notes.add(note().pos(firstLength).len(secondLength).tuplet(TupletType.STOP).build());
		return notes;
	}
	
	public static void main(String[] args) {
		OneNoteTriplet oneNoteUneven = new OneNoteTriplet();
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
