package cp.out.orchestration;

import cp.combination.RhythmCombination;
import cp.model.note.Note;

import java.util.List;

@FunctionalInterface
public interface Orchestration {

	public List<Note> orchestrate(RhythmCombination rhythmCombination, int beat, int...pitchClasses);
}
