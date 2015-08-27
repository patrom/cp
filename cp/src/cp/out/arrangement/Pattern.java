package cp.out.arrangement;

import static cp.model.note.NoteBuilder.note;

import java.util.ArrayList;
import java.util.List;

import cp.model.note.Note;

public class Pattern {

	/**
	 * Positions: depending on quarterNote: 6 12 or 12 24
	 * @param noteLength
	 * @return
	 */
	public static List<Note> waltz(int quarterNote, int noteLength){
		List<Note> notes = new ArrayList<>();
		notes.add(note().pos(quarterNote).len(noteLength).build());
		notes.add(note().pos(quarterNote * 2).len(noteLength).build());
		return notes;
	}
	
	public static List<Note> waltzSecBeat(int quarterNote, int noteLength){
		List<Note> notes = new ArrayList<>();
		notes.add(note().pos(quarterNote).len(noteLength).build());
		return notes;
	}
	
	/**
	 * Positions: repeat note depending on noteLength for harmonyLength
	 * @param noteLength
	 * @param harmonyLength
	 * @return
	 */
	public static List<Note> repeat(int noteLength, int harmonyLength){
		List<Note> notes = new ArrayList<>();
		for (int i = 0; i < harmonyLength; i = i + noteLength) {
			notes.add(note().pos(i).len(noteLength).build());
		}
		return notes;
	}
}
