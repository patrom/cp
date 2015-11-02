package cp.model.melody;

import static cp.model.note.NoteBuilder.note;
import static java.util.Comparator.reverseOrder;
import static java.util.stream.Collectors.toList;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

import cp.model.note.Note;
import cp.model.note.NoteBuilder;
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
	private OperatorType operatorType;
	private int dependingVoice = -1;
	private List<Integer> contour = new ArrayList<>();
	private int startOctave;
	private List<Integer> orderedPitchIntervals = new ArrayList<>();
	
	public CpMelody(List<Note> notes, Scale scale, int voice) {
		this.voice = voice;
		this.notes = notes;
		this.scale = scale;
		this.start = notes.get(0).getPosition();
		this.end = notes.get(notes.size() - 1).getPosition();
		notes.forEach(n -> n.setVoice(voice));
		updateContour();
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
		this.operatorType = anotherMelody.getOperatorType();
		this.dependingVoice = anotherMelody.getDependingVoice();
		this.contour = new ArrayList<>(anotherMelody.getContour());
		this.startOctave = anotherMelody.getStartOctave();
		this.orderedPitchIntervals = new ArrayList<>(anotherMelody.getOrderedPitchIntervals());
	}

	@Override
	public CpMelody clone() {
		return new CpMelody(this);
	}
	
	private void updateContour() {
		for (int i = 0; i < notes.size(); i++) {
			contour.add(RandomUtil.randomAscendingOrDescending());
		}
	}
	
//	public void updatePitches(int octave){
//		Note firstNote = notes.get(0);
//		firstNote.setOctave(octave);
//		firstNote.setPitch(firstNote.getPitchClass() + (octave * 12));
//		int size = notes.size() - 1;
//		for (int i = 0; i < size; i++) {
//			int direction = RandomUtil.random(2);//0 == lower or 1 == higher
//			updatePitchFrom(notes.get(i), notes.get(i + 1), direction);
//		}
//	}
	
