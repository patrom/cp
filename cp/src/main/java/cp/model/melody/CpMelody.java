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

import static java.util.Comparator.reverseOrder;
import static java.util.stream.Collectors.toList;

public class CpMelody implements Comparable<CpMelody>{

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
	private TimeLineKey timeLineKey;
	private int notesSize;
	private MutationType mutationType;
	
	public CpMelody(List<Note> notes, int voice, int start, int end) {
		this.voice = voice;
		this.start = start;
		this.end = end;
		this.notes = notes;
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
		this.timeLineKey = anotherMelody.getTimeLineKey();
		this.tonality = anotherMelody.getTonality();
		this.notesSize = anotherMelody.getNotesSize();
		this.mutationType = anotherMelody.getMutationType();

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
//		if (notesNoRest.size() > 1) {
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
//		}
	}

	public void updateDynamic(Dynamic dynamic) {
		List<Note> notesNoRest = getNotesNoRest();
//		if (notesNoRest.size() > 1) {
			//		notes.forEach(note -> note.setArticulation(Note.DEFAULT_ARTICULATION));//reset?
//			Note note = RandomUtil.getRandomFromList(notesNoRest);
//			note.setDynamic(dynamic);
//			note.setDynamicLevel(dynamic.getLevel());
            List<Note> sublist = RandomUtil.getRandomListFromList(notesNoRest);
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
//		}
	}

	public void updateTechnical(Technical technical) {
		List<Note> notesNoRest = getNotesNoRest();
//		if (notesNoRest.size() > 1) {
			//		notes.forEach(note -> note.setArticulation(Note.DEFAULT_ARTICULATION));//reset?
//			Note note = RandomUtil.getRandomFromList(notesNoRest);
//			note.setTechnical(technical);

            List<Note> sublist = RandomUtil.getRandomListFromList(notesNoRest);
            for (Note note : sublist) {
                note.setTechnical(technical);
            }

//			Note removeArticulation = RandomUtil.getRandomFromList(notesNoRest);
//			removeArticulation.setTechnical(Note.DEFAULT_TECHNICAL);
//		}
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

    /**
     * convert to timeLineKey of timeline of first note in melody!
     * @param timeLine the timeline
     */
    public void convertToTimelineKey(TimeLine timeLine) {
        List<Note> notesNoRest = getNotesNoRest();
        if (!notesNoRest.isEmpty()) {
            Note firstNote = notesNoRest.get(0);
            TimeLineKey timeLineKeyForPosition = timeLine.getTimeLineKeyAtPosition(firstNote.getPosition(), firstNote.getVoice());
			notesNoRest.stream()
                    .sorted()
                    .forEach(note -> {
                        int pitchClassKeyOfC = Util.convertToKeyOfC(note.getPitchClass(), this.timeLineKey.getKey().getInterval());
                        int indexInC = timeLineKey.getScale().getIndex(pitchClassKeyOfC);
                        int pitchClassTimelineKey = timeLineKeyForPosition.getScale().getPitchClasses()[indexInC];
                        int pitchClass = (pitchClassTimelineKey + timeLineKeyForPosition.getKey().getInterval()) % 12;
                        note.setPitchClass(pitchClass);
                    });
            this.setTimeLineKey(new TimeLineKey(timeLineKeyForPosition.getKey(), timeLineKeyForPosition.getScale()));
        }
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
	public void inversePitchClasses(TimeLine timeLine) {
        List<Note> notesNoRest = getNotesNoRest();
        if (!notesNoRest.isEmpty()) {
			Note firstNote = notesNoRest.get(0);
			TimeLineKey timeLineKeyForPosition = timeLine.getTimeLineKeyAtPosition(firstNote.getPosition(), firstNote.getVoice());
            Scale timeLineScale = timeLineKeyForPosition.getScale();
            int functionalDegreeCenter = RandomUtil.getRandomNumberInRange(1, timeLineScale.getPitchClasses().length);
            notesNoRest.stream()
                    .sorted()
                    .forEach(n -> {
						int pitchClassKeyOfC = Util.convertToKeyOfC(n.getPitchClass(), this.timeLineKey.getKey().getInterval());
						int indexInC = timeLineKey.getScale().getIndex(pitchClassKeyOfC);
						int pcInTimelineScale = timeLineScale.getPitchClasses()[indexInC];
						int inversePcInTimelineScale = timeLineKeyForPosition.getScale().getInversedPitchClassIndex(functionalDegreeCenter, indexInC);
						int pitchClassInTimeline = (inversePcInTimelineScale + timeLineKeyForPosition.getKey().getInterval()) % 12;
                        n.setPitchClass(pitchClassInTimeline);
                    });
		}
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

    public TimeLineKey getTimeLineKey() {
        return timeLineKey;
    }

    public void setTimeLineKey(TimeLineKey timeLineKey) {
        this.timeLineKey = timeLineKey;
    }

    public MutationType getMutationType() {
        return mutationType;
    }

    public void setMutationType(MutationType mutationType) {
        this.mutationType = mutationType;
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

	public void updateNotes(Timbre timbre, int start){
        notes.forEach(n -> {
            n.setDynamic(timbre.getDynamic());
            n.setDynamicLevel(timbre.getDynamic().getLevel());
            n.setTechnical(timbre.getTechnical());
            n.setPosition(n.getPosition() + start);
        });
    }

	public CpMelody R() {
        int size = notes.size() - 1;
        Note lastNote = notes.get(size);
        int lastPosition = start + lastNote.getPosition();
		List<Note> reversed = notes.stream().sorted(reverseOrder()).collect(toList());
		Collections.reverse(contour);
        List<Integer> reversedPositions = notes.stream().sorted(reverseOrder()).map(Note::getPosition).collect(toList());
		this.notes = reversed;
        Note firstNote = notes.get(0);
        Integer firstPosition = firstNote.getPosition();
		for (int i = 1; i < size; i++) {
            Integer nextPosition = reversedPositions.get(i);
            Note note = notes.get(i);
            note.setPosition(start + (firstPosition - nextPosition));
		}
        firstNote.setPosition(start);
        lastNote = notes.get(size);
        lastNote.setPosition(start + lastPosition);
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

}
