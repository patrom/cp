package cp.model.melody;

import static java.util.Comparator.reverseOrder;
import static java.util.stream.Collectors.toList;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.function.Predicate;

import cp.composition.timesignature.TimeConfig;
import cp.model.TimeLine;
import cp.model.TimeLineKey;
import cp.model.note.Note;
import cp.out.instrument.Instrument;
import cp.util.RandomUtil;
import cp.util.Util;

public class MelodyBlock {

	private List<CpMelody> melodyBlocks = new ArrayList<>();
	private int startOctave;
	private Instrument instrument;
	private OperatorType operatorType;
	private int dependingVoice = -1;
	private boolean mutable = true;
	private boolean rhythmMutable = true;
	private int voice = -1;
	private int offset;
	private TimeConfig timeConfig;
	private boolean rhythmDependant;
	
	public MelodyBlock(int startOctave, int voice) {
		this.startOctave = startOctave;
		this.voice = voice;
	}
	
	private MelodyBlock(MelodyBlock anotherBlock) {
		this.melodyBlocks = anotherBlock.getMelodyBlocks().stream().map(m -> m.clone()).collect(toList());
		clone(anotherBlock);
	}

	private MelodyBlock(MelodyBlock anotherBlock, int end) {
		this.melodyBlocks = anotherBlock.getMelodyBlocks().stream()
				.filter(m -> m.getStart() < end)
				.map(m -> m.clone(end))
				.collect(toList());
		clone(anotherBlock);
	}
	
	private void clone(MelodyBlock anotherBlock) {
		this.startOctave = anotherBlock.getStartOctave();
		this.instrument = anotherBlock.getInstrument();
		this.mutable = anotherBlock.isMutable();
		this.rhythmMutable = anotherBlock.isRhythmMutable();
		this.operatorType = anotherBlock.getOperatorType();
		this.dependingVoice = anotherBlock.getDependingVoice();
		this.voice = anotherBlock.getVoice();
		this.offset = anotherBlock.getOffset();
		this.timeConfig = anotherBlock.getTimeConfig();
		this.rhythmDependant = anotherBlock.isRhythmDependant();
	}

	@Override
	public MelodyBlock clone() {
		return new MelodyBlock(this);
	}
	
	public MelodyBlock clone(int end) {
		return new MelodyBlock(this, end);
	}
	
	public List<Note> getMelodyBlockNotes(){
		return melodyBlocks.stream()
				.flatMap(m -> m.getNotesNoRest().stream()).sorted().collect(toList());
	}
	
	public List<Note> getMelodyBlockNotesWithRests(){
		return melodyBlocks.stream().flatMap(m -> m.getNotes().stream()).sorted().collect(toList());
	}
	
	public List<Integer> getMelodyBlockContour(){
		return melodyBlocks.stream().flatMap(m -> m.getContour().stream()).collect(toList());
	}
	
//	public void updateRandomNote() {
//		CpMelody melody = RandomUtil.getRandomFromList(melodyBlocks);
//		melody.updateRandomNote();
//	}
	
	public void updateArticulation(){
		CpMelody melody = RandomUtil.getRandomFromList(melodyBlocks);
		melody.updateArticulation();
	}
	
	public void replaceMelody(CpMelody melody){
		List<CpMelody> melodies = melodyBlocks.stream()
				.filter(m -> m.isReplaceable())
				.filter(m -> m.getType() == melody.getType())
				.collect(toList());
		if (!melodies.isEmpty()) {
			CpMelody melodyToReplace = RandomUtil.getRandomFromList(melodies);
			int index = melodyBlocks.indexOf(melodyToReplace);
			
			melodyBlocks.set(index, melody);
		}
	}
	
	public Optional<CpMelody> getRandomMelody(Predicate<CpMelody> filterPredicate){
		List<CpMelody> melodies = melodyBlocks.stream()
				.filter(filterPredicate)
				.collect(toList());
		if (!melodies.isEmpty()) {
			return Optional.of(RandomUtil.getRandomFromList(melodies));
		}
		return Optional.empty();
	}
	
