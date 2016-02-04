package cp.model.melody;

import static java.util.stream.Collectors.toList;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import cp.model.TimeLine;
import cp.model.note.Note;
import cp.model.note.Scale;
import cp.out.instrument.Articulation;
import cp.util.RandomUtil;
import cp.util.Util;

public class CpMelody implements Cloneable{

	private static Logger LOGGER = LoggerFactory.getLogger(CpMelody.class);
	
	private int voice;
	private boolean mutable = true;
	private boolean rhythmMutable = true;
	private boolean replaceable = true;
	private List<Note> notes;
	private Scale scale;
	private int start;
	private int end;
	private List<Integer> contour = new ArrayList<>();
	private int type = 2;
	private int beat;
//	private Key noteStep;
	
	public CpMelody(List<Note> notes, Scale scale, int voice) {
		this.voice = voice;
		this.notes = notes;
		this.scale = scale;
		this.start = notes.get(0).getPosition();
		this.end = notes.get(notes.size() - 1).getPosition();
		this.notes.forEach(n -> n.setVoice(voice));
		updateContour();
	}
	
	public CpMelody(List<Note> notes, Scale scale, int voice, int start, int end) {
		this.voice = voice;
		this.scale = scale;
		this.start = start;
		this.end = end;
		this.notes = notes;
		updateContour();
	}
	
	public CpMelody(List<Note> notes, Scale scale, int start, int end) {
		this.start = start;
		this.end = end;
		this.scale = scale;
		this.notes = notes;
		updateContour();
	}

	public CpMelody(Scale scale, int voice, int start, int end) {
		this.voice = voice;
		this.notes = new ArrayList<>();
		this.scale = scale;
		this.start = start;
		this.end = end;
	}

	private CpMelody(CpMelody anotherMelody) {
		this.notes = anotherMelody.getNotes().stream().map(note -> note.clone()).collect(toList());
		clone(anotherMelody);
	}

	private CpMelody(CpMelody anotherMelody, int end) {
		this.notes = anotherMelody.getNotes().stream()
				.filter(n -> n.getPosition() < end)
				.map(note -> (Note)note.clone())
				.collect(toList());
		clone(anotherMelody);
	}
	
	private void clone(CpMelody anotherMelody) {
		this.mutable = anotherMelody.isMutable();
		this.rhythmMutable = anotherMelody.isRhythmMutable();
		this.voice = anotherMelody.getVoice();
		this.scale = anotherMelody.getScale();
		this.start = anotherMelody.getStart();
		this.end = anotherMelody.getEnd();
		this.contour = new ArrayList<>(anotherMelody.getContour());
		this.type = anotherMelody.getType();
		this.replaceable = anotherMelody.isReplaceable();
		this.beat = anotherMelody.getBeat();
//		this.noteStep = anotherMelody.getNoteStep();
	}

	@Override
	public CpMelody clone() {
		return new CpMelody(this);
	}
	
	public CpMelody clone(int end) {
		return new CpMelody(this, end);
	}
	
	private void updateContour() {
		List<Note> notesNoRest = getNotesNoRest();
		for (int i = 0; i < notesNoRest.size(); i++) {
			contour.add(RandomUtil.randomAscendingOrDescending());
		}
	}

	public void updateRandomNote(TimeLine timeline) {
		List<Note> notesNoRest = getNotesNoRest();
		if (!notesNoRest.isEmpty()) {
			int index = RandomUtil.getRandomIndex(notesNoRest);
			Note note = notesNoRest.get(index);
			int key = timeline.getKeyAtPosition(note.getPosition()).getInterval();
			int pitchClass = (getScale().pickRandomPitchClass() + key) % 12;
			note.setPitchClass(pitchClass);
			
			updateContourDirections(index);
			LOGGER.info("one note mutated");
		}
	}
	
	protected void updateContourDirections(int index){
		updateNextContour(index, RandomUtil.randomAscendingOrDescending()); 
		updatePreviousContour(index, RandomUtil.randomAscendingOrDescending());
	}

	protected void updateNextContour(int index, int direction) {
		if (index <= contour.size() - 1) {
			contour.set(index, direction);
		}
	}

	protected void updatePreviousContour(int index, int direction) {
		if (index > 0) {
			contour.set(index - 1, direction);
		}
	}
	
	protected void removeContour(int index, int direction){
		contour.remove(index);
		if (index > 0) {
			contour.set(index - 1, direction);
		}
	}

	protected void insertContourDirections(int index){
		contour.add(index, RandomUtil.randomAscendingOrDescending());
		updatePreviousContour(index, RandomUtil.randomAscendingOrDescending());
	}

