package cp.generator.dependant;

import cp.model.TimeLine;
import cp.model.TimeLineKey;
import cp.model.harmony.DependantHarmony;
import cp.model.melody.MelodyBlock;
import cp.model.note.Note;
import cp.util.Util;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * Created by prombouts on 26/01/2017.
 */
public class DependantGenerator implements DependantHarmonyGenerator{

    private TimeLine timeLine;

    private int sourceVoice;
    private int dependingVoice;
    private int secondDependingVoice = -1;

    public DependantGenerator(TimeLine timeLine, int sourceVoice, int dependingVoice) {
        this.timeLine = timeLine;
        this.sourceVoice = sourceVoice;
        this.dependingVoice = dependingVoice;
    }

    public DependantGenerator(TimeLine timeLine, int sourceVoice, int dependingVoice, int secondDependingVoice) {
        this.timeLine = timeLine;
        this.sourceVoice = sourceVoice;
        this.dependingVoice = dependingVoice;
        this.secondDependingVoice = secondDependingVoice;
    }

    public void generateDependantHarmonies(List<MelodyBlock> melodies) {
        MelodyBlock melodyBlock = melodies.stream().filter(m -> m.getVoice() == sourceVoice).findFirst().get();
        MelodyBlock dependantMelodyBlock = melodies.stream().filter(m -> m.getVoice() == dependingVoice).findFirst().get();
        Optional<MelodyBlock> secondDependantMelodyBlock = melodies.stream().filter(m -> m.getVoice() == secondDependingVoice).findFirst();
        List<Note> notes = new ArrayList<>();
        List<Note> notesSecondBlock = new ArrayList<>();
        List<Note> melodyBlockNotesWithRests = melodyBlock.getMelodyBlockNotesWithRests();
        for (Note note : melodyBlockNotesWithRests) {
            DependantHarmony dependantHarmony = note.getDependantHarmony();
            switch (dependantHarmony.getChordType().getSize()){
                case 2:
                    Note clone = singleNoteDependency(note);
                    clone.setVoice(dependantMelodyBlock.getVoice());
                    notes.add(clone);
                    if (secondDependantMelodyBlock.isPresent()) {
                        Note rest = note.clone();
                        rest.setPitch(Note.REST);
                        rest.setVoice(secondDependantMelodyBlock.get().getVoice());
                        notesSecondBlock.add(rest);
                    }
                    break;
                case 3:
                    NoteTuple noteTuple = multiNoteDependency(note);
                    Note first = noteTuple.getFirst();
                    first.setVoice(dependantMelodyBlock.getVoice());
                    notes.add(first);
                    Note second = noteTuple.getSecond();
                    second.setVoice(secondDependantMelodyBlock.get().getVoice());
                    notesSecondBlock.add(second);
                    break;
                default:
                    throw new IllegalArgumentException("Dependant harmony not set for type: " + dependantHarmony.getChordType());
            }
        }
        dependantMelodyBlock.setNotes(notes);
        if (secondDependantMelodyBlock.isPresent()) {
            secondDependantMelodyBlock.get().setNotes(notesSecondBlock);
        }
    }

    protected NoteTuple multiNoteDependency(Note note) {
        Note clone = note.clone();
        Note secondClone = note.clone();
        int pitchClass;
        int interval;
        int pitchClassSecond;
        int intervalSecond;
        if (!note.isRest()) {
            switch (note.getDependantHarmony().getChordType()){
                case MAJOR:
                    pitchClass = getDependantPitchClass(note, 2);
                    interval = getIntervalClockWise(note.getPitchClass(), pitchClass);
                    pitchClassSecond = getDependantPitchClass(note, 4);
                    intervalSecond = getIntervalClockWise(note.getPitchClass(), pitchClassSecond);
                    break;
                case MAJOR_1:
                    pitchClass = getDependantPitchClass(note, 2);
                    interval = getIntervalClockWise(note.getPitchClass(), pitchClass);
                    pitchClassSecond = getDependantPitchClass(note, 5);
                    intervalSecond = getIntervalClockWise(note.getPitchClass(), pitchClassSecond);
                    break;
                case MAJOR_2:
                    pitchClass = getDependantPitchClass(note, 3);
                    interval = getIntervalClockWise(note.getPitchClass(), pitchClass);
                    pitchClassSecond = getDependantPitchClass(note, 5);
                    intervalSecond = getIntervalClockWise(note.getPitchClass(), pitchClassSecond);
                    break;
                case DOM:
                    pitchClass = getDependantPitchClass(note, 3);
                    interval = getIntervalClockWise(note.getPitchClass(), pitchClass);
                    pitchClassSecond = getDependantPitchClass(note, 6);
                    intervalSecond = getIntervalClockWise(note.getPitchClass(), pitchClassSecond);
                    break;
                case MAJOR_1_CHR:
                    pitchClass = (note.getPitchClass() + 3) % 12;
                    interval = getIntervalClockWise(note.getPitchClass(), pitchClass);
                    pitchClassSecond = (note.getPitchClass() + 8) % 12;
                    intervalSecond = getIntervalClockWise(note.getPitchClass(), pitchClassSecond);
                    break;
                default:
                    throw new IllegalArgumentException("Dependant harmony not set for note: " + note);
            }
            clone.setPitchClass(pitchClass);
            clone.setPitch(note.getPitch() + interval);
            secondClone.setPitchClass(pitchClassSecond);
            secondClone.setPitch(note.getPitch() + intervalSecond);
        } else {
            clone.setPitch(Note.REST);
            secondClone.setPitch(Note.REST);
        }
        return new NoteTuple(clone, secondClone);
    }

