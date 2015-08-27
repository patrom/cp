package cp.midi;

import java.util.ArrayList;
import java.util.List;

import cp.model.note.Note;

public class HarmonyPosition implements Comparable<HarmonyPosition>{

	private List<Note> notes = new ArrayList<>();
	private int position;
	
	public void addNote(Note note){
		notes.add(note);
	}

	public List<Note> getNotes() {
		return notes;
	}
	
	public void setNotes(List<Note> notes) {
		this.notes = notes;
	}

	public int getPosition() {
		return position;
	}

	public void setPosition(int position) {
		this.position = position;
	}

	@Override
	public int compareTo(HarmonyPosition harmonyInstrument) {
		if (getPosition() < harmonyInstrument.getPosition()) {
			return -1;
		} if (getPosition() > harmonyInstrument.getPosition()) {
			return 1;
		} else {
			return 0;
		}
	}
}
