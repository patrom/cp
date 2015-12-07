package cp.model.melody;

import static java.util.Comparator.reverseOrder;
import static java.util.stream.Collectors.toList;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Component;

import com.google.common.base.Objects;

import cp.model.note.Note;
import cp.out.instrument.Instrument;
import cp.util.RandomUtil;

public class MelodyBlock {

	private List<CpMelody> melodyBlocks = new ArrayList<>();
	private int startOctave;
	private int[] innerMetricDistance;
	private Instrument instrument;
	private OperatorType operatorType;
	private int dependingVoice = -1;
	private boolean mutable = true;
	private boolean rhythmMutable = true;
	private int voice = -1;
	private int offset;
	
	public MelodyBlock(int startOctave) {
		this.startOctave = startOctave;
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
		this.innerMetricDistance = anotherBlock.getInnerMetricDistance();
		this.instrument = anotherBlock.getInstrument();
		this.mutable = anotherBlock.isMutable();
		this.rhythmMutable = anotherBlock.isRhythmMutable();
		this.operatorType = anotherBlock.getOperatorType();
		this.dependingVoice = anotherBlock.getDependingVoice();
		this.voice = anotherBlock.getVoice();
		this.offset = anotherBlock.getOffset();
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
	
	public void updateRandomNote() {
		CpMelody melody = RandomUtil.getRandomFromList(melodyBlocks);
		melody.updateRandomNote();
	}
	
	public void addRandomRhythmNote(int minimumValue) {
		CpMelody melody = RandomUtil.getRandomFromList(melodyBlocks);
		melody.addRandomRhythmNote(minimumValue);
	}
	
	public void removeNote() {
		CpMelody melody = RandomUtil.getRandomFromList(melodyBlocks);
		melody.removeNote();
	}
	
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
	
	public Optional<CpMelody> getRandomMelody(){
		List<CpMelody> melodies = melodyBlocks.stream()
				.filter(m -> m.isReplaceable())
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
			int interval = calculateInterval(direction, difference);
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
	
	public void transformDependingOn(MelodyBlock melody){
		int end = melody.getLastMelody().getEnd();
		MelodyBlock melodyBlock = melody.clone(end - offset);
		melodyBlocks = melodyBlock.getMelodyBlocks();
		T(0);
		updatePitchesFromContour();
		melodyBlocks.stream()
			.flatMap(m -> m.getNotes().stream())
			.forEach(note -> { 
				note.setVoice(voice);
				note.setPosition(note.getPosition() + offset);
			});
	}
	
	public void updateMelodyBetween(){
		for (Note note : getMelodyBlockNotes()) {
			while (note.getPitch() < instrument.getLowest()) {
				note.transposePitch(12);
			}
			while (note.getPitch() > instrument.getHighest()) {
				note.transposePitch(-12);
			}
		}
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
		for (int i = 0; i < notes.size(); i++) {
			Note note = notes.get(i);
			Integer pc = reversedPitchClasses.get(i);
			note.setPitchClass(pc);
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
	
	public boolean isRhythmMutable() {
		return rhythmMutable;
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
	
	public int[] getInnerMetricDistance() {
		return innerMetricDistance;
	}

	public void setInnerMetricDistance(int[] innerMetricDistance) {
		this.innerMetricDistance = innerMetricDistance;
	}
	
	public boolean isDependant(){
		return(operatorType != null)?true:false;
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

}
