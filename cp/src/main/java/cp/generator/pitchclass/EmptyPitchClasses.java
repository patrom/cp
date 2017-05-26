package cp.generator.pitchclass;

import cp.model.note.Note;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * Created by prombouts on 15/05/2017.
 */
@Component
public class EmptyPitchClasses {

    public List<Note> updatePitchClasses(List<Note> notes) {
        return notes;
    }

}
