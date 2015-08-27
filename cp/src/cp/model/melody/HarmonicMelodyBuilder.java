package cp.model.melody;

import java.util.ArrayList;
import java.util.List;

import cp.model.note.Note;

public class HarmonicMelodyBuilder {

	private List<Note> melodyNotes = new ArrayList<>();
	private int voice;
	private int position;
	private Note harmonyNote;
	
	public static HarmonicMelodyBuilder harmonicMelody(){
		return new HarmonicMelodyBuilder();
	}
	
	public HarmonicMelodyBuilder notes(List<Note> melodyNotes){
		this.melodyNotes.addAll(melodyNotes);
		return this;
	}
	
	public HarmonicMelodyBuilder notes(Note ... melodyNote){
		for (int i = 0; i < melodyNote.length; i++) {
			melodyNotes.add(melodyNote[i]);
		}
		return this;
	}
	
	public HarmonicMelodyBuilder voice(int voice){
		this.voice = voice;
		return this;
	}
	
	public HarmonicMelodyBuilder pos(int position){
		this.position = position;
		return this;
	}
	
	public HarmonicMelodyBuilder harmonyNote(Note harmonyNote){
		this.harmonyNote = harmonyNote;
		return this;
	}
	
	public HarmonicMelody build(){
		return new HarmonicMelody(harmonyNote, melodyNotes, voice, position);
	}
	
}
