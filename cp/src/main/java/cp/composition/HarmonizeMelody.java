package cp.composition;

import cp.model.note.Note;

import java.util.List;
import java.util.Map;

@FunctionalInterface
public interface HarmonizeMelody {

	Map<String, List<Note>> getNotesToHarmonize();
}
