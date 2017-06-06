package cp.model.texture;

import cp.model.TimeLine;
import cp.model.TimeLineKey;
import cp.model.harmony.DependantHarmony;
import cp.model.note.Note;
import cp.util.Util;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by prombouts on 26/05/2017.
 */
@Component
public class Texture {

    @Autowired
    private TimeLine timeLine;

    public List<Note> getTextureNotes(List<Note> harmonyNotes){
        List<Note> textureNotes = new ArrayList<>();
        for (Note note : harmonyNotes) {
            if (note.getVoice() == 0 ){
                textureNotes.addAll(getTextureForNoteAbove(note));
            }else{
                textureNotes.addAll(getTextureForNoteBelow(note));
            }
        }
        return textureNotes;
    }

    public List<Note> getTextureForNote(Note note) {
        if(note.getVoice() == 0){
            return getTextureForNoteAbove(note);
        }
        return getTextureForNoteBelow(note);
    }

    public List<Note> getTextureForNoteBelow(Note note) {
        List<Note> textureNotes = new ArrayList<>();
        DependantHarmony dependantHarmony = note.getDependantHarmony();
        if(dependantHarmony != null) {
            switch (dependantHarmony.getChordType().getSize()) {
                case 0:
                    break;
                case 2:
                    textureNotes.add(singleNoteDependency(note, true));
                    break;
                case 3:
                    textureNotes.addAll(multiNoteDependency(note, true));
                    break;
                default:
                    throw new IllegalArgumentException("Dependant harmony not set for type: " + dependantHarmony.getChordType());

            }
        }
        return textureNotes;
    }

    public List<Note> getTextureForNoteAbove(Note note) {
        List<Note> textureNotes = new ArrayList<>();
        DependantHarmony dependantHarmony = note.getDependantHarmony();
        if(dependantHarmony != null) {
            switch (dependantHarmony.getChordType().getSize()) {
                case 0:
                    break;
                case 2:
                    textureNotes.add(singleNoteDependency(note, false));
                    break;
                case 3:
                    textureNotes.addAll(multiNoteDependency(note, false));
                    break;
                default:
                    throw new IllegalArgumentException("Dependant harmony not set for type: " + dependantHarmony.getChordType());

            }
        }
        return textureNotes;
    }

    public List<Note> multiNoteDependency(Note note, boolean octave) {
        List<Note> notes = new ArrayList<>();
        int pitchClass;
        int interval;
        int pitchClassSecond;
        int intervalSecond;
        if (!note.isRest()) {
            switch (note.getDependantHarmony().getChordType()){
                case MAJOR://major and minor
                    pitchClass = getDependantPitchClass(note, 2);
                    interval = getIntervalClockWise(note.getPitchClass(), pitchClass);
                    pitchClassSecond = getDependantPitchClass(note, 4);
                    intervalSecond = getIntervalClockWise(note.getPitchClass(), pitchClassSecond);
                    break;
                case MAJOR_1://major and minor
                    pitchClass = getDependantPitchClass(note, 2);
                    interval = getIntervalClockWise(note.getPitchClass(), pitchClass);
                    pitchClassSecond = getDependantPitchClass(note, 5);
                    intervalSecond = getIntervalClockWise(note.getPitchClass(), pitchClassSecond);
                    break;
                case MAJOR_2://major and minor
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
                case MAJOR_CHR:
                    pitchClass = (note.getPitchClass() + 4) % 12;
                    interval = getIntervalClockWise(note.getPitchClass(), pitchClass);
                    pitchClassSecond = (note.getPitchClass() + 7) % 12;
                    intervalSecond = getIntervalClockWise(note.getPitchClass(), pitchClassSecond);
                    break;
                case MAJOR_1_CHR:
                    pitchClass = (note.getPitchClass() + 3) % 12;
                    interval = getIntervalClockWise(note.getPitchClass(), pitchClass);
                    pitchClassSecond = (note.getPitchClass() + 8) % 12;
                    intervalSecond = getIntervalClockWise(note.getPitchClass(), pitchClassSecond);
                    break;
                case MAJOR_2_CHR:
                    pitchClass = (note.getPitchClass() + 5) % 12;
                    interval = getIntervalClockWise(note.getPitchClass(), pitchClass);
                    pitchClassSecond = (note.getPitchClass() + 9) % 12;
                    intervalSecond = getIntervalClockWise(note.getPitchClass(), pitchClassSecond);
                    break;
                case MINOR_CHR:
                    pitchClass = (note.getPitchClass() + 3) % 12;
                    interval = getIntervalClockWise(note.getPitchClass(), pitchClass);
                    pitchClassSecond = (note.getPitchClass() + 7) % 12;
                    intervalSecond = getIntervalClockWise(note.getPitchClass(), pitchClassSecond);
                    break;
                case MINOR_1_CHR:
                    pitchClass = (note.getPitchClass() + 4) % 12;
                    interval = getIntervalClockWise(note.getPitchClass(), pitchClass);
                    pitchClassSecond = (note.getPitchClass() + 9) % 12;
                    intervalSecond = getIntervalClockWise(note.getPitchClass(), pitchClassSecond);
                    break;
                case MINOR_2_CHR:
                    pitchClass = (note.getPitchClass() + 5) % 12;
                    interval = getIntervalClockWise(note.getPitchClass(), pitchClass);
                    pitchClassSecond = (note.getPitchClass() + 8) % 12;
                    intervalSecond = getIntervalClockWise(note.getPitchClass(), pitchClassSecond);
                    break;
                default:
                    throw new IllegalArgumentException("Dependant harmony not set for note: " + note);
            }
            Note clone = note.clone();
            clone.setPitchClass(pitchClass);
            if (octave) {
                clone.setPitch(note.getPitch() + interval - 12);
            }else{
                clone.setPitch(note.getPitch() + interval);
            }
            clone.setOctave(clone.getPitch() / 12);
            notes.add(clone);
            Note secondClone = note.clone();
            secondClone.setPitchClass(pitchClassSecond);
            if (octave) {
                secondClone.setPitch(note.getPitch() + intervalSecond - 12);
            }else{
                secondClone.setPitch(note.getPitch() + intervalSecond);
            }
            secondClone.setOctave(clone.getPitch() / 12);
            notes.add(secondClone);
        }
        return notes;
    }

    public Note singleNoteDependency(Note note, boolean octave) {
        List<Note> notes = new ArrayList<>();
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
//                case NO_INTERVALS:
//                    clone.setPitch(Note.REST);
//                    break;
                default:
                    throw new IllegalArgumentException("Dependant harmony not set for note: " + note);
            }
            clone.setPitchClass(pitchClass);
            if (octave) {
                clone.setPitch(note.getPitch() + interval - 12);
            }else{
                clone.setPitch(note.getPitch() + interval);
            }
            clone.setOctave((clone.getPitch() / 12) );
            notes.add(clone);
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
