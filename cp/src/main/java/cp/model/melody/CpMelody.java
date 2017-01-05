package cp.model.melody;

import cp.composition.beat.BeatGroup;
import cp.model.TimeLine;
import cp.model.TimeLineKey;
import cp.model.note.Dynamic;
import cp.model.note.Note;
import cp.model.note.Scale;
import cp.out.instrument.Articulation;
import cp.util.RandomUtil;
import cp.util.Util;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;

import static java.util.stream.Collectors.toList;

public class CpMelody implements Comparable<CpMelody>{

	private static final Logger LOGGER = LoggerFactory.getLogger(CpMelody.class);
	
	private int voice;
	private boolean mutable = true;
	private boolean rhythmMutable = true;
	private boolean replaceable = true;
	private List<Note> notes;
	private int start;
	private int end;
	private List<Integer> contour = new ArrayList<>();
	private BeatGroup beatGroup;
	
	public CpMelody(List<Note> notes, int voice, int start, int end) {
		this.voice = voice;
		this.start = start;
		this.end = end;
		this.notes = notes;
		updateContour();
	}

	private CpMelody(CpMelody anotherMelody) {
		this.notes = anotherMelody.getNotes().stream().map(note -> note.clone()).collect(toList());
		clone(anotherMelody);
	}

	private CpMelody(CpMelody anotherMelody, int voice) {
		this.notes = anotherMelody.getNotes().stream()
				.map(note -> {
					Note clone = note.clone();
					clone.setVoice(voice);
					return clone;
				})
				.collect(toList());
		clone(anotherMelody);
	}

	private CpMelody(CpMelody anotherMelody, int end, int voice) {
		this.notes = anotherMelody.getNotes().stream()
				.filter(n -> n.getPosition() < end)
				.map(note -> {
					Note clone = note.clone();
					clone.setVoice(voice);
					return clone;
				})
				.collect(toList());
		clone(anotherMelody);
	}
	
	private void clone(CpMelody anotherMelody) {
		this.mutable = anotherMelody.isMutable();
		this.rhythmMutable = anotherMelody.isRhythmMutable();
		this.voice = anotherMelody.getVoice();
		this.start = anotherMelody.getStart();
		this.end = anotherMelody.getEnd();
		this.contour = new ArrayList<>(anotherMelody.getContour());
		this.replaceable = anotherMelody.isReplaceable();
		this.beatGroup = anotherMelody.getBeatGroup();
	}

	public CpMelody clone() {
		return new CpMelody(this);
	}

	public CpMelody clone(int voice) {
		CpMelody melody = new CpMelody(this, voice);
		melody.setVoice(voice);
		return melody;
	}
	
