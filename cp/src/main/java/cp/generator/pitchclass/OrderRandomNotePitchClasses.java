package cp.generator.pitchclass;

import cp.model.TimeLine;
import cp.model.TimeLineKey;
import cp.model.note.Note;
import cp.util.RandomUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.toList;

@Component
public class OrderRandomNotePitchClasses {
    private static Logger LOGGER = LoggerFactory.getLogger(OrderRandomNotePitchClasses.class);

    @Autowired
    private TimeLine timeLine;

    public List<Note> updatePitchClasses(List<Note> notes) {
        LOGGER.debug("OrderRandomNotePitchClasses");
        List<Note> melodyNotes = notes.stream().filter(n -> !n.isRest()).collect(toList());
        if (!melodyNotes.isEmpty()) {
            Note firstNote = melodyNotes.get(0);
            TimeLineKey timeLineKey = timeLine.getTimeLineKeyAtPosition(firstNote.getPosition(), firstNote.getVoice());
            int[] pitchClasses = timeLineKey.getScale().getPitchClasses();
            List<Integer> pitchClassesList = Arrays.stream(pitchClasses).boxed().collect(Collectors.toList());
            for (Note note : notes) {
                if (pitchClassesList.isEmpty()) {
                    note.setPitchClass(RandomUtil.getRandomFromIntArray(pitchClasses));
                } else {
                    Integer pc = pitchClassesList.get(0);
                    note.setPitchClass((pc + timeLineKey.getKey().getInterval()) % 12);
                    pitchClassesList.remove(pc);
                }
            }
        }
        return notes;
    }

}

