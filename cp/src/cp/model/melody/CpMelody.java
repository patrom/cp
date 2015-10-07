package cp.model.melody;

import static cp.model.note.NoteBuilder.note;
import static java.util.stream.Collectors.toList;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.function.Function;

import cp.model.note.Note;
import cp.model.note.Scale;
import cp.out.instrument.Articulation;
import cp.out.instrument.Instrument;
import cp.util.RandomUtil;

public class CpMelody implements Cloneable{

	private int voice;
	private boolean mutable = true;
	private boolean rhythmMutable = true;
	private List<Note> notes;
	private Scale scale;
	private int start;
	private int end;
	private int[] innerMetricDistance;
	private Instrument instrument;
	
	public CpMelody(List<Note> notes, Scale scale, int voice) {
		this.voice = voice;
		this.notes = notes;
		this.scale = scale;
		this.start = notes.get(0).getPosition();
		this.end = notes.get(notes.size() - 1).getPosition();
		notes.forEach(n -> n.setVoice(voice));
	}
	
	public CpMelody(Scale scale, int voice, int start, int end) {
		this.voice = voice;
		this.notes = new ArrayList<>();
		this.scale = scale;
		this.start = start;
		this.end = end;
	}

	protected CpMelody(CpMelody anotherMelody) {
		this.mutable = anotherMelody.isMutable();
		this.rhythmMutable = anotherMelody.isRhythmMutable();
		this.voice = anotherMelody.getVoice();
		this.notes = anotherMelody.getNotes().stream().map(note -> (Note)note.clone()).collect(toList());
		this.scale = anotherMelody.getScale();
		this.start = anotherMelody.getNotes().get(0).getPosition();
		this.end = anotherMelody.getNotes().get(anotherMelody.getNotes().size() - 1).getPosition();
		this.innerMetricDistance = anotherMelody.getInnerMetricDistance();
		this.instrument = anotherMelody.getInstrument();
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
			int octaveDifference = getOctaveIndirection(note, notesToChange.get(0), direction);
			int octave = note.getOctave() + octaveDifference;
			notesToChange.forEach(n -> {
				n.updateNote(octave);
			});
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
			int octaveDifference = getOctaveIndirection(note, notesToChange.get(0), dir);
			int octave = note.getOctave() + octaveDifference;
			notesToChange.forEach(n -> {
				n.updateNote(octave);
			});
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
	
	public void updateMelodyBetween(){
		for (Note note : notes) {
			while (note.getPitch() < instrument.getLowest()) {
				note.transpose(12);
			}
			while (note.getPitch() > instrument.getHighest()) {
				note.transpose(-12);
			}
		}
	}

	public void removeNote() {
		int size = notes.size() - 1;
		if (size > 4) {
			int index = RandomUtil.randomInt(1, size);//don't remove outer notes
			notes.remove(index);
		}
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

	public boolean isRhythmMutable() {
		return rhythmMutable;
	}

	public void setRhythmMutable(boolean rhythmMutable) {
		this.rhythmMutable = rhythmMutable;
	}

	public void updateArticulation() {
//		notes.forEach(note -> note.setArticulation(Note.DEFAULT_ARTICULATION));//reset?
		Articulation[] articulations = Articulation.class.getEnumConstants();
		Articulation articulation = RandomUtil.getRandomFromArray(articulations);
		Note note = RandomUtil.getRandomFromList(notes);
		note.setArticulation(articulation);
		
		Note removeArticulation = RandomUtil.getRandomFromList(notes);
		removeArticulation.setArticulation(Note.DEFAULT_ARTICULATION);
	}
	
	public void copyMelody(CpMelody melody, int steps, Transposition function){
		Function<Note, Note> transposition = getFunction(melody, steps, function);

		notes = melody.getNotes().stream()
			.map(transposition)
			.filter(n -> n.getPosition() <= end)
			.collect(toList());
	}

	private Function<Note, Note> getFunction(CpMelody melody, int steps, Transposition function) {
		switch (function) {
			case RELATIVE:
				return (n -> { 
							Note note = n.clone();
							note.setVoice(voice);
							note.setPosition(n.getPosition() + start);
							int index = melody.getScale().getIndex(n.getPitchClass());
							int pitchClass = this.scale.getScale()[index];
							note.setPitchClass(pitchClass);
							note.setPitch(pitchClass + (n.getOctave() * 12));
							
							note.transpose(steps);
							return note;
						});
			case ABSOLUTE:
			return  (n -> { 
					Note note = n.clone();
					note.setVoice(voice);
					note.setPosition(n.getPosition() + start);
					note.transpose(steps);
					return note;
				});
			default:
				break;
			}
		throw new IllegalArgumentException("Unknown transposition function: " + function);
	}

	public int[] getInnerMetricDistance() {
		return innerMetricDistance;
	}

	public void setInnerMetricDistance(int[] innerMetricDistance) {
		this.innerMetricDistance = innerMetricDistance;
	}

	public Instrument getInstrument() {
		return instrument;
	}

	public void setInstrument(Instrument instrument) {
		this.instrument = instrument;
	}
	
}
