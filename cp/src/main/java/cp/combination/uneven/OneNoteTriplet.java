package cp.combination.uneven;

import cp.model.note.Note;
import cp.model.note.TupletType;
import cp.model.rhythm.DurationConstants;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

import static cp.model.note.NoteBuilder.note;

@Component
public class OneNoteTriplet {

	public List<Note> pos1(int beat) {
		List<Note> notes = new ArrayList<>();
		notes.add(note().pos(0).len(beat).build());
		return notes;
	}
	
	public List<Note> pos2(int beat) {
		List<Note> notes;
		int noteLength = beat/3;
		int noteLength2 = noteLength * 2;
		switch (beat) {
			case DurationConstants.QUARTER:
				notes =  posRestTuplet(noteLength, noteLength2);
				notes.forEach(n -> {n.setTriplet(true);
									n.setTimeModification("eighth");
									n.setBracket(true);});
				return notes;
			case DurationConstants.HALF:
				notes =  posRestTuplet(noteLength, noteLength2);
				notes.forEach(n -> {n.setTriplet(true);
									n.setTimeModification("quarter");
									n.setBracket(true);});
				return notes;
			case DurationConstants.THREE_EIGHTS:
				return posRest(noteLength, noteLength2);
			case DurationConstants.THREE_QUARTERS:
				return posRest(noteLength, noteLength2);
			default:
				return posRest(noteLength, noteLength2);
		}
	}
	
	public List<Note> pos3(int beat) {
		List<Note> notes;
		int noteLength = beat/3;
		int noteLength2 = noteLength * 2;
		switch (beat) {
			case DurationConstants.QUARTER:
				notes =  posRestTuplet(noteLength2, noteLength);
				notes.forEach(n -> {n.setTriplet(true);
									n.setTimeModification("eighth");
									n.setBracket(true);});
				return notes;
			case DurationConstants.HALF:
				notes =  posRestTuplet(noteLength2, noteLength);
				notes.forEach(n -> {n.setTriplet(true);
									n.setTimeModification("quarter");
									n.setBracket(true);});
				return notes;
			case DurationConstants.THREE_EIGHTS:
				notes = posRest(noteLength2, noteLength);
				return notes;
			case DurationConstants.THREE_QUARTERS:
				return posRest(noteLength2, noteLength);
			default:
				return posRest(noteLength2, noteLength);
		}
	}
	
	private List<Note> posRest(int firstLength, int secondLength){
		List<Note> notes = new ArrayList<>();
		notes.add(note().pos(0).rest().len(firstLength).build());
		notes.add(note().pos(firstLength).len(secondLength).build());
		return notes;
	}
	
	private List<Note> posRestTuplet(int firstLength, int secondLength){
		List<Note> notes = new ArrayList<>();
		notes.add(note().pos(0).rest().len(firstLength).tuplet(TupletType.START).build());
		notes.add(note().pos(firstLength).len(secondLength).tuplet(TupletType.STOP).build());
		return notes;
	}
	
	public static void main(String[] args) {
		OneNoteTriplet oneNoteUneven = new OneNoteTriplet();
		List<Note > notes = oneNoteUneven.pos1(DurationConstants.THREE_QUARTERS);
		notes.forEach(n -> System.out.println(n.getPosition() + ", " + n.getLength()));

		notes = oneNoteUneven.pos1(DurationConstants.THREE_QUARTERS);
		notes.forEach(n -> System.out.println(n.getPosition() + ", " + n.getLength()));

		notes = oneNoteUneven.pos2(DurationConstants.HALF);
		notes.forEach(n -> System.out.println(n.getPosition() + ", " + n.getLength()));

		notes = oneNoteUneven.pos3(DurationConstants.HALF);
		notes.forEach(n -> System.out.println(n.getPosition() + ", " + n.getLength()));
	}

}
