package cp.model.melody;

import static java.util.stream.Collectors.toList;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.function.Consumer;

import cp.model.note.Note;
import cp.model.note.Scale;
import cp.util.RandomUtil;

public class HarmonicMelody {

	private List<Note> melodyNotes = new ArrayList<>();
	private int voice;
	private int position;
	private Note harmonyNote;
	private Random random = new Random();
	
	public HarmonicMelody(Note harmonyNote, List<Note> melodyNotes, int voice, int position) {
		this.harmonyNote = harmonyNote;
		this.melodyNotes = melodyNotes;
		this.voice = voice;
		this.position = position;
		updateNotesForVoice(voice);
	}
	
	public HarmonicMelody(Note note, int voice, int position) {
		this.harmonyNote = note;
		this.melodyNotes.add(note.copy());
		this.voice = voice;
		this.position = position;
		updateNotesForVoice(voice);
	}
	
	private void updateNotesForVoice(int voice){
		this.melodyNotes.forEach(note -> note.setVoice(voice));
		this.harmonyNote.setVoice(voice);
	}

	public List<Note> getMelodyNotes() {
		return melodyNotes;
	}

	public int getVoice() {
		return voice;
	}

	public int getPosition() {
		return position;
	}

	public Note getHarmonyNote() {
		return harmonyNote;
	}
	
	public void mutateHarmonyNoteToRandomPitch(Scale scale){
		int oldPitchClass = harmonyNote.getPitchClass();
		int newPitchClass = scale.pickRandomPitchClass();
		updateMelodyNotes(oldPitchClass, newPitchClass);
		harmonyNote.setPitchClass(newPitchClass);
	}
	
	public void mutateHarmonyPreviousNoteToPitch(Scale scale){
		int oldPitchClass = harmonyNote.getPitchClass();
		int newPitchClass = scale.pickPreviousPitchFromScale(oldPitchClass);
		updateMelodyNotes(oldPitchClass, newPitchClass);
		harmonyNote.setPitchClass(newPitchClass);
	}
	
	public void mutateHarmonyNextNoteToPitch(Scale scale){
		int oldPitchClass = harmonyNote.getPitchClass();
		int newPitchClass = scale.pickNextPitchFromScale(oldPitchClass);
		updateMelodyNotes(oldPitchClass, newPitchClass);
		harmonyNote.setPitchClass(newPitchClass);
	}
	
	public void mutateMelodyNoteToHarmonyNote(int newPitchClass){
		if (melodyNotes.size() > 1) {
			if (melodyContainsHarmonyNote()) {
				List<Note> nonChordNotes = getNonChordNotes();
				if (nonChordNotes.size() > 0) {
					randomUpdateNoteInList(newPitchClass, getNonChordNotes());
				}
			}else{
				throw new RuntimeException("Melody contains no harmony note");
			}
		}
	}

	private void randomUpdateNoteInList(int newPitchClass, List<Note> notes) {
		Note melodyNote = RandomUtil.getRandomFromList(notes);
		melodyNote.setPitchClass(newPitchClass);
	}

	protected boolean melodyContainsHarmonyNote() {
		return melodyNotes.stream()
				.anyMatch(note -> harmonyNote.getPitchClass() == note.getPitchClass());
	}
	
	public List<Note> getNonChordNotes(){
		return melodyNotes.stream()
			.filter(note -> note.getPitchClass() != harmonyNote.getPitchClass())
			.collect(toList());
	}
	
	public List<Note> getChordNotes(){
		return melodyNotes.stream()
			.filter(note -> note.getPitchClass() == harmonyNote.getPitchClass())
			.collect(toList());
	}
	
	public void updateMelodyNotes(int oldPitchClass, int newPitchClass){
		Consumer<Note> updatePitchClass = (Note note) -> note.setPitchClass(newPitchClass);
		melodyNotes.stream()
			.filter(n -> n.getPitchClass() == oldPitchClass)
			.forEach(updatePitchClass);
	}
	
	public void randomUpdateMelodyNotes(int newPitchClass){
		List<Note> nonChordNotes = getNonChordNotes();
		Note note = null;
		if (melodyNotes.size() != nonChordNotes.size() + 1) {
			// at least 1 note should be a harmony note
			note = randomNote(melodyNotes);
			note.setPitchClass(newPitchClass);
		}  else if(!nonChordNotes.isEmpty()){
			note = randomNote(nonChordNotes);
			note.setPitchClass(newPitchClass);
		}
		
	}
	
	public void updateMelodyPitchesToHarmonyPitch(){
		melodyNotes.forEach(melodyNote -> {
			melodyNote.setOctave(harmonyNote.getOctave());
			melodyNote.setPitch((harmonyNote.getOctave() * 12) + melodyNote.getPitchClass());
		});
	}
	
	private Note randomNote(List<Note> notes) {
		int indexNote = random.ints(0, notes.size()).findFirst().getAsInt();
		return notes.get(indexNote);
	}

	public void setHarmonyNote(Note harmonyNote) {
		this.harmonyNote = harmonyNote;
	}
	
	//set random melody (chord tones) notes (avoid repeated notes), only first note = harmony note
	public void updateHarmonyAndMelodyNotes(int harmonyPitch, Consumer<Note> updateMelodyPitchClasses){
		melodyNotes.forEach(updateMelodyPitchClasses);
		melodyNotes.get(0).setPitchClass(harmonyPitch);
		harmonyNote.setPitchClass(harmonyPitch);
	}
	
	public HarmonicMelody copy(int voice) {
		List<Note> notes = melodyNotes.stream().map(note -> note.copy()).collect(toList());
		return new HarmonicMelody(harmonyNote.copy(), notes, voice, position);
	}
	
}
