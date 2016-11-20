package cp.generator.pitchclass;

import cp.model.note.Note;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.List;

import static cp.model.note.NoteBuilder.note;

/**
 * Created by prombouts on 20/11/2016.
 */
@Component
public class RestPitchClasses {

    private static Logger LOGGER = LoggerFactory.getLogger(RestPitchClasses.class);

    public List<Note> updatePitchClasses(List<Note> notes) {
        LOGGER.debug("RestPitchClasses");
        int length = notes.stream().mapToInt(n -> n.getLength()).sum();
        Note firstNote = notes.get(0);
        return Collections.singletonList(note().rest().pos(firstNote.getPosition()).len(length).voice(firstNote.getVoice()).build());
    }
}
