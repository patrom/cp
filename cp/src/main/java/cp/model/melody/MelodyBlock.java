package cp.model.melody;

import cp.model.TimeLine;
import cp.model.TimeLineKey;
import cp.model.contour.Contour;
import cp.model.note.Dynamic;
import cp.model.note.Note;
import cp.model.rhythm.DurationConstants;
import cp.out.instrument.Articulation;
import cp.out.instrument.Instrument;
import cp.out.instrument.Technical;
import cp.util.RandomUtil;
import cp.util.Util;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import static java.util.Comparator.reverseOrder;
import static java.util.stream.Collectors.toList;

public class MelodyBlock {

	private List<CpMelody> melodyBlocks = new ArrayList<>();
	private int startOctave;
	private int dependingVoice = -1;
	private int voice = -1;
	private int offset;
	private boolean mutable = true;
	private boolean calculable = true;
	
	public MelodyBlock(int startOctave, int voice) {
		this.startOctave = startOctave;
		this.voice = voice;
	}
	
	private MelodyBlock(MelodyBlock anotherBlock) {
		this.melodyBlocks = anotherBlock.getMelodyBlocks().stream().map(m -> m.clone()).collect(toList());
		clone(anotherBlock);
	}

	private MelodyBlock(MelodyBlock anotherBlock, int voice) {
		this.melodyBlocks = anotherBlock.getMelodyBlocks().stream().map(m -> m.clone(voice)).collect(toList());
		clone(anotherBlock);
	}

	private MelodyBlock(MelodyBlock anotherBlock, int end, int voice) {
		this.melodyBlocks = anotherBlock.getMelodyBlocks().stream()
				.filter(m -> m.getStart() < end)
				.map(m -> m.clone(end, voice))
				.collect(toList());
		clone(anotherBlock);
	}
	
	private void clone(MelodyBlock anotherBlock) {
		this.startOctave = anotherBlock.getStartOctave();
		this.mutable = anotherBlock.isMutable();
		this.dependingVoice = anotherBlock.getDependingVoice();
		this.voice = anotherBlock.getVoice();
		this.offset = anotherBlock.getOffset();
		this.calculable = anotherBlock.isCalculable();
	}

	public MelodyBlock clone() {
		return new MelodyBlock(this);
	}

	public MelodyBlock clone(int voice) {
		MelodyBlock melodyBlock = new MelodyBlock(this, voice);
		melodyBlock.setVoice(voice);
		return melodyBlock;
	}
	
	public MelodyBlock clone(int end, int voice) {
		MelodyBlock melodyBlock = new MelodyBlock(this, end, voice);
		melodyBlock.setVoice(voice);
		return melodyBlock;
	}
	
	public List<Note> getMelodyBlockNotes(){
		return melodyBlocks.stream()
				.flatMap(m -> m.getNotesNoRest().stream()).sorted().collect(toList());
	}

	public Note getNoteAtPosition(int position){
		List<Note> notes = getMelodyBlockNotes();
		int size = notes.size() - 1;
		for (int i = 0; i < size; i++) {
			 Note note = notes.get(i);
			 Note nextNote = notes.get(i + 1);
			 if(position >= note.getPosition()  && position < nextNote.getPosition()){
			 	return note;
			 }
		}
		return notes.get(size);
	}
	
	public List<Note> getMelodyBlockNotesWithRests(){
		return melodyBlocks.stream().flatMap(m -> m.getNotes().stream()).sorted().collect(toList());
	}
	
	public List<Integer> getMelodyBlockContour(){
		return melodyBlocks.stream().flatMap(m -> m.getContour().stream()).collect(toList());
	}
	
	public void updateArticulation(Articulation articulation){
		CpMelody melody = RandomUtil.getRandomFromList(melodyBlocks);
		melody.updateArticulation(articulation);
	}

	public void updateDynamic(Dynamic dynamic){
		CpMelody melody = RandomUtil.getRandomFromList(melodyBlocks);
		melody.updateDynamic(dynamic);
	}