	public CpMelody clone(int end, int voice) {
		CpMelody melody = new CpMelody(this, end, voice);
		melody.setVoice(voice);
		return melody;
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
			TimeLineKey timeLineKey = timeline.getTimeLineKeyAtPosition(note.getPosition(), note.getVoice());
			int pitchClass = (timeLineKey.getScale().pickRandomPitchClass() + timeLineKey.getKey().getInterval()) % 12;
			note.setPitchClass(pitchClass);
			
			updateContourDirections(index);
//			LOGGER.info("one note mutated");
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
	
	public void updateArticulation(Articulation articulation) {
		List<Note> notesNoRest = getNotesNoRest();
		if (notesNoRest.size() > 1) {
			//		notes.forEach(note -> note.setArticulation(Note.DEFAULT_ARTICULATION));//reset?
			Note note = RandomUtil.getRandomFromList(notesNoRest);
			note.setArticulation(articulation);

			Note removeArticulation = RandomUtil.getRandomFromList(notesNoRest);
			removeArticulation.setArticulation(Note.DEFAULT_ARTICULATION);
		}
	}

	public void updateDynamic(Dynamic dynamic) {
		List<Note> notesNoRest = getNotesNoRest();
		if (notesNoRest.size() > 1) {
			//		notes.forEach(note -> note.setArticulation(Note.DEFAULT_ARTICULATION));//reset?
			Note note = RandomUtil.getRandomFromList(notesNoRest);
			note.setDynamic(dynamic);
			note.setDynamicLevel(dynamic.getLevel());

			Note removeArticulation = RandomUtil.getRandomFromList(notesNoRest);
			removeArticulation.setDynamic(Note.DEFAULT_DYNAMIC);
		}
	}
	
	protected int invertPitchClass(int functionalDegreeCenter, int pitchClass, Scale scale, Scale dependingScale, int key, int dependingKey){
		if (scale.getPitchClasses().length != dependingScale.getPitchClasses().length) {
			throw new IllegalArgumentException("Scales should have the same length");
		}
		
		int pitchClassKeyOfC = convertToKeyOfC(pitchClass, key);
		
		int invertedPC;
		if (scale != dependingScale) {
			int indexPitchClass = scale.getIndex(pitchClassKeyOfC);
			int transformPitchClass = dependingScale.getPitchClasses()[indexPitchClass];
			invertedPC = dependingScale.getInversedPitchClass(functionalDegreeCenter, transformPitchClass);
		} else {
			invertedPC = dependingScale.getInversedPitchClass(functionalDegreeCenter, pitchClassKeyOfC);
		}
		return (invertedPC + dependingKey) % 12;
	}
	
	/**
	 * Converts pitch class of the depending melody to the same functional degree in this melody and rotates to match the note
	 */
	protected int transposePitchClass(int pitchClass, Scale scale, Scale dependingScale, int key, int dependingKey, int steps){
		int scaleDistance = dependingKey - key;
		int totalSteps = Util.getSteps(scaleDistance) + steps;
		return convertPitchClass(pitchClass, scale, dependingScale, totalSteps, key, dependingKey);
	}
	
	/**
	 * Converts pitch class of the melody to the same functional degree in the depending melody
	 * eg. F sharp in G = C sharp in D
	 */
	protected int convertPitchClass(int pitchClass, Scale scale, Scale dependingScale, int steps, int key, int dependingKey){
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
		return (rotatedPC + dependingKey) % 12;
	}

	public void inversePitchClasses(int functionalDegreeCenter, int offset, TimeLine timeLine) {
		notes.stream().filter(n -> !n.isRest())
				.sorted()
				.forEach(n -> {
					TimeLineKey timeLineKey = timeLine.getTimeLineKeyAtPosition(n.getPosition(), n.getVoice());
					TimeLineKey dependingTimeLineKey = timeLine.getTimeLineKeyAtPosition(n.getPosition() + offset, n.getVoice());
					int invertedPC = this.invertPitchClass(functionalDegreeCenter, n.getPitchClass(), timeLineKey.getScale(), dependingTimeLineKey.getScale(), timeLineKey.getKey().getInterval(), dependingTimeLineKey.getKey().getInterval());
					n.setPitchClass(invertedPC);
				});
	}

	public void transposePitchClasses(int steps, int offset, TimeLine timeLine){
		notes.stream().filter(n -> !n.isRest())
				.sorted()
				.forEach(n -> {
					TimeLineKey timeLineKey = timeLine.getTimeLineKeyAtPosition(n.getPosition(), n.getVoice());
					TimeLineKey dependingTimeLineKey = timeLine.getTimeLineKeyAtPosition(n.getPosition() + offset, n.getVoice());
					int transposedPc = this.transposePitchClass(n.getPitchClass(), timeLineKey.getScale(), dependingTimeLineKey.getScale(), timeLineKey.getKey().getInterval(), dependingTimeLineKey.getKey().getInterval(), steps);
					n.setPitchClass(transposedPc);

				});
	}

	public void T(int steps){
		this.getNotesNoRest().forEach(note -> note.setPitchClass((note.getPitchClass() + steps) % 12));
	}

	public void I(){
		this.getNotesNoRest().forEach(note -> note.setPitchClass((12 - note.getPitchClass()) % 12));
	}

	public void M(int steps){
		this.getNotesNoRest().forEach(note -> note.setPitchClass((note.getPitchClass() * steps) % 12));
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

	public void setContour(List<Integer> contour) {
		this.contour = contour;
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
	
	public int getEnd() {
		return end;
	}

	public boolean isReplaceable() {
		return replaceable;
	}

	public void setReplaceable(boolean replaceable) {
		this.replaceable = replaceable;
	}

	public BeatGroup getBeatGroup() {
		return beatGroup;
	}

	public int getBeatGroupLength() {
		return beatGroup.getBeatLength();
	}

	public void setBeatGroup(BeatGroup beatGroup) {
		this.beatGroup = beatGroup;
	}

	public void setStart(int start) {
		this.start = start;
	}

	public void setEnd(int end) {
		this.end = end;
	}

	@Override
	public int compareTo(CpMelody melody) {
		if(this.start < melody.getStart()){
			return -1;
		}else if(this.start < melody.getStart()){
			return 1;
		}
		return 0;
	}
}
