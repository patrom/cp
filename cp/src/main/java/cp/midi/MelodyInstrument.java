package cp.midi;

import cp.model.note.Note;
import cp.out.play.InstrumentMapping;

import javax.sound.midi.MidiEvent;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class MelodyInstrument implements Comparable<MelodyInstrument>{

	private int voice;
	private List<Note> notes;
	private InstrumentMapping instrumentMapping;
	private List<MidiEvent> midiEvents = new ArrayList<>();
	
	public MelodyInstrument(List<Note> notes, int voice) {
		this.notes = notes;
		this.voice = voice;
	}

	public void setMidiEvents(List<MidiEvent> midiEvents) {
		this.midiEvents = midiEvents;
	}

	public List<MidiEvent> getMidiEvents() {
		return midiEvents;
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


	@Override
	public int compareTo(MelodyInstrument melodyInstrument) {
		if (this.getInstrumentMapping().getScoreOrder() < melodyInstrument.getInstrumentMapping().getScoreOrder()) {
			return -1;
		}else if (this.getInstrumentMapping().getScoreOrder() > melodyInstrument.getInstrumentMapping().getScoreOrder()){
			return 1;
		}
		return 0;
	}
}
