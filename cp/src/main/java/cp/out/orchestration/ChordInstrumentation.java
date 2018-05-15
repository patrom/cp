package cp.out.orchestration;

import cp.model.note.Dynamic;
import cp.out.instrument.Articulation;
import cp.out.instrument.Instrument;
import cp.out.instrument.Technical;

public class ChordInstrumentation {

    private Instrument instrument;
    private Articulation articulation;
    private Dynamic dynamic;
    private Technical technical;

    public Articulation getArticulation() {
        return articulation;
    }

    public void setArticulation(Articulation articulation) {
        this.articulation = articulation;
    }

    public Dynamic getDynamic() {
        return dynamic;
    }

    public void setDynamic(Dynamic dynamic) {
        this.dynamic = dynamic;
    }

    public Technical getTechnical() {
        return technical;
    }

    public void setTechnical(Technical technical) {
        this.technical = technical;
    }
}
