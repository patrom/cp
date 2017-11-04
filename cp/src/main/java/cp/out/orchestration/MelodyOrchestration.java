package cp.out.orchestration;

import cp.model.note.Dynamic;
import cp.out.instrument.Articulation;
import cp.out.instrument.Instrument;
import cp.out.instrument.Technical;
import cp.out.orchestration.quality.OrchestralQuality;

public class MelodyOrchestration {

    private Instrument instrument;
    private int voice;
    private Articulation articulation;
    private Technical technical;
    private Dynamic dynamic;
    private OrchestralQuality orchestralQuality;
    private OrchestralTechnique orchestralTechnique;

    public MelodyOrchestration(Instrument instrument,
                               int voice,
                               Articulation articulation,
                               Technical technical,
                               Dynamic dynamic,
                               OrchestralQuality orchestralQuality,
                               OrchestralTechnique orchestralTechnique) {
        this.instrument = instrument;
        this.voice = voice;
        this.articulation = articulation;
        this.technical = technical;
        this.dynamic = dynamic;
        this.orchestralQuality = orchestralQuality;
        this.orchestralTechnique = orchestralTechnique;
    }

    public Instrument getInstrument() {
        return instrument;
    }

    public int getVoice() {
        return voice;
    }

    public Articulation getArticulation() {
        return articulation;
    }

    public Technical getTechnical() {
        return technical;
    }

    public Dynamic getDynamic() {
        return dynamic;
    }

    public OrchestralQuality getOrchestralQuality() {
        return orchestralQuality;
    }

    public OrchestralTechnique getOrchestralTechnique() {
        return orchestralTechnique;
    }
}
