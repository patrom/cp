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

@Component
public class OrderNoteRepetitionPitchClasses {
    private static Logger LOGGER = LoggerFactory.getLogger(OrderNoteRepetitionPitchClasses.class);

    public List<Note> updatePitchClasses(CpMelody melody) {
        LOGGER.debug("OrderNoteRepetitionPitchClasses");
        List<Note> melodyNotes = melody.getNotesNoRest();
        melody.updateTimeLineKeysNotes();
        BeatGroup beatGroup = melody.getBeatGroup();
        if (beatGroup.hasMelody() && !melodyNotes.isEmpty()) {
            Note firstNote = melodyNotes.get(0);
            TimeLineKey timeLineKey = firstNote.getTimeLineKey();
            int[] pitchClasses = timeLineKey.getScale().getPitchClasses();//TODO getbeatgroup pitchclasses
            int i = 0;
            for (Note note : melodyNotes) {
                note.setPitchClass((pitchClasses[i]+ timeLineKey.getKey().getInterval()) % 12);
                if (RandomUtil.toggleSelection()) {//repeat
                    i = (i + 1) % pitchClasses.length;
                }
            }
        }
        return melodyNotes;
    }

}