//	public void transformDependingOn(CpMelody melody){
//		Function<Note, Note> transformFunction = getFunction(melody);
//		if (operatorType.getOperator().equals(Operator.R)) {
//			List<Note> reversedNotes = melody.getNotes().stream().map(n -> n.clone()).sorted(Comparator.reverseOrder()).collect(Collectors.toList());
//			List<Integer> positions = melody.getNotes().stream().map(n -> n.getPosition()).collect(Collectors.toList());
//			for (int i = 0; i < positions.size(); i++) {
//				Integer position = positions.get(i);
//				Note reversed = reversedNotes.get(i);
//				reversed.setPosition(position);
//			}
//			notes = reversedNotes.stream()
//					.map(transformFunction)
//					.filter(n -> n.getPosition() <= end)
//					.collect(toList());
//		} else {
//			notes = melody.getNotes().stream()
//					.map(transformFunction)
//					.filter(n -> n.getPosition() <= end)
//					.collect(toList());
//		}
//	}
	
	public void transformDependingOn(CpMelody melody){
		CpMelody clonedMelody = melody.clone();
		clonedMelody.T(0);
//		clonedMelody.updatePitches(5);
		clonedMelody.setStartOctave(4);
		clonedMelody.updatePitchesFromContour();
		notes = clonedMelody.getNotes().stream()
				.map(note -> { 
					note.setVoice(voice);
					note.setPosition(note.getPosition() + start);
					return note;
				})
				.filter(n -> n.getPosition() <= end)
				.collect(toList());
	}
	
	public void updatePitchesFromContour(){
		orderedPitchIntervals.clear();
		int size = notes.size() - 1;
		Note firsNote = notes.get(0);
		firsNote.setPitch((startOctave * 12) + firsNote.getPitchClass());
		firsNote.setOctave(startOctave);
		for (int i = 0; i < size; i++) {
			Note note = notes.get(i);
			Note nextNote = notes.get(i + 1);
			int difference = nextNote.getPitchClass() - note.getPitchClass();
			int direction = contour.get(i);
			int interval = calculateInterval(direction, difference);
			orderedPitchIntervals.add(interval);//store for pattern search
			nextNote.setPitch(note.getPitch() + interval);
			nextNote.setOctave(nextNote.getPitch()/12);
		}
	}
	
	protected int calculateInterval(int direction, int difference){
		if(isAscending(direction) && difference < 0){
			return difference + 12;
		}
		if(!isAscending(direction) && difference > 0){
			return difference - 12;
		}
		return difference;
	}

	private boolean isAscending(int direction) {
		if (direction == 1) {
			return true;
		} else {
			return false;
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
		int index = RandomUtil.getRandomIndex(notes);
		Note note = notes.get(index);
		int pitchClass = getScale().pickRandomPitchClass();
		note.setPitchClass(pitchClass);
//		int direction = RandomUtil.random(2);//lower or higher
//		updateNote(note, direction);
		
		updateContourDirections(index);
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

//	protected void updateNote(Note note, int direction) {
//		int index = notes.indexOf(note);
//		int previousIndex = index - 1;
//		int nextIndex = index + 1;
//		if (index == 0) {//first
//			note.setPitch(note.getPitchClass() + (note.getOctave() * 12));
//			List<Note> notesToChange = notes.subList(nextIndex, notes.size());
//			int octaveDifference = getOctaveIndirection(note, notesToChange.get(0), direction);
//			int octave = note.getOctave() + octaveDifference;
//			notesToChange.forEach(n -> {
//				n.updateNote(octave);
//			});
//		} else if (nextIndex == notes.size() - 1){//penultimate
//			Note previousNote = notes.get(previousIndex);
//			updatePitchFrom(previousNote, note, direction);
//			
//			int dir = RandomUtil.random(2);
//			Note lastNote = notes.get(nextIndex);
//			updatePitchFrom(note, lastNote, dir);
//		} else if (index == notes.size() - 1){//last
//			Note previousNote = notes.get(previousIndex);
//			updatePitchFrom(previousNote, note, direction);
//		} else {
//			Note previousNote = notes.get(previousIndex);
//			updatePitchFrom(previousNote, note, direction);
//			
//			int dir = RandomUtil.random(2);
//			List<Note> notesToChange = notes.subList(nextIndex, notes.size());
//			int octaveDifference = getOctaveIndirection(note, notesToChange.get(0), dir);
//			int octave = note.getOctave() + octaveDifference;
//			notesToChange.forEach(n -> {
//				n.updateNote(octave);
//			});
//		}
//	}

	public void addRandomRhythmNote(int minimumValue) {
			int newPosition = RandomUtil.randomInt(this.start/minimumValue + 1, this.end/minimumValue) * minimumValue;
			int pitchClass = getScale().pickRandomPitchClass();
			insertRhythm(newPosition, pitchClass);
	}

	protected void insertRhythm(int newPosition, int pitchClass) {
		List<Integer> positions = notes.stream().map(n -> n.getPosition()).collect(toList());
		if (!positions.contains(newPosition)) {
			Note note = note().pos(newPosition).voice(voice).pc(pitchClass).build();
			insertNote(note);
		}
	}
	
	protected void insertContourDirections(int index){
		contour.add(index, RandomUtil.randomAscendingOrDescending());
		updatePreviousContour(index, RandomUtil.randomAscendingOrDescending());
	}
	
	protected int getOctavePreviousNote(Note note){
		int index = notes.indexOf(note);
		return notes.get(index - 1).getOctave();
	}
	
	public void updateMelodyBetween(){
		for (Note note : notes) {
			while (note.getPitch() < instrument.getLowest()) {
				note.transposePitch(12);
			}
			while (note.getPitch() > instrument.getHighest()) {
				note.transposePitch(-12);
			}
		}
	}

	public void removeNote() {
		int size = notes.size() - 1;
		if (size > 4) {
			int index = RandomUtil.randomInt(1, size);//don't remove outer notes
			removeNoteAt(index);
		}
	}

	public void removeNoteAt(int index) {
		notes.remove(index);
		removeContour(index, RandomUtil.randomAscendingOrDescending());
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

	public void updateArticulation() {
//		notes.forEach(note -> note.setArticulation(Note.DEFAULT_ARTICULATION));//reset?
		Articulation[] articulations = Articulation.class.getEnumConstants();
		Articulation articulation = RandomUtil.getRandomFromArray(articulations);
		Note note = RandomUtil.getRandomFromList(notes);
		note.setArticulation(articulation);
		
		Note removeArticulation = RandomUtil.getRandomFromList(notes);
		removeArticulation.setArticulation(Note.DEFAULT_ARTICULATION);
	}
	
	public CpMelody T(int steps){
		notes.forEach(note -> note.setPitchClass((note.getPitchClass() + steps) % 12));
		return this;
	}
	
	public CpMelody I(){
		notes.forEach(note -> note.setPitchClass((12 - note.getPitchClass()) % 12));
		return this;
	}
	
	public CpMelody M(int steps){
		notes.forEach(note -> note.setPitchClass((note.getPitchClass() * steps) % 12));
		return this;
	}
	
	public CpMelody R(){
		List<Integer> reversedPitchClasses = notes.stream().sorted(reverseOrder()).map(n -> n.getPitchClass()).collect(toList());
		for (int i = 0; i < notes.size(); i++) {
			Note note = notes.get(i);
			Integer pc = reversedPitchClasses.get(i);
			note.setPitchClass(pc);
		}
		return this;
	}
	
//	public void insert(int position, int minimumPulse, Integer[] pulses){
//		int noteLength = minimumPulse/pulses.length;
//		for (int i = 0; i < pulses.length; i++) {
//			if (pulses[i] == 1) {
//				int notePosition = position + (i * noteLength);
//				Note note = NoteBuilder.note().pos(notePosition).len(noteLength).pc(scale.pickRandomPitchClass()).voice(voice).build();
//				insertNote(note);
//			}
//		}
//		
//	}
	
	protected void insertNote(Note note) {
		notes.add(note);
		Collections.sort(notes);
		insertContourDirections(notes.indexOf(note));
	}

//	protected void remove(int position, int length){
//		notes = notes.stream().filter(n -> n.getPosition() < position || n.getPosition() >= (position + length))
//						.sorted()
//						.collect(Collectors.toList());
//	}
//	
//	public void insertNotes(int position, int minimumPulse, Integer[] pulses){
//		remove(position, minimumPulse);
//		insert(position, minimumPulse, pulses);
//	}

//	private Function<Note, Note> getFunction(CpMelody melody) {
//		int steps = operatorType.getSteps();
//		switch (operatorType.getOperator()) {
//			case RELATIVE:
//				return (n -> { 
//							Note note = n.clone();
//							note.setVoice(voice);
//							note.setPosition(n.getPosition() + start);
//							int index = melody.getScale().getIndex(n.getPitchClass());
//							int pitchClass = this.scale.getPitchClasses()[index];
//							note.setPitchClass(pitchClass);
//							note.setPitch(pitchClass + (n.getOctave() * 12));
//							
//							note.transposePitch(steps);
//							return note;
//						});
//			case T:
//				return  (n -> { 
//							Note note = n.clone();
//							note.setVoice(voice);
//							note.setPosition(n.getPosition() + start);
//							note.transposePitch(steps);
//							return note;
//					});
//			case I:
//				return  (n -> { 
//							Note note = n.clone();
//							note.setVoice(voice);
//							note.setPosition(n.getPosition() + start);
//							note.setPitchClass((12 - n.getPitchClass()) % 12);
//							note.setPitch(note.getPitchClass() + (n.getOctave() * 12));
//							
//							note.transposePitch(steps);
//							return note;
//					});
//			case R:
//				return  (n -> { 
//							Note note = n.clone();
//							note.setVoice(voice);
//							note.setPosition(n.getPosition() + start);
//							return note;
//					});
//			default:
//				break;
//			}
//		throw new IllegalArgumentException("Unknown transposition function: " + operatorType.getOperator());
//	}
	
	public boolean isRhythmMutable() {
		return rhythmMutable;
	}

	public void setRhythmMutable(boolean rhythmMutable) {
		this.rhythmMutable = rhythmMutable;
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
	
	public int getStart() {
		return start;
	}
	
	public void setOperatorType(OperatorType operatorType) {
		this.mutable = false;
		this.rhythmMutable = false;
		this.operatorType = operatorType;
	}
	
	public OperatorType getOperatorType() {
		return operatorType;
	}
	
	public boolean isDenpendant(){
		return(operatorType != null)?true:false;
	}
	
	public void dependsOn(int voice){
		this.dependingVoice = voice;
	}
	
	public int getDependingVoice() {
		return dependingVoice;
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

	public List<Note> getNotes() {
		return notes;
	}
	
	public Scale getScale() {
		return scale;
	}
	
	public void setStartOctave(int startOctave) {
		this.startOctave = startOctave;
	}
	
	public int getStartOctave() {
		return startOctave;
	}
	
	public List<Integer> getOrderedPitchIntervals() {
		return orderedPitchIntervals;
	}
	
}