	public void updatePitchesFromContour(){
		List<Note> notes = getMelodyBlockNotes();
		List<Integer> contour = getMelodyBlockContour();
		int size = notes.size() - 1;
		Note firstNote = notes.get(0);
		firstNote.setPitch((startOctave * 12) + firstNote.getPitchClass());
		firstNote.setOctave(startOctave);
		for (int i = 0; i < size; i++) {
			Note note = notes.get(i);
			Note nextNote = notes.get(i + 1);
			int difference = nextNote.getPitchClass() - note.getPitchClass();
			int direction = contour.get(i);
			int interval = Util.calculateInterval(direction, difference);
			nextNote.setPitch(note.getPitch() + interval);
			nextNote.setOctave(nextNote.getPitch()/12);
		}
	}
	
	public void transformDependingOn(MelodyBlock dependingMelodyBlock, TimeLine timeLine){
		int end = dependingMelodyBlock.getLastMelody().getEnd();
		MelodyBlock melodyBlock = dependingMelodyBlock.clone(end - offset);
		melodyBlocks = melodyBlock.getMelodyBlocks();
		switch (operatorType.getOperator()) {
			case T:
				T(operatorType.getSteps());
				break;
			case I:
				I();
				break;
			case R:
				R();
				break;
			case M:
				M(operatorType.getSteps());
				break;
			case T_RELATIVE:
				Trelative(operatorType.getSteps(), timeLine);
				break;
			case I_RELATIVE:
				Irelative(operatorType.getFunctionalDegreeCenter(), timeLine);
				break;
			case RHYTHMIC:
			default:
				break;
		}
		updatePitchesFromContour();
		updateMelodyBetween(getMelodyBlockNotes());
		melodyBlocks.stream()
			.flatMap(m -> m.getNotes().stream())
			.forEach(note -> { 
				note.setVoice(voice);
				note.setPosition(note.getPosition() + offset);
			});
	}
	
	public void updateMelodyBetween(List<Note> notes){
		instrument.updateMelodyInRange(notes);
	}
	
	/**
	 * @param steps Steps are functional degrees of scale.
	 * @param timeLine 
	 * @param melody 
	 * @return
	 */
	public MelodyBlock Trelative(int steps, TimeLine timeLine){
		melodyBlocks.stream().forEach(m -> transposePitchClasses(steps, m, timeLine));
		return this;
	}
	
	protected void transposePitchClasses(int steps, CpMelody melody, TimeLine timeLine){
		melody.getNotes().stream().filter(n -> !n.isRest())
			.sorted()
			.forEach(n -> {
				TimeLineKey timeLineKey = timeLine.getTimeLineKeyAtPosition(n.getPosition(), n.getVoice());
				TimeLineKey dependingTimeLineKey = timeLine.getTimeLineKeyAtPosition(n.getPosition() + offset, n.getVoice());
				int transposedPc = melody.transposePitchClass(n.getPitchClass(), timeLineKey.getScale(), dependingTimeLineKey.getScale(), timeLineKey.getKey().getInterval(), dependingTimeLineKey.getKey().getInterval(), steps);
				n.setPitchClass(transposedPc);
			});
	}

	/**
	 * @param functional degree center pitch class
	 * @return
	 */
	public MelodyBlock Irelative(int functionalDegreeCenter, TimeLine timeLine){
		melodyBlocks.stream().forEach(m -> inversePitchClasses(functionalDegreeCenter, m, timeLine));
		return this;
	}
	
