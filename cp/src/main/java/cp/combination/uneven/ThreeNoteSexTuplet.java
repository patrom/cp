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
public class ThreeNoteSexTuplet {

	public List<Note> pos145(int beat) {
		List<Note> notes;
		int noteLength = beat/6;
		int length3 = noteLength * 3;
		int length2 = noteLength * 2;
		switch (beat) {
		case 12:
			notes =  posWithBeam(length3, noteLength, length2);
			notes.forEach(n -> {n.setSextuplet(true);
								n.setTimeModification("16th");});
			return notes;
//		case 24:
//			notes =  pos(beat/3);
//			notes.forEach(n -> n.setTriplet(true));
//			return notes;
		case DurationConstants.THREE_EIGHTS:
			notes =  posWithBeam(length3, noteLength, length2);
			return notes;
		default:
			notes =  pos(length3, noteLength, length2);
			return notes;
		}
	}
	
	public List<Note> pos136(int beat) {
		List<Note> notes;
		int noteLength = beat/6;
		int length3 = noteLength * 3;
		int length2 = noteLength * 2;
		switch (beat) {
		case 12:
			notes =  posWithBeam(length2, length3, noteLength);
			notes.forEach(n -> n.setSextuplet(true));
			return notes;
//		case 24:
//			notes =  pos(beat/3);
//			notes.forEach(n -> n.setTriplet(true));
//			return notes;
		case DurationConstants.THREE_EIGHTS:
			notes =  posWithBeam(length2, length3, noteLength);
			return notes;
		default:
			notes =  pos(length2, length3, noteLength);
			return notes;
		}
	}
	
	public List<Note> pos156(int beat) {
		List<Note> notes;
		int noteLength = beat/6;
		int length4 = noteLength * 4;
		switch (beat) {
		case 12:
			notes =  posWithBeam(length4, noteLength, noteLength);
			notes.forEach(n -> n.setSextuplet(true));
			return notes;
//		case 24:
//			notes =  pos(beat/3);
//			notes.forEach(n -> n.setTriplet(true));
//			return notes;
		case DurationConstants.THREE_EIGHTS:
			notes =  posWithBeam(length4, noteLength, noteLength);
			return notes;
		default:
			notes =  pos(length4, noteLength, noteLength);
			return notes;
		}
	}
	
	private List<Note> posWithBeam(int first, int second, int third){
		List<Note> notes = new ArrayList<>();
		notes.add(note().pos(0).len(first).beam(BeamType.BEGIN).tuplet(TupletType.START).build());
		notes.add(note().pos(first).len(second).beam(BeamType.CONTINUE).build());
		notes.add(note().pos(first + second).len(third).beam(BeamType.END).tuplet(TupletType.STOP).build());
		return notes;
	}
	
	private List<Note> pos(int first, int second, int third){
		List<Note> notes = new ArrayList<>();
		notes.add(note().pos(0).len(first).build());
		notes.add(note().pos(first).len(second).build());
		notes.add(note().pos(first + second).len(third).build());
		return notes;
	}
	
	public static void main(String[] args) {
		ThreeNoteSexTuplet threeNoteSexTuplet = new ThreeNoteSexTuplet();
		List<Note > notes = threeNoteSexTuplet.pos145(DurationConstants.THREE_EIGHTS);
		notes.forEach(n -> System.out.println(n.getPosition() + ", " + n.getLength()));
		
//		notes = threeNoteSexTuplet.pos145(DurationConstants.SIX_EIGHTS);
//		notes.forEach(n -> System.out.println(n.getPosition() + ", " + n.getLength()));
//		
//		notes = threeNoteSexTuplet.pos136(DurationConstants.THREE_EIGHTS);
//		notes.forEach(n -> System.out.println(n.getPosition() + ", " + n.getLength()));
//		
//		notes = threeNoteSexTuplet.pos136(DurationConstants.SIX_EIGHTS);
//		notes.forEach(n -> System.out.println(n.getPosition() + ", " + n.getLength()));
		
		notes = threeNoteSexTuplet.pos156(DurationConstants.SIX_EIGHTS);
		notes.forEach(n -> System.out.println(n.getPosition() + ", " + n.getLength()));
	}
}

