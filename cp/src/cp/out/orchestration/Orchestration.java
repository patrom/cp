package cp.out.orchestration;

import java.util.List;

import cp.combination.RhythmCombination;
import cp.model.note.Note;

@FunctionalInterface
public interface Orchestration {

	public List<Note> orchestrate(RhythmCombination rhythmCombination, int beat, int...pitchClasses);
}
