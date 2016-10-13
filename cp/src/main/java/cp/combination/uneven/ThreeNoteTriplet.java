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
public class ThreeNoteTriplet {

	public List<Note> pos123(int beat) {
		List<Note> notes;
		switch (beat) {
		case DurationConstants.QUARTER:
			notes =  posWithBeamTuplet(beat/3);
			notes.forEach(n -> {n.setTriplet(true);
								n.setTimeModification("eighth");});
			return notes;
		case DurationConstants.HALF:
			notes =  posTuplet(beat/3);
			notes.forEach(n -> {n.setTriplet(true);
								n.setTimeModification("quarter");
								n.setBracket(true);});
			return notes;
		case DurationConstants.THREE_EIGHTS:
			notes =  posWithBeam(beat/3);
			return notes;
		default:
			notes =  pos(beat/3);
			return notes;
		}
	}
	
	private List<Note> posWithBeam(int noteLength){
		List<Note> notes = new ArrayList<Note>();
		notes.add(note().pos(0).len(noteLength).beam(BeamType.BEGIN).build());
		notes.add(note().pos(noteLength).len(noteLength).beam(BeamType.CONTINUE).build());
		notes.add(note().pos(2 * noteLength).len(noteLength).beam(BeamType.END).build());
		return notes;
	}
	
	private List<Note> posWithBeamTuplet(int noteLength){
		List<Note> notes = new ArrayList<Note>();
		notes.add(note().pos(0).len(noteLength).beam(BeamType.BEGIN).tuplet(TupletType.START).build());
		notes.add(note().pos(noteLength).len(noteLength).beam(BeamType.CONTINUE).build());
		notes.add(note().pos(2 * noteLength).len(noteLength).beam(BeamType.END).tuplet(TupletType.STOP).build());
		return notes;
	}
	
	private List<Note> pos(int noteLength){
		List<Note> notes = new ArrayList<Note>();
		notes.add(note().pos(0).len(noteLength).build());
		notes.add(note().pos(noteLength).len(noteLength).build());
		notes.add(note().pos(2 * noteLength).len(noteLength).build());
		return notes;
	}
	
	private List<Note> posTuplet(int noteLength){
		List<Note> notes = new ArrayList<Note>();
		notes.add(note().pos(0).len(noteLength).tuplet(TupletType.START).build());
		notes.add(note().pos(noteLength).len(noteLength).build());
		notes.add(note().pos(2 * noteLength).len(noteLength).tuplet(TupletType.STOP).build());
		return notes;
	}
	
	public static void main(String[] args) {
		ThreeNoteTriplet threeNoteUneven = new ThreeNoteTriplet();
		List<Note > notes = threeNoteUneven.pos123(12);
		notes.forEach(n -> System.out.println(n.getPosition() + ", " + n.getLength()));
	}
}
