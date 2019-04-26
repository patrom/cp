package cp.generator.pitchclass;

import cp.model.TimeLineKey;
import cp.model.melody.CpMelody;
import cp.model.note.Note;
import cp.model.note.Scale;
import cp.util.RandomUtil;
import org.apache.commons.lang.ArrayUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class RandomAllPitchClasses {

    private static Logger LOGGER = LoggerFactory.getLogger(RandomAllPitchClasses.class);
    private int[] pitchClasses;

    public List<Note> randomPitchClasses(CpMelody melody){
        LOGGER.debug("RandomAllPitchClasses");
        melody.updateTimeLineKeysNotes();
        pitchClasses = null; //clear state!
        List<Note> notes = melody.getNotesNoRest().stream().map(note -> {
            TimeLineKey timeLineKey = note.getTimeLineKey();
            Scale scale = timeLineKey.getScale();
            if (pitchClasses == null || pitchClasses.length == 0) {
                pitchClasses = scale.getPitchClasses();
            }
            note.setPitchClass(timeLineKey.getPitchClassForKey(pickRandomPitchClass()));
            return note;
        }).collect(Collectors.toList());
        return notes;
    }

    public int pickRandomPitchClass(){
        int pc = RandomUtil.getRandomFromIntArray(pitchClasses);
        pitchClasses = ArrayUtils.removeElement(pitchClasses, pc);
        return pc;
    }
}
