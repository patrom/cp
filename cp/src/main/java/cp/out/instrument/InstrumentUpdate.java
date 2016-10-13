package cp.out.instrument;

import cp.model.note.Note;

import java.util.List;
@FunctionalInterface
public interface InstrumentUpdate {

	List<Note> updateInstrumentNotes(List<Note> notes);
}
