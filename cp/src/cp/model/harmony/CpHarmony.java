package cp.model.harmony;

import java.util.List;

import cp.model.note.Note;

public class CpHarmony {

	private List<Note> notes;
	private Chord chord;

	public CpHarmony(List<Note> notes) {
		this.notes = notes;
	}
	
	public double getHarmonyWeight(){
		return notes.stream().mapToDouble(n -> n.getPositionWeight()).sum();
	}
	
	public void toChord(){
		chord = new Chord(getBassNote(), notes);
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
	
	public List<Note> getNotes() {
		return notes;
	}
	
	public Chord getChord() {
		return chord;
	}
	
}
