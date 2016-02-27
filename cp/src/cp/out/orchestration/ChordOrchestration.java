package cp.out.orchestration;

import static cp.model.note.NoteBuilder.note;

import java.util.ArrayList;
import java.util.List;

import cp.combination.RhythmCombination;
import cp.model.note.Note;
import cp.out.orchestration.notetemplate.NoteTemplate;

public class ChordOrchestration {
	
	private int start;
	private int end;
	private int octave;

	public ChordOrchestration(int start, int end, int octave) {
		this.start = start;
		this.end = end;
		this.octave = octave;
	}
	
	public Note[] applyNoteTemplate(NoteTemplate noteTemplate, int[] pitchClasses) {
		int[] noteIndices = noteTemplate.getNoteTemplate();
		Note[] notes = new Note[noteIndices.length];
		for (int i = 0; i < noteIndices.length; i++) {
			notes[i] = note().pc(pitchClasses[noteIndices[i]]).ocatve(octave).build();
		}
		return notes;
	}

	public List<Note> applyRhythmCombination(RhythmCombination rhythmCombination, int beat) {
		List<Note> rhythmNotes = new ArrayList<>();
		int length = start;
		while (length < end) {
			List<Note> rNotes = rhythmCombination.getNotes(beat);
			for (Note note : rNotes) {
				note.setPosition(note.getPosition() + length);
				rhythmNotes.add(note);
			}
			length = length + beat;
		}
		return rhythmNotes;
	}
	
	public List<Note> orchestrateChord(List<Note> rhythmNotes, Note[] chordNotes){
		List<Note> notes = new ArrayList<>();
		int size = rhythmNotes.size();
		int j = 0;
		for (int i = 0; i < size; i++) {
			Note rhythmNote = rhythmNotes.get(i).clone();
			if (rhythmNote.isRest()) {
				notes.add(rhythmNote);
				continue;
			} else {
				Note chordNote = getNextChordNote(j, chordNotes);
				rhythmNote.setPitchClass(chordNote.getPitchClass());
				rhythmNote.setOctave(chordNote.getOctave());
				rhythmNote.setPitch(chordNote.getPitchClass() + (chordNote.getOctave() * 12));
				notes.add(rhythmNote);
				j++;
			}
		}
		return notes;
	}
	
	private Note getNextChordNote(int i, Note[] chordNotes) {
		return chordNotes[i % chordNotes.length];
	}
	
	public List<Note> orchestrate(NoteTemplate noteTemplate, RhythmCombination rhythmCombination, int[] pitchClasses, int beat){
		Note[] notes = applyNoteTemplate(noteTemplate, pitchClasses);
		List<Note> rhythmNotes = applyRhythmCombination(rhythmCombination, beat);
		return orchestrateChord(rhythmNotes, notes);
	}

}
