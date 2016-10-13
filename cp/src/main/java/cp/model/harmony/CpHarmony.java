package cp.model.harmony;

import cp.model.note.Note;

import java.util.List;

public class CpHarmony {

	private List<Note> notes;
	private Chord chord;
	private int position;

	public CpHarmony(List<Note> notes, int position) {
		this.notes = notes;
		this.position = position;
	}
	
	public double getHarmonyWeight(){
		return notes.stream().mapToDouble(n -> n.getPositionWeight()).sum();
	}
	
	public void toChord(){
		chord = new Chord(getBassNote(), notes);
	}
	
	public void transpose(int t){
		notes.forEach(note -> note.setPitchClass((note.getPitchClass() + t) % 12));
		toChord();
	}
	
	private int getBassNote(){
		int minimumPitch = notes.stream()
					.mapToInt(n -> n.getPitch())
					.min()
					.getAsInt();
		return notes.stream()
				.filter(n -> n.getPitch() == minimumPitch)
				.findFirst()
				.get()
				.getPitchClass();
	}
	
	public int beat(int beat){
		return position/beat;
	}
	
	public List<Note> getNotes() {
		return notes;
	}
	
	public Chord getChord() {
		return chord;
	}
	
}
