package cp.model.harmony;

import cp.model.note.Note;

import java.util.List;

/**
 * Created by prombouts on 22/01/2017.
 */
public class DependantHarmony{

    private ChordType chordType;
    private int axisPitchClassHigh;
    private int axisPitchClassLow;
    private List<Note> notes;

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
    }

    public DependantHarmony(ChordType chordType, int axisPitchClassHigh, int axisPitchClassLow) {
        this.chordType = chordType;
        this.axisPitchClassHigh = axisPitchClassHigh;
        this.axisPitchClassLow = axisPitchClassLow;
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

}
