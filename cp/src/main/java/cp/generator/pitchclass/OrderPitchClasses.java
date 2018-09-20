package cp.generator.pitchclass;

import cp.composition.beat.BeatGroup;
import cp.model.TimeLineKey;
import cp.model.melody.CpMelody;
import cp.model.melody.Tonality;
import cp.model.note.Note;
import cp.util.RandomUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class OrderPitchClasses {
    private static Logger LOGGER = LoggerFactory.getLogger(PassingPitchClasses.class);

    public List<Note> updatePitchClasses(CpMelody melody) {
        LOGGER.debug("OrderPitchClasses");
        List<Note> melodyNotes = melody.getNotesNoRest();
        melody.updateTimeLineKeysNotes();
        BeatGroup beatGroup = melody.getBeatGroup();
        if (!melodyNotes.isEmpty()) {
            if (beatGroup.hasMelody()) {
                setPitchClasses(melodyNotes, beatGroup);
            } else {
                int i = 0;
                for (Note note : melodyNotes) {
                    TimeLineKey timeLineKey = note.getTimeLineKey();
                    int[] pitchClasses = timeLineKey.getScale().getPitchClasses();
                    note.setPitchClass(timeLineKey.getPitchClassForKey(pitchClasses[i]));
                    i = (i + 1) % pitchClasses.length;//TODO pitchclass length will not be the same!!
                }
            }
        }
        return melodyNotes;
    }

    private void setPitchClasses(List<Note> notes, BeatGroup beatGroup) {
        if (beatGroup.getTonality() == Tonality.TONAL) {
            int[] indexesMotivePitchClasses = RandomUtil.getRandomFromList(beatGroup.getIndexesMotivePitchClasses());
            TimeLineKey timeLineKey = RandomUtil.getRandomFromList(beatGroup.getTimeLineKeys());
            List<Integer> pitchClasses = timeLineKey.getScale().getPitchClasses(indexesMotivePitchClasses, 0, timeLineKey.getKey());

            int i = 0;
            for (Note note : notes) {
                note.setPitchClass(pitchClasses.get(i));
                i = (i + 1) % pitchClasses.size();
            }
        } else {
            int[] motivePitchClasses = RandomUtil.getRandomFromList(beatGroup.getMotivePitchClasses()).getPitchClasses();
            int i = 0;
            for (Note note : notes) {
                note.setPitchClass(motivePitchClasses[i] % 12);
                i = (i + 1) % motivePitchClasses.length;
            }
        }
    }

}

