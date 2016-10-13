package cp.composition;

import cp.model.note.Note;

import java.util.List;

@FunctionalInterface
public interface HarmonizeMelody {

	List<Note> getNotesToHarmonize();
}
