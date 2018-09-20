package cp.generator.pitchclass;

import cp.composition.beat.BeatGroup;
import cp.model.TimeLineKey;
import cp.model.melody.CpMelody;
import cp.model.note.Note;
import cp.util.RandomUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class OrderRandomNotePitchClasses {
    private static Logger LOGGER = LoggerFactory.getLogger(OrderRandomNotePitchClasses.class);

    public List<Note> updatePitchClasses(CpMelody melody) {
        LOGGER.debug("OrderRandomNotePitchClasses");
        List<Note> melodyNotes = melody.getNotesNoRest();
        melody.updateTimeLineKeysNotes();
        BeatGroup beatGroup = melody.getBeatGroup();
        if (beatGroup.hasMelody() && !melodyNotes.isEmpty()) {
            Note firstNote = melodyNotes.get(0);
            TimeLineKey timeLineKey = firstNote.getTimeLineKey();
            int[] pitchClasses = timeLineKey.getScale().getPitchClasses();//TODO getbeatgroup pitchclasses
            List<Integer> pitchClassesList = Arrays.stream(pitchClasses).boxed().collect(Collectors.toList());
            for (Note note : melodyNotes) {
                if (pitchClassesList.isEmpty()) {
                    note.setPitchClass(RandomUtil.getRandomFromIntArray(pitchClasses));
                } else {
                    Integer pc = pitchClassesList.get(0);
                    note.setPitchClass((pc + timeLineKey.getKey().getInterval()) % 12);
                    pitchClassesList.remove(pc);
                }
            }
        } else {
            throw new IllegalArgumentException("OrderRandomNotePitchClasses: Can this be implemented for mutliple keys?");
        }
        return melodyNotes;
    }

}

