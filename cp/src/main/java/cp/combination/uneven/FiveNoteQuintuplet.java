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
public class FiveNoteQuintuplet {

	public List<Note> pos12345(int beat) {
		List<Note> notes = new ArrayList<>();
		int noteLength = beat/5;
		switch (beat) {
			case DurationConstants.QUARTER:
				notes =  posWithBeam(noteLength, noteLength, noteLength, noteLength, noteLength);
				notes.forEach(n -> {n.setQuintuplet(true);
									n.setTimeModification("16th");});
//			case DurationConstants.THREE_EIGHTS:
//				notes =  posWithBeam(noteLength, noteLength, noteLength, noteLength, noteLength);
//				notes.forEach(n -> n.setQuintuplet(true));//TODO check 5:3 ??
			case DurationConstants.HALF:
				notes =  posWithBeamHalf(noteLength, noteLength, noteLength, noteLength, noteLength);
				notes.forEach(n -> {n.setQuintuplet(true);
									n.setTimeModification("eighth");});
		}
		return notes;
	}
	
	private List<Note> posWithBeam(int first, int second, int third, int fourth, int fifth){
		List<Note> notes = new ArrayList<Note>();
		notes.add(note().pos(0).len(first).beam(BeamType.BEGIN_BEGIN).tuplet(TupletType.START).build());
		notes.add(note().pos(first).len(second).beam(BeamType.CONTINUE_CONTINUE).build());
		notes.add(note().pos(first + second).len(third).beam(BeamType.CONTINUE_CONTINUE).build());
		notes.add(note().pos(first + second + third).len(fourth).beam(BeamType.CONTINUE_CONTINUE).build());
		notes.add(note().pos(first + second + third + fourth).len(fifth).beam(BeamType.END_END).tuplet(TupletType.STOP).build());
		return notes;
	}
	
	private List<Note> posWithBeamHalf(int first, int second, int third, int fourth, int fifth){
		List<Note> notes = new ArrayList<Note>();
		notes.add(note().pos(0).len(first).beam(BeamType.BEGIN).tuplet(TupletType.START).build());
		notes.add(note().pos(first).len(second).beam(BeamType.CONTINUE).build());
		notes.add(note().pos(first + second).len(third).beam(BeamType.CONTINUE).build());
		notes.add(note().pos(first + second + third).len(fourth).beam(BeamType.CONTINUE).build());
		notes.add(note().pos(first + second + third + fourth).len(fifth).beam(BeamType.END).tuplet(TupletType.STOP).build());
		return notes;
	}
	
	public static void main(String[] args) {
		FiveNoteQuintuplet quinTuplet = new FiveNoteQuintuplet();
		List<Note > notes = quinTuplet.pos12345(DurationConstants.QUARTER);
		notes.forEach(n -> System.out.println(n.getPosition() + ", " + n.getLength()));
	}
}


