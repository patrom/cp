package cp.out.orchestration;

import cp.model.note.Dynamic;
import cp.out.instrument.Articulation;
import cp.out.instrument.Instrument;
import cp.out.instrument.Technical;
import cp.out.orchestration.quality.OrchestralQuality;

public class MelodyOrchestrationBuilder {
    private Instrument instrument;
    private int voice;
    private Articulation articulation;
    private Technical technical;
    private Dynamic dynamic;
    private OrchestralQuality orchestralQuality;
    private OrchestralTechnique orchestralTechnique;

    public MelodyOrchestrationBuilder setInstrument(Instrument instrument) {
        this.instrument = instrument;
        return this;
    }

    public MelodyOrchestrationBuilder setVoice(int voice) {
        this.voice = voice;
        return this;
    }

    public MelodyOrchestrationBuilder setArticulation(Articulation articulation) {
        this.articulation = articulation;
        return this;
    }

    public MelodyOrchestrationBuilder setTechnical(Technical technical) {
        this.technical = technical;
        return this;
    }

    public MelodyOrchestrationBuilder setDynamic(Dynamic dynamic) {
        this.dynamic = dynamic;
        return this;
    }

    public MelodyOrchestrationBuilder setOrchestralQuality(OrchestralQuality orchestralQuality) {
        this.orchestralQuality = orchestralQuality;
        return this;
    }

    public MelodyOrchestrationBuilder setOrchestralTechnique(OrchestralTechnique orchestralTechnique) {
        this.orchestralTechnique = orchestralTechnique;
        return this;
    }

    public MelodyOrchestration createMelodyOrchestration() {
        return new MelodyOrchestration(instrument, voice, articulation, technical, dynamic, orchestralQuality, orchestralTechnique);
    }
}