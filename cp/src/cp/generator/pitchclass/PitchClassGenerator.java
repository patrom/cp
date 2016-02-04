package cp.generator.pitchclass;

import java.util.List;

import cp.model.note.Note;
import cp.model.note.Scale;

@FunctionalInterface
public interface PitchClassGenerator {

	List<Note> updatePitchClasses(List<Note> notes, Scale scale);
}
