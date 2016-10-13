package cp.out.instrument;

import cp.model.note.Note;

import java.util.List;
@FunctionalInterface
public interface InstrumentUpdate {

	public List<Note> updateInstrumentNotes(List<Note> notes);
}
