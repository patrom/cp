package cp.out.orchestration;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import cp.combination.RhythmCombination;
import cp.model.note.Note;

public class ChordOrchestration {
	
	private int start;
	private int end;
	private Map<Integer, List<Note>> map = new TreeMap<>();

	public ChordOrchestration(int start, int end) {
		this.start = start;
		this.end = end;
	}

	public List<Note> getRhythmNotes(RhythmCombination rhythmCombination, int beat) {
		List<Note> rhythmNotes = new ArrayList<>();
		int length = start + beat;
		while (length <= end) {
			List<Note> rNotes = rhythmCombination.getNotes(beat);
			for (Note note : rNotes) {
				note.setPosition(note.getPosition() + start);
				rhythmNotes.add(note);
			}
			start = length;
			length = start + beat;
		}
		return rhythmNotes;
	}
	
	public List<Note> orchestrateChord(List<Note> rhythmNotes, Note... chordNotes){
		List<Note> notes = new ArrayList<>();
		int size = rhythmNotes.size();
		for (int i = 0; i < size; i++) {
			Note chordNote = getNextChordNote(i, chordNotes);
			Note rhythmNote = rhythmNotes.get(i).clone();
			rhythmNote.setPitchClass(chordNote.getPitchClass());
			rhythmNote.setOctave(chordNote.getOctave());
			rhythmNote.setPitch(chordNote.getPitch());
			notes.add(rhythmNote);
		}
		return notes;
	}
	
	private Note getNextChordNote(int i, Note... chordNotes) {
		return chordNotes[i % chordNotes.length];
	}
	
	public List<Note> orchestrate(RhythmCombination rhythmCombination, int beat, Note... chordNotes){
		List<Note> rhythmNotes = getRhythmNotes(rhythmCombination, beat);
		return orchestrateChord(rhythmNotes, chordNotes);
	}

}
