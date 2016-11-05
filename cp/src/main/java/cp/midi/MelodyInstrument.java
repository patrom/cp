package cp.midi;

import cp.model.note.Note;
import cp.out.play.InstrumentMapping;

import java.util.Collections;
import java.util.List;

public class MelodyInstrument {

	private int voice;
	private List<Note> notes;
	private InstrumentMapping instrumentMapping;
	
	public MelodyInstrument(List<Note> notes, int voice) {
		this.notes = notes;
		this.voice = voice;
	}
	
	public int getVoice() {
		return voice;
	}
	public void setVoice(int voice) {
		this.voice = voice;
	}
	public List<Note> getNotes() {
		return notes;
	}
	public void setNotes(List<Note> notes) {
		this.notes = notes;
	}
	public InstrumentMapping getInstrumentMapping() {
		return instrumentMapping;
	}
	public void setInstrumentMapping(InstrumentMapping instrument) {
		this.instrumentMapping = instrument;
	}
	
	public void addNotes(List<Note> notes){
		this.notes.addAll(notes);
		Collections.sort(this.notes);
	}
	
	
}
