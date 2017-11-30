package cp.model.humanize;

import cp.model.note.Note;
import cp.out.instrument.Instrument;

import java.util.List;

public interface HumanizeRule {

    void humanizationRule(List<Note> notes, Instrument instrument);
}
