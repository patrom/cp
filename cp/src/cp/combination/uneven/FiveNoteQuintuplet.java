package cp.combination.uneven;

import static cp.model.note.NoteBuilder.note;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;

import cp.model.note.BeamType;
import cp.model.note.Note;
import cp.model.note.TupletType;

@Component
public class FiveNoteQuintuplet {

	public List<Note> pos12345(int beat) {
		List<Note> notes = new ArrayList<>();
		int noteLength = beat/5;
		switch (beat) {
		case 12:
		case 18:
		case 24:
			notes =  posWithBeam(noteLength, noteLength, noteLength, noteLength, noteLength, noteLength);
			notes.forEach(n -> n.setQuintuplet(true));
		}
		return notes;
	}
	
	private List<Note> posWithBeam(int first, int second, int third, int fourth, int fifth, int sixth){
		List<Note> notes = new ArrayList<Note>();
		notes.add(note().pos(0).len(first).beam(BeamType.BEGIN).tuplet(TupletType.START).build());
		notes.add(note().pos(first).len(second).beam(BeamType.CONTINUE).build());
		notes.add(note().pos(first + second).len(third).beam(BeamType.CONTINUE).build());
		notes.add(note().pos(first + second + third).len(fourth).beam(BeamType.CONTINUE).build());
		notes.add(note().pos(first + second + third + fourth + fifth).len(sixth).beam(BeamType.END).tuplet(TupletType.STOP).build());
		return notes;
	}
	
	public static void main(String[] args) {
		FiveNoteQuintuplet quinTuplet = new FiveNoteQuintuplet();
		List<Note > notes = quinTuplet.pos12345(12);
		notes.forEach(n -> System.out.println(n.getPosition() + ", " + n.getLength()));
	}
}


