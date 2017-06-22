package cp.generator.pitchclass;

import cp.model.TimeLineKey;
import cp.model.note.Note;

import java.util.List;

/**
 * Created by prombouts on 22/06/2017.
 */
@FunctionalInterface
public interface PitchClassProvidedGenerator {

    List<Note> updatePitchClasses(List<Note> notes, TimeLineKey timeLineKey);
}
