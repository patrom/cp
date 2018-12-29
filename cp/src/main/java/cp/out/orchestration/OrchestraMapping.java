package cp.out.orchestration;

import cp.model.note.Note;
import cp.out.instrument.Instrument;
import cp.out.orchestration.quality.OrchestralQuality;

import java.util.ArrayList;
import java.util.List;

public class OrchestraMapping {

    private int voice;
    private int size;
    private List<Note> notes = new ArrayList<>();
    private Instrument instrument;
    private OrchestralQuality orchestralQuality;

    public OrchestraMapping(int size, Instrument instrument, OrchestralQuality orchestralQuality) {
        this.size = size;
        this.instrument = instrument;
        this.orchestralQuality = orchestralQuality;
    }

    public int getVoice() {
        return voice;
    }

    public int getSize() {
        return size;
    }

    public List<Note> getNotes() {
        return notes;
    }

    public Instrument getInstrument() {
        return instrument;
    }

    public OrchestralQuality getOrchestralQuality() {
        return orchestralQuality;
    }

    public void addNote(Note note) {
        notes.add(note);
    }
}
