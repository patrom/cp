package cp.out.print;

import java.util.ArrayList;
import java.util.List;

import cp.model.note.Note;

public class Measure implements Comparable<Measure>{

	private int start;
	private int end;
	private List<Note> notes = new ArrayList<>();
	
	public int getStart() {
		return start;
	}
	public void setStart(int start) {
		this.start = start;
	}
	public int getEnd() {
		return end;
	}
	public void setEnd(int end) {
		this.end = end;
	}
	public List<Note> getNotes() {
		return notes;
	}
	public void setNotes(List<Note> notes) {
		this.notes = notes;
	}
	
	public void addNote(Note note){
		notes.add(note);
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + end;
		result = prime * result + start;
		return result;
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Measure other = (Measure) obj;
		if (end != other.end)
			return false;
		if (start != other.start)
			return false;
		return true;
	}
	@Override
	public int compareTo(Measure measure) {
		if (this.start < measure.getStart()) {
			return 1;
		} else if (this.start == measure.getStart()) {
			return 0;
		} else {
			return -1;
		}
	}
	
}
