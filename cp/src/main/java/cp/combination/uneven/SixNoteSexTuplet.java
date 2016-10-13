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
public class SixNoteSexTuplet {

	public List<Note> pos123456(int beat) {
		List<Note> notes;
		int noteLength = beat/6;
		switch (beat) {
		case DurationConstants.QUARTER:
			notes =  posWithBeam(noteLength, noteLength, noteLength, noteLength, noteLength, noteLength);
			notes.forEach(n -> {n.setSextuplet(true);
								n.setTimeModification("16th");});
			return notes;
//		case 24:
//			notes =  pos(beat/3);
//			notes.forEach(n -> n.setTriplet(true));
//			return notes;
		case DurationConstants.THREE_EIGHTS:
			notes =  posWithBeam(noteLength, noteLength, noteLength, noteLength, noteLength, noteLength);
			return notes;
		default:
			notes =  pos(noteLength, noteLength, noteLength, noteLength, noteLength, noteLength);
			return notes;
		}
	}
	
	private List<Note> posWithBeam(int first, int second, int third, int fourth, int fifth, int sixth){
		List<Note> notes = new ArrayList<Note>();
		notes.add(note().pos(0).len(first).beam(BeamType.BEGIN).tuplet(TupletType.START).build());
		notes.add(note().pos(first).len(second).beam(BeamType.CONTINUE).build());
		notes.add(note().pos(first + second).len(third).beam(BeamType.CONTINUE).build());
		notes.add(note().pos(first + second + third).len(fourth).beam(BeamType.CONTINUE).build());
		notes.add(note().pos(first + second + third + fourth).len(fifth).beam(BeamType.CONTINUE).build());
		notes.add(note().pos(first + second + third + fourth + fifth).len(sixth).beam(BeamType.END).tuplet(TupletType.STOP).build());
		return notes;
	}
	
	private List<Note> pos(int first, int second, int third, int fourth, int fifth, int sixth){
		List<Note> notes = new ArrayList<Note>();
		notes.add(note().pos(0).len(first).build());
		notes.add(note().pos(first).len(second).build());
		notes.add(note().pos(first + second).len(third).build());
		notes.add(note().pos(first + second + third).len(fourth).build());
		notes.add(note().pos(first + second + third + fourth).len(fifth).build());
		notes.add(note().pos(first + second + third + fourth + fifth).len(sixth).build());
		return notes;
	}
	
	public static void main(String[] args) {
		SixNoteSexTuplet sixNoteSexTuplet = new SixNoteSexTuplet();
		List<Note > notes = sixNoteSexTuplet.pos123456(DurationConstants.THREE_EIGHTS);
		notes.forEach(n -> System.out.println(n.getPosition() + ", " + n.getLength()));
	}
}
