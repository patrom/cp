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
public class FourNoteSexTuplet {

	public List<Note> pos1456(int beat) {
		List<Note> notes;
		int noteLength = beat/6;
		int length3 = noteLength * 3;
		switch (beat) {
		case DurationConstants.QUARTER:
			notes =  posWithBeam(length3, noteLength, noteLength, noteLength);
			notes.forEach(n -> {n.setSextuplet(true);
								n.setTimeModification("16th");});
			return notes;
//		case 24:
//			notes =  pos(beat/3);
//			notes.forEach(n -> n.setTriplet(true));
//			return notes;
		case DurationConstants.THREE_EIGHTS:
			return posWithBeam(length3, noteLength, noteLength, noteLength);
		default:
			return  pos(length3, noteLength, noteLength, noteLength);
		}
	}
	
	public List<Note> pos1345(int beat) {
		List<Note> notes;
		int noteLength = beat/6;
		int length2 = noteLength * 2;
		switch (beat) {
		case DurationConstants.QUARTER:
			notes =  posWithBeam(length2, noteLength, noteLength, length2);
			notes.forEach(n -> n.setSextuplet(true));
			return notes;
//		case 24:
//			notes =  pos(beat/3);
//			notes.forEach(n -> n.setTriplet(true));
//			return notes;
		case DurationConstants.THREE_EIGHTS:
			return posWithBeam(length2, noteLength, noteLength, length2);
		default:
			return  pos(length2, noteLength, noteLength, length2);
		}
	}

	public List<Note> pos1346(int beat) {
		List<Note> notes;
		int noteLength = beat/6;
		int length2 = noteLength * 2;
		switch (beat) {
			case DurationConstants.QUARTER:
				notes =  posWithBeam(length2, noteLength, length2, noteLength);
				notes.forEach(n -> n.setSextuplet(true));
				return notes;
//		case 24:
//			notes =  pos(beat/3);
//			notes.forEach(n -> n.setTriplet(true));
//			return notes;
			case DurationConstants.THREE_EIGHTS:
				return posWithBeam(length2, noteLength, length2, noteLength);
			default:
				return  pos(length2, noteLength, length2, noteLength);
		}
	}
	
	public List<Note> pos1356(int beat) {
		List<Note> notes;
		int noteLength = beat/6;
		int length2 = noteLength * 2;
		switch (beat) {
		case DurationConstants.QUARTER:
			notes =  posWithBeam(length2, length2, noteLength, noteLength);
			notes.forEach(n -> n.setSextuplet(true));
			return notes;
//		case 24:
//			notes =  pos(beat/3);
//			notes.forEach(n -> n.setTriplet(true));
//			return notes;
		case DurationConstants.THREE_EIGHTS:
			return  posWithBeam(length2, length2, noteLength, noteLength);
		default:
			return  pos(length2, length2, noteLength, noteLength);
		}
	}

	public List<Note> pos1245(int beat) {
		List<Note> notes;
		int noteLength = beat/6;
		int length2 = noteLength * 2;
		switch (beat) {
			case DurationConstants.QUARTER:
				notes =  posWithBeam(noteLength, length2, noteLength, length2);
				notes.forEach(n -> n.setSextuplet(true));
				return notes;
			case DurationConstants.THREE_EIGHTS:
				return  posWithBeam(noteLength, length2, noteLength, length2);
			default:
				return  posWithBeam(noteLength, length2, noteLength, length2);
		}
	}
	
	
	private List<Note> posWithBeam(int first, int second, int third, int fourth){
		List<Note> notes = new ArrayList<>();
		notes.add(note().pos(0).len(first).beam(BeamType.BEGIN).tuplet(TupletType.START).build());
		notes.add(note().pos(first).len(second).beam(BeamType.CONTINUE).build());
		notes.add(note().pos(first + second).len(third).beam(BeamType.CONTINUE).build());
		notes.add(note().pos(first + second + third).len(fourth).beam(BeamType.END).tuplet(TupletType.STOP).build());
		return notes;
	}
	
	private List<Note> pos(int first, int second, int third, int fourth){
		List<Note> notes = new ArrayList<>();
		notes.add(note().pos(0).len(first).build());
		notes.add(note().pos(first).len(second).build());
		notes.add(note().pos(first + second).len(third).build());
		notes.add(note().pos(first + second + third).len(fourth).build());
		return notes;
	}
	
	public static void main(String[] args) {
		FourNoteSexTuplet fourNoteSexTuplet = new FourNoteSexTuplet();
//		List<Note > notes = fourNoteSexTuplet.pos1456(DurationConstants.THREE_EIGHTS);
//		notes.forEach(n -> System.out.println(n.getPosition() + ", " + n.getLength()));
//
		List<Note> notes = fourNoteSexTuplet.pos1346(DurationConstants.SIX_EIGHTS);
		notes.forEach(n -> System.out.println(n.getPosition() + ", " + n.getLength()));
		
		notes = fourNoteSexTuplet.pos1356(DurationConstants.SIX_EIGHTS);
		notes.forEach(n -> System.out.println(n.getPosition() + ", " + n.getLength()));
	}
}


