package cp.out.instrument;

import java.util.List;

import cp.model.note.Note;
@FunctionalInterface
public interface InstrumentUpdate {

	public List<Note> updateInstrumentNotes(List<Note> notes);
}