	private void inversePitchClasses(int functionalDegreeCenter, CpMelody melody, TimeLine timeLine) {
		melody.getNotes().stream().filter(n -> !n.isRest())
			.sorted()
			.forEach(n -> {
				TimeLineKey timeLineKey = timeLine.getTimeLineKeyAtPosition(n.getPosition(), n.getVoice());
				TimeLineKey dependingTimeLineKey = timeLine.getTimeLineKeyAtPosition(n.getPosition() + offset, n.getVoice());
				int invertedPC = melody.invertPitchClass(functionalDegreeCenter, n.getPitchClass(), timeLineKey.getScale(), dependingTimeLineKey.getScale(), timeLineKey.getKey().getInterval(), dependingTimeLineKey.getKey().getInterval());
				n.setPitchClass(invertedPC);
			});
	}

	public MelodyBlock T(int steps){
		melodyBlocks.stream().flatMap(m -> m.getNotesNoRest().stream()).forEach(note -> note.setPitchClass((note.getPitchClass() + steps) % 12));
		return this;
	}
	
	public MelodyBlock I(){
		melodyBlocks.stream().flatMap(m -> m.getNotesNoRest().stream()).forEach(note -> note.setPitchClass((12 - note.getPitchClass()) % 12));
		return this;
	}
	
	public MelodyBlock M(int steps){
		melodyBlocks.stream().flatMap(m -> m.getNotesNoRest().stream()).forEach(note -> note.setPitchClass((note.getPitchClass() * steps) % 12));
		return this;
	}
	
	public MelodyBlock R(){
		List<Note> notes = melodyBlocks.stream().flatMap(m -> m.getNotesNoRest().stream()).collect(toList());
		List<Integer> reversedPitchClasses = notes.stream().sorted(reverseOrder()).map(n -> n.getPitchClass()).collect(toList());
		int i = 0;
		for (CpMelody melody : melodyBlocks) {
			List<Note> melodyNotes = melody.getNotes();
			for (Note note : melodyNotes) {
				Integer pc = reversedPitchClasses.get(i);
				note.setPitchClass(pc);
				i++;
			}
		}
		return this;
	}
	
	public List<CpMelody> getMelodyBlocks() {
		return melodyBlocks;
	}
	
	public void addMelodyBlock(CpMelody melody) {
		melody.setVoice(voice);
		melody.getNotes().forEach(n -> n.setVoice(voice));
		this.melodyBlocks.add(melody);
	}

	public boolean isMutable() {
		return mutable;
	}
	
	public void setMutable(boolean mutable) {
		this.mutable = mutable;
	}
	
	public boolean isRhythmMutable() {
		return rhythmMutable;
	}
	
	public void setRhythmMutable(boolean rhythmMutable) {
		this.rhythmMutable = rhythmMutable;
	}

	public void setVoice(int voice) {
		this.voice = voice;
	}
	
	public int getVoice() {
		return voice;
	}
	
	public Instrument getInstrument() {
		return instrument;
	}
	
	public void setInstrument(Instrument instrument) {
		this.instrument = instrument;
	}
	
	public boolean isDependant(){
		return(operatorType != null)?true:false;
	}
	
	public boolean isRhythmDependant(){
		return rhythmDependant;
	}
	
	public void setRhythmDependant(boolean rhythmDependant) {
		this.rhythmDependant = rhythmDependant;
	}
	
	public int getStartOctave() {
		return startOctave;
	}

	public void setOperatorType(OperatorType operatorType) {
		this.mutable = false;
		this.rhythmMutable = false;
		this.operatorType = operatorType;
	}
	
	public OperatorType getOperatorType() {
		return operatorType;
	}
	
	public void dependsOn(int voice){
		this.dependingVoice = voice;
	}
	
	public int getDependingVoice() {
		return dependingVoice;
	}
	
	public int getOffset() {
		return offset;
	}
	
	public void setOffset(int offset) {
		this.offset = offset;
	}
	
	private CpMelody getLastMelody(){
		int last = this.melodyBlocks.size() - 1;
		return this.melodyBlocks.get(last);
	}

	public TimeConfig getTimeConfig() {
		return timeConfig;
	}

	public void setTimeConfig(TimeConfig timeConfig) {
		this.timeConfig = timeConfig;
	}
	
}
