package cp.out.arrangement;

import cp.model.note.Note;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@FunctionalInterface
public interface Accompagnement {

	List<List<Note>> applyAccompagnement(List<Note> notes);
	
	static List<List<Note>> chordal(List<Note> harmonyNotes) {
		ArrayList<List<Note>> list = new ArrayList<>();
		list.add(harmonyNotes);
		return list;
	}
	
	static List<List<Note>> arpeggio(List<Note> harmonyNotes) {
		return harmonyNotes.stream().map(note -> {
			List<Note> list = new ArrayList<Note>();
			list.add(note);
			return list;
		}).collect(Collectors.toList());
	}
}
