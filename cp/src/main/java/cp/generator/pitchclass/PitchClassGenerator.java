package cp.generator.pitchclass;

import cp.model.note.Note;

import java.util.List;

@FunctionalInterface
public interface PitchClassGenerator {

	List<Note> updatePitchClasses(List<Note> notes);
}
