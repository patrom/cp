package cp.model.harmony;

/**
 * Created by prombouts on 22/01/2017.
 */
public class DependantHarmony{

    private ChordType chordType;

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

    public DependantHarmony clone() {
        return new DependantHarmony(this);
    }
}