	public void updateTechnical(Technical technical){
		CpMelody melody = RandomUtil.getRandomFromList(melodyBlocks);
		melody.updateTechnical(technical);
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

	public void updatePitchesFromInstrument(Instrument instrument){
		List<Note> notes = getMelodyBlockNotes();
		int size = notes.size() - 1;
		Note firstNote = notes.get(0);
		int startOctave = instrument.pickRandomOctaveFromRange();
		firstNote.setPitch((startOctave * 12) + firstNote.getPitchClass());
		firstNote.setOctave(startOctave);
		for (int i = 0; i < size; i++) {
			Note note = notes.get(i);
			Note nextNote = notes.get(i + 1);
			int difference = nextNote.getPitchClass() - note.getPitchClass();
			int octave = instrument.pickRandomOctaveFromRange();
			nextNote.setPitch(note.getPitchClass() + (12 * octave));
			nextNote.setOctave(octave);
		}
	}

	public void updatePitchesFromContour(TimeLine timeLine){
		List<Note> notes = getMelodyBlockNotes();
		int size = notes.size() - 1;
		Note firstNote = notes.get(0);
		firstNote.setPitch((startOctave * 12) + firstNote.getPitchClass());
		firstNote.setOctave(startOctave);
		for (int i = 0; i < size; i++) {
			Note note = notes.get(i);
			Note nextNote = notes.get(i + 1);
			int difference = nextNote.getPitchClass() - note.getPitchClass();
			Contour contour = timeLine.getContourAtPosition(note.getPosition(), note.getVoice());
			int interval = Util.calculateInterval(contour.getDirection(), difference);
			nextNote.setPitch(note.getPitch() + interval);
			nextNote.setOctave(nextNote.getPitch()/12);
		}
	}
	
	public MelodyBlock Trelative(int steps, TimeLine timeLine){
		melodyBlocks.forEach(m -> m.transposePitchClasses(steps, offset, timeLine));
		return this;
	}
	

	public MelodyBlock Irelative(int functionalDegreeCenter, TimeLine timeLine){
		melodyBlocks.forEach(m -> m.inversePitchClasses(functionalDegreeCenter, offset, timeLine));
		return this;
	}
	
	public MelodyBlock T(int steps){
		melodyBlocks.forEach(m -> m.T(steps));
		return this;
	}
	
	public MelodyBlock I(){
		melodyBlocks.forEach(m -> m.I());
		return this;
	}
	
	public MelodyBlock M(int steps){
		melodyBlocks.forEach(m -> m.M(steps));
		return this;
	}

	public MelodyBlock augmentation(double factor, TimeLine timeLine){
		for (CpMelody melodyBlock : melodyBlocks) {
//			BeatGroup beatgroup = melodyBlock.getBeatGroup();
//			BeatGroup clonedBeatgroup = beatgroup.clone(beatgroup.getLength() * factor);
//			melodyBlock.setBeatGroup(clonedBeatgroup);
			melodyBlock.getNotes().forEach(note -> {
				int length = (int) (note.getLength() * factor);
				int newPosition = (int)(note.getPosition() * factor);
				if (!note.isRest()) {
					TimeLineKey timeLineKey = timeLine.getTimeLineKeyAtPosition(note.getPosition(), note.getVoice());
					TimeLineKey dependingTimeLineKey = timeLine.getTimeLineKeyAtPosition(newPosition + offset, note.getVoice());
					if (!timeLineKey.getKey().getStep().equals(dependingTimeLineKey.getKey().getStep())) {
						int transposedPc = melodyBlock.transposePitchClass(note.getPitchClass(), timeLineKey.getScale(), dependingTimeLineKey.getScale(), timeLineKey.getKey().getInterval(), dependingTimeLineKey.getKey().getInterval(), 0);
						note.setPitchClass(transposedPc);
					}
				}
				note.setLength(length);
				note.setDisplayLength(length);
				note.setPosition(newPosition);
			});
		}
		return this;
	}

	public MelodyBlock diminution(double factor, TimeLine timeLine){
		for (CpMelody melodyBlock : melodyBlocks) {
//			BeatGroup beatgroup = melodyBlock.getBeatGroup();
//			BeatGroup clonedBeatgroup = beatgroup.clone(beatgroup.getLength() * factor);
//			melodyBlock.setBeatGroup(clonedBeatgroup);
			melodyBlock.getNotes().forEach(note -> {
				int length = (int) (note.getLength() * factor);
				if (length >= DurationConstants.SIXTEENTH_TRIPLET) {
					int newPosition = (int)(note.getPosition() * factor);
					if (!note.isRest()) {
						TimeLineKey timeLineKey = timeLine.getTimeLineKeyAtPosition(note.getPosition(), note.getVoice());
						TimeLineKey dependingTimeLineKey = timeLine.getTimeLineKeyAtPosition(newPosition + offset, note.getVoice());
						if (!timeLineKey.getKey().getStep().equals(dependingTimeLineKey.getKey().getStep())) {
							int transposedPc = melodyBlock.transposePitchClass(note.getPitchClass(), timeLineKey.getScale(), dependingTimeLineKey.getScale(), timeLineKey.getKey().getInterval(), dependingTimeLineKey.getKey().getInterval(), 0);
							note.setPitchClass(transposedPc);
						}
					}
					note.setLength(length);
					note.setDisplayLength(length);
					note.setPosition(newPosition);
				}else{
					throw new IllegalStateException("Diminuation length is too short: " + length);
				}
			});
		}
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

	public void updateMelodyBlock(MelodyBlock melodyBlock, int position){
		int end = melodyBlock.getLastMelody().getEnd() + position;
		int endBlock = this.getLastMelody().getEnd();
		List<CpMelody> filteredBlock = melodyBlocks.stream().filter(m -> m.getStart() < position || m.getStart() >= end).collect(toList());
		List<CpMelody> melodyBlocksToInsert = melodyBlock.getMelodyBlocks();
		List<CpMelody> clonedMelodyBlock = melodyBlocksToInsert.stream()
				.filter(m -> m.getEnd() <= endBlock)
				.map(m -> m.clone())
				.map(m -> {
					m.getNotes()
							.forEach(n -> {
								n.setPosition(position + n.getPosition());
								n.setVoice(this.voice);
							});
					m.setVoice(this.voice);
					m.setStart(position + m.getStart());
					m.setEnd(position + m.getEnd());
					return m;
				})
				.collect(Collectors.toList());
		melodyBlocks.clear();
		melodyBlocks.addAll(filteredBlock);
		melodyBlocks.addAll(clonedMelodyBlock);
		Collections.sort(melodyBlocks);
	}

	public void setNotes(List<Note> notes){
//		melodyBlocks.clear();
		CpMelody melody = melodyBlocks.get(0);
		melody.setNotes(notes);
	}

	public List<Note> filterMelody(int lengthToExclude){
        List<Note> melodyNotes = getMelodyBlockNotes();
        List<Note> filteredNotes = new ArrayList<>();
        int size = melodyNotes.size() - 2;
        if(melodyNotes.size() > 2){
            filteredNotes.add(melodyNotes.get(0));
            for (int i = 0; i < size; i++) {
                Note firstNote = melodyNotes.get(i);
                Note middleNote = melodyNotes.get(i + 1);
                Note lastNote = melodyNotes.get(i + 2);
                if(middleNote.getLength() > lengthToExclude
                        || (middleNote.getPositionWeight() > firstNote.getPositionWeight()
                        || middleNote.getPositionWeight() > lastNote.getPositionWeight())){
                    filteredNotes.add(middleNote);
                }

            }
            filteredNotes.add(melodyNotes.get(melodyNotes.size() - 1));
        }
        return filteredNotes;
    }

	public void repeatMelody(List<CpMelody> melodies, List<CpMelody> melodiesToReplace, TimeLine timeline) {
        int positionDifference = melodiesToReplace.get(0).getStart() - melodies.get(0).getStart();
        melodies.stream()
                .map(m -> {
                    m.setStart(m.getStart() + positionDifference);
                    m.setEnd(m.getEnd() + positionDifference);
                    m.transposePitchClasses(0, positionDifference, timeline);
                    return m;
                })
                .flatMap(m -> m.getNotes().stream())
                .forEach(n -> n.setPosition(n.getPosition() + positionDifference));
        int i = 0;
        for (CpMelody clonedMelody : melodies) {
            int index = melodyBlocks.indexOf(melodiesToReplace.get(i));
            melodyBlocks.set(index, clonedMelody);
            i++;
        }
	}

	public void replaceMelody(CpMelody oldMelody, CpMelody newMelody){
        final int index = melodyBlocks.indexOf(oldMelody);
        melodyBlocks.remove(oldMelody);
		melodyBlocks.add(index, newMelody);
	}

	public boolean isMutable() {
		return mutable;
	}
	
	public void setMutable(boolean mutable) {
		this.mutable = mutable;
	}

	public void setVoice(int voice) {
		this.voice = voice;
	}
	
	public int getVoice() {
		return voice;
	}
	
	public int getStartOctave() {
		return startOctave;
	}

	public boolean isCalculable() {
		return calculable;
	}

	public void setCalculable(boolean calculable) {
		this.calculable = calculable;
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

	public CpMelody getFirstMelody(){
		return this.melodyBlocks.get(0);
	}
	
	public CpMelody getLastMelody(){
		Collections.sort(melodyBlocks);
		int last = this.melodyBlocks.size() - 1;
		return this.melodyBlocks.get(last);
	}

	public void setMelodyBlocks(List<CpMelody> melodyBlocks) {
		this.melodyBlocks = melodyBlocks;
	}
}
