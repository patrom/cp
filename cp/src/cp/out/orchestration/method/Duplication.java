package cp.out.orchestration.method;

import java.util.List;

import cp.model.note.Note;
import cp.out.instrument.Instrument;

@FunctionalInterface
public interface Duplication {

	public List<Note> duplicate(Instrument instrumentToDuplicate, Instrument instrument, int octave);
}
