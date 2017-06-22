package cp.generator.pitchclass;

import cp.model.TimeLineKey;
import cp.model.note.Note;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * Created by prombouts on 22/06/2017.
 */
@Component
public class RandomPitchClassesProvidedGenerator {
    private static Logger LOGGER = LoggerFactory.getLogger(RandomPitchClassesProvidedGenerator.class);

    public List<Note> randomPitchClasses(List<Note> notes, TimeLineKey timeLineKey){
        LOGGER.debug("RandomPitchClasses");
        notes.forEach(n -> {
            if (!n.isRest()) {
                n.setPitchClass((timeLineKey.getScale().pickRandomPitchClass() + timeLineKey.getKey().getInterval()) % 12);
            }
        });
        return notes;
    }
}

