package cp.generator.pitchclass;

import cp.composition.beat.BeatGroup;
import cp.model.TimeLine;
import cp.model.TimeLineKey;
import cp.model.note.Note;
import cp.util.RandomUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

import static java.util.stream.Collectors.toList;

@Component
public class OrderNoteRepetitionPitchClasses {
    private static Logger LOGGER = LoggerFactory.getLogger(OrderNoteRepetitionPitchClasses.class);

    @Autowired
    private TimeLine timeLine;

    public List<Note> updatePitchClasses(List<Note> notes, BeatGroup beatGroup) {
        LOGGER.debug("OrderNoteRepetitionPitchClasses");
        List<Note> melodyNotes = notes.stream().filter(n -> !n.isRest()).collect(toList());
        if (!melodyNotes.isEmpty()) {
            Note firstNote = melodyNotes.get(0);
            TimeLineKey timeLineKey = timeLine.getTimeLineKeyAtPosition(firstNote.getPosition(), firstNote.getVoice());
            int[] pitchClasses = timeLineKey.getScale().getPitchClasses();
            int i = 0;
            for (Note note : melodyNotes) {
                note.setPitchClass((pitchClasses[i]+ timeLineKey.getKey().getInterval()) % 12);
                if (RandomUtil.toggleSelection()) {//repeat
                    i = (i + 1) % pitchClasses.length;
                }
            }
        }
        return notes;
    }

}
