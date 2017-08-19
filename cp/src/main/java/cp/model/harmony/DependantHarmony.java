package cp.model.harmony;

/**
 * Created by prombouts on 22/01/2017.
 */
public class DependantHarmony{

    private ChordType chordType;
    private int axisPitchClassHigh;
    private int axisPitchClassLow;

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
}
