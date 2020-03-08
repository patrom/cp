package cp.out.orchestration.orchestra;

import cp.model.note.Note;
import cp.out.instrument.strings.*;
import cp.out.orchestration.MelodyOrchestration;
import cp.out.orchestration.MelodyOrchestrationBuilder;
import cp.out.play.InstrumentMapping;

import java.util.ArrayList;
import java.util.List;

public class StringOrchestra extends Orchestra {

    public StringOrchestra() {
        violin1 = new InstrumentMapping(new ViolinsI(), 5, 0);
        violin2 = new InstrumentMapping(new ViolinsII(), 4, 1);
        viola = new InstrumentMapping(new Viola(), 3, 2);
        cello = new InstrumentMapping(new Cello(), 2, 3);
        bass = new InstrumentMapping(new DoubleBass(), 1, 4);
        map.put(violin1, new ArrayList<>());
        map.put(violin2, new ArrayList<>());
        map.put(viola, new ArrayList<>());
        map.put(cello, new ArrayList<>());
        map.put(bass, new ArrayList<>());
    }

    public void setViolinsI(MelodyOrchestrationBuilder melodyOrchestrationBuilder){
        melodyOrchestrations.add(melodyOrchestrationBuilder.setInstrument(violin1.getInstrument()).createMelodyOrchestration());
    }

    public void setViolinsII(MelodyOrchestrationBuilder melodyOrchestrationBuilder){
        melodyOrchestrations.add(melodyOrchestrationBuilder.setInstrument(violin2.getInstrument()).createMelodyOrchestration());
    }

    public void setViolas(MelodyOrchestrationBuilder melodyOrchestrationBuilder){
        melodyOrchestrations.add(melodyOrchestrationBuilder.setInstrument(viola.getInstrument()).createMelodyOrchestration());
    }

    public void setCellos(MelodyOrchestrationBuilder melodyOrchestrationBuilder){
        melodyOrchestrations.add(melodyOrchestrationBuilder.setInstrument(cello.getInstrument()).createMelodyOrchestration());
    }

    public void setBasses(MelodyOrchestrationBuilder melodyOrchestrationBuilder){
        melodyOrchestrations.add(melodyOrchestrationBuilder.setInstrument(bass.getInstrument()).createMelodyOrchestration());
    }
}
