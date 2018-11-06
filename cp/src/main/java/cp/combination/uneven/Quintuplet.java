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
public class Quintuplet {

	public List<Note> pos12345(int beat) {
		List<Note> notes = new ArrayList<>();
		int noteLength = beat/5;
		switch (beat) {
			case DurationConstants.QUARTER:
				notes =  posWithBeam(noteLength);
				notes.forEach(n -> {n.setQuintuplet(true);
									n.setTimeModification("16th");});
//			case DurationConstants.THREE_EIGHTS:
//				notes =  posWithBeam(noteLength, noteLength, noteLength, noteLength, noteLength);
//				notes.forEach(n -> n.setQuintuplet(true));//TODO check 5:3 ??
                break;
			case DurationConstants.HALF:
				notes =  posWithBeamHalf(noteLength);
				notes.forEach(n -> {n.setQuintuplet(true);
									n.setTimeModification("eighth");});
		}
		return notes;
	}
	
	private List<Note> posWithBeam(int length){
		List<Note> notes = new ArrayList<>();
		notes.add(note().pos(0).len(length).beam(BeamType.BEGIN_BEGIN).tuplet(TupletType.START).build());
		notes.add(note().pos(length).len(length).beam(BeamType.CONTINUE_CONTINUE).build());
		notes.add(note().pos(length + length).len(length).beam(BeamType.CONTINUE_CONTINUE).build());
		notes.add(note().pos(length + length + length).len(length).beam(BeamType.CONTINUE_CONTINUE).build());
		notes.add(note().pos(length + length + length + length).len(length).beam(BeamType.END_END).tuplet(TupletType.STOP).build());
		return notes;
	}
	
	private List<Note> posWithBeamHalf(int length){
		List<Note> notes = new ArrayList<>();
		notes.add(note().pos(0).len(length).beam(BeamType.BEGIN).tuplet(TupletType.START).build());
		notes.add(note().pos(length).len(length).beam(BeamType.CONTINUE).build());
		notes.add(note().pos(length + length).len(length).beam(BeamType.CONTINUE).build());
		notes.add(note().pos(length + length + length).len(length).beam(BeamType.CONTINUE).build());
		notes.add(note().pos(length + length + length + length).len(length).beam(BeamType.END).tuplet(TupletType.STOP).build());
		return notes;
	}

	public List<Note> pos2345(int beat) {
		List<Note> notes = new ArrayList<>();
		int noteLength = beat/5;
		switch (beat) {
			case DurationConstants.QUARTER:
				notes =  posWithBeamRest(noteLength);
				notes.forEach(n -> {n.setQuintuplet(true);
					n.setTimeModification("16th");});
//			case DurationConstants.THREE_EIGHTS:
//				notes =  posWithBeam(noteLength, noteLength, noteLength, noteLength, noteLength);
//				notes.forEach(n -> n.setQuintuplet(true));//TODO check 5:3 ??
                break;
			case DurationConstants.HALF:
				notes =  posWithBeamHalfRest(noteLength);
				notes.forEach(n -> {n.setQuintuplet(true);
					n.setTimeModification("eighth");});
		}
		return notes;
	}

	private List<Note> posWithBeamRest(int length){
		List<Note> notes = new ArrayList<>();
		notes.add(note().pos(0).rest().len(length).beam(BeamType.BEGIN_BEGIN).tuplet(TupletType.START).build());
		notes.add(note().pos(length).len(length).beam(BeamType.CONTINUE_CONTINUE).build());
		notes.add(note().pos(length + length).len(length).beam(BeamType.CONTINUE_CONTINUE).build());
		notes.add(note().pos(length + length + length).len(length).beam(BeamType.CONTINUE_CONTINUE).build());
		notes.add(note().pos(length + length + length + length).len(length).beam(BeamType.END_END).tuplet(TupletType.STOP).build());
		return notes;
	}

	private List<Note> posWithBeamHalfRest(int length){
		List<Note> notes = new ArrayList<>();
		notes.add(note().rest().pos(0).len(length).beam(BeamType.BEGIN).tuplet(TupletType.START).build());
		notes.add(note().pos(length).len(length).beam(BeamType.CONTINUE).build());
		notes.add(note().pos(length + length).len(length).beam(BeamType.CONTINUE).build());
		notes.add(note().pos(length + length + length).len(length).beam(BeamType.CONTINUE).build());
		notes.add(note().pos(length + length + length + length).len(length).beam(BeamType.END).tuplet(TupletType.STOP).build());
		return notes;
	}
	
	public static void main(String[] args) {
		Quintuplet quinTuplet = new Quintuplet();
		List<Note > notes = quinTuplet.pos12345(DurationConstants.QUARTER);
		notes.forEach(n -> System.out.println(n.getPosition() + ", " + n.getLength()));
	}
}


