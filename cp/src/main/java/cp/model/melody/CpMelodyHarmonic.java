package cp.model.melody;

import java.util.List;

public class CpMelodyHarmonic implements MusicElement {

    private CpMelody cpMelody;
    private List<MelodyBlock> melodyBlocks;

    public CpMelody getCpMelody() {
        return cpMelody;
    }

    public void setCpMelody(CpMelody cpMelody) {
        this.cpMelody = cpMelody;
    }

    public List<MelodyBlock> getMelodyBlocks() {
        return melodyBlocks;
    }

    public void setMelodyBlocks(List<MelodyBlock> melodyBlocks) {
        this.melodyBlocks = melodyBlocks;
    }
}