	public void removeNote() {
		List<Note> notesNoRest = getNotesNoRest();
		int size = notesNoRest.size();
		if (size > 1) {
			int index = RandomUtil.randomInt(0, size);
			notesNoRest.remove(index);
			removeContour(index, RandomUtil.randomAscendingOrDescending());
			LOGGER.info("rhythm note removed");
		}
	}
	
	public void updateNotes(List<Note> melodyNotes) {
		this.notes = melodyNotes;
		this.contour.clear();
		updateContour();
	}
	
	public void updateArticulation() {
		List<Note> notesNoRest = getNotesNoRest();
//		notes.forEach(note -> note.setArticulation(Note.DEFAULT_ARTICULATION));//reset?
		Articulation[] articulations = Articulation.class.getEnumConstants();
		Articulation articulation = RandomUtil.getRandomFromArray(articulations);
		Note note = RandomUtil.getRandomFromList(notesNoRest);
		note.setArticulation(articulation);
		
		Note removeArticulation = RandomUtil.getRandomFromList(notesNoRest);
		removeArticulation.setArticulation(Note.DEFAULT_ARTICULATION);
	}
	
	public void inversePitchClasses(int functionalDegreeCenter){
//		notes.stream().filter(n -> !n.isRest())
//					.sorted()
//					.forEach(n -> n.setPitchClass((scale.getInversedPitchClass(functionalDegreeCenter, pitchClassNoKey(n.getPitchClass())) + noteStep.getInterval()) % 12));
	}
	
	/**
	 * Converts pitch class of the depending melody to the same functional degree in this melody and rotates to match the note
	 * @param pitchClass
	 * @param dependingMelody
	 * @return
	 */
	protected int transposePitchClass(int pitchClass, Scale dependingScale, int key, int dependingKey){
		int scaleDistance = dependingKey - key;
		int steps = Util.getSteps(scaleDistance);
		return convertPitchClass(pitchClass, dependingScale, steps, key, dependingKey);
	}
	
	/**
	 * Converts pitch class of the melody to the same functional degree in the depending melody
	 * eg. F sharp in G = C sharp in D
	 * @param pitchClass
	 * @param dependingMelody
	 * @param steps
	 * @return
	 */
	protected int convertPitchClass(int pitchClass, Scale dependingScale, int steps, int key, int dependingKey){
		if (scale.getPitchClasses().length != dependingScale.getPitchClasses().length) {
			throw new IllegalArgumentException("Scales should have the same length");
		}
		
		int pitchClassKeyOfC = convertToKeyOfC(pitchClass, key);
		
		int rotatedPC;
		if (scale != dependingScale) {
			int indexPitchClass = scale.getIndex(pitchClassKeyOfC);
			int transformPitchClass = dependingScale.getPitchClasses()[indexPitchClass];
			rotatedPC = dependingScale.transposePitchClass(transformPitchClass, steps);
		} else {
			rotatedPC = dependingScale.transposePitchClass(pitchClassKeyOfC, steps);
		}
		int transposedPitchClass = (rotatedPC + dependingKey) % 12;
		return transposedPitchClass;
	}
	
	private int convertToKeyOfC(int pitchClass, int key) {
		return (12 + pitchClass - key) % 12;
	}
	
	public boolean isRhythmMutable() {
		return rhythmMutable;
	}

	public void setRhythmMutable(boolean rhythmMutable) {
		this.rhythmMutable = rhythmMutable;
	}
	
	public int getStart() {
		return start;
	}
	
	public List<Integer> getContour() {
		return contour;
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
	
	public void setVoice(int voice) {
		this.voice = voice;
	}
	
	public List<Note> getNotes() {
		return notes;
	}
	
	public List<Note> getNotesNoRest() {
		return notes.stream().filter(n -> !n.isRest()).sorted().collect(toList());
	}
	
	public Scale getScale() {
		return scale;
	}
	
	public void setScale(Scale scale) {
		this.scale = scale;
	}
	
	public int getEnd() {
		return end;
	}

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}

	public int getBeat() {
		return beat;
	}

	public void setBeat(int beat) {
		this.beat = beat;
	}

	public boolean isReplaceable() {
		return replaceable;
	}

	public void setReplaceable(boolean replaceable) {
		this.replaceable = replaceable;
	}
	
//	public void setNoteStep(Key noteStep) {
//		this.noteStep = noteStep;
//	}
//	
//	public Key getNoteStep() {
//		return noteStep;
//	}

}
