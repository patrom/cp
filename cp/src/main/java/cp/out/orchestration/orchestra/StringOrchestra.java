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
        violin1 = new InstrumentMapping(new ViolinsI(), 0, 1);
        violin2 = new InstrumentMapping(new ViolinsII(), 1, 2);
        viola = new InstrumentMapping(new Viola(), 2, 3);
        cello = new InstrumentMapping(new Cello(), 3, 4);
        bass = new InstrumentMapping(new DoubleBass(), 4, 5);
        map.put(violin1, new ArrayList<>());
        map.put(violin2, new ArrayList<>());
        map.put(viola, new ArrayList<>());
        map.put(cello, new ArrayList<>());
        map.put(bass, new ArrayList<>());
    }

    public void setViolin(MelodyOrchestrationBuilder melodyOrchestrationBuilder){
        melodyOrchestrations.add(melodyOrchestrationBuilder.setInstrument(violin1.getInstrument()).createMelodyOrchestration());
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
            notesPerVoice.get(melodyOrchestration.getVoice()).addAll(notes);
        }
    }
}
