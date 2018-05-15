package cp.out.orchestration;

import cp.model.note.Note;
import cp.out.instrument.Ensemble;
import cp.out.instrument.brass.ensemble.TrHnEnsemble;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class ChordOchestrator {

    private Map<Integer, ChordInstrumentation> chordInstrumentations = new HashMap<>();
    @Autowired
    private TrHnEnsemble trHnEnsemble;

    public void orchestrate() {
        //notes
        List<Note> notes = new ArrayList<>();

        //ensemble
        Ensemble ensemble = trHnEnsemble;
        //articulations

        //play
    }
}
