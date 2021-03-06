package cp.model.melody;

import cp.composition.beat.BeatGroup;
import cp.model.TimeLine;
import cp.model.TimeLineKey;
import cp.model.note.Dynamic;
import cp.model.note.Note;
import cp.model.note.Scale;
import cp.model.timbre.Timbre;
import cp.nsga.operator.mutation.MutationType;
import cp.out.instrument.Articulation;
import cp.out.instrument.Technical;
import cp.util.RandomUtil;
import cp.util.Util;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static java.util.stream.Collectors.toList;

public class CpMelody implements Comparable<CpMelody> {

	private static final Logger LOGGER = LoggerFactory.getLogger(CpMelody.class);
	
	private int voice;
	private boolean mutable = true;
	private boolean replaceable = true;
	private List<Note> notes;
	private int start;
	private int end;
	private List<Integer> contour = new ArrayList<>();
	private BeatGroup beatGroup;
	private Tonality tonality = Tonality.TONAL;
	private List<TimeLineKey> timeLineKeys;
	private int notesSize;
	private MutationType mutationType = MutationType.ALL;
	private int length;
	private int melodyNumber;
	
	public CpMelody(List<Note> notes, int voice, int start, int end) {
		this.voice = voice;
		this.start = start;
		this.end = end;
		this.notes = notes;
		this.length = end - start;
		updateContour();
	}

