package cp.generator.pitchclass.combination;

import cp.model.melody.CpMelody;
import cp.model.note.Note;

import java.util.List;

@FunctionalInterface
public interface PitchClassCombination {

     CpMelody getMelody(List<Integer> pitchClasses, List<Note> notes);
}
