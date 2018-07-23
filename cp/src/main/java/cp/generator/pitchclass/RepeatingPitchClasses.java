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

/**
 * Created by prombouts on 20/11/2016.
 */
@Component
public class RepeatingPitchClasses {

    private static Logger LOGGER = LoggerFactory.getLogger(RepeatingPitchClasses.class);

    @Autowired
    private TimeLine timeLine;

    public List<Note> updatePitchClasses(List<Note> notes, BeatGroup beatGroup) {
        LOGGER.debug("RepeatingPitchClasses");
        List<Note> melodyNotes = notes.stream().filter(n -> !n.isRest()).collect(toList());
        if (!melodyNotes.isEmpty()) {
            Note firstNote = melodyNotes.get(0);
            TimeLineKey timeLineKeyFirst = timeLine.getTimeLineKeyAtPosition(firstNote.getPosition(), firstNote.getVoice());
            int firstPitchClass = timeLineKeyFirst.getScale().pickRandomPitchClass();

            firstNote.setPitchClass((firstPitchClass + timeLineKeyFirst.getKey().getInterval()) % 12);
            for (int i = 1; i < melodyNotes.size(); i++) {
                Note nextNote = melodyNotes.get(i);
                TimeLineKey timeLineKey = timeLine.getTimeLineKeyAtPosition(nextNote.getPosition(), nextNote.getVoice());
                if (timeLineKeyFirst.getKey().getStep().equals(timeLineKey.getKey().getStep())) {
                    nextNote.setPitchClass((firstPitchClass + timeLineKey.getKey().getInterval()) % 12);
                } else {
                    int pitchClass;
                    if (RandomUtil.toggleSelection()) {
                        pitchClass = timeLineKey.getScale().pickNextPitchFromScale(firstPitchClass);
                    } else {
                        pitchClass = timeLineKey.getScale().pickPreviousPitchFromScale(firstPitchClass);
                    }
                    firstPitchClass = pitchClass;
                    nextNote.setPitchClass((pitchClass + timeLineKey.getKey().getInterval()) % 12);
                }
            }
        }
        return notes;
    }
}
