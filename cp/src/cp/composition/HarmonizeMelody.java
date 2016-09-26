package cp.composition;

import java.util.List;

import cp.model.note.Note;

@FunctionalInterface
public interface HarmonizeMelody {

	List<Note> getNotesToHarmonize();
}
