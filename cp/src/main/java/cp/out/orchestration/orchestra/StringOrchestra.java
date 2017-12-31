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

    public void execute(){
        for (MelodyOrchestration melodyOrchestration : melodyOrchestrations) {
            List<Note> notes = notesPerVoice.get(melodyOrchestration.getVoice());
            if(melodyOrchestration.getOrchestralQuality() != null){
                notes = melodyOrchestration.getInstrument().updateInQualityRange(notes);
            }
            if(melodyOrchestration.getOrchestralTechnique() != null){
                switch (melodyOrchestration.getOrchestralTechnique()){
                    case UNISONO:
                        break;
                    case OCTAVE_UP:
                        notes.forEach(Note::transposeOctaveUp);
                        break;
                    case OCTAVE_DOWN:
                        notes.forEach(Note::transposeOctaveDown);
                        break;
                    case OCTAVE_DOUBLE_UP:
                        notes.forEach(note -> note.transposeOctave(2));
                        break;
                    case OCTAVE_DOUBLE_DOWN:
                        notes.forEach(note -> note.transposeOctave(-2));
                        break;
                }
            }
            notes.forEach(note -> {
                if (melodyOrchestration.getArticulation() != null) {
                    note.setArticulation(melodyOrchestration.getArticulation());
                }
                if (melodyOrchestration.getTechnical() != null) {
                    note.setTechnical(melodyOrchestration.getTechnical());
                }
                if (melodyOrchestration.getDynamic() != null) {
                    note.setDynamic(melodyOrchestration.getDynamic());
                }
            });
            InstrumentMapping mapping = map.keySet().stream().filter(instrumentMapping -> instrumentMapping.getInstrument().equals(melodyOrchestration.getInstrument()))
                    .findFirst()
                    .orElseThrow(() -> new IllegalArgumentException("No instrument found in map"));
            map.get(mapping).addAll(notes);
        }
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
