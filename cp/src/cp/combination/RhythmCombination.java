package cp.combination;

import java.util.List;

import cp.model.note.Note;

@FunctionalInterface
public interface RhythmCombination {

	List<Note> getNotes(int beat);
}
