package cp.generator.pitchclass;

import cp.composition.beat.BeatGroup;
import cp.model.TimeLineKey;
import cp.model.melody.CpMelody;
import cp.model.note.Note;
import cp.util.RandomUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * Created by prombouts on 20/11/2016.
 */
@Component
public class RepeatingPitchClasses {

    private static Logger LOGGER = LoggerFactory.getLogger(RepeatingPitchClasses.class);

    public List<Note> updatePitchClasses(CpMelody melody) {
        LOGGER.debug("RepeatingPitchClasses");
        List<Note> melodyNotes = melody.getNotesNoRest();
        melody.updateTimeLineKeysNotes();
        BeatGroup beatGroup = melody.getBeatGroup();
        if (!melodyNotes.isEmpty()) {
            Note firstNote = melodyNotes.get(0);
            TimeLineKey timeLineKeyFirst = firstNote.getTimeLineKey();
            int firstPitchClass = timeLineKeyFirst.getScale().pickRandomPitchClass();
            firstNote.setPitchClass(timeLineKeyFirst.getPitchClassForKey(firstPitchClass));

            for (int i = 1; i < melodyNotes.size(); i++) {
                Note nextNote = melodyNotes.get(i);
                TimeLineKey timeLineKey = nextNote.getTimeLineKey();
                if (timeLineKeyFirst.getKey().getStep().equals(timeLineKey.getKey().getStep())) {
                    nextNote.setPitchClass(timeLineKeyFirst.getPitchClassForKey(firstPitchClass));
                } else {
                    int pitchClass;
                    if (RandomUtil.toggleSelection()) {
                        pitchClass = timeLineKey.getScale().pickNextPitchFromScale(firstPitchClass);
                    } else {
                        pitchClass = timeLineKey.getScale().pickPreviousPitchFromScale(firstPitchClass);
                    }
                    firstPitchClass = pitchClass;
                    timeLineKeyFirst = timeLineKey;
                    nextNote.setPitchClass(timeLineKey.getPitchClassForKey(pitchClass));
                }
            }
        }
        return melodyNotes;
    }
}
