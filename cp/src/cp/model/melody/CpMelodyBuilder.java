package cp.model.melody;

import java.util.ArrayList;
import java.util.List;

import cp.model.note.Note;

public class CpMelodyBuilder {
	
	private int beat = 24;
	private int start = 0;
	private int voice = -1;
	List<Note> notes = new ArrayList<>();

	public CpMelodyBuilder start(int start){
		this.start = start;
		return this;
	}
	
	public CpMelodyBuilder beat(int beat){
		this.beat = beat;
		return this;
	}
	
	public CpMelodyBuilder voice(int voice){
		this.voice = voice;
		return this;
	}
	
	public CpMelodyBuilder notes(List<Note> notes){
		this.notes = notes;
		return this;
	}
	
	public CpMelody build(){
		int end = start + beat;
		CpMelody melody = new CpMelody(notes, voice, start, end);
//		melody.setBeatGroup(beatGroup);
		return melody;
	}
}
