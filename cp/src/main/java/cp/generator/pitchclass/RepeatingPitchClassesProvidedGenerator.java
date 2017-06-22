package cp.generator.pitchclass;

import cp.model.TimeLineKey;
import cp.model.note.Note;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.List;

import static java.util.stream.Collectors.toList;

/**
 * Created by prombouts on 22/06/2017.
 */
@Component
public class RepeatingPitchClassesProvidedGenerator {

    private static Logger LOGGER = LoggerFactory.getLogger(RepeatingPitchClassesProvidedGenerator.class);

    public List<Note> updatePitchClasses(List<Note> notes, TimeLineKey timeLineKey) {
        LOGGER.debug("RepeatingPitchClasses");
        List<Note> melodyNotes = notes.stream().filter(n -> !n.isRest()).collect(toList());
        if (!melodyNotes.isEmpty()) {
            Note firstNote = melodyNotes.get(0);
            int firstPitchClass = timeLineKey.getScale().pickRandomPitchClass();
            firstNote.setPitchClass((firstPitchClass + timeLineKey.getKey().getInterval()) % 12);
            for (int i = 1; i < melodyNotes.size(); i++) {
                Note nextNote = melodyNotes.get(i);
                    nextNote.setPitchClass((firstPitchClass + timeLineKey.getKey().getInterval()) % 12);
            }
        }
        return notes;
    }
}
