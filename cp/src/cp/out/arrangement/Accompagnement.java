package cp.out.arrangement;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Component;

import cp.model.note.Note;

@FunctionalInterface
public interface Accompagnement {

	public List<List<Note>> applyAccompagnement(List<Note> notes);
	
	public static List<List<Note>> chordal(List<Note> harmonyNotes) {
		ArrayList<List<Note>> list = new ArrayList<List<Note>>();
		list.add(harmonyNotes);
		return list;
	}
	
	public static List<List<Note>> arpeggio(List<Note> harmonyNotes) {
		List<List<Note>> arpeggio = harmonyNotes.stream().map(note -> {
			List<Note> list = new ArrayList<Note>();
			list.add(note);
			return list;
		}).collect(Collectors.toList());
		return arpeggio;
	}
}