    protected Note singleNoteDependency(Note note) {
        Note clone = note.clone();
        int pitchClass = -1;
        int interval = 0;
        if (!note.isRest()) {
            switch (note.getDependantHarmony().getChordType()){
                case ALL_INTERVALS:
                    TimeLineKey timeLineKeyAtPosition = timeLine.getTimeLineKeyAtPosition(note.getPosition(), note.getVoice());
                    pitchClass = timeLineKeyAtPosition.getScale().pickRandomPitchClass();
                    pitchClass = (pitchClass + timeLineKeyAtPosition.getKey().getInterval()) % 12;
                    interval = getIntervalClockWise(note.getPitchClass(), pitchClass);
                    break;
                case CH2_GROTE_TERTS:
                    pitchClass = getDependantPitchClass(note, 2);
                    interval = getIntervalClockWise(note.getPitchClass(), pitchClass);
                    break;

                case CH2_GROTE_SIXT:
                    pitchClass = getDependantPitchClass(note, 5);
                    interval = getIntervalClockWise(note.getPitchClass(), pitchClass);
                    break;
                case CH2_KWART:
                    pitchClass = getDependantPitchClass(note, 3);
                    interval = getIntervalClockWise(note.getPitchClass(), pitchClass);
                    break;
                case CH2_KWINT:
                    pitchClass = getDependantPitchClass(note, 4);
                    interval = getIntervalClockWise(note.getPitchClass(), pitchClass);
                    break;
                case CH2_GROTE_TERTS_CHR:
                case CH2_KLEINE_TERTS_CHR:
                case CH2_GROTE_SIXT_CHR:
                case CH2_KLEINE_SIXT_CHR:
                    interval = note.getDependantHarmony().getChordType().getInterval();
                    pitchClass = (note.getPitchClass() + interval) % 12;
                    break;
                default:
                    throw new IllegalArgumentException("Dependant harmony not set for note: " + note);
            }
            clone.setPitchClass(pitchClass);
            clone.setPitch(note.getPitch() + interval);
        } else {
            clone.setPitch(Note.REST);
        }
        return clone;
    }

    protected int getIntervalClockWise(int firstPitchClass, int clockWiseSecondPc) {
        return (12 - firstPitchClass + clockWiseSecondPc) % 12;
    }

    protected int getDependantPitchClass(Note note, int step) {
        TimeLineKey timeLineKeyAtPosition = timeLine.getTimeLineKeyAtPosition(note.getPosition(), note.getVoice());
        int pcInKeyOfC = Util.convertToKeyOfC(note.getPitchClass(), timeLineKeyAtPosition.getKey().getInterval());
        int pitchClass = timeLineKeyAtPosition.getScale().pickHigerStepFromScale(pcInKeyOfC, step);
        pitchClass = (pitchClass + timeLineKeyAtPosition.getKey().getInterval()) % 12;
        return pitchClass;
    }

    @Override
    public boolean hasSecondDependingVoice() {
        return secondDependingVoice > 0;
    }

    @Override
    public int getSourceVoice() {
        return sourceVoice;
    }

    @Override
    public int getDependingVoice() {
        return dependingVoice;
    }

    @Override
    public int getSecondDependingVoice() {
        return secondDependingVoice;
    }
}