package cp.combination;

import cp.model.note.Note;

import java.util.List;

@FunctionalInterface
public interface RhythmCombination {

	List<Note> getNotes(int beatLength, int pulse);

}
