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

	public List<Note> pos145(int beat, int pulse) {
		List<Note> notes;
		int noteLength = beat / 6;
		int length3 = noteLength * 3;
		int length2 = noteLength * 2;
		switch (beat) {
			case DurationConstants.QUARTER:
				notes = posWithBeam(length3, noteLength, length2);
				notes.forEach(n -> {
					n.setSextuplet(true);
					n.setTimeModification("16th");
				});
				return notes;
			case DurationConstants.HALF:
				notes = posWithBeam(length3, noteLength, length2);
				notes.forEach(n -> {
					n.setSextuplet(true);
					n.setTimeModification("eighth");
				});
				return notes;
			case DurationConstants.THREE_EIGHTS:
				return posWithBeam(length3, noteLength, length2);
			case DurationConstants.THREE_QUARTERS:
				return pos(length3, noteLength, length2);
			default:
				return pos(length3, noteLength, length2);
		}
	}

	public List<Note> pos136(int beat, int pulse) {
		List<Note> notes;
		int noteLength = beat / 6;
		int length3 = noteLength * 3;
		int length2 = noteLength * 2;
		switch (beat) {
			case DurationConstants.QUARTER:
				notes = posWithBeam(length2, length3, noteLength);
				notes.forEach(n -> {
					n.setSextuplet(true);
					n.setTimeModification("16th");
				});
				return notes;
			case DurationConstants.HALF:
				notes = posWithBeam(length2, length3, noteLength);
				notes.forEach(n -> {
					n.setSextuplet(true);
					n.setTimeModification("eighth");
				});
				return notes;
			case DurationConstants.THREE_EIGHTS:
				return posWithBeam(length2, length3, noteLength);
			case DurationConstants.THREE_QUARTERS:
				return pos(length2, length3, noteLength);
			default:
				notes = pos(length2, length3, noteLength);
				return notes;
		}
	}

	public List<Note> pos156(int beat, int pulse) {
		List<Note> notes;
		int noteLength = beat / 6;
		int length4 = noteLength * 4;
		switch (beat) {
			case DurationConstants.QUARTER:
				notes = posWithBeam(length4, noteLength, noteLength);
				notes.forEach(n -> {
					n.setSextuplet(true);
					n.setTimeModification("16th");
				});
				return notes;
			case DurationConstants.HALF:
				notes = posWithBeam(length4, noteLength, noteLength);
				notes.forEach(n -> {
					n.setSextuplet(true);
					n.setTimeModification("eighth");
				});
				return notes;
			case DurationConstants.THREE_EIGHTS:
				return posWithBeam(length4, noteLength, noteLength);
			case DurationConstants.THREE_QUARTERS:
				return pos(length4, noteLength, noteLength);
			default:
				return pos(length4, noteLength, noteLength);
		}
	}

	public List<Note> pos123(int beat, int pulse) {
		List<Note> notes;
		int noteLength = beat / 6;
		int length4 = noteLength * 4;
		switch (beat) {
			case DurationConstants.QUARTER:
				notes = posWithBeam(noteLength, noteLength,length4);
				notes.forEach(n -> {
					n.setSextuplet(true);
					n.setTimeModification("16th");
				});
				return notes;
			case DurationConstants.HALF:
				notes = posWithBeam(noteLength, noteLength, length4);
				notes.forEach(n -> {
					n.setSextuplet(true);
					n.setTimeModification("eighth");
				});
				return notes;
			case DurationConstants.THREE_EIGHTS:
				return posWithBeam(noteLength, noteLength,length4);
			case DurationConstants.THREE_QUARTERS:
				return posWithBeam(noteLength, noteLength,length4);
			default:
				return posWithBeam(noteLength, noteLength,length4);
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
		List<Note > notes = threeNoteSexTuplet.pos145(DurationConstants.HALF, DurationConstants.QUARTER);
		notes.forEach(n -> System.out.println(n.getPosition() + ", " + n.getLength()));
		
//		notes = threeNoteSexTuplet.pos145(DurationConstants.SIX_EIGHTS);
//		notes.forEach(n -> System.out.println(n.getPosition() + ", " + n.getLength()));
//		
//		notes = threeNoteSexTuplet.pos136(DurationConstants.THREE_EIGHTS);
//		notes.forEach(n -> System.out.println(n.getPosition() + ", " + n.getLength()));
//		
//		notes = threeNoteSexTuplet.pos136(DurationConstants.SIX_EIGHTS);
//		notes.forEach(n -> System.out.println(n.getPosition() + ", " + n.getLength()));
		
		notes = threeNoteSexTuplet.pos156(DurationConstants.HALF, DurationConstants.QUARTER);
		notes.forEach(n -> System.out.println(n.getPosition() + ", " + n.getLength()));
	}
}

