package cp.model.melody;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import cp.combination.NoteCombination;
import cp.generator.MelodyGenerator;
import cp.model.note.Note;
import cp.model.note.Scale;

public class CpMelodyBuilder {
	
	private Scale scale = Scale.MAJOR_SCALE;
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
	
	public CpMelodyBuilder scale(Scale scale){
		this.scale = scale;
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
		CpMelody melody = new CpMelody(notes, scale, voice, start, end);
		melody.setBeat(beat);
		return melody;
	}
}
