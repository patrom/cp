package cp.variation.nonchordtone;

import static cp.model.note.NoteBuilder.note;

import java.util.ArrayList;
import java.util.List;

import cp.model.note.Note;
import cp.model.note.Scale;
import cp.variation.pattern.VariationPattern;

public abstract class Variation {
	
	protected VariationPattern variationPattern;
	protected List<Scale> scales;
	protected int profile;
	protected boolean secondNoteChanged;
	protected List<Integer> excludedVoices;

	public Variation() {
		scales = new ArrayList<>();
		scales.add(Scale.MAJOR_SCALE);
		profile = 100;
		excludedVoices = new ArrayList<>();
	}
	
	/**
	 * Generate non chord note at second position of pattern
	 */
	protected List<Note> generateNonChordNote(Note firstNote, int newPitchClass, int newPitch, double[] pattern) {
		List<Note> notes = new ArrayList<Note>();
		int notePc = firstNote.getPitchClass();
		int notePitch = firstNote.getPitch();
		int noteLength = firstNote.getLength();
		int voice = firstNote.getVoice();
		
		int position = firstNote.getPosition();
		int length = (int) (noteLength * pattern[0]);
		Note firstNoteCopy = note().pc(notePc).pitch(notePitch).pos(position).len(length).voice(voice).build();
		notes.add(firstNoteCopy);
		
		position = position + length;
		length = (int) (noteLength * pattern[1]);
		Note nonChordNote = note().pc(newPitchClass).pitch(newPitch).pos(position).len(length).voice(voice).build();
		notes.add(nonChordNote);
		return notes;
	}

	/**
	 * Generate non chord note at first position of pattern
	 */
	protected List<Note> generateAccentedNonChordNote(Note note, int newPitchClass, int newPitch, double[] pattern) {
		List<Note> notes = new ArrayList<Note>();
		int notePc = note.getPitchClass();
		int notePitch = note.getPitch();
		int noteLength = note.getLength();
		int voice = note.getVoice();
		
		int position = note.getPosition();
		int length = (int) (noteLength * pattern[0]);
		Note firstNote = note().pc(newPitchClass).pitch(newPitch).pos(position).len(length).voice(voice).build();
		notes.add(firstNote);
		
		position = position + length;
		length = (int) (noteLength * pattern[1]);
		Note passingNote = note().pc(notePc).pitch(notePitch).pos(position).len(length).voice(voice).build();
		notes.add(passingNote);
		return notes;
	}
	
	public abstract List<Note> createVariation(Note firstNote, Note secondNote);
	
	public VariationPattern getVariationPattern() {
		return variationPattern;
	}
	public void setVariationPattern(VariationPattern variationPattern) {
		this.variationPattern = variationPattern;
	}
	public List<Scale> getScales() {
		return scales;
	}
	public void setScales(List<Scale> scales) {
		this.scales = scales;
	}

	public int getProfile() {
		return profile;
	}

	public void setProfile(int profile) {
		this.profile = profile;
	}

	public boolean isSecondNoteChanged() {
		return secondNoteChanged;
	}

	public void setSecondNoteChanged(boolean secondNoteChanged) {
		this.secondNoteChanged = secondNoteChanged;
	}

	public List<Integer> getExcludedVoices() {
		return excludedVoices;
	}

	public void setExcludedVoices(List<Integer> excludedVoices) {
		this.excludedVoices = excludedVoices;
	}

}
