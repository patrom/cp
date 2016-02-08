package cp.generator.pitchclass;

import java.util.List;

import cp.model.note.Note;

@FunctionalInterface
public interface PitchClassGenerator {

	List<Note> updatePitchClasses(List<Note> notes);
}