	private CpMelody(CpMelody anotherMelody) {
		this.notes = anotherMelody.getNotes().stream().map(Note::clone).collect(toList());
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
		this.voice = anotherMelody.getVoice();
		this.start = anotherMelody.getStart();
		this.end = anotherMelody.getEnd();
		this.contour = new ArrayList<>(anotherMelody.getContour());
		this.replaceable = anotherMelody.isReplaceable();
		this.beatGroup = anotherMelody.getBeatGroup();
		this.timeLineKeys = anotherMelody.getTimeLineKeys();
		this.tonality = anotherMelody.getTonality();
		this.notesSize = anotherMelody.getNotesSize();
		this.mutationType = anotherMelody.getMutationType();
        this.length = anotherMelody.getLength();
        this.melodyNumber = anotherMelody.getMelodyNumber();
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
	
	public void updateContour() {
        this.contour.clear();
        notes.stream().filter(n -> !n.isRest()).forEach(note -> contour.add(RandomUtil.randomAscendingOrDescending()));
	}

    public void updateContourAscending() {
        this.contour.clear();
        notes.stream().filter(n -> !n.isRest()).forEach(note -> contour.add(1));
        contour.set(contour.size() - 1, -1);
    }

    public void updateContourDescending() {
        this.contour.clear();
        notes.stream().filter(n -> !n.isRest()).forEach(note -> contour.add(-1));
        contour.set(contour.size() - 1, 1);
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

	public void updateRandomNote(int pitchClass) {
		List<Note> notesNoRest = getNotesNoRest();
		if (!notesNoRest.isEmpty()) {
			int index = RandomUtil.getRandomIndex(notesNoRest);
			Note note = notesNoRest.get(index);
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
	
//	protected void removeContour(int index, int direction){
//		contour.remove(index);
//		if (index > 0) {
//			contour.set(index - 1, direction);
//		}
//	}
//
//	public void removeNote() {
//		List<Note> notesNoRest = getNotesNoRest();
//		int size = notesNoRest.size();
//		if (size > 1) {
//			int index = RandomUtil.randomInt(0, size);
//			notesNoRest.remove(index);
//			removeContour(index, RandomUtil.randomAscendingOrDescending());
//			LOGGER.info("rhythm note removed");
//		}
//	}
	
	public void updateNotes(List<Note> melodyNotes) {
		this.notes = melodyNotes;
		updateContour();
	}

    public void updateNotePositions(int start, int voice) {
        notes.forEach(n -> {
            n.setPosition(n.getPosition() + start);
            n.setVoice(voice);
        });
    }

    public void updateRhythmNotes(List<Note> rhythmNotes) {
		List<Note> melodyNotes = getNotesNoRest();
		for (int i = 0, j = 0; i < rhythmNotes.size(); i++) {
			Note rhythmNote = rhythmNotes.get(i);
            rhythmNote.setPosition(start + rhythmNote.getPosition());
			if(!rhythmNote.isRest()){
                Note note = melodyNotes.get(j);
				rhythmNote.setPitch(note.getPitch());
				rhythmNote.setPitchClass(note.getPitchClass());
				rhythmNote.setOctave(note.getOctave());
				j++;
			}
		}
        this.notes = rhythmNotes;
    }
	
	public void updateArticulation(Articulation articulation) {
		List<Note> notesNoRest = getNotesNoRest();
		if (notesNoRest.size() > 1) {
			//		notes.forEach(note -> note.setArticulation(Note.DEFAULT_ARTICULATION));//reset?
			Note note = RandomUtil.getRandomFromList(notesNoRest);
			note.setArticulation(articulation);
			switch (note.getDynamic()){
				case SFZ:
				case FP:
					note.setDynamic(null);
			}

//			Note removeArticulation = RandomUtil.getRandomFromList(notesNoRest);
//			removeArticulation.setArticulation(null);
		}
	}

	public void updateDynamic(Dynamic dynamic) {
		List<Note> notesNoRest = getNotesNoRest();
		if (notesNoRest.size() > 1) {
            //		notes.forEach(note -> note.setArticulation(Note.DEFAULT_ARTICULATION));//reset?
//			Note note = RandomUtil.getRandomFromList(notesNoRest);
//			note.setDynamic(dynamic);
//			note.setDynamicLevel(dynamic.getLevel());
            List<Note> sublist = RandomUtil.getRandomSublistFromList(notesNoRest);

            for (Note note : sublist) {
                note.setDynamic(dynamic);
			    note.setDynamicLevel(dynamic.getLevel());
			    switch (dynamic){
					case SFZ:
					case FP:
						note.setArticulation(null);
				}
            }

//            Note removeArticulation = RandomUtil.getRandomFromList(notesNoRest);
//			removeArticulation.setDynamic(Note.DEFAULT_DYNAMIC);
		}
	}

	public void updateTechnical(Technical technical) {
		List<Note> notesNoRest = getNotesNoRest();
		if (notesNoRest.size() > 1) {
			//		notes.forEach(note -> note.setArticulation(Note.DEFAULT_ARTICULATION));//reset?
//			Note note = RandomUtil.getRandomFromList(notesNoRest);
//			note.setTechnical(technical);

            List<Note> sublist = RandomUtil.getRandomSublistFromList(notesNoRest);
            for (Note note : sublist) {
                note.setTechnical(technical);
            }

//			Note removeArticulation = RandomUtil.getRandomFromList(notesNoRest);
//			removeArticulation.setTechnical(Note.DEFAULT_TECHNICAL);
		}
	}
	
	protected int invertPitchClass(int functionalDegreeCenter, int pitchClass, Scale scale, Scale dependingScale, int key, int dependingKey){
		if (scale.getPitchClasses().length != dependingScale.getPitchClasses().length) {
			throw new IllegalArgumentException("Scales should have the same length");
		}
		
		int pitchClassKeyOfC = Util.convertToKeyOfC(pitchClass, key);
		
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

	protected int invertPitchClass(int functionalDegreeCenter, int pitchClass, Scale scale, int key){
		int pitchClassKeyOfC = Util.convertToKeyOfC(pitchClass, key);
		int invertedPC = scale.getInversedPitchClass(functionalDegreeCenter, pitchClassKeyOfC);
		return (invertedPC + key) % 12;
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
		
		int pitchClassKeyOfC = Util.convertToKeyOfC(pitchClass, key);
		
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

    protected int transpose(int pitchClass, Scale scale, int steps, int key){
        int pitchClassKeyOfC = Util.convertToKeyOfC(pitchClass, key);
        int transposedPC = scale.transposePitchClass(pitchClassKeyOfC, steps);
        return (transposedPC + key) % 12;
    }

//    /**
//     * convert to timeLineKey of timeline of first note in melody!
//     * @param timeLine the timeline
//     */
//    public void convertToTimelineKey(TimeLine timeLine) {
//        List<Note> notesNoRest = getNotesNoRest();
//        if (!notesNoRest.isEmpty()) {
//            Note firstNote = notesNoRest.get(0);
//            TimeLineKey timeLineKeyForPosition = timeLine.getTimeLineKeyAtPosition(firstNote.getPosition(), firstNote.getVoice());
//			notesNoRest.stream()
//                    .sorted()
//                    .forEach(note -> {
//                        int pitchClassKeyOfC = Util.convertToKeyOfC(note.getPitchClass(), timeLineKey.getKey().getInterval());
//                        int indexInC = timeLineKey.getScale().getIndex(pitchClassKeyOfC);
//                        int pitchClassTimelineKey = timeLineKeyForPosition.getScale().getPitchClasses()[indexInC];
//                        int pitchClass = (pitchClassTimelineKey + timeLineKeyForPosition.getKey().getInterval()) % 12;
//                        note.setPitchClass(pitchClass);
//                    });
//            this.setTimeLineKey(new TimeLineKey(timeLineKeyForPosition.getKey(), timeLineKeyForPosition.getScale()));
//        }
//    }

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

	public void inversePitchClasses(int functionalDegreeCenter, TimeLine timeLine) {
		notes.stream().filter(n -> !n.isRest())
				.sorted()
				.forEach(n -> {
					TimeLineKey timeLineKey = timeLine.getTimeLineKeyAtPosition(n.getPosition(), n.getVoice());
					int invertedPC = this.invertPitchClass(functionalDegreeCenter, n.getPitchClass(), timeLineKey.getScale(), timeLineKey.getKey().getInterval());
					n.setPitchClass(invertedPC);
				});
	}

	public void inverseRandom(Scale oldScale, Scale newScale){
		int functionalDegreeCenter = RandomUtil.getRandomNumberInRange(1, newScale.getPitchClasses().length);
		notes.stream().filter(n -> !n.isRest())
				.sorted()
				.forEach(n -> {
					int inversePc = newScale.inverse(oldScale , n.getPitchClass(),  functionalDegreeCenter);
					n.setPitchClass(inversePc);
				});
	}
	/**
	 * Only first note is checked on timeline
	 */
//	public void inversePitchClasses(TimeLine timeLine) {
//        List<Note> notesNoRest = getNotesNoRest();
//        if (!notesNoRest.isEmpty()) {
//			Note firstNote = notesNoRest.get(0);
//			TimeLineKey timeLineKeyForPosition = timeLine.getTimeLineKeyAtPosition(firstNote.getPosition(), firstNote.getVoice());
//            Scale timeLineScale = timeLineKeyForPosition.getScale();
//            int functionalDegreeCenter = RandomUtil.getRandomNumberInRange(1, timeLineScale.getPitchClasses().length);
//            notesNoRest.stream()
//                    .sorted()
//                    .forEach(n -> {
//						int pitchClassKeyOfC = Util.convertToKeyOfC(n.getPitchClass(), this.timeLineKey.getKey().getInterval());
//						int indexInC = timeLineKey.getScale().getIndex(pitchClassKeyOfC);
//						int pcInTimelineScale = timeLineScale.getPitchClasses()[indexInC];
//						int inversePcInTimelineScale = timeLineKeyForPosition.getScale().getInversedPitchClassForIndex(functionalDegreeCenter, indexInC);
//						int pitchClassInTimeline = (inversePcInTimelineScale + timeLineKeyForPosition.getKey().getInterval()) % 12;
//                        n.setPitchClass(pitchClassInTimeline);
//                    });
//		}
//	}

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

    public void transposePitchClasses(int steps, TimeLine timeLine){
        List<Note> notesNoRest = getNotesNoRest();
        notesNoRest.stream()
                .sorted()
                .forEach(n -> {
                    TimeLineKey timeLineKey = timeLine.getTimeLineKeyAtPosition(n.getPosition(), n.getVoice());
                    int transposedPc = this.transpose(n.getPitchClass(), timeLineKey.getScale(), steps, timeLineKey.getKey().getInterval());
                    n.setPitchClass(transposedPc);
                });
    }

	/**
	 * Only first note is checked on timeline
	 */
	public void transposePitchClasses(TimeLine timeLine){
		if (!notes.isEmpty()) {
			Note firstNote = notes.get(0);
			TimeLineKey timeLineKey = timeLine.getTimeLineKeyAtPosition(firstNote.getPosition(), firstNote.getVoice());
			int steps = RandomUtil.getRandomNumberInRange(0,timeLineKey.getScale().getPitchClasses().length);
			notes.stream().filter(n -> !n.isRest())
                    .sorted()
                    .forEach(n -> {
                        int transposedPc = this.transpose(n.getPitchClass(), timeLineKey.getScale(), steps, timeLineKey.getKey().getInterval());
                        n.setPitchClass(transposedPc);
                    });
		}
	}

    public void transposePitchClasses(int step){
        if (!notes.isEmpty()) {
            notes.stream().filter(n -> !n.isRest())
                    .forEach(n -> {
                        n.setPitchClass((n.getPitchClass() + step) % 12);
                    });
        }
    }

	public CpMelody T(int steps){
		this.getNotesNoRest().forEach(note -> note.setPitchClass((note.getPitchClass() + steps) % 12));
		return this;
	}

	public CpMelody I(){
		this.getNotesNoRest().forEach(note -> note.setPitchClass((12 - note.getPitchClass()) % 12));
		return this;
	}

	public CpMelody M(int steps){
		this.getNotesNoRest().forEach(note -> note.setPitchClass((note.getPitchClass() * steps) % 12));
		return this;
	}

	public void setNotes(List<Note> notes){
		this.notes = notes;
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

    public Tonality getTonality() {
        return tonality;
    }

    public void setTonality(Tonality tonality) {
        this.tonality = tonality;
    }

    public List<TimeLineKey> getTimeLineKeys() {
        return timeLineKeys;
    }

    public void setTimeLineKeys(List<TimeLineKey> timeLineKeys) {
        this.timeLineKeys = timeLineKeys;
    }

    public MutationType getMutationType() {
        return mutationType;
    }

    public void setMutationType(MutationType mutationType) {
        this.mutationType = mutationType;
    }

    public int getLength() {
        return length;
    }

    public void setLength(int length) {
        this.length = length;
    }

    @Override
	public int compareTo(CpMelody melody) {
		if(this.start < melody.getStart()){
			return -1;
		}else if(this.start > melody.getStart()){
			return 1;
		}
		return 0;
	}

	public void updateNotes(Timbre timbre, int start){
        notes.forEach(n -> {
            n.setDynamic(timbre.getDynamic());
            n.setDynamicLevel(timbre.getDynamic().getLevel());
            n.setTechnical(timbre.getTechnical());
            n.setPosition(n.getPosition() + start);
        });
    }

	public CpMelody Retrograde() {
		Collections.reverse(contour);
        for (Note note : notes) {
            if (note.getPosition() > start) {
                note.setPosition(note.getPosition() - end);
            } else if (note.getPosition() < start) {
                note.setPosition(note.getPosition() + start);
            }
        }
		return this;
	}

    public int getNotesSize() {
        return notesSize;
    }

    public void setNotesSize(int notesSize) {
        this.notesSize = notesSize;
    }

    public void symmetricalInverse(int axisHigh, int axisLow){
		List<Note> notesNoRest = getNotesNoRest();
		for (Note note : notesNoRest) {
			int interval = note.getPitchClass() - axisHigh;
			int pitchClass = (axisLow - interval + 12) % 12;
            note.setPitchClass(pitchClass);
            int pitch = pitchClass + (note.getOctave() * 12);
            note.setPitch(pitch);
            note.setOctave(note.getPitch()/12);
		}
	}

    public boolean hasScale() {
        if (beatGroup != null) {
            return beatGroup.getTonality() != null;
        }
        return false;
    }

    public boolean hasPCGenerators() {
        if (beatGroup != null) {
            return !beatGroup.getPitchClassGenerators().isEmpty();
        }
        return false;
    }

    public TimeLineKey getTimeLineKeyAtPosition(int position){
        if(timeLineKeys.size() == 1){
	        return timeLineKeys.get(0);
        }
//        Map<TimeLineKey, List<Note>> notesPerTimeLineKey = new HashMap<>();
//        for (TimeLineKey timeLineKey : timeLineKeys) {
//            List<Note> notesTimeLineKey = this.notes.stream()
//                    .filter(note -> note.getPosition() >= timeLineKey.getStart() && note.getPosition() < timeLineKey.getEnd())
//                    .collect(Collectors.toList());
//            notesPerTimeLineKey.put(timeLineKey, notesTimeLineKey);
//        }

        Optional<TimeLineKey> optional = timeLineKeys.stream().filter(k -> k.getStart() <= position && position < k.getEnd()).findFirst();
        if(optional.isPresent()){
            return optional.get();
        }
        throw new IllegalArgumentException("No Key found at position; " + position + " for voice: " + voice);
    }

    public void updateTimeLineKeysNotes() {
        for (TimeLineKey timeLineKey : timeLineKeys) {
            this.notes.stream()
                    .filter(note -> note.getPosition() >= timeLineKey.getStart() && note.getPosition() < timeLineKey.getEnd())
                    .forEach(note -> note.setTimeLineKey(timeLineKey));
        }
    }

    public void insertNoteRandom(Note insertNote){
        int randomIndex = RandomUtil.getRandomIndex(notes);
        insertNote.setVoice(this.voice);
        notes.add(randomIndex, insertNote);
        contour.add(randomIndex, RandomUtil.randomAscendingOrDescending());
        end = end + insertNote.getLength();
        length = length + insertNote.getLength();
        updatePositions();
    }

    public void insertNoteRandom(){
        int randomIndex = RandomUtil.getRandomIndex(notes);
        Note insertNote = notes.get(randomIndex).clone();
        insertNote.setPosition(0);
        randomIndex = RandomUtil.getRandomIndex(notes);
        notes.add(randomIndex, insertNote);
        contour.add(randomIndex, RandomUtil.randomAscendingOrDescending());
        end = end + insertNote.getLength();
        length = length + insertNote.getLength();
        updatePositions();
    }

    public void insertNotesRandom(){
        List<Note> sublist = RandomUtil.getRandomSublistFromList(notes);
        List<Note> clonedSublist = sublist.stream().map(note -> {
            Note clone = note.clone();
            clone.setPosition(0);
            return clone;
        }).collect(toList());
        int randomIndex = RandomUtil.getRandomIndex(notes);
        randomIndex = RandomUtil.getRandomIndex(notes);
        notes.addAll(randomIndex, clonedSublist);
        List<Integer> contourSublist = clonedSublist.stream().map(note -> RandomUtil.randomAscendingOrDescending()).collect(toList());
        contour.addAll(randomIndex, contourSublist);
        int length = clonedSublist.stream().mapToInt(note -> note.getLength()).sum();
        end = end + length;
        this.length = this.length + length;
        updatePositions();
    }

    public void insertNotesOrdered(){
        List<Note> sublist = RandomUtil.getRandomSublistFromList(notes);
        List<Note> clonedSublist = sublist.stream().map(note -> {
            Note clone = note.clone();
            clone.setPosition(0);
            return clone;
        }).collect(toList());
        int startIndex = notes.indexOf(sublist.get(0));
        int randomIndex = RandomUtil.getRandomNumberInRange(startIndex, notes.size());
        notes.addAll(randomIndex, clonedSublist);
        List<Integer> contourSublist = clonedSublist.stream().map(note -> RandomUtil.randomAscendingOrDescending()).collect(toList());
        contour.addAll(randomIndex, contourSublist);
        int length = clonedSublist.stream().mapToInt(note -> note.getLength()).sum();
        end = end + length;
        this.length = this.length + length;
        updatePositions();
    }

    public void removeNoteRandom(){
        int randomIndex = RandomUtil.getRandomIndex(notes);
        Note removedNote = notes.remove(randomIndex);
        contour.remove(randomIndex);
        end = end - removedNote.getLength();
        length = length - removedNote.getLength();
        updatePositions();
    }

    public void updateNoteLengthRandom(int noteLength){
        int randomIndex = RandomUtil.getRandomIndex(notes);
        Note note = notes.get(randomIndex);
        end = end + noteLength - note.getLength();
        length = length + noteLength - note.getLength();

        note.setLength(noteLength);
        note.setDisplayLength(noteLength);
        updatePositions();
    }

    public void updateLastNoteLength(int noteLength){
        Note note = notes.get(notes.size() - 1);
        end = end + noteLength - note.getLength();
        length = length + noteLength - note.getLength();

        note.setLength(noteLength);
        note.setDisplayLength(noteLength);
        updatePositions();
    }

    private void updatePositions() {
        int size = notes.size() - 1;
        for (int i = 0; i < size; i++) {
            Note note = notes.get(i);
            Note nextNote = notes.get(i + 1);
            nextNote.setPosition(note.getPosition() + note.getLength());
        }
    }

    public void updatePitchesFromContour(int startOctave){
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

    public int getMelodyNumber() {
        return melodyNumber;
    }

    public void setMelodyNumber(int melodyNumber) {
        this.melodyNumber = melodyNumber;
    }
}
