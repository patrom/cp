package cp.combination.uneven;

import cp.model.note.BeamType;
import cp.model.note.Note;
import cp.model.note.TupletType;
import cp.model.rhythm.DurationConstants;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

import static cp.model.note.NoteBuilder.note;

@Component
public class TwoNoteTriplet {
	
	public List<Note> pos13(int beat) {
		List<Note> notes = new ArrayList<>();
		int noteLength = beat/3;
		int noteLength2 = noteLength * 2;
		switch (beat) {
			case DurationConstants.QUARTER:
				notes =  pos(noteLength2, noteLength);
				notes.forEach(n -> {n.setTriplet(true);
									n.setTimeModification("eighth");
									n.setBracket(true);});
				return notes;
			case DurationConstants.HALF:
				notes =  pos(noteLength2, noteLength);
				notes.forEach(n -> {n.setTriplet(true);
									n.setTimeModification("quarter");
									n.setBracket(true);});
				return notes;
			case DurationConstants.THREE_EIGHTS:
				return posWithBeam(noteLength2, noteLength);
			case DurationConstants.THREE_QUARTERS:
				return  pos(noteLength2, noteLength);
			default:
				return notes;
		}
	}
	
	public List<Note> pos12(int beat) {
		List<Note> notes;
		int noteLength = beat/3;
		int noteLength2 = noteLength * 2;
		switch (beat) {
			case DurationConstants.QUARTER:
				notes =  pos(noteLength, noteLength2);
				notes.forEach(n -> {n.setTriplet(true);
									n.setTimeModification("eighth");
									n.setBracket(true);});
				return notes;
			case DurationConstants.HALF:
				notes =  pos(noteLength, noteLength2);
				notes.forEach(n -> {n.setTriplet(true);
									n.setTimeModification("quarter");
									n.setBracket(true);});
				return notes;
			case DurationConstants.THREE_EIGHTS:
				return  posWithBeam(noteLength, noteLength2);
			case DurationConstants.THREE_QUARTERS:
				return  pos(noteLength, noteLength2);
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
			case DurationConstants.QUARTER:
				notes =  posWithBeamStartRestTriplet(noteLength, noteLength2);
				notes.forEach(n -> {n.setTriplet(true);
									n.setTimeModification("eighth");
									n.setBracket(true);});
				return notes;
			case DurationConstants.HALF:
				notes =  posWithBracketStartRestTriplet(noteLength, noteLength2);
				notes =  pos(noteLength, noteLength2);
				notes.forEach(n -> {n.setTriplet(true);
									n.setTimeModification("quarter");
									n.setBracket(true);});
				return notes;
			case DurationConstants.THREE_EIGHTS:
				return  posWithBeamStartRest(noteLength, noteLength2);
			case DurationConstants.THREE_QUARTERS:
				return  posWithBeamStartRestTriplet(noteLength, noteLength2);
			default:
				notes =  posStartRest(noteLength, noteLength2);
				return notes;
		}
	}
	
	private List<Note> posWithBracketStartRestTriplet(int firstLength, int secondLength){
		List<Note> notes = new ArrayList<>();
		notes.add(note().pos(0).len(firstLength).rest().tuplet(TupletType.START).build());
		notes.add(note().pos(firstLength).len(firstLength).build());
		notes.add(note().pos(secondLength).len(firstLength).tuplet(TupletType.STOP).build());
		return notes;
	}
	
	private List<Note> posWithBeamStartRest(int firstLength, int secondLength){
		List<Note> notes = new ArrayList<>();
		notes.add(note().pos(0).len(firstLength).rest().build());
		notes.add(note().pos(firstLength).len(firstLength).beam(BeamType.BEGIN).build());
		notes.add(note().pos(secondLength).len(firstLength).beam(BeamType.END).build());
		return notes;
	}
	
	private List<Note> posWithBeamStartRestTriplet(int firstLength, int secondLength){
		List<Note> notes = new ArrayList<>();
		notes.add(note().pos(0).len(firstLength).rest().tuplet(TupletType.START).build());
		notes.add(note().pos(firstLength).len(firstLength).beam(BeamType.BEGIN).build());
		notes.add(note().pos(secondLength).len(firstLength).beam(BeamType.END).tuplet(TupletType.STOP).build());
		return notes;
	}
	
	private List<Note> posStartRest(int firstLength, int secondLength){
		List<Note> notes = new ArrayList<>();
		notes.add(note().pos(0).len(firstLength).rest().build());
		notes.add(note().pos(firstLength).len(firstLength).build());
		notes.add(note().pos(secondLength).len(firstLength).build());
		return notes;
	}
	
	private List<Note> posWithBeam(int firstLength, int secondLength){
		List<Note> notes = new ArrayList<>();
		notes.add(note().pos(0).len(firstLength).beam(BeamType.BEGIN).build());
		notes.add(note().pos(firstLength).len(secondLength).beam(BeamType.END).build());
		return notes;
	}
	
//	private List<Note> posWithBeamTuplet(int firstLength, int secondLength){
//		List<Note> notes = new ArrayList<Note>();
//		notes.add(note().pos(0).len(firstLength).beam(BeamType.BEGIN).tuplet(TupletType.START).build());
//		notes.add(note().pos(firstLength).len(secondLength).beam(BeamType.END).tuplet(TupletType.STOP).build());
//		return notes;
//	}
	
	private List<Note> pos(int firstLength, int secondLength){
		List<Note> notes = new ArrayList<>();
		notes.add(note().pos(0).len(firstLength).tuplet(TupletType.START).build());
		notes.add(note().pos(firstLength).len(secondLength).tuplet(TupletType.STOP).build());
		return notes;
	}
	
	public static void main(String[] args) {
		TwoNoteTriplet twoNoteUneven = new TwoNoteTriplet();
		List<Note > notes = twoNoteUneven.pos13(DurationConstants.SIX_EIGHTS);
//		notes.forEach(n -> System.out.println(n.getPosition() + ", " + n.getLength() + ","  + n.isRest()));
		notes = twoNoteUneven.pos23(DurationConstants.THREE_QUARTERS);
//		notes.forEach(n -> System.out.println(n.getPosition() + ", " + n.getLength() + ","  + n.isRest()));
		notes = twoNoteUneven.pos23(DurationConstants.THREE_QUARTERS);
		notes.forEach(n -> System.out.println(n.getPosition() + ", " + n.getLength() + ","  + n.isRest()));
	}
}
