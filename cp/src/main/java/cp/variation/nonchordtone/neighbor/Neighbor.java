package cp.variation.nonchordtone.neighbor;

import cp.model.note.Note;
import cp.variation.nonchordtone.Variation;

import java.util.ArrayList;
import java.util.List;

public abstract class Neighbor extends Variation {
	
	public Neighbor() {
		profile = 20;
		excludedVoices.add(0);
	}
	
	protected List<Note> generateNeighborNote(Note firstNote, int pitchClass, int pitch,
			double[] pattern) {
		List<Note> notes = new ArrayList<>();
		int firstNotePc = firstNote.getPitchClass();
		int firstNotePitch = firstNote.getPitch();
		int firstNoteLength = firstNote.getLength();
		int voice = firstNote.getVoice();
		
		int position = firstNote.getPosition();
		int length = (int) (firstNoteLength * pattern[0]);
		Note note = new Note(firstNotePc, voice, position, length);
		note.setPitch(firstNotePitch);
		notes.add(note);
		
		position = position + length;
		length = (int) (firstNoteLength * pattern[1]);
		note = new Note(pitchClass, voice, position, length);
		note.setPitch(pitch);
		notes.add(note);
		
		position = position + length;
		length = (int) (firstNoteLength * pattern[2]);
		note = new Note(firstNotePc, voice, position, length);
		note.setPitch(firstNotePitch);
		notes.add(note);
		return notes;
	}

}
