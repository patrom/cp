package cp.model.texture;

import cp.model.TimeLine;
import cp.model.TimeLineKey;
import cp.model.harmony.DependantHarmony;
import cp.model.note.Note;
import cp.util.Util;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static java.util.Collections.emptyList;

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

    protected List<Note> getTextureForNoteBelow(Note note) {
        DependantHarmony dependantHarmony = note.getDependantHarmony();
        if(dependantHarmony != null) {
            List<Note> textureNotes = new ArrayList<>();
            switch (dependantHarmony.getChordType().getSize()) {
                case -1:
                    textureNotes.add(symmetryNoteDependencyBelow(note));
                    break;
                case 0:
                    break;
                case 2:
                    textureNotes.add(singleNoteDependency(note, true));
                    break;
                case 3:
                    textureNotes.addAll(multiNoteDependency(note, true));
                    break;
                case 12:
                    textureNotes.addAll(updateDependantNotesBelow(note));
                    break;
                case 100:
                    textureNotes.addAll(getDependantNotesBelow(note));
                    break;
                default:
                    throw new IllegalArgumentException("Dependant harmony not set for type: " + dependantHarmony.getChordType());

            }
            return textureNotes;
        }
        return emptyList();
    }

    protected List<Note> getTextureForNoteAbove(Note note) {
        DependantHarmony dependantHarmony = note.getDependantHarmony();
        if(dependantHarmony != null) {
            List<Note> textureNotes = new ArrayList<>();
            switch (dependantHarmony.getChordType().getSize()) {
                case -1:
                    textureNotes.add(symmetryNoteDependencyAbove(note));
                    break;
                case 0:
                    break;
                case 2:
                    textureNotes.add(singleNoteDependency(note, false));
                    break;
                case 3:
                    textureNotes.addAll(multiNoteDependency(note, false));
                    break;
                case 12:
                    textureNotes.addAll(updateDependantNotesAbove(note));
                    break;
                case 100:
                    textureNotes.addAll(getDependantNotesAbove(note));
                    break;
                default:
                    throw new IllegalArgumentException("Dependant harmony not set for type: " + dependantHarmony.getChordType());

            }
            return textureNotes;
        }
        return emptyList();
    }

    protected List<Note> multiNoteDependency(Note note, boolean octave) {
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
                case DOM_CHR_1:
                    pitchClass = (note.getPitchClass() + 3) % 12;
                    interval = getIntervalClockWise(note.getPitchClass(), pitchClass);
                    pitchClassSecond = (note.getPitchClass() + 9) % 12;
                    intervalSecond = getIntervalClockWise(note.getPitchClass(), pitchClassSecond);
                    break;
                case DOM_CHR_2:
                    pitchClass = (note.getPitchClass() + 6) % 12;
                    interval = getIntervalClockWise(note.getPitchClass(), pitchClass);
                    pitchClassSecond = (note.getPitchClass() + 9) % 12;
                    intervalSecond = getIntervalClockWise(note.getPitchClass(), pitchClassSecond);
                    break;



                //Interval between octave
                case CH2_GROTE_TERTS_OCTAVE:
                    pitchClass = getDependantPitchClass(note, 2);
                    interval = getIntervalClockWise(note.getPitchClass(), pitchClass);
                case CH2_GROTE_SIXT_OCTAVE:
                    pitchClass = getDependantPitchClass(note, 5);
                    interval = getIntervalClockWise(note.getPitchClass(), pitchClass);
                case CH2_KWART_OCTAVE:
                    pitchClass = getDependantPitchClass(note, 3);
                    interval = getIntervalClockWise(note.getPitchClass(), pitchClass);
                case CH2_KWINT_OCTAVE:
                    pitchClass = getDependantPitchClass(note, 4);
                    interval = getIntervalClockWise(note.getPitchClass(), pitchClass);
//                case CH2_TRITONE_CHR:
//                case CH2_GROTE_TERTS_CHR:
//                case CH2_KLEINE_TERTS_CHR:
//                case CH2_GROTE_SIXT_CHR:
//                case CH2_KLEINE_SIXT_CHR:
//                    interval = note.getDependantHarmony().getChordType().getInterval();
//                    pitchClass = (note.getPitchClass() + interval) % 12;

                    //octave
                    if (octave) {
                        intervalSecond = 0;
                    }else{
                        intervalSecond = 12;
                    }
                    pitchClassSecond = note.getPitchClass();
                    break;

                default:
                    throw new IllegalArgumentException("Dependant harmony not set for note: " + note.getDependantHarmony().getChordType());
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

    protected Note singleNoteDependency(Note note, boolean octave) {
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
                case CH2_GROTE_SECONDE:
                    pitchClass = getDependantPitchClass(note, 1);
                    interval = getIntervalClockWise(note.getPitchClass(), pitchClass);
                    break;
                case CH2_GROTE_TERTS:
                    pitchClass = getDependantPitchClass(note, 2);
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
                case CH2_GROTE_SIXT:
                    pitchClass = getDependantPitchClass(note, 5);
                    interval = getIntervalClockWise(note.getPitchClass(), pitchClass);
                    break;
                case CH2_TRITONE_CHR:
                case CH2_GROTE_TERTS_CHR:
                case CH2_KLEINE_TERTS_CHR:
                case CH2_GROTE_SIXT_CHR:
                case CH2_KLEINE_SIXT_CHR:
                    interval = note.getDependantHarmony().getChordType().getInterval();
                    pitchClass = (note.getPitchClass() + interval) % 12;
                    break;
                case CH2_OCTAVE:
                    if (octave) {
                        interval = 0;
                    }else{
                        interval = 12;
                    }
                    pitchClass = note.getPitchClass();
                    break;
//                case NO_INTERVALS:
//                    clone.setPitch(Note.REST);
//                    break;
                default:
                    throw new IllegalArgumentException("Dependant harmony not set for note: " + note.getDependantHarmony().getChordType());
            }
            clone.setPitchClass(pitchClass);
            if (octave) {
                clone.setPitch(note.getPitch() + interval - 12);
            }else{
                clone.setPitch(note.getPitch() + interval);
            }
            clone.setOctave((clone.getPitch() / 12) );
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

    protected Note symmetryNoteDependencyBelow(Note note) {
        Note clone = note.clone();
        int pitchClass = -1;
        if (!note.isRest()) {
            DependantHarmony dependantHarmony = note.getDependantHarmony();
            switch (dependantHarmony.getChordType()){
                case SYMMETRY:
                    int interval = note.getPitchClass() - dependantHarmony.getAxisPitchClassHigh();
                    pitchClass = (dependantHarmony.getAxisPitchClassLow() - interval + 12) % 12;
                    break;
                default:
                    throw new IllegalArgumentException("Dependant harmony not set for note: " + note);
            }
            clone.setPitchClass(pitchClass);
            int pitchSymmetryNote = pitchClass + note.getOctave() * 12;
            if (pitchSymmetryNote > note.getPitch()) {
                clone.setPitch(pitchSymmetryNote - 12);
                clone.setOctave(note.getOctave() - 1);
            }else{
                clone.setPitch(pitchSymmetryNote);
                clone.setOctave(note.getOctave());
            }
        }
        return clone;
    }

    protected Note symmetryNoteDependencyAbove(Note note) {
        Note clone = note.clone();
        int pitchClass = -1;
        if (!note.isRest()) {
            DependantHarmony dependantHarmony = note.getDependantHarmony();
            switch (dependantHarmony.getChordType()){
                case SYMMETRY:
                    int interval = note.getPitchClass() - dependantHarmony.getAxisPitchClassHigh();
                    pitchClass = (dependantHarmony.getAxisPitchClassLow() - interval + 12) % 12;
                    break;
                default:
                    throw new IllegalArgumentException("Dependant harmony not set for note: " + note);
            }
            clone.setPitchClass(pitchClass);
            int pitchSymmetryNote = pitchClass + note.getOctave() * 12;
            if (pitchSymmetryNote < note.getPitch()) {
                clone.setPitch(pitchSymmetryNote + 12);
                clone.setOctave(note.getOctave() + 1);
            }else{
                clone.setPitch(pitchSymmetryNote);
                clone.setOctave(note.getOctave());
            }
        }
        return clone;
    }

    protected List<Note> getDependantNotesBelow(Note note){
        DependantHarmony dependantHarmony = note.getDependantHarmony();
        if(dependantHarmony != null) {
            dependantHarmony.dependantBelow(note);
            return dependantHarmony.getNotes();
        }
        return Collections.emptyList();
    }

    protected List<Note> getDependantNotesAbove(Note note){
        DependantHarmony dependantHarmony = note.getDependantHarmony();
        if(dependantHarmony != null) {
            dependantHarmony.dependantAbove(note);
            return dependantHarmony.getNotes();
        }
        return Collections.emptyList();
    }

    protected List<Note> updateDependantNotesBelow(Note note){
        DependantHarmony dependantHarmony = note.getDependantHarmony();
        if (dependantHarmony != null) {
            List<Note> harmonyNotes = dependantHarmony.getNotes();
            for (Note harmonyNote : harmonyNotes) {
                int harmonyPitch = note.getOctave() * 12 + harmonyNote.getPitchClass();
                if (harmonyPitch <= note.getPitch()) {
                    harmonyNote.setPitch(harmonyPitch);
                    harmonyNote.setOctave(note.getOctave());
                } else {
                    harmonyNote.setPitch(harmonyPitch - 12);
                    harmonyNote.setOctave(note.getOctave() - 1);
                }
            }
            return harmonyNotes;
        }
        return Collections.emptyList();
    }

    protected List<Note> updateDependantNotesAbove(Note note){
        DependantHarmony dependantHarmony = note.getDependantHarmony();
        if (dependantHarmony != null) {
            List<Note> harmonyNotes = dependantHarmony.getNotes();
            for (Note harmonyNote : harmonyNotes) {
                int harmonyPitch = note.getOctave() * 12 + harmonyNote.getPitchClass();
                if (harmonyPitch >= note.getPitch()) {
                    harmonyNote.setPitch(harmonyPitch);
                    harmonyNote.setOctave(note.getOctave());
                } else {
                    harmonyNote.setPitch(harmonyPitch + 12);
                    harmonyNote.setOctave(note.getOctave() + 1);
                }
            }
            return harmonyNotes;
        }
        return Collections.emptyList();
    }

}
