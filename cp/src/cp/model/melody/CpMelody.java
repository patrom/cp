package cp.model.melody;

import static cp.model.note.NoteBuilder.note;
import static java.util.stream.Collectors.toList;

import java.util.Collections;
import java.util.List;

import cp.model.note.Note;
import cp.model.note.Scale;
import cp.util.RandomUtil;

public class CpMelody implements Cloneable{

	private int voice;
	private boolean mutable = true;
	private List<Note> notes;
	private Scale scale;
	private int start;
	private int end;
	
	public CpMelody(List<Note> notes, Scale scale, int voice) {
		this.voice = voice;
		this.notes = notes;
		this.scale = scale;
		this.start = notes.get(0).getPitchClass();
		this.end = notes.get(notes.size() - 1).getPosition();
	}

	protected CpMelody(CpMelody anotherMelody) {
		this.setMutable(anotherMelody.isMutable());
		this.voice = anotherMelody.getVoice();
		this.notes = anotherMelody.getNotes().stream().map(note -> (Note)note.clone()).collect(toList());
		this.scale = anotherMelody.getScale();
		this.start = anotherMelody.getNotes().get(0).getPitchClass();
		this.end = anotherMelody.getNotes().get(anotherMelody.getNotes().size() - 1).getPosition();
	}

	public boolean isMutable() {
		return mutable;
	}

	public void setMutable(boolean mutable) {
		this.mutable = mutable;
	}

	public int getVoice() {
		return voice;
	}

	public List<Note> getNotes() {
		return notes;
	}
	
	public Scale getScale() {
		return scale;
	}
	
	@Override
	public Object clone() {
		return new CpMelody(this);
	}
	
	public void updatePitches(int octave){
		Note firstNote = notes.get(0);
		firstNote.setOctave(octave);
		firstNote.setPitch(firstNote.getPitchClass() + (octave * 12));
		int size = notes.size() - 1;
		for (int i = 0; i < size; i++) {
			int direction = RandomUtil.random(2);//0 == lower or 1 == higher
			updatePitchFrom(notes.get(i), notes.get(i + 1), direction);
		}
	}

	protected void updatePitchFrom(Note note, Note noteToUpdate, int direction) {
		int octaveDifference = getOctaveIndirection(note, noteToUpdate, direction);
		int octave = note.getOctave() + octaveDifference;
		noteToUpdate.updateNote(octave);
	}
	
	protected void updatePitchFrom(Note note, List<Note> notesToUpdate, int direction) {
		Note noteToUpdate = notesToUpdate.get(0);
		int octaveDifference = getOctaveIndirection(note, noteToUpdate, direction);
		int octave = note.getOctave() + octaveDifference;
		notesToUpdate.forEach(n -> {
			n.updateNote(octave);
		});
	}
	
	protected int getOctaveIndirection(Note note, Note nextNote, int direction) {
		int pc = note.getPitchClass();
		int nextPc = nextNote.getPitchClass();
		int signum = (int) Math.signum(pc - nextPc);
		if (direction == 0 && signum == -1) {
			return -1;
		} else if(direction == 1 && signum == 1) {
			return 1;
		}
		return 0;
	}

	public void updateRandomNote() {
		Note note = RandomUtil.getRandomFromList(notes);
		int pitchClass = getScale().pickRandomPitchClass();
		int direction = RandomUtil.random(2);//lower or higher
		updateNote(note, pitchClass, direction);
	}

	protected void updateNote(Note note, int pitchClass, int direction) {
		int index = notes.indexOf(note);
		int previousIndex = index - 1;
		int nextIndex = index + 1;
		note.setPitchClass(pitchClass);
		if (index == 0) {//first
			note.setPitch(note.getPitchClass() + (note.getOctave() * 12));
			List<Note> notesToChange = notes.subList(nextIndex, notes.size());
			updatePitchFrom(note, notesToChange, direction);
		} else if (nextIndex == notes.size() - 1){//penultimate
			Note previousNote = notes.get(previousIndex);
			updatePitchFrom(previousNote, note, direction);
			
			int dir = RandomUtil.random(2);
			Note lastNote = notes.get(nextIndex);
			updatePitchFrom(note, lastNote, dir);
		} else if (index == notes.size() - 1){//last
			Note previousNote = notes.get(previousIndex);
			updatePitchFrom(previousNote, note, direction);
		} else {
			Note previousNote = notes.get(previousIndex);
			updatePitchFrom(previousNote, note, direction);
			
			int dir = RandomUtil.random(2);
			List<Note> notesToChange = notes.subList(nextIndex, notes.size());
			updatePitchFrom(note, notesToChange, dir);
		}
	}

	public void addRandomRhythmNote(int minimumValue) {
		int newPosition = RandomUtil.randomInt(this.start/minimumValue + 1, this.end/minimumValue) * minimumValue;
		int pitchClass = getScale().pickRandomPitchClass();
		insertRhythm(newPosition, pitchClass);
	}

	protected void insertRhythm(int newPosition, int pitchClass) {
		List<Integer> positions = notes.stream().map(n -> n.getPosition()).collect(toList());
		if (!positions.contains(newPosition)) {
			Note note = note().pos(newPosition).build();
			notes.add(note);
			Collections.sort(notes);
			
			int direction = RandomUtil.random(2);//lower or higher
			updateNote(note, pitchClass, direction);
		}
	}
	
	protected int getOctavePreviousNote(Note note){
		int index = notes.indexOf(note);
		return notes.get(index - 1).getOctave();
	}
	
	public void updateMelodyBetween(int low, int high){
		for (Note note : notes) {
			while (note.getPitch() < low) {
				note.setPitch(note.getPitch() + 12);
			}
			while (note.getPitch() > high) {
				note.setPitch(note.getPitch() - 12);
			}
		}
	}

	public void removeNote() {
		Note note = RandomUtil.getRandomFromList(notes);
		notes.remove(note);
	}
	
	public void changeInterval(){
		int index = RandomUtil.randomInt(1, notes.size());
		changeIntervalFrom(index);
	}

	protected void changeIntervalFrom(int index) {
		int previousNotePc = notes.get(index - 1).getPitchClass();
		int notePc = notes.get(index).getPitchClass();
		float signum = Math.signum(previousNotePc - notePc);
		if (signum == 0.0) {
			return;
		}
		updateNotesFrom(index, (int) signum);
	}

	private void updateNotesFrom(int index, int octaveChange) {
		List<Note> notesToChange = notes.subList(index, notes.size());
		notesToChange.forEach(n -> {
			n.setOctave(n.getOctave() + octaveChange);
			n.setPitch(n.getPitchClass() + (n.getOctave() * 12));
		});
	}
	
}
