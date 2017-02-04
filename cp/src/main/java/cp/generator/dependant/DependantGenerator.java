package cp.generator.dependant;

import cp.model.TimeLine;
import cp.model.TimeLineKey;
import cp.model.harmony.DependantHarmony;
import cp.model.melody.MelodyBlock;
import cp.model.note.Note;
import cp.util.Util;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * Created by prombouts on 26/01/2017.
 */
@Component(value = "dependantGenerator")
public class DependantGenerator implements DependantHarmonyGenerator{

    @Autowired
    private TimeLine timeLine;
    @Autowired
    private DependantConfig dependantConfig;

    public void generateDependantHarmonies(List<MelodyBlock> melodies) {
        if (dependantConfig.hasSourcegVoice()) {
            MelodyBlock MelodyBlock = melodies.stream().filter(m -> m.getVoice() == dependantConfig.getSourceVoice()).findFirst().get();
            MelodyBlock dependantMelodyBlock = melodies.stream().filter(m -> m.getVoice() == dependantConfig.getDependingVoice()).findFirst().get();
            Optional<MelodyBlock> secondDependantMelodyBlock = melodies.stream().filter(m -> m.getVoice() == dependantConfig.getSecondDependingVoice()).findFirst();
            List<Note> notes = new ArrayList<>();
            List<Note> notesSecondBlock = new ArrayList<>();
            List<Note> melodyBlockNotesWithRests = MelodyBlock.getMelodyBlockNotesWithRests();
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
                        throw new IllegalArgumentException("Dependant harmony not set for note: " + note);
                }
            }
            dependantMelodyBlock.setNotes(notes);
            if (secondDependantMelodyBlock.isPresent()) {
                secondDependantMelodyBlock.get().setNotes(notesSecondBlock);
            }
        }
    }

    protected NoteTuple multiNoteDependency(Note note) {
        Note clone = note.clone();
        Note secondClone = note.clone();
        int pitchClass = -1;
        int interval = 0;
        int pitchClassSecond = -1;
        int intervalSecond = 0;
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

}