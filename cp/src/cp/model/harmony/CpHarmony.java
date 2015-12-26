package cp.model.harmony;

import java.util.List;

import cp.model.note.Note;

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
	
	public boolean isDissonant(){
		switch (chord.getChordType()) {
			case CH2_GROTE_SECONDE:
			case CH2_GROOT_SEPTIEM:
			case CH2_KLEIN_SEPTIEM:
			case CH2_KLEINE_SECONDE:
			case CH2_TRITONE:
			case CH2_KWART:
				return true;
			default:
				break;
		}
		return false;
	}
	
}
