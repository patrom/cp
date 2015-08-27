package cp.model.rhythm;

import java.util.ArrayList;
import java.util.List;

import cp.model.note.Note;

public class RhythmPosition {

	private int position;
	private List<Note> selectableNotes;
	private List<Note> notes = new ArrayList<>();
	
	public RhythmPosition(int position, List<Note> selectableNotes) {
		super();
		this.position = position;
		this.selectableNotes = selectableNotes;
	}
	
	public int getPosition() {
		return position;
	}
	
	public List<Note> getSelectableNotes() {
		return selectableNotes;
	}

	public List<Note> getNotes() {
		return notes;
	}
	
	public void updateNote(Note note){
		this.notes.clear();
		this.notes.add(note);
	}
	
	public void updateAllNotes(List<Note> notes){
		this.notes.clear();
		this.notes.addAll(notes);
	}
	
}
