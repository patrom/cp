package cp.model.harmony;

import cp.model.note.Note;
import cp.util.RowMatrix;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static java.util.Comparator.comparingInt;
import static java.util.stream.Collectors.toList;

/**
 * Created by prombouts on 22/01/2017.
 */
public class DependantHarmony{

    private ChordType chordType;
    private int axisPitchClassHigh;
    private int axisPitchClassLow;
    private List<Note> notes = new ArrayList<>();
    private VoicingType voicingType;
    private int[] setClass;

    public ChordType getChordType() {
        return chordType;
    }

    public void setChordType(ChordType chordType) {
        this.chordType = chordType;
    }

    public DependantHarmony() {
    }

    private DependantHarmony(DependantHarmony dependantHarmony) {
        this.chordType = dependantHarmony.getChordType();
        this.setClass = dependantHarmony.getSetClass();
        this.voicingType = dependantHarmony.getVoicingType();

    }

    public DependantHarmony(ChordType chordType, int axisPitchClassHigh, int axisPitchClassLow) {
        this.chordType = chordType;
        this.axisPitchClassHigh = axisPitchClassHigh;
        this.axisPitchClassLow = axisPitchClassLow;
    }

    public DependantHarmony(int[] setClass, VoicingType voicingType) {
        this.setClass = setClass;
        this.voicingType = voicingType;
    }

    public DependantHarmony clone() {
        return new DependantHarmony(this);
    }

    public int getAxisPitchClassHigh() {
        return axisPitchClassHigh;
    }

    public int getAxisPitchClassLow() {
        return axisPitchClassLow;
    }

    public void setNotes(List<Note> notes) {
        this.notes = notes;
    }

    public List<Note> getNotes() {
        return notes;
    }

    public int[] getSetClass() {
        return setClass;
    }

    public VoicingType getVoicingType() {
        return voicingType;
    }


    public void dependantBelow(Note note){
        notes.clear();
        updatePitchClassesBelow(note);
        sortDependantNotesCloseBelow(note);
        switch (voicingType) {
            case DROP_2:
                drop2Voicing();
                break;
            case DROP_3:
                drop3Voicing();
                break;
            case DROP_2_4:
                drop2And4Voicing();
                break;
        }
    }

    protected void updatePitchClassesBelow(Note note){
        int[] pitchClasses = setClass;
        for (int i = 1; i < pitchClasses.length; i++) {
            int pitchClass = pitchClasses[i];
            Note clone = note.clone();
            int newPc = (note.getPitchClass() - pitchClass + 12) % 12;
            clone.setPitchClass(newPc);
            notes.add(clone);
        }
    }

    protected void updatePitchClassesAbove(Note note){
        int[] pitchClasses = setClass;
        for (int i = 1; i < pitchClasses.length; i++) {
            int pitchClass = pitchClasses[i];
            Note clone = note.clone();
            int newPc = (note.getPitchClass() + pitchClass + 12) % 12;
            clone.setPitchClass(newPc);
            notes.add(clone);
        }
    }

    /**
     * Update all notes within an octave range below the melody note (sorted).
     */
    protected void sortDependantNotesCloseBelow(Note topNote){
        for (Note note : notes) {
            int harmonyPitch = topNote.getOctave() * 12 + note.getPitchClass();
            if (harmonyPitch <= topNote.getPitch()) {
                note.setPitch(harmonyPitch);
                note.setOctave(topNote.getOctave());
            } else {
                note.setPitch(harmonyPitch - 12);
                note.setOctave(topNote.getOctave() - 1);
            }
        }
        Collections.sort(notes, comparingInt(Note::getPitch).reversed());
    }

    /**
     * Update all notes within an octave range above the melody note (sorted).
     */
    protected void sortDependantNotesCloseAbove(Note topNote){
        for (Note note : notes) {
            int harmonyPitch = topNote.getOctave() * 12 + note.getPitchClass();
            if (harmonyPitch >= topNote.getPitch()) {
                note.setPitch(harmonyPitch);
                note.setOctave(topNote.getOctave());
            } else {
                note.setPitch(harmonyPitch + 12);
                note.setOctave(topNote.getOctave() + 1);
            }
        }
        Collections.sort(notes, comparingInt(Note::getPitch));
    }

    protected void drop2Voicing(){
        Note dropNote = notes.get(0);
        notes.remove(0);
        dropNote.transposeOctaveDown();
        notes.add(dropNote);
    }

    protected void drop3Voicing(){
        if ( notes.size() < 2) {
           throw new IllegalStateException("drop 3 voicing not possible");
        }
        Note dropNote = notes.get(1);
        notes.remove(1);
        dropNote.transposeOctaveDown();
        notes.add(dropNote);
    }

    protected void drop2And4Voicing(){
        if ( notes.size() < 3) {
            throw new IllegalStateException("drop 2 + 4 voicing not possible");
        }
        Note drop2Note = notes.get(0);
        Note drop4Note = notes.get(2);

        notes.remove(drop2Note);
        notes.remove(drop4Note);

        drop2Note.transposeOctaveDown();
        drop4Note.transposeOctaveDown();

        notes.add(drop2Note);
        notes.add(drop4Note);
    }

    public List<DependantHarmony> getAllRowMatrixBelow(Note note){
        List<DependantHarmony> setClassTypes = new ArrayList<>();

        List<Integer> pitchClasses = Arrays.stream(setClass).boxed().collect(toList());
        RowMatrix rowMatrix = new RowMatrix(setClass.length, pitchClasses);
        rowMatrix.show();

        for (int i = 0; i < setClass.length; i++) {
            int[] row = rowMatrix.getRow(i);
            Arrays.sort(row);
//            System.out.println(Arrays.toString(row));
            DependantHarmony dependantHarmonyRow = new DependantHarmony(row, voicingType);
            dependantHarmonyRow.dependantBelow(note);
            dependantHarmonyRow.setChordType(ChordType.SETCLASS);
            setClassTypes.add(dependantHarmonyRow);

            int[] column = rowMatrix.getColumn(i);
            Arrays.sort(column);
//            System.out.println(Arrays.toString(column));
            DependantHarmony dependantHarmonyInversion = new DependantHarmony(column, voicingType);
            dependantHarmonyInversion.dependantBelow(note);
            dependantHarmonyInversion.setChordType(ChordType.SETCLASS);
            setClassTypes.add(dependantHarmonyInversion);
        }
        return setClassTypes;
    }

}
